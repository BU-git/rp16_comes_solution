package com.bionic.dto;

/**
 * @author Dima Budko
 */
public class ConsigmentFeeDTO {
    private String fee;
    private Integer feeAllowances;
    private String feeType;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Integer getFeeAllowances() {
        return feeAllowances;
    }

    public void setFeeAllowances(Integer feeAllowances) {
        this.feeAllowances = feeAllowances;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
}
