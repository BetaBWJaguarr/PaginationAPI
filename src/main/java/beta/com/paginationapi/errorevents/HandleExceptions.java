package beta.com.paginationapi.errorevents;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HandleExceptions {

    private String logFilePath;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HandleExceptions() {
        String userHome = System.getProperty("user.home");
        Path defaultLogPath = Paths.get(userHome, "Documents", "PaginationAPI_Logs");
        try {
            if (!Files.exists(defaultLogPath)) {
                Files.createDirectories(defaultLogPath);
            }
            logFilePath = defaultLogPath.resolve("exceptions.log").toString();
        } catch (IOException e) {
            System.err.println("Failed to create log directory: " + e.getMessage());
        }
    }

    public void handle(Exception e, String className, String methodName) {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String errorMessage = String.format("[%s] Exception in %s.%s: %s", timestamp, className, methodName, e.getMessage());

        System.err.println(errorMessage);

        logExceptionToFile(e, className, methodName, timestamp);

        throw new RuntimeException(errorMessage, e);
    }

    private void logExceptionToFile(Exception e, String className, String methodName, String timestamp) {
        try (FileWriter fw = new FileWriter(logFilePath, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("=====================================================");
            pw.println("Timestamp: " + timestamp);
            pw.println("Class: " + className);
            pw.println("Method: " + methodName);
            pw.println("Exception Message: " + e.getMessage());
            pw.println("Stack Trace:");
            e.printStackTrace(pw);
            pw.println("=====================================================");
        } catch (IOException ioException) {
            System.err.println("Failed to log exception: " + ioException.getMessage());
        }
    }
}