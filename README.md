# Insider Test Automation Framework

This project is a **TestNG-based automation framework** built using **Java**, **Maven**, **Selenium**, **RESTAssured**, and **Extent Reports**.  
It supports both **UI** and **API** testing, with configurable suites and environment parameters.

---

## Prerequisites

Before running the tests, ensure the following tools are installed and properly configured on your system:

#### 1️. Java Development Kit (JDK)
- Install **Java 24**.  
- Verify installation:
  ```bash
  java -version

#### 2. Eclipse IDE
- Download from https://eclipse.org/downloads/
- Import this project as a Maven Project.

#### 3. TestNG Plugin for Eclipse
- Go to: Help → Eclipse Marketplace
- Search for TestNG and install it.
- Restart Eclipse.

#### 4.  Apache Maven (for test execution)
- Download Maven from https://maven.apache.org/download.cgi
- Extract it to a folder, e.g.: C:\Program Files\Apache\maven\apache-maven-3.9.9
- Add the bin directory to your system PATH


## How to Execute Tests
#### Option 1: Run from Command Line
- Open Command Prompt or PowerShell.
- Navigate to the project root directory:
- Execute the following command: **mvn clean test -DsuiteXmlFile="testsuite.xml"**

#### Option 2: Run from Eclipse
- Open Project using Eclipse
- Right-click "testsuite.xml" file and select Run As → TestNG Suite

## View Executio Report
After execution, an HTML report is automatically generated under the project root directory as **ExtentReport.html**