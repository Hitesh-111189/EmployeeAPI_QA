package com.smartinc.APIFunctions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

public class RequestGet extends HttpGet {
	
	private String baseURL;
    private ContentType contentType;

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public RequestGet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestGet(String uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	public RequestGet(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}
	
	public void setURL(String uri) throws URISyntaxException{
		setURI(new URI(uri));
	}
	
	 public ContentType getContentType() {
	        return contentType;
	    }
	
	public Response submitGet(String uri, String postBody, Headers headers) throws IOException, URISyntaxException {
		
		setURI(new URI(uri));
		for (Header header : headers.getAllHeaders()) {
		    addHeader(header.getName(), header.getValue());
	    }
        HttpClient client = HttpClientBuilder.create().disableRedirectHandling().build();
        return new Response(client.execute(this));
    }

}
