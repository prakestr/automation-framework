# E-Commerce Test Automation Suite

## Description
This suite automates web UI tests for an e-commerce application. Designed to validate critical workflows such as product selection, cart management, and checkout process, it ensures a seamless shopping experience for users.

## Technologies Used
- **Java**: Core programming language for test scripts.
- **Selenium WebDriver**: Used for browser interaction and execution of test cases.
- **Cucumber**: BDD framework for readable test scenarios.
- **TestNG**: Testing framework serving as the backbone for running tests with annotations.
- **Allure**: Integrates with TestNG to provide detailed test execution reports.

## Installation
1. Clone the repository to your local machine.
2. Ensure Java is installed and configured on your system.
3. Install Maven for dependency management and build execution.

## Configuration
Configure the `testng.xml` file to include or exclude specific test groups. Set browser preferences and test environment details in the `config.properties` file.

## Running Tests
Execute the following command in the terminal:
```bash
mvn clean test
