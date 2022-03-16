package se150222.order;

import java.sql.Date;

public class OrderDTO {

    private int orderID;
    private Date orderDate;
    private float total;
    private String userID;

    public OrderDTO() {
    }

    public OrderDTO(Date orderDate, float total, String userID) {
        this.orderDate = orderDate;
        this.total = total;
        this.userID = userID;
    }

    public OrderDTO(int orderID, Date orderDate, float total, String userID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.total = total;
        this.userID = userID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
