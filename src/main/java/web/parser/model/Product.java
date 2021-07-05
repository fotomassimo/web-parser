package web.parser.model;

public class Product {
    private String productName;
    private String brand;
    private String colors;
    private String price;
    private String articleID;

    public Product(String productName,
                   String brand,
                   String colors,
                   String price,
                   String articleID) {
        this.productName = productName;
        this.brand = brand;
        this.colors = colors;
        this.price = price;
        this.articleID = articleID;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public String getColors() {
        return colors;
    }

    public String getPrice() {
        return price;
    }

    public String getArticleID() {
        return articleID;
    }
}
