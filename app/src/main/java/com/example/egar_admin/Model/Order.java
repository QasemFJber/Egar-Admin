package com.example.egar_admin.Model;

import android.location.Location;

import com.example.egar_admin.enams.OrderStatus;

import java.util.Date;

public class Order {
    private String orderId;
    private String userId;
    private String serviceProviderId;
    private int quantity;
    private double totalAmount;
    private Date orderDate;
    private OrderStatus orderStatus;
    private String paymentMethod;
    private Location shippingLocation;

    public Order() {
        // Required empty constructor for Firebase
    }

    public Order(String orderId, String userId, String serviceProviderId, int quantity, double totalAmount, Date orderDate, OrderStatus orderStatus, String paymentMethod, Location shippingLocation) {
        this.orderId = orderId;
        this.userId = userId;
        this.serviceProviderId = serviceProviderId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.shippingLocation = shippingLocation;
    }

    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Location getShippingLocation() {
        return shippingLocation;
    }

    public void setShippingLocation(Location shippingLocation) {
        this.shippingLocation = shippingLocation;
    }
}
