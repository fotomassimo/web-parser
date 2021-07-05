package web.parser.util;

import com.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvOutputFileWriter implements GenericFileWriter<String[][]> {
    public void writeToFile(String[][] csvReport, String filePath) {
        try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(filePath, true)),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            for (String[] reportRow : csvReport) {
                writer.writeNext(reportRow);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
