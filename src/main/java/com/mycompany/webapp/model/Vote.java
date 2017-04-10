package com.mycompany.webapp.model;

public class Vote {
    private long id;
    private long pollId;
    private String customerName;
    private long ansId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPollId() {
        return pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getAnsId() {
        return ansId;
    }

    public void setAnsId(long ansId) {
        this.ansId = ansId;
    }
}
