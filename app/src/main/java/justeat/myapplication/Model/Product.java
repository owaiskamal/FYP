package justeat.myapplication.Model;

public class Product {


    private  String  parentId;
 private  String  productDescription;

    private  String  productName;
    private  String  productPrice;
    public String url;


    public Product(String parentId, String productDescription, String productName, String productPrice, String url) {
        this.parentId = parentId;
        this.productDescription = productDescription;
        this.productName = productName;
        this.productPrice = productPrice;
        this.url = url;
    }

    public Product() {
    }

    public String getParent_Id() {
        return parentId;
    }

    public void setParent_Id(String parent_Id) {
        this.parentId = parent_Id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
