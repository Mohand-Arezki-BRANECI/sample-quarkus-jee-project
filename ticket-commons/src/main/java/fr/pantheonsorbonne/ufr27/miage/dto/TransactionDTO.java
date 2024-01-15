package fr.pantheonsorbonne.ufr27.miage.dto;

public class TransactionDTO {

    private Long transactionId;
    private String email;
    private String password;
    private long toAccountId;

    private long toBankId;

    private long fromAccountId;

    private long fromBankId;
    private double amount;

    private String reservationId;

    private String transactionPurpose;


    public TransactionDTO() {}

    public TransactionDTO(String email, String password, long toAccount, long toBankId, long fromAccount, long fromBankId, double amount, String transactionPurpose, String reservationId) {
        this.email = email;
        this.password = password;
        this.toAccountId = toAccount;
        this.toBankId = toBankId;
        this.fromBankId = fromBankId;
        this.fromAccountId = fromAccount;
        this.amount = amount;
        this.reservationId = reservationId;
        this.transactionPurpose = transactionPurpose;

    }

    public Long getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public long getToBankId() {
        return toBankId;
    }

    public void setToBankId(long bankId) {
        this.toBankId = bankId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public long getFromBankId() {
        return fromBankId;
    }

    public void setFromBankId(long fromBankId) {
        this.fromBankId = fromBankId;
    }

    public String getTransactionPurpose() {
        return transactionPurpose;
    }
    public void setTransactionPurpose(String transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
    }

    public String getReservationId() {
        return reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionId=" + transactionId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", toAccountId=" + toAccountId +
                ", toBankId=" + toBankId +
                ", fromAccountId=" + fromAccountId +
                ", fromBankId=" + fromBankId +
                ", amount=" + amount +
                ", reservationId='" + reservationId + '\'' +
                ", transactionPurpose='" + transactionPurpose + '\'' +
                '}';
    }
}

