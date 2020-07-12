package com.smartinc.APIFunctions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



public class RequestPost extends HttpPost{
	
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

	public RequestPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestPost(String uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	public RequestPost(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}
	
	public void setURL(String uri) throws URISyntaxException{
		setURI(new URI(uri));
	}
	
	 public ContentType getContentType() {
	        return contentType;
	    }
	
	public Response submitPost(String uri, String postBody, Headers headers){
		Response response = null;
		try{
		setURI(new URI(uri));
		for (Header header : headers.getAllHeaders()) {
		    addHeader(header.getName(), header.getValue());
	    }
        HttpClient client = HttpClientBuilder.create().disableRedirectHandling().build();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(postBody);
        String prettyJson = gson.toJson(je);
        setEntity(new StringEntity(prettyJson));
        return new Response(client.execute(this));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return response;
    }

}
