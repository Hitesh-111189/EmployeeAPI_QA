package com.smartinc.Utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smartinc.APIFunctions.Headers;
import com.smartinc.APIFunctions.RequestPost;
import com.smartinc.APIFunctions.Response;

public class GeneralPost {
	
	public int generalPost(ExtentTest oTest, String payload, SoftAssert softAssert){
		RequestPost request = new RequestPost();
		Headers headers = new Headers();
		headers.addHeader("Content-Type", "application/json");
		headers.addHeader("Accept", "application/json");
		headers.addHeader("Cookie","PHPSESSID=854ec07ab3f38127a34090377b840043; ezCMPCCS=true");
		String url = "http://dummy.restapiexample.com/api/v1/create";
		Response response = request.submitPost(url, payload, headers);
		HTMLReport.writeRequestResponse(oTest, url, response, headers, payload, response.getStatusLine(), "Post Response Request", softAssert);
		System.out.println(response.getResponseString());
	    JsonObject  data = response.getJSONObject().get("data").getAsJsonObject();
	    int id = data.get("id").getAsInt();
		System.out.println(id);
		return id;
	}
	
	public JsonObject getJSONObject(HttpResponse response) {
		JsonObject jsonObject= null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String jsonS = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonS += line;
			}
			Gson gson = new Gson();
			JsonElement jelement = new JsonParser().parse(jsonS);
			jsonObject = gson.fromJson(jelement, JsonObject.class);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
