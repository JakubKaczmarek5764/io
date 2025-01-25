package FileGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReportGeneratorVisualizer {
    public void visualize(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            System.out.println("---------- Generated Report ----------");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading the report file: " + e.getMessage());
            }
        } else {
            System.out.println("The report file does not exist: " + fileName);
        }
    }

    public static void deleteReportFiles() {
        File currentDir = new File(".");
        File[] filesList = currentDir.listFiles((dir, name) -> name.endsWith("Report.pdf") || name.endsWith("Report.csv") || name.endsWith("Report.json"));
        if (filesList != null) {
            for (File file : filesList) {
                if (file.delete()) {
                    System.out.println("Deleted: " + file.getName());
                } else {
                    System.out.println("Failed to delete: " + file.getName());
                }
            }
        } else {
            System.out.println("No report files found.");
        }
    }
}