package com.smartinc.Utilities;

public class ProcessPayload {

	
	public String getPostPayload(String payload, Object[] obj){
		return payload.replace("$(name)", (String)obj[0]).replace("$(salary)",(String)obj[1]).replace("$(age)",(String)obj[2]);
		
	}

}
