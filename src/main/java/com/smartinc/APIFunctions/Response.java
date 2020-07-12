package com.smartinc.APIFunctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;


import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class Response {
	private HttpResponse response;

	public Response(HttpResponse response) {
		//super();
		this.response = response;
		
	}

	public HttpResponse getHttpResponse() {
		return response;
	}

	public void setHttpResponse(HttpResponse response) {
		this.response = response;
	}
	
	public String getStatusLine(){
		return response.getStatusLine().toString();
	}
	
	public int getStatusCode(){
		return response.getStatusLine().getStatusCode();
	}
	
	public Header getResponseHeader(String name){
		return response.getFirstHeader(name);
	}
	
	public JsonObject getJSONObject() {
		JsonObject jsonObject= null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder jsonS = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonS.append(line + "\n");
			}
			System.out.println(jsonS.toString());
			Gson gson = new Gson();
			jsonObject = gson.fromJson(jsonS.toString(), JsonObject.class);
			
			return jsonObject;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return jsonObject;
	}
	    
	
	
	public String getResponseHeaders(){
		String retValue = "";
		for (Header header : getHttpResponse().getAllHeaders()) {
			retValue = retValue + header.toString() + "\r\n";
		}
		return retValue;
	}
}
