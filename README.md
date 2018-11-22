KIE-Server Client Examples
=============================================
This demo project provides examples on how to use the KIE-Server Java Client to:
- Start a business process
- Execute rules
- Evaluate a DMN model
on a remote KIE-Server execution server.

The code has 3 simple `Main` classes, one for each example, that can be executed to interact with a remote KIE-Server. Comments in the code explain the various APIs that are being used to successfully execute a command against KIE-Server.

The `Main` classes can be found here:  
- [Starting a process](./src/main/java/com/redhat/bpms/examples/mortgage/Main.java)
- [Executing rules](./src/main/java/com/redhat/demo/qlb/loan_application/Main.java)
- [Evaluating DMN](./src/main/java/com/redhat/demos/dm/insurance_pricing_dmn/Main.java)

All the demos that these examples use can be found on the [JBoss Demo Central](https://www.github.com/jbossdemocentral) Github repository:
- [Red Hat Process Automation Manager 7 Mortgage Demo](https://github.com/jbossdemocentral/rhpam7-mortgage-demo)
- [Red Hat Decision Manager 7 Quick Loan Bank Demo](https://github.com/jbossdemocentral/rhdm7-qlb-loan-demo)
- [Red Hat Decision Manager 7 Insurance Pricing DMN Demo](https://github.com/jbossdemocentral/rhdm7-insurance-pricing-dmn-demo)
