package com.smartinc.EmployeeAPI_QA.POSTP1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.gson.JsonObject;
import com.smartinc.APIFunctions.Headers;
import com.smartinc.APIFunctions.RequestGet;
import com.smartinc.APIFunctions.RequestPost;
import com.smartinc.APIFunctions.Response;
import com.smartinc.Utilities.Assertions;
import com.smartinc.Utilities.HTMLReport;
import com.smartinc.Utilities.ProcessPayload;
import com.smartinc.Utilities.ReadExcel;


public class POST_200_Test {

	ExtentReports oExtent;
	ExtentTest oTest;
	SoftAssert softAssert;
	String baseUrl;
	String endpointVariable;
	String path;
	ReadExcel readExcel;
	ProcessPayload processPayload;


	public POST_200_Test() {
		oExtent = HTMLReport.createExtentFreshReport("DEV");
		baseUrl = "http://dummy.restapiexample.com/api/";
		endpointVariable = "v1";
		path = "/create";
		readExcel = new ReadExcel("src/test/resources/TestData.xlsx");
		processPayload = new ProcessPayload();
	}

	@Test
	public void run200PostTest() {

		try {
			softAssert = new SoftAssert();
			oTest  = HTMLReport.createTestCase("Post an Employee /create", "This Scenario is to test Post Employee /create");
			String url = baseUrl + endpointVariable + path;
			String payload = readExcel.getCellData("Payloads", "Payload", 1);
			System.out.println(payload);
			List<Object[]> inputData = readExcel.getInputData("InputData");
			Iterator<Object[]> row= inputData.iterator();
			int i=1;
			while(row.hasNext()){
				String json = processPayload.getPostPayload(payload, row.next());
				System.out.println(json);
				RequestPost request = new RequestPost();
				Headers headers = new Headers();
				headers.addHeader("Content-Type", "application/json");
				headers.addHeader("Accept", "application/json");
				oTest.log(Status.INFO, "Testing RowID " + i++);
				oTest.log(Status.INFO, "Hitting Endpoint URL " + url);
				Response response = request.submitPost(url, json, headers);
				HTMLReport.writeRequestResponse(oTest, url, response, headers, json, response.getStatusLine(), "Post Response Request", softAssert);
				Assertions assertions = new Assertions(response.getHttpResponse());
				System.out.println("Response Code : " + response.getStatusCode());
				System.out.println("Response StatusLine : " + response.getStatusLine());
				Boolean result = assertions.isEqual(oTest, response.getStatusCode(), 200, "Validate Response Code", softAssert);
				result = assertions.isEqual(oTest,response.getStatusLine(), "HTTP/1.1 200 OK", "Validate Response Message",softAssert);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		finally{
			oExtent.flush();
			softAssert.assertAll();
		}

	}

}
