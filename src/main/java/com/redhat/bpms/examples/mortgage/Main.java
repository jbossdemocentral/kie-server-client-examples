package com.redhat.bpms.examples.mortgage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.credentials.EnteredCredentialsProvider;

/**
 * KIE-Server client example for the Mortgage demo that can be found here: https://github.com/jbossdemocentral/rhpam7-mortgage-demo
 *
 * Shows how the KIE-Server client API can be used to start a mortgage process.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class Main {

	private static final String KIE_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";

	private static final String USERNAME = "kieserver";

	private static final String PASSWORD = "kieserver1!";

	// We use the container 'alias' instead of container name to decouple the client from the KIE-Contianer deployments.
	private static final String CONTAINER_ID = "mortgage";

	private static final String PROCESS_ID = "com.redhat.bpms.examples.mortgage.MortgageApplication";

	public static void main(String[] args) {

		CredentialsProvider credentialsProvider = new EnteredCredentialsProvider(USERNAME, PASSWORD);

		KieServicesConfiguration kieServicesConfig = KieServicesFactory.newRestConfiguration(KIE_SERVER_URL, credentialsProvider);

		// Adding classes that our client uses to our Marshaler.
		Set<Class<?>> extraClasses = new HashSet<>();
		extraClasses.add(Application.class);
		extraClasses.add(Applicant.class);
		extraClasses.add(Property.class);
		kieServicesConfig.addExtraClasses(extraClasses);

		// Set the Marshaling Format to JSON. Other options are JAXB and XSTREAM
		kieServicesConfig.setMarshallingFormat(MarshallingFormat.JSON);

		KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);

		// Retrieve the ProcessServicesClient.
		ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);

		// Build the process start variables.
		Map<String, Object> variables = new HashMap<>();
		variables.put("application", getApplication());

		// Start the process.
		processClient.startProcess(CONTAINER_ID, PROCESS_ID, variables);

	}

	private static Application getApplication() {
		Application application = new Application();
		application.setApplicant(getApplicant());
		application.setProperty(getProperty());
		application.setDownPayment(10000);
		application.setAmortization(10);
		return application;
	}

	private static Applicant getApplicant() {
		Applicant applicant = new Applicant();
		applicant.setName("ddoyle");
		applicant.setCreditScore(450);
		applicant.setIncome(70000);
		applicant.setSsn(987654321);
		return applicant;
	}

	private static Property getProperty() {
		Property property = new Property();
		property.setAddress("Rotterdam");
		property.setPrice(240000);
		return property;
	}

}
