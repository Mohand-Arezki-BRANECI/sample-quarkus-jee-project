package fr.pantheonsorbonne.ufr27.miage.resources;

public record TestData(long fromAccount, long fromBank, long toAccount, long toBank, String email, String password, double amount, String transactionPurpose, String reservationId) {
}
