package com.smartinc.EmployeeAPI_QA.DELETED1;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.smartinc.APIFunctions.Headers;
import com.smartinc.APIFunctions.RequestDelete;
import com.smartinc.APIFunctions.Response;
import com.smartinc.Utilities.Assertions;
import com.smartinc.Utilities.GeneralPost;
import com.smartinc.Utilities.HTMLReport;
import com.smartinc.Utilities.ReadExcel;


public class Delete_200_Test {
	
	ExtentReports oExtent;
	ExtentTest oTest;
	SoftAssert softAssert;
	String baseUrl;
	String endpointVariable;
	String path;
	ReadExcel readExcel;
	
	public Delete_200_Test() {
		oExtent = HTMLReport.createExtentFreshReport("DEV");
		baseUrl = "http://dummy.restapiexample.com/api/";
		endpointVariable = "v1";
		readExcel = new ReadExcel("src/test/resources/TestData.xlsx");
	}
	
	
	@Test
	public void run200DeleteTest(){
		try {
			softAssert = new SoftAssert();
			oTest  = oExtent.createTest("Delete Employee by /delete/id", "This Scenario is to test Delete employee by /employees");
			String postPayload = readExcel.getCellData("Payloads", "Payload", 2);
			System.out.println(postPayload);
			GeneralPost gp = new GeneralPost();
			int id = gp.generalPost(postPayload);
			path = "/delete/" + id;
			String url = baseUrl + endpointVariable + path;
			RequestDelete request = new RequestDelete();
			Headers headers = new Headers();
			headers.addHeader("Content-Type", "application/json");
			headers.addHeader("Accept", "application/json");
			oTest.log(Status.INFO, "Hitting Endpoint URL " + url);
			Response response = request.submitDelete(url, "", headers);
			HTMLReport.writeRequestResponse(oTest, url, response, headers, "", response.getStatusLine(), "Delete Response Request", softAssert);
			Assertions assertions = new Assertions(response.getHttpResponse());
			System.out.println("Response Code : " + response.getStatusCode());
			Boolean result = assertions.isEqual(oTest, response.getStatusCode(), 200, "Validate Response Code", softAssert);
			System.out.println("Response StatusLine : " + response.getStatusLine());
			result = assertions.isEqual(oTest,response.getStatusLine(), "HTTP/1.1 200 OK", "Validate Response Message",softAssert);

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
