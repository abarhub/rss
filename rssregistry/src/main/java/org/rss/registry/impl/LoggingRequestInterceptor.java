package org.rss.registry.impl;

import com.google.common.base.Preconditions;
import org.rss.beans.OutilsGeneriques;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Alain on 13/02/2016.
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

	private final String nomServeur;
	private final String nomUrl;

	public LoggingRequestInterceptor(String nomServeur,String nomUrl) {
		Preconditions.checkNotNull(nomServeur);
		Preconditions.checkState(!nomServeur.isEmpty());
		Preconditions.checkNotNull(nomUrl);
		Preconditions.checkState(!nomUrl.isEmpty());
		this.nomServeur=nomServeur;
		this.nomUrl=nomUrl;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

		ClientHttpResponse response = execution.execute(request, body);

		log(request,body,response);

		return response;
	}

	private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
		try {
			//MDC.put("server", nomServeur);
			//MDC.put("getUrl", nomUrl);
			OutilsGeneriques.addMdc("server", nomServeur);
			OutilsGeneriques.addMdc("getUrl", nomUrl);

			String str = new String(body, StandardCharsets.UTF_8);
			logger.info("appel rest : body = " + str);
			logger.info("appel rest : response status = " + response.getRawStatusCode() + ":" + response.getStatusText());
			//byte[] tab=ByteStreams.toByteArray(response.getBody());
			//String str2= new String(tab, StandardCharsets.UTF_8);
			String str2 = read(response.getBody());
			logger.info("appel rest : response = " + str2);
		}finally{
			//MDC.remove("server");
			//MDC.remove("getUrl");
			OutilsGeneriques.removeMdc("server");
			OutilsGeneriques.removeMdc("getUrl");
		}
	}

	private String read(InputStream in)
	{
		//BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		byte buf[];
		int len;

		String line;
		buf=new byte[500];
		try {

			//br = new BufferedReader(new InputStreamReader(in));
			while ((len=in.read(buf)) >0) {
				//sb.append(line);
				sb.append(new String(buf,0,len));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
}
