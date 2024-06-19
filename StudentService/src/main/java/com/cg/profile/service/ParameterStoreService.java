//package com.cg.profile.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
//import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
//import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
//
//@Service
//public class ParameterStoreService {
//	@Autowired
//	private AWSSimpleSystemsManagement ssmClient;
//
//	public String getParameterValue(String parameterName) {
//		GetParameterRequest request = new GetParameterRequest().withName(parameterName).withWithDecryption(true);
//
//		GetParameterResult result = ssmClient.getParameter(request);
//		return result.getParameter().getValue();
//	}
//}
