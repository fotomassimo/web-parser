package web.parser.util;

import web.parser.model.Product;

public class CsvOutputFileFormatter implements GenericFormatter<String[][], Product[]> {
    private static final int NUMBER_OF_PRODUCT_ATTRIBUTES = 5;

    @Override
    public String[][] createReport(Product[] products) {
        String[][] csvReport = new String[products.length][NUMBER_OF_PRODUCT_ATTRIBUTES];
        int index = 0;
        for(Product product: products) {
            String[] reportRow = new String[] {
                    product.getProductName(),
                    product.getBrand(),
                    product.getColors(),
                    product.getPrice(),
                    product.getArticleID()
            };
            csvReport[index++] = reportRow;
        }
        return csvReport;
    }
}
