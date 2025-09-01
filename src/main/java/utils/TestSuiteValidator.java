package utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * JUnit 5 extension to validate test suites
 */
public class TestSuiteValidator implements TestWatcher {
    
    private static List<String> executedTests = new ArrayList<>();
    private static Properties expectedTests = new Properties();
    private static boolean initialized = false;
    
    /**
     * Initializes the validator with expected tests
     */
    private static void initialize() {
        if (!initialized) {
            try {
                // Load from classpath resource (preferred method)
                InputStream inputStream = TestSuiteValidator.class.getClassLoader()
                    .getResourceAsStream("expected_tests.properties");
                
                if (inputStream != null) {
                    expectedTests.load(inputStream);
                    initialized = true;
                } else {
                    // Fallback to file system if not found in classpath
                    try {
                        expectedTests.load(new FileInputStream("src/test/resources/expected_tests.properties"));
                        initialized = true;
                    } catch (IOException e) {
                        System.err.println("Failed to load expected tests from file system: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to load expected tests from classpath: " + e.getMessage());
            }
            
            if (initialized) {
                System.out.println("Successfully loaded " + expectedTests.size() + " expected tests");
            } else {
                System.err.println("WARNING: Failed to initialize test validator. Test validation will be incomplete.");
            }
        }
    }
    
    @Override
    public void testSuccessful(ExtensionContext context) {
        recordTest(context);
    }
    
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        recordTest(context);
        System.err.println("Test failed: " + context.getDisplayName() + " - " + cause.getMessage());
    }
    
    /**
     * Records an executed test
     */
    private void recordTest(ExtensionContext context) {
        initialize();
        String testName = context.getTestClass().get().getSimpleName() + "." + context.getTestMethod().get().getName();
        executedTests.add(testName);
    }
    
    /**
     * Validates that all expected tests were executed
     * 
     * @return true if all expected tests were executed, false otherwise
     */
    public static boolean validateAllTestsExecuted() {
        initialize();
        
        boolean allTestsExecuted = true;
        
        // Check if all expected tests were executed
        for (String expectedTest : expectedTests.stringPropertyNames()) {
            if (!executedTests.contains(expectedTest)) {
                System.err.println("Expected test not executed: " + expectedTest);
                allTestsExecuted = false;
            }
        }
        
        // Check if any unexpected tests were executed
        for (String executedTest : executedTests) {
            if (!expectedTests.containsKey(executedTest)) {
                System.err.println("Unexpected test executed: " + executedTest);
                // Not failing validation for extra tests
            }
        }
        
        return allTestsExecuted;
    }
    
    /**
     * Gets the list of executed tests
     * 
     * @return the list of executed tests
     */
    public static List<String> getExecutedTests() {
        return executedTests;
    }
    
    /**
     * Gets the count of executed tests
     * 
     * @return the count of executed tests
     */
    public static int getExecutedTestCount() {
        return executedTests.size();
    }
    
    /**
     * Gets the count of expected tests
     * 
     * @return the count of expected tests
     */
    public static int getExpectedTestCount() {
        initialize();
        return expectedTests.size();
    }
}
