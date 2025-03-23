package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Cheque
{
    private int chequeId;
    private int accountId;
    private String payeeName;
    private BigDecimal amount;
    private Date issuedOn;
    private String status;

    public int getChequeId() {
        return chequeId;
    }

    public void setChequeId(int chequeId) {
        this.chequeId = chequeId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getIssuedOn() {
        return issuedOn;
    }

    public void setIssuedOn(Date issuedOn) {
        this.issuedOn = issuedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "chequeId=" + chequeId +
                ", accountId=" + accountId +
                ", payeeName='" + payeeName + '\'' +
                ", amount=" + amount +
                ", issuedOn=" + issuedOn +
                ", status='" + status + '\'' +
                '}';
    }
}
