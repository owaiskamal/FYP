package justeat.myapplication.Model;

public class Category {
    public String categoryName;
    public String url;


    public Category(String categoryName, String url) {
        this.categoryName = categoryName;
        this.url = url;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
