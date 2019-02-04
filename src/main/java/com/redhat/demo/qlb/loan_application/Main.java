package com.redhat.demo.qlb.loan_application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.credentials.EnteredCredentialsProvider;

import com.redhat.demo.qlb.loan_application.model.Applicant;
import com.redhat.demo.qlb.loan_application.model.Loan;

/**
 * KIE-Server client example for the Quick Loan Bank demo that can be found here: https://github.com/jbossdemocentral/rhdm7-qlb-loan-demo
 *
 * Shows how the KIE-Server client API can be used to send data to the rules engine in KIE-Server and retrieve the results.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class Main {

	private static final String KIE_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";

	private static final String USERNAME = "kieserver";

	private static final String PASSWORD = "kieserver1!";

	private static final String STATELESS_KIE_SESSION_ID = "default-stateless-ksession";

	// We use the container 'alias' instead of container name to decouple the client from the KIE-Contianer deployments.
	private static final String CONTAINER_ID = "loan-application";

	public static void main(String[] args) {

		KieServices kieServices = KieServices.Factory.get();

		CredentialsProvider credentialsProvider = new EnteredCredentialsProvider(USERNAME, PASSWORD);

		KieServicesConfiguration kieServicesConfig = KieServicesFactory.newRestConfiguration(KIE_SERVER_URL, credentialsProvider);

		// Set the Marshaling Format to JSON. Other options are JAXB and XSTREAM
		kieServicesConfig.setMarshallingFormat(MarshallingFormat.JSON);

		KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);

		// Retrieve the RuleServices Client.
		RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

		/*
		 * Create the list of commands that we want to fire against the rule engine. In this case we insert 2 objects, applicant and loan,
		 * and we trigger a ruleflow (with the StartProcess command).
		 */
		List<Command<?>> commands = new ArrayList<>();

		KieCommands commandFactory = kieServices.getCommands();
		//The identifiers that we provide in the insert commands can later be used to retrieve the object from the response.
		commands.add(commandFactory.newInsert(getApplicant(), "applicant"));
		commands.add(commandFactory.newInsert(getLoan(), "loan"));
		commands.add(commandFactory.newStartProcess("loan-application.loan-application-decision-flow"));

		/*
		 * The BatchExecutionCommand contains all the commands we want to execute in the rules session, as well as the identifier of the
		 * session we want to use.
		 */
		BatchExecutionCommand batchExecutionCommand = commandFactory.newBatchExecution(commands, STATELESS_KIE_SESSION_ID);

		ServiceResponse<ExecutionResults> response = rulesClient.executeCommandsWithResults(CONTAINER_ID, batchExecutionCommand);

		ExecutionResults results = response.getResult();

		//We can retrieve the objects from the response using the identifiers we specified in the Insert commands.
		Applicant resultApplicant = (Applicant) results.getValue("applicant");
		Loan resultLoan = (Loan) results.getValue("loan");

		if (resultLoan.isApproved()) {
			System.out.println("Loan has been approved.");
			System.out.println("Interest rate is: " + resultLoan.getInterestRate());
			System.out.println("Monthly repayment is: " + resultLoan.getMonthlyRepayment());
		} else {
			System.out.println("Loan has not been approved.");
		}

	}

	private static Applicant getApplicant() {
		Applicant applicant = new Applicant();
		applicant.setName("ddoyle");
		applicant.setAge(39);
		applicant.setMonthlyIncome(70000);
		applicant.setCreditScore(450);
		applicant.setYearlyIncome(70000);
		return applicant;
	}

	private static Loan getLoan() {
		Loan loan = new Loan();
		loan.setAmount(10000);
		loan.setDuration(10);
		return loan;
	}

}
