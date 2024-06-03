# *Welcome on the official SEDAP-Express repository!* :+1:


## Remarks 
This repository has not yet been officially published. The ICD (Interface Description Document) is ready, but many important software parts are still missing. This repository will be completed in the next two weeks (by mid-June).

 
## Scope

SEDAP-Express is an exceptionally fast path to integrate new applications, sensors, effectors or other similar things into the ecosystem of MESE (Military Expandable Software Environment). That's why it is intentionally kept simple and offers several technical ways of communication. Of course, this results in limitations, but in most cases where quick and easy integration is required, these are negligible. If increased demands arise later on, the “bigger” SEDAP API respective MESE interface can be used if necessary.

SEDAP-Express is licensed under the “Simplified BSD License” (BSD-2-Clause). Therefore, there should be no problems using SEDAP-Express in commercial or non-commercial projects or integrating parts of the SEDAP-Express framework.


## Sub-Projects

Below you will find a brief overview and description of the sub-projects. If you have any questions or comments, please write to us here. And even better: If you find any errors, feel free to open a bug report!


### Documentation 
here to make it as easy as possible for everyone to get started.


### SEDAPExpress

Here you can find everything you need about SEDAP-Express and to understand the protocols. In the future we will also publish tutorial videos and such stuff here to make it as easy as possible for everyone to get started.

### MessageTool
The main function of that tool is to manually generate messages for test and debugging but also in operational use cases ("sneakernet"). 

### SampleTCPClient
For most cases, this sample client is the right way to get started with SEDAP Express development. TCP has some significant advantages over UDP, so if you are able to use it in your future use case, it is better to start with this one.

### SampleUDPClient
Almost the same client as the TCP variant, but for use cases in which only UDP can be used or is required for other reasons.

### SampleRESTClient
If you want to implement a REST API client for SEDAP Express, you can use this sample client as a basis. It already contains everything you need for a smooth start.

### SampleProtobugClient
The protocol buffer standard is another method for exchanging SEDAP Express messages. You can see how it works in this example client. As always, the required libraries can already be found in the libs folder of the project.

### SampleSerialClient
This is another example client that can be used as a basic structure for your new client if you need to communicate via a serial line.

### SECMockUp
This mockup simulates the real SEDAP-Express connector of the MESE framework. It answers your acknowledge request or heartbeats. You can take it also for verifing and debugging your own SEDAP-Express client software.

### C2MockUp
As a complement to SECMockUp, this mockup is intended to be a simple kind of Command&Control (C2) system simulator by providing a simple map with Mil-Std-2525c icons. The engine uses the standard NASA WorldWind framework without our multithread and other modifications.




***An NOW greetings from the North Sea and Happy Coding***


***VolkerVoß***
