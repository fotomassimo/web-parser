package web.parser.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import web.parser.model.Product;

public class JsonToProductsParser {
    private static final String STRING_SEPARATOR = "\u0020";
    private static final int PRICE_DENOMINATOR = 100;

    public Product[] extractAllProductsFromURL(String webPageJsonContent) {
        JsonObject webPageContent = new JsonParser().parse(webPageJsonContent).getAsJsonObject();
        Product[] products = new Product[0];
        if (webPageContent.has("entities") && !webPageContent.isJsonNull()) {
            JsonArray entitiesArray = webPageContent.get("entities").getAsJsonArray();
            products = new Product[entitiesArray.size()];
            int index = 0;
            for (JsonElement entityElement : entitiesArray) {
                JsonObject entity = entityElement.getAsJsonObject();
                if (entity.get("isActive").getAsBoolean()) {
                    JsonObject attributes = entity.get("attributes").getAsJsonObject();

                    String productName = attributes
                            .get("name")
                            .getAsJsonObject()
                            .get("values")
                            .getAsJsonObject()
                            .get("label")
                            .getAsString();

                    String brand = attributes
                            .get("brand")
                            .getAsJsonObject()
                            .get("values")
                            .getAsJsonObject()
                            .get("label")
                            .getAsString();

                    JsonObject priceObject = entity.get("priceRange")
                            .getAsJsonObject()
                            .get("min")
                            .getAsJsonObject();
                    //Raw price number is converted to price value with decimal point
                    String priceValue = String.format("%.02f",
                            priceObject.get("withTax").getAsFloat() / PRICE_DENOMINATOR);
                    StringBuffer priceBuilder = new StringBuffer(priceValue)
                            .append(STRING_SEPARATOR)
                            .append(priceObject.get("currencyCode").getAsString());
                    String price = priceBuilder.toString();

                    String articleID = entity.get("id").getAsString();

                    String firstColor = attributes
                            .get("colorDetail")
                            .getAsJsonObject()
                            .get("values")
                            .getAsJsonArray()
                            .get(0)
                            .getAsJsonObject()
                            .get("label")
                            .getAsString()
                            .toLowerCase();
                    StringBuffer colorsBuilder = new StringBuffer(firstColor);
                    if (entity.get("advancedAttributes").getAsJsonObject().has("siblings")) {
                        JsonArray colorArray = entity
                                .get("advancedAttributes")
                                .getAsJsonObject()
                                .get("siblings")
                                .getAsJsonObject()
                                .get("values")
                                .getAsJsonArray()
                                .get(0)
                                .getAsJsonObject()
                                .get("fieldSet")
                                .getAsJsonArray()
                                .get(0)
                                .getAsJsonArray();
                        for (JsonElement colorElement : colorArray) {
                            JsonObject colorObject = colorElement.getAsJsonObject();
                            String color = colorObject
                                    .get("color")
                                    .getAsJsonArray()
                                    .get(0)
                                    .getAsJsonObject()
                                    .get("label")
                                    .getAsString()
                                    .toLowerCase();
                            //Condition checks and collects only available for sale and distinct colors
                            if (!(colorObject.get("isSoldOut").getAsBoolean())
                                    && colorsBuilder.indexOf(color) == -1) {
                                colorsBuilder.append(STRING_SEPARATOR)
                                        .append(color);
                            }
                        }
                    }
                    String colors = colorsBuilder.toString();

                    products[index++] = new Product(productName,
                            brand,
                            colors,
                            price,
                            articleID);
                }
            }
        }
        return products;
    }
}
