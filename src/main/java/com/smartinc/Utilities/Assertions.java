package com.smartinc.Utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;



public class Assertions {
	private HttpResponse response;
	private boolean result;
	public Assertions(HttpResponse response) {
		super();
		this.response = response;
	}
	
	public boolean isEqual(ExtentTest oTest,String actual, String expected, String stepName, SoftAssert softAssert){
		result = (actual.equals(expected));
		if (result) {
			softAssert.assertTrue(result);
			oTest.log(Status.PASS, stepName + " Actual : " + actual + " Expected : " + expected);
		} else {
			softAssert.assertTrue(result);
			oTest.log(Status.FAIL, stepName + " Actual : " + actual + " Expected : " + expected);
		}
		return result;
	}
	
	public boolean isEqual(ExtentTest oTest, int actual, int expected, String stepName,
			SoftAssert softAssert) {

		result = (actual == expected);
		if (result) {
			softAssert.assertTrue(result);
			oTest.log(Status.PASS, stepName + " Actual : " + actual + " Expected : " + expected);
		} else {
			softAssert.assertTrue(result);
			oTest.log(Status.FAIL, stepName + " Actual : " + actual + " Expected : " + expected);
		}
		return result;
	}
	
	/*public void validateMultipleHeaders(HashMap<String,String> headers, SoftAssert softAssert){
		
		try{
			Set<Map.Entry<String, String>> set = headers.entrySet();
			Iterator<Map.Entry<String, String>> it = set.iterator();
			while(it.hasNext()){
				result = false;
				Map.Entry<String, String> me = it.next();
				for(Header header : response.getAllHeaders()){
					if(me.getKey().equals(header.getName())){
						System.out.println("PASS : Header " + me.getKey() + " exists in the response.");
                        result = isEqual(header.getValue(), me.getValue(), softAssert);
                        softAssert.assertTrue(result);
					}
				}
				if(result == false){
					System.out.println("FAIL : Header " + me.getKey() + " does not exist in the response.");
                    softAssert.assertTrue(result);
				}
			}
		}
		catch(Exception e){
			
		}
		
	}*/
}
