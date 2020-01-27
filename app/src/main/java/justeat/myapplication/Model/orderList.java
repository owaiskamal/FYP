package justeat.myapplication.Model;

import java.util.List;

public class orderList {
   private String name;
    private String tableNo;
    private String totalPrice;
    private List<Cart> order ;

    public orderList() {
    }

    public orderList(String tableNo , String totalPrice , List<Cart> order) {
        //this.name = name;
        this.tableNo = tableNo;
        this.order = order;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public List<Cart> getOrder() {
        return order;
    }

    public void setOrder(List<Cart> order) {
        this.order = order;
    }
}
