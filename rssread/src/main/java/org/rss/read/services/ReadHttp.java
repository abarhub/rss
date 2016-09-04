package org.rss.read.services;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
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
					PushbackInputStream in3=new PushbackInputStream(in,1024);
					InputStreamReader in2;
					char buf[];
					int len;
					if(encoding==null) {
						String encoding2=determinationEncoding(in3);
						if(!StringUtils.isEmpty(encoding2)){
							encoding=encoding2;
							LOGGER.info("encoding2 : "+encoding);
						}
					}
					if(encoding==null) {
						encoding="UTF-8";
					}
					LOGGER.info("encoding3 : "+encoding);
					in2=new InputStreamReader(in3,encoding);
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

	private String determinationEncoding(PushbackInputStream in3) throws IOException {
		String encoding=null;
		byte buf[]=new byte[1024];
		int len;
		len=in3.read(buf);
		if(len>0){
			String s=new String(buf,0,len, Charsets.UTF_8);
			final String chaine="encoding=\"";
			if(s.contains(chaine)){
				int pos=s.indexOf(chaine);
				if(pos>=0){
					String s2=s.substring(pos+chaine.length());
					pos=s2.indexOf("\"");
					if(pos>0){
						encoding=s2.substring(0,pos);
					}
				}
			}
			in3.unread(buf,0,len);
		}
		return encoding;
	}

	private String getEncoding(HttpResponse response) {
		String res,s;
		//res="UTF-8";
		res=null;
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
