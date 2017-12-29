package br.com.stock.model;

import javax.persistence.*;

@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dealId;

    public enum Operation{ COMPRA, VENDA };
    private Operation operation;
    private double negotiatedValue;
    private double shareAmount;
    private double shareValue;

    private String dealTime;

    public Long companyId;

    public Long getDealId() { return dealId; }

    public void setDealId(Long dealId) { this.dealId = dealId; }

    public Operation getOperation() { return operation; }

    public void setOperation(Operation operation) { this.operation = operation; }

    public double getNegotiatedValue() { return negotiatedValue; }

    public void setNegotiatedValue(double negotiatedValue) { this.negotiatedValue = negotiatedValue; }

    public double getShareAmount() { return shareAmount; }

    public void setShareAmount(double shareAmount) { this.shareAmount = shareAmount; }

    public double getShareValue() { return shareValue; }

    public void setShareValue(double shareValue) { this.shareValue = shareValue; }

    public String getDealTime() { return dealTime; }

    public void setDealTime(String dealTime) { this.dealTime = dealTime; }

    public Long getCompanyId() { return companyId; }

    public void setCompanyId(Long companyId) { this.companyId = companyId; }
}
