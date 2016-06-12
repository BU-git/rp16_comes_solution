package com.bionic.dto;

/**
 * @author Dima Budko
 */
public class ConsignmentFeeDTO {
    private String fee;
    private Double feeAllowances;
    private String feeType;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Double getFeeAllowances() {
        return feeAllowances;
    }

    public void setFeeAllowances(Double feeAllowances) {
        this.feeAllowances = feeAllowances;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
}
