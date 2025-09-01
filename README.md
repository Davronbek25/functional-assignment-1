# Selenium WebDriver Test Suite Project

This project contains end-to-end test suites for four web applications (ExpressCart, Joomla, Kanboard, and MediaWiki) implemented using Selenium WebDriver with Java and JUnit 5. The test suites were developed using two contrasting approaches: manual development and AI-assisted development.

## Project Structure

- `src/main/java/`: Contains page object classes and utility classes
  - `utils/`: Utility classes for WebDriver setup, common operations, and timing measurement
  - `expresscart/pages/`: Page objects for ExpressCart application
  - `joomla/pages/`: Page objects for Joomla application
  - `kanboard/pages/`: Page objects for Kanboard application
  - `mediawiki/pages/`: Page objects for MediaWiki application

- `src/test/java/`: Contains test classes implementing the Gherkin scenarios
  - `expresscart/`: Test classes for ExpressCart (Manual development)
  - `joomla/`: Test classes for Joomla (Manual development)
  - `kanboard/`: Test classes for Kanboard (AI-Assisted development)
  - `mediawiki/`: Test classes for MediaWiki (AI-Assisted development)

- `docs/`: Contains documentation and measurement data
  - `excel/`: Excel workbook with development times for all test cases
  - `Post-Experiment_Questionnaire.pdf`: Post-experiment questionnaire

## Development Approaches

1. **Manual Development**:
   - ExpressCart and Joomla test suites
   - Developed without AI assistance
   - Each test case was written by hand

2. **AI-Assisted Development**:
   - Kanboard and MediaWiki test suites
   - Developed with GitHub Copilot assistance
   - Each test case was generated based on Gherkin scenarios

## Setup and Running Tests

### Prerequisites

- Java JDK 11 or higher
- Maven
- Chrome browser
- ChromeDriver matching your Chrome version

### Setup Instructions

1. Clone or extract this project to your local machine
2. Install Maven if not already installed
3. Download ChromeDriver that matches your Chrome browser version
4. Add ChromeDriver to your system PATH or update the WebDriverSetup class with the path to your ChromeDriver

### Running Tests

To run all tests:
```
mvn test
```

To run tests for a specific application:
```
mvn test -Dtest=expresscart.* or mvnd test -Dtest=expresscart.*Test
mvn test -Dtest=joomla.* or mvnd test -Dtest=joomla.*Test
mvn test -Dtest=kanboard.* or mvnd test -Dtest=kanboard.*Test
mvn test -Dtest=mediawiki.* or mvnd test -Dtest=mediawiki.*Test
```

To run a specific test class:
```
mvn test -Dtest=expresscart.UserManagementTest
```

### After Running Tests

To generate development times:
```
mvn exec:java -Dexec.mainClass="utils.TimingCompiler" or 5.	mvnd exec:java -Dexec.mainClass="utils.TimingCompiler"

## Deliverables

1. **Test Suites**: 40 Java test cases across four applications
2. **Excel Workbook**: Development times for all test cases (in docs/excel/)
3. **Post-Experiment Questionnaire**: Personal observations and insights

## Troubleshooting

- If you encounter ChromeDriver version mismatch errors, download the correct ChromeDriver version that matches your Chrome browser
- If you see compilation errors about missing imports, ensure you have the correct dependencies in your pom.xml and that you've imported the necessary classes
- For any other issues, check the Maven and Selenium documentation
