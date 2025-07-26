# 🐾 Petstore API Test Automation

Automated API test suite for the [Swagger Petstore](https://petstore.swagger.io/) application using **Java**, **RestAssured**, **TestNG**, and **ExtentReports**.  
This project covers CRUD operations for **User** and **Pet** endpoints, using dynamic test data and clean request modeling.  
It is designed to demonstrate **professional-level framework design**, **data-driven testing**, and **CI integration via Jenkins**.

---

## 🧰 Tech Stack
| Tool / Library | Purpose                                      |
| -------------- | -------------------------------------------- |
| Java 21        | Programming language                         |
| RestAssured    | API testing framework                        |
| TestNG         | Test runner and assertions                   |
| Apache POI     | Excel file interaction (Data-Driven Testing) |
| ExtentReports  | Rich test reporting                          |
| Lombok         | Simplifies POJO creation                     |
| Log4j2         | Logging framework                            |
| Faker          | Randomized test data generation              |
| Maven          | Build and dependency management              |
| Jenkins        | CI/CD integration                            |

---

## 📁 Project Structure
~~~
petstore-api-tests/
├── src
│   └── test
│       ├── java
│       │   ├── endpoints/              # API call logic (UserEndPoints.java, PetEndPoints.java)
│       │   ├── payload/                # POJO classes with Lombok for request/response mapping
│       │   ├── tests/                  # TestNG test classes
│       │   └── utilities/              # DataProviders, Helper with retry logic, XLUtility, ConfigReader, ExtentReportsManager
│       └── resources
│           ├── log4j2.xml              # Logging configuration
│           └── petstore.properties     # Base URL and other configs
├── testData/
│   └── *.xlsx                          # Excel files for User and Pet test data
├── reports/
│   └── Test-Report.html                # ExtentReports output
├── logs/
│   └── automation.log                  # Execution logs
├── testng.xml                          # TestNG suite configuration
└── pom.xml                             # Maven dependencies and plugins
~~~
---

## ✅ Test Coverage
- **User API & Pet API**:
  - Create, Read, Update, Delete
  - Data-driven from Excel
  - Retry logic for flaky endpoints
    
---

## 📸 Screenshots
<img width="1889" height="914" alt="image" src="https://github.com/user-attachments/assets/3c3cdc04-d5a3-40b2-8705-69e15edc3a2f" />
<img width="1908" height="894" alt="image" src="https://github.com/user-attachments/assets/952d2750-bfae-4c95-98cd-7f0646e89697" />

---

### 🧱 Framework Highlights

- **Modular Architecture**  
  Clear separation of concerns with dedicated packages for endpoints, payloads, utilities, and tests — improving readability and scalability.

- **Data-Driven Testing with Excel**  
  User and Pet test cases are powered by dynamic data from `.xlsx` files using **Apache POI**, enabling easy test coverage expansion without changing code.

- **Reusable API Layer**  
  Centralized request logic in `UserEndPoints` and `PetEndPoints` with consistent request/response handling using **RestAssured**.

- **POJO Mapping with Lombok**  
  Simplified request and response object modeling via Lombok annotations like `@Data`, `@Builder`, and `@AllArgsConstructor`.

- **Robust Logging**  
  Integrated **Log4j2** logging for both framework activity and test execution. Logs are stored in the `/logs` folder for debugging and traceability.

- **Retry Logic for Flaky Tests**  
  Custom retry logic is implemented inside the `Helper` utility class. It intelligently re-invokes failed tests to reduce false negatives caused by temporary API glitches or timing issues.

- **Custom HTML Reporting**  
  Detailed **ExtentReports** include test metadata, status coloring, and logs — generated after each test run and stored in the `/reports` folder.

- **Configurable Test Runs**  
  External configuration via `petstore.properties` allows easy switching of environments (e.g., different base URLs) without modifying the codebase.

- **CI-Ready with Jenkins**  
  Fully compatible with Jenkins. ExtentReports are published automatically using the *Publish HTML Reports* plugin for easy result sharing.
  
---

## 🚀 Running the Tests

**✅ Locally**   
_git clone https://github.com/aiymaoroz/petstore-api-tests.git  
cd petstore-api-tests  
mvn clean test_   

**✅ Jenkins Integration**

- The Jenkins job is configured to run tests via _mvn clean test_
- ExtentReports are published using Publish HTML Reports 
Make sure the following steps are part of the Jenkins configuration:
- GitHub repo connected in Jenkins job
- Maven installed and configured in Jenkins
- Post-build action → Publish HTML Reports
  
---

## 📄 Logs
  - All execution logs are saved in logs/automation.log
  - Configured with log4j2.xml
---

## 📘 Test Data
User tests are driven by Excel files located in testData/ folder.

---

## Author
**Aiyma (Amy) Orozobaeva**  
QA Automation Engineer  
📧 aiymaoroz@gmail.com  
🔗 [LinkedIn](https://linkedin.com/in/aiymaoroz) | [GitHub](https://github.com/aiymaoroz)
