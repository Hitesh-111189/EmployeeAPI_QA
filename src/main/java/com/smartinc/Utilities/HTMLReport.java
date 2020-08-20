package com.smartinc.Utilities;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.junit.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.smartinc.APIFunctions.Headers;
import com.smartinc.APIFunctions.Response;


public class HTMLReport {
	
	private static HTMLReport htmlReport = null; 
	private static ExtentHtmlReporter reporter = null;
	private static ExtentReports oExtent = null;
	private static ExtentTest oTest = null;
	private static String projectPath = System.getProperty("user.dir"); 
	private static String resultDirName = "results";
	private static String requestResponseLogsFolderName = "RequestResponseLogs";
	private static String htmlFileName = "ExecutionReport.html";
	private boolean result;
	    
	
	private HTMLReport(){
		
	}
	
	public static HTMLReport getInstance() 
    { 
        if (htmlReport == null) 
        	htmlReport = new HTMLReport(); 
  
        return htmlReport; 
    } 
	
	public static void createFolder( String folderName)
    {
        try
        {
            String sResultFolder = projectPath + "/" + folderName;
            File oCreateResultFolder = new File(sResultFolder);
            if( !oCreateResultFolder.exists() )
            {
                oCreateResultFolder.mkdirs();
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        } 
    }
   
	
    public static void cleanUPFolder( String folderName )
    {
        try
        {
            File fileResult = new File(projectPath + "/" + folderName );
            for(File file: fileResult.listFiles()) 
            if (!file.isDirectory()) 
                file.delete();
        }
        catch (Exception e)
        {
            e.getMessage();
        }        
    }

	
	public static ExtentReports createExtentFreshReport(String environment)
    {
        try
        {
            if(oExtent == null){
            	 createFolder( resultDirName );
                 cleanUPFolder( resultDirName );            
                 createFolder( resultDirName + "/" + requestResponseLogsFolderName );
                 cleanUPFolder( resultDirName + "/" + requestResponseLogsFolderName );
                 reporter = new ExtentHtmlReporter(projectPath + "/" + resultDirName + "/" + htmlFileName);
                 oExtent = new ExtentReports();
                 oExtent.attachReporter(reporter);
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
        return oExtent;
    }
	
	public static ExtentTest createTestCase (String testName, String description)
    {
		oTest = oExtent.createTest(testName, description);
		return oTest;
    }
	
	public static ExtentTest getExtentTest(){
		return oTest;
	}
	
	public static void writeRequestResponse( ExtentTest oTest, String endpoint, Response response, Headers headers, String request, String statusLine, String stepName, SoftAssert softAssert ){
		JsonObject responseBody;
        try{
            String jsonResponseString;
            responseBody  = response.getJSONObject();
            if (!(responseBody==null)){
                jsonResponseString = responseBody.toString();
            }else{
                jsonResponseString = "";
            }
            String reqFilePath = writeRequestFile( endpoint, headers, request, stepName + "_Request");
            String resFilePath = writeResponseFile( statusLine, response.getResponseHeaders(), jsonResponseString, stepName + "_Response");
            oTest.log(Status.INFO, stepName + "\n"+"<a href= " + "\"" + reqFilePath + "\"" + ">Click here to see the Request.</a>"+ "\n"+"<a href= \"" + resFilePath + "\">Click here to see the Reponse.</a>");
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        } 
    }
	
	 public static String writeRequestFile(String endpoint, Headers headers, String payload, String filename) {
	        String headerValue = "";
	        String fullRequest = "";
	        String filePath = null;
	        try{            
	            for (Header header : headers.getAllHeaders()) {
	                    headerValue = headerValue + header.getName() + ": " + header.getValue() + "\r\n";
	            }
	            fullRequest = fullRequest + endpoint + "\r\n" + headerValue; 
	            filename = filename.replaceAll(" ", "_");
	            filePath = writeJSONFile(fullRequest, payload, filename);            
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	            Assert.assertTrue(false);
	        }
	        return filePath;
	    }
	 
	 public static String writeResponseFile(String statusLine, String headers, String payload,  String filename) {
	        String fullRequest = "";
	        String filePath = null;
	        try{            
	            fullRequest = fullRequest + statusLine + "\r\n" + headers; 
	            filename = filename.replaceAll(" ", "_");
	            filePath = writeJSONFile(fullRequest, payload, filename);
	        }        
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	            Assert.assertTrue(false);
	        }
	        return filePath;
	    }
	 
	 public static String writeJSONFile(String rawContent, String json, String fileName)
	 {
		 String prettyJson;
		 String filePath = null;
		 try
		 {

			 if (!json.equals(""))
				 prettyJson = rawContent + "\r\n" + printPrettyJsonFromString( json );
			 else
				 prettyJson = rawContent;

			 Date oDate = new Date();
			 SimpleDateFormat formatter =  new SimpleDateFormat( "yyyy-MM-dd'T'HHmmss.SSS" );
			 String timestamp = formatter.format( oDate );
			 filePath = "" + projectPath + "/" + resultDirName + "/" + requestResponseLogsFolderName + "/" + fileName + "_" + timestamp + ".txt";
			 File oResponseInputFile = new File( filePath );
			 try ( FileOutputStream fileOutputStream = new FileOutputStream( oResponseInputFile, true ); 
					 PrintWriter printWriter = new PrintWriter( fileOutputStream )) 
			 {
				 printWriter.write( prettyJson );
			 }

		 }
		 catch (IOException e)
		 {
			 e.getMessage();
		 }
		 return filePath;
	 }
	 
	 public  static String printPrettyJsonFromString( String json )
	    {
	        String prettyJson = null;
	        try{
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        JsonParser jp = new JsonParser();
	        JsonElement je = jp.parse(json);
	        prettyJson = gson.toJson(je);
	        return prettyJson;
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        return prettyJson;
	    }
}
