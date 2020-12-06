package com.example.cosmicomapp;

public class ShipperResponse {

    private String name;
    private String email;;
    private String id;
    private String status;
    private String netAmt;
    private String paymentMode;
    private int totalCount;

    public ShipperResponse(String name, String email, String id, String status, String netAmt, String paymentMode, int totalCount) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.status = status;
        this.netAmt = netAmt;
        this.paymentMode = paymentMode;
        this.totalCount = totalCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(String netAmt) {
        this.netAmt = netAmt;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
