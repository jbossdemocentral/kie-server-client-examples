package com.redhat.demos.dm.insurance_pricing_dmn;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.credentials.EnteredCredentialsProvider;


/**
 * KIE-Server client example for the Insurance Pricing DMN demo that can be found here: https://github.com/jbossdemocentral/rhdm7-insurance-pricing-dmn-demo
 *
 * Shows how the KIE-Server client API can be used to execute DMN models deployed on the execution server.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class Main {
	
	private static final String KIE_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";
	
	private static final String USERNAME = "kieserver";

	private static final String PASSWORD = "kieserver1!";
	
	private static final String DMN_MODEL_NAMESPACE = "http://www.trisotech.com/definitions/_bb8b9304-b29f-462e-9f88-03d0d868aec5";
	
	private static final String DMN_MODEL_NAME = "Insurance Pricing";
	
	private static final String CONTAINER_ID = "insurance-pricing-dmn_1.0.0";
	
	
	public static void main(String[] args) {
		
		CredentialsProvider credentialsProvider = new EnteredCredentialsProvider(USERNAME, PASSWORD);
		
		KieServicesConfiguration kieServicesConfig = KieServicesFactory.newRestConfiguration(KIE_SERVER_URL, credentialsProvider);
		
		KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);
		
		// Get the DMN Services Client.
		DMNServicesClient dmnServicesClient = kieServicesClient.getServicesClient(DMNServicesClient.class);
		
		/*
		 *  Create a DMNContext and set the "Age" and "had previous incidents"
		 *  The "Age" should be set to 23, "had previous incidents" should be set to false.
		 */
		DMNContext dmnContext = dmnServicesClient.newContext();
		dmnContext.set("Age", 23);
		dmnContext.set("had previous incidents", false);
		
		// Retrieve the DMNResult by evaluating the DMN Model.
		ServiceResponse<DMNResult> dmnResultResponse = dmnServicesClient.evaluateAll(CONTAINER_ID, DMN_MODEL_NAMESPACE, DMN_MODEL_NAME, dmnContext);
		
		// Retrieve the DMNDecisionResult "Insurance Total Price"
		DMNDecisionResult decisionResult = dmnResultResponse.getResult().getDecisionResultByName("Insurance Total Price");
		
		// Print the result:
		System.out.println("Insurance Total Price: " + decisionResult.getResult());
	}
	
}
