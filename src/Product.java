import java.io.Serializable;

abstract public class Product implements Serializable {
    private String productId;
    private String productName;
    private int noOfItemsAvailable;
    private double price;

    public Product(String productId, String productName, int noOfItemsAvailable, double price) {
        this.productId = productId;
        this.productName = productName;
        this.noOfItemsAvailable = noOfItemsAvailable;
        this.price = price;
    }
    public Product(){
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNoOfItemsAvailable() {
        return noOfItemsAvailable;
    }

    public void setNoOfItemsAvailable(int noOfItemsAvailable) {
        this.noOfItemsAvailable = noOfItemsAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    @Override
    public String toString() {
        return "Product{" +
                " product ID ='" + productId + '\'' +
                ", Product Name ='" + productName + '\'' +
                ", Available Items =" + noOfItemsAvailable +
                ", price = " + price;
    }
}


