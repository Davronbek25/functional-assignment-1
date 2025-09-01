package utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Utility class to compile all test suites and validate their completeness
 */
public class TestSuiteCompiler {
    
    // Fixed paths relative to project root
    private static final String TEST_DIR = "src/test/java";
    private static final String REPORT_FILE = "docs/test_suite_report.md";
    
    /**
     * Main method to compile and validate all test suites
     */
    public static void main(String[] args) {
        try {
            // Collect all test files
            List<String> testFiles = collectTestFiles();
            
            // Count tests per application
            int expresscartTests = countTests(testFiles, "expresscart");
            int joomlaTests = countTests(testFiles, "joomla");
            int kanboardTests = countTests(testFiles, "kanboard");
            int mediawikiTests = countTests(testFiles, "mediawiki");
            
            // Validate test counts
            boolean isValid = validateTestCounts(expresscartTests, joomlaTests, kanboardTests, mediawikiTests);
            
            // Generate report
            generateReport(testFiles, expresscartTests, joomlaTests, kanboardTests, mediawikiTests);
            
            // Print summary
            System.out.println("\nTest Suite Compilation Summary:");
            System.out.println("ExpressCart: " + expresscartTests + " tests");
            System.out.println("Joomla: " + joomlaTests + " tests");
            System.out.println("Kanboard: " + kanboardTests + " tests");
            System.out.println("MediaWiki: " + mediawikiTests + " tests");
            System.out.println("Total: " + (expresscartTests + joomlaTests + kanboardTests + mediawikiTests) + " tests");
            System.out.println("Validation: " + (isValid ? "PASSED" : "FAILED"));
            
        } catch (IOException e) {
            System.err.println("Error compiling test suites: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Collects all test files in the project
     * 
     * @return list of test file paths
     * @throws IOException if an I/O error occurs
     */
    private static List<String> collectTestFiles() throws IOException {
        List<String> testFiles = new ArrayList<>();
        
        // Get the project root directory
        File projectRoot = new File("").getAbsoluteFile();
        File testDir = new File(projectRoot, TEST_DIR);
        
        if (!testDir.exists()) {
            System.err.println("Test directory not found: " + testDir.getAbsolutePath());
            System.err.println("Current directory: " + projectRoot.getAbsolutePath());
            System.err.println("Looking for test directory in parent...");
            
            // Try to find the test directory in parent directories
            File parent = projectRoot.getParentFile();
            if (parent != null) {
                testDir = new File(parent, TEST_DIR);
                if (!testDir.exists()) {
                    throw new IOException("Test directory not found: " + testDir.getAbsolutePath());
                } else {
                    System.out.println("Found test directory: " + testDir.getAbsolutePath());
                }
            }
        }
        
        // Walk the test directory and collect all Java files
        try (Stream<java.nio.file.Path> paths = Files.walk(testDir.toPath())) {
            testFiles = paths
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .map(path -> path.toString())
                .collect(Collectors.toList());
        }
        
        return testFiles;
    }
    
    /**
     * Counts the number of tests for a specific application
     * 
     * @param testFiles list of test file paths
     * @param appName name of the application
     * @return number of tests for the application
     */
    private static int countTests(List<String> testFiles, String appName) {
        return (int) testFiles.stream()
            .filter(file -> file.contains(File.separator + appName + File.separator))
            .count();
    }
    
    /**
     * Validates that each application has the required number of tests
     * 
     * @param expresscartTests number of ExpressCart tests
     * @param joomlaTests number of Joomla tests
     * @param kanboardTests number of Kanboard tests
     * @param mediawikiTests number of MediaWiki tests
     * @return true if all applications have the required number of tests, false otherwise
     */
    private static boolean validateTestCounts(int expresscartTests, int joomlaTests, int kanboardTests, int mediawikiTests) {
        final int REQUIRED_TESTS_PER_APP = 10;
        
        boolean isValid = true;
        
        if (expresscartTests != REQUIRED_TESTS_PER_APP) {
            System.err.println("ExpressCart has " + expresscartTests + " tests, but should have " + REQUIRED_TESTS_PER_APP);
            isValid = false;
        }
        
        if (joomlaTests != REQUIRED_TESTS_PER_APP) {
            System.err.println("Joomla has " + joomlaTests + " tests, but should have " + REQUIRED_TESTS_PER_APP);
            isValid = false;
        }
        
        if (kanboardTests != REQUIRED_TESTS_PER_APP) {
            System.err.println("Kanboard has " + kanboardTests + " tests, but should have " + REQUIRED_TESTS_PER_APP);
            isValid = false;
        }
        
        if (mediawikiTests != REQUIRED_TESTS_PER_APP) {
            System.err.println("MediaWiki has " + mediawikiTests + " tests, but should have " + REQUIRED_TESTS_PER_APP);
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Generates a report of all test suites
     * 
     * @param testFiles list of test file paths
     * @param expresscartTests number of ExpressCart tests
     * @param joomlaTests number of Joomla tests
     * @param kanboardTests number of Kanboard tests
     * @param mediawikiTests number of MediaWiki tests
     * @throws IOException if an I/O error occurs
     */
    private static void generateReport(List<String> testFiles, int expresscartTests, int joomlaTests, int kanboardTests, int mediawikiTests) throws IOException {
        StringBuilder report = new StringBuilder();
        
        report.append("# Test Suite Compilation Report\n\n");
        
        report.append("## Summary\n\n");
        report.append("| Application | Development Approach | Test Count | Status |\n");
        report.append("|------------|----------------------|------------|--------|\n");
        report.append("| ExpressCart | Manual               | ").append(expresscartTests).append("          | ")
              .append(expresscartTests == 10 ? "✅ Complete" : "❌ Incomplete").append(" |\n");
        report.append("| Joomla      | Manual               | ").append(joomlaTests).append("          | ")
              .append(joomlaTests == 10 ? "✅ Complete" : "❌ Incomplete").append(" |\n");
        report.append("| Kanboard    | AI-Assisted          | ").append(kanboardTests).append("          | ")
              .append(kanboardTests == 10 ? "✅ Complete" : "❌ Incomplete").append(" |\n");
        report.append("| MediaWiki   | AI-Assisted          | ").append(mediawikiTests).append("          | ")
              .append(mediawikiTests == 10 ? "✅ Complete" : "❌ Incomplete").append(" |\n");
        report.append("| **Total**   |                      | ").append(expresscartTests + joomlaTests + kanboardTests + mediawikiTests)
              .append("          | ").append((expresscartTests + joomlaTests + kanboardTests + mediawikiTests) == 40 ? "✅ Complete" : "❌ Incomplete").append(" |\n\n");
        
        report.append("## Test Files\n\n");
        
        // ExpressCart
        report.append("### ExpressCart (Manual)\n\n");
        List<String> expresscartFiles = testFiles.stream()
            .filter(file -> file.contains(File.separator + "expresscart" + File.separator))
            .collect(Collectors.toList());
        for (String file : expresscartFiles) {
            report.append("- ").append(new File(file).getName()).append("\n");
        }
        report.append("\n");
        
        // Joomla
        report.append("### Joomla (Manual)\n\n");
        List<String> joomlaFiles = testFiles.stream()
            .filter(file -> file.contains(File.separator + "joomla" + File.separator))
            .collect(Collectors.toList());
        for (String file : joomlaFiles) {
            report.append("- ").append(new File(file).getName()).append("\n");
        }
        report.append("\n");
        
        // Kanboard
        report.append("### Kanboard (AI-Assisted)\n\n");
        List<String> kanboardFiles = testFiles.stream()
            .filter(file -> file.contains(File.separator + "kanboard" + File.separator))
            .collect(Collectors.toList());
        for (String file : kanboardFiles) {
            report.append("- ").append(new File(file).getName()).append("\n");
        }
        report.append("\n");
        
        // MediaWiki
        report.append("### MediaWiki (AI-Assisted)\n\n");
        List<String> mediawikiFiles = testFiles.stream()
            .filter(file -> file.contains(File.separator + "mediawiki" + File.separator))
            .collect(Collectors.toList());
        for (String file : mediawikiFiles) {
            report.append("- ").append(new File(file).getName()).append("\n");
        }
        report.append("\n");
        
        // Get the project root directory
        File projectRoot = new File("").getAbsoluteFile();
        File reportFile = new File(projectRoot, REPORT_FILE);
        
        // Create parent directories if they don't exist
        reportFile.getParentFile().mkdirs();
        
        // Write report to file
        try (FileWriter writer = new FileWriter(reportFile)) {
            writer.write(report.toString());
        }
        
        System.out.println("Test suite report generated at: " + reportFile.getAbsolutePath());
    }
}
