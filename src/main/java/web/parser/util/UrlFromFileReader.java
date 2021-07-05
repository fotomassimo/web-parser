package web.parser.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UrlFromFileReader implements GenericFileReader<String> {

    @Override
    public String readFromFile(String filename) {
        StringBuffer stringLines = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(filename)))) {
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                stringLines.append(tmp);
            }
            return stringLines.toString();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
