package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility class to record development times for test cases
 */
public class TimingRecorder {
    
    private String application;
    private String testCase;
    private String approach;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long durationSeconds;
    
    /**
     * Constructor for the timing recorder
     * 
     * @param application the application being tested
     * @param testCase the test case being implemented
     * @param approach the development approach (Manual or AI-Assisted)
     */
    public TimingRecorder(String application, String testCase, String approach) {
        this.application = application;
        this.testCase = testCase;
        this.approach = approach;
    }
    
    /**
     * Starts the timer
     */
    public void startTimer() {
        this.startTime = LocalDateTime.now();
        System.out.println("Timer started for " + application + " - " + testCase + " (" + approach + ")");
    }
    
    /**
     * Stops the timer and records the development time
     */
    public void stopTimer() {
        this.endTime = LocalDateTime.now();
        this.durationSeconds = ChronoUnit.SECONDS.between(startTime, endTime);
        
        System.out.println("Timer stopped for " + application + " - " + testCase + " (" + approach + ")");
        System.out.println("Development time: " + formatDuration(durationSeconds));
        
        // Record the timing data
        recordTiming();
        
        // Add to the compiler for Excel generation
        TimingCompiler.addTimingData(application, testCase, approach, startTime, endTime, durationSeconds);
    }
    
    /**
     * Records the timing data to a CSV file
     */
    private void recordTiming() {
        String filePath = "docs/excel/timing_data.csv";
        boolean fileExists = new java.io.File(filePath).exists();
        
        try {
            // Create directory if it doesn't exist
            new java.io.File("docs/excel/").mkdirs();
            
            // Append to the CSV file
            FileWriter writer = new FileWriter(filePath, true);
            
            // Write header if file doesn't exist
            if (!fileExists) {
                writer.append("Application,TestCase,Approach,StartTime,EndTime,DurationSeconds\n");
            }
            
            // Write data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            writer.append(application).append(",")
                  .append(testCase).append(",")
                  .append(approach).append(",")
                  .append(startTime.format(formatter)).append(",")
                  .append(endTime.format(formatter)).append(",")
                  .append(String.valueOf(durationSeconds)).append("\n");
            
            writer.flush();
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Formats duration in seconds to HH:MM:SS format
     */
    private String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
}
