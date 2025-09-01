package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to compile timing data and generate Excel reports
 */
public class TimingCompiler {

    private static List<TimingData> timingDataList = new ArrayList<>();

    /**
     * Data structure to hold timing information
     */
    public static class TimingData {
        private String application;
        private String testCase;
        private String approach;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private long durationSeconds;

        public TimingData(String application, String testCase, String approach, LocalDateTime startTime, LocalDateTime endTime, long durationSeconds) {
            this.application = application;
            this.testCase = testCase;
            this.approach = approach;
            this.startTime = startTime;
            this.endTime = endTime;
            this.durationSeconds = durationSeconds;
        }

        public String getApplication() {
            return application;
        }

        public String getTestCase() {
            return testCase;
        }

        public String getApproach() {
            return approach;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public long getDurationSeconds() {
            return durationSeconds;
        }
    }

    /**
     * Adds timing data to the list
     */
    public static void addTimingData(String application, String testCase, String approach, LocalDateTime startTime, LocalDateTime endTime, long durationSeconds) {
        timingDataList.add(new TimingData(application, testCase, approach, startTime, endTime, durationSeconds));
    }

    /**
     * Loads timing data from a CSV file
     */
    public static void loadTimingDataFromCSV(String filePath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip the header row
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String application = parts[0].trim();
                    String testCase = parts[1].trim();
                    String approach = parts[2].trim();
                    LocalDateTime startTime = LocalDateTime.parse(parts[3].trim(), formatter);
                    LocalDateTime endTime = LocalDateTime.parse(parts[4].trim(), formatter);
                    long durationSeconds = Long.parseLong(parts[5].trim());

                    addTimingData(application, testCase, approach, startTime, endTime, durationSeconds);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading timing data from CSV: " + e.getMessage());
        }
    }

    /**
     * Compiles all timing data into an Excel workbook
     */
    public static void generateExcelReport(String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create sheets for each application
            Sheet expresscartSheet = workbook.createSheet("ExpressCart");
            Sheet joomlaSheet = workbook.createSheet("Joomla");
            Sheet kanboardSheet = workbook.createSheet("Kanboard");
            Sheet mediawikiSheet = workbook.createSheet("MediaWiki");
            Sheet summarySheet = workbook.createSheet("Summary");
            
            // Create header row style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            // Create headers for each application sheet
            createHeaders(expresscartSheet, headerStyle);
            createHeaders(joomlaSheet, headerStyle);
            createHeaders(kanboardSheet, headerStyle);
            createHeaders(mediawikiSheet, headerStyle);
            
            // Create summary sheet headers
            Row summaryHeader = summarySheet.createRow(0);
            summaryHeader.createCell(0).setCellValue("Application");
            summaryHeader.createCell(1).setCellValue("Approach");
            summaryHeader.createCell(2).setCellValue("Total Time (seconds)");
            summaryHeader.createCell(3).setCellValue("Average Time per Test (seconds)");
            
            for (int i = 0; i < 4; i++) {
                summaryHeader.getCell(i).setCellStyle(headerStyle);
            }
            
            // Populate data for each application
            int expresscartRow = 1;
            int joomlaRow = 1;
            int kanboardRow = 1;
            int mediawikiRow = 1;
            
            // Variables to calculate summary statistics
            long expresscartManualTotal = 0;
            long joomlaManualTotal = 0;
            long kanboardAITotal = 0;
            long mediawikiAITotal = 0;
            int expresscartManualCount = 0;
            int joomlaManualCount = 0;
            int kanboardAICount = 0;
            int mediawikiAICount = 0;
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            for (TimingData data : timingDataList) {
                Row newRow = null;
                
                switch (data.application) {
                    case "ExpressCart":
                        newRow = expresscartSheet.createRow(expresscartRow++);
                        if (data.approach.equals("Manual")) {
                            expresscartManualTotal += data.durationSeconds;
                            expresscartManualCount++;
                        }
                        break;
                    case "Joomla":
                        newRow = joomlaSheet.createRow(joomlaRow++);
                        if (data.approach.equals("Manual")) {
                            joomlaManualTotal += data.durationSeconds;
                            joomlaManualCount++;
                        }
                        break;
                    case "Kanboard":
                        newRow = kanboardSheet.createRow(kanboardRow++);
                        if (data.approach.equals("AI-Assisted")) {
                            kanboardAITotal += data.durationSeconds;
                            kanboardAICount++;
                        }
                        break;
                    case "MediaWiki":
                        newRow = mediawikiSheet.createRow(mediawikiRow++);
                        if (data.approach.equals("AI-Assisted")) {
                            mediawikiAITotal += data.durationSeconds;
                            mediawikiAICount++;
                        }
                        break;
                }
                
                if (newRow != null) {
                    newRow.createCell(0).setCellValue(data.testCase);
                    newRow.createCell(1).setCellValue(data.approach);
                    newRow.createCell(2).setCellValue(data.startTime.format(formatter));
                    newRow.createCell(3).setCellValue(data.endTime.format(formatter));
                    newRow.createCell(4).setCellValue(formatDuration(data.durationSeconds));
                    newRow.createCell(5).setCellValue(data.durationSeconds);
                }
            }
            
            // Populate summary sheet
            int summaryRow = 1;
            
            // ExpressCart Manual
            Row expresscartSummary = summarySheet.createRow(summaryRow++);
            expresscartSummary.createCell(0).setCellValue("ExpressCart");
            expresscartSummary.createCell(1).setCellValue("Manual");
            expresscartSummary.createCell(2).setCellValue(expresscartManualTotal);
            expresscartSummary.createCell(3).setCellValue(expresscartManualCount > 0 ? 
                                                        (double)expresscartManualTotal / expresscartManualCount : 0);
            
            // Joomla Manual
            Row joomlaSummary = summarySheet.createRow(summaryRow++);
            joomlaSummary.createCell(0).setCellValue("Joomla");
            joomlaSummary.createCell(1).setCellValue("Manual");
            joomlaSummary.createCell(2).setCellValue(joomlaManualTotal);
            joomlaSummary.createCell(3).setCellValue(joomlaManualCount > 0 ? 
                                                   (double)joomlaManualTotal / joomlaManualCount : 0);
            
            // Kanboard AI-Assisted
            Row kanboardSummary = summarySheet.createRow(summaryRow++);
            kanboardSummary.createCell(0).setCellValue("Kanboard");
            kanboardSummary.createCell(1).setCellValue("AI-Assisted");
            kanboardSummary.createCell(2).setCellValue(kanboardAITotal);
            kanboardSummary.createCell(3).setCellValue(kanboardAICount > 0 ? 
                                                     (double)kanboardAITotal / kanboardAICount : 0);
            
            // MediaWiki AI-Assisted
            Row mediawikiSummary = summarySheet.createRow(summaryRow++);
            mediawikiSummary.createCell(0).setCellValue("MediaWiki");
            mediawikiSummary.createCell(1).setCellValue("AI-Assisted");
            mediawikiSummary.createCell(2).setCellValue(mediawikiAITotal);
            mediawikiSummary.createCell(3).setCellValue(mediawikiAICount > 0 ? 
                                                      (double)mediawikiAITotal / mediawikiAICount : 0);
            
            // Total Manual vs AI-Assisted
            Row totalManualRow = summarySheet.createRow(summaryRow++);
            totalManualRow.createCell(0).setCellValue("Total");
            totalManualRow.createCell(1).setCellValue("Manual");
            totalManualRow.createCell(2).setCellValue(expresscartManualTotal + joomlaManualTotal);
            totalManualRow.createCell(3).setCellValue((expresscartManualCount + joomlaManualCount) > 0 ? 
                                                    (double)(expresscartManualTotal + joomlaManualTotal) / 
                                                    (expresscartManualCount + joomlaManualCount) : 0);
            
            Row totalAIRow = summarySheet.createRow(summaryRow++);
            totalAIRow.createCell(0).setCellValue("Total");
            totalAIRow.createCell(1).setCellValue("AI-Assisted");
            totalAIRow.createCell(2).setCellValue(kanboardAITotal + mediawikiAITotal);
            totalAIRow.createCell(3).setCellValue((kanboardAICount + mediawikiAICount) > 0 ? 
                                                (double)(kanboardAITotal + mediawikiAITotal) / 
                                                (kanboardAICount + mediawikiAICount) : 0);
            
            // Auto-size columns
            for (int i = 0; i < 6; i++) {
                expresscartSheet.autoSizeColumn(i);
                joomlaSheet.autoSizeColumn(i);
                kanboardSheet.autoSizeColumn(i);
                mediawikiSheet.autoSizeColumn(i);
            }
            
            for (int i = 0; i < 4; i++) {
                summarySheet.autoSizeColumn(i);
            }
            
            // Create directory if it doesn't exist
            File file = new File(outputPath);
            file.getParentFile().mkdirs();
            
            // Write the workbook to file
            try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
                workbook.write(outputStream);
            }
            
            System.out.println("Excel file created successfully at: " + outputPath);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates header rows for application sheets
     */
    private static void createHeaders(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Test Case");
        headerRow.createCell(1).setCellValue("Approach");
        headerRow.createCell(2).setCellValue("Start Time");
        headerRow.createCell(3).setCellValue("End Time");
        headerRow.createCell(4).setCellValue("Duration");
        headerRow.createCell(5).setCellValue("Duration (seconds)");
        
        for (int i = 0; i < 6; i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }
    }
    
    /**
     * Formats duration in seconds to HH:MM:SS format
     */
    private static String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
    /**
     * Clears all collected timing data
     */
    public static void clearTimingData() {
        timingDataList.clear();
        System.out.println("Timing data cleared.");
    }

    /**
     * Gets the current number of timing records
     */
    public static int getTimingDataCount() {
        return timingDataList.size();
    }

    /**
     * Main method to generate Excel report from collected data
     */
    public static void main(String[] args) {
        String csvPath = "docs/excel/timing_data.csv";
        String outputPath = "docs/excel/development_times.xlsx";

        // Load timing data from the CSV file
        loadTimingDataFromCSV(csvPath);

        // Generate the Excel report
        generateExcelReport(outputPath);
    }
}