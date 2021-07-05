package web.parser;

import web.parser.model.Product;
import web.parser.service.FromUrlContentLoader;
import web.parser.service.GenericContentLoader;
import web.parser.service.JsonToProductsParser;
import web.parser.util.CsvOutputFileFormatter;
import web.parser.util.CsvOutputFileWriter;
import web.parser.util.GenericFormatter;
import web.parser.util.GenericFileReader;
import web.parser.util.GenericFileWriter;
import web.parser.util.UrlFromFileReader;

public class Main {
    public static final String URL_FILE_PATH = "URL_with_variable_index.txt";
    public static final String OUTPUT_FILE_PATH = System.getProperty("user.dir") + "/output.csv";
    public static final int NUMBER_OF_SUBPAGES_TO_LOAD_PAGE_CONTENT = 5;
    public static final String EMPTY_CONTENT_JSON = "{}";
    public static final String URL_VARIABLE_INDEX_NAME = "<variable_index>";

    public static void main(String[] args) {
        GenericFileReader<String> urlReader = new UrlFromFileReader();
        String urlWithVariableIndex = urlReader.readFromFile(URL_FILE_PATH);
        GenericContentLoader<String> loader = new FromUrlContentLoader();
        JsonToProductsParser parser = new JsonToProductsParser();
        GenericFormatter<String[][], Product[]> formatter = new CsvOutputFileFormatter();
        GenericFileWriter<String[][]> writer = new CsvOutputFileWriter();

        int subpagesIndex = 1;
        int productsCount = 0;
        String webPageJsonContent = EMPTY_CONTENT_JSON;
        while (subpagesIndex <= NUMBER_OF_SUBPAGES_TO_LOAD_PAGE_CONTENT) {
            String urlWithStaticIndex = urlWithVariableIndex
                    .replace(URL_VARIABLE_INDEX_NAME, String.valueOf(subpagesIndex));
            subpagesIndex++;
            try {
                webPageJsonContent = loader.getContent(urlWithStaticIndex);
            } catch (RuntimeException exception) {
                //Exception was handled to console message to show subpage loading error without interrupting iteration
                System.out.println("Page is not loaded. " + exception.toString());
            }
            Product[] products = parser.extractAllProductsFromURL(webPageJsonContent);
            productsCount += products.length;
            String[][] report = formatter.createReport(products);
            writer.writeToFile(report, OUTPUT_FILE_PATH);
        }

        System.out.println("Amount of triggered HTTP requests: " + (subpagesIndex - 1));
        System.out.println("Amount of extracted products: " + productsCount);
    }
}
