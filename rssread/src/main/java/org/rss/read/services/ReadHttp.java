package org.rss.read.services;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Alain on 18/10/2015.
 */
@Service
public class ReadHttp {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReadHttp.class);

	public ReadHttp(){

	}

	public String read(String url) throws IOException, ExecutionException, InterruptedException {
		String res,encoding;
		StringWriter out;

		res="";

		LOGGER.info("read : "+url);

		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			httpclient.start();
			HttpGet request = new HttpGet(url);
			Future<HttpResponse> future = httpclient.execute(request, null);
			HttpResponse response = future.get();
			encoding= getEncoding(response);
			LOGGER.info("encoding : "+encoding);
			out=new StringWriter();
			try(InputStream in=response.getEntity().getContent())
			{
				if(in!=null)
				{
					InputStreamReader in2;
					char buf[];
					int len;
					in2=new InputStreamReader(in,encoding);
					buf=new char[1024];
					while((len=in2.read(buf))!=-1)
					{
						out.write(buf,0,len);
					}
				}
			}
			res=out.toString();
		} finally {
			httpclient.close();
		}

		return res;
	}

	private String getEncoding(HttpResponse response) {
		String res,s;
		res="UTF-8";
		if(response!=null)
		{
			if(response.getEntity()!=null)
			{
				if(response.getEntity().getContentEncoding()!=null)
				{
					Header tmp = response.getEntity().getContentEncoding();
					s=tmp.getValue();
					if(s!=null&&s.trim().length()>0)
					{
						res=s.trim();
					}
				}
			}
		}
		return res;
	}
}
