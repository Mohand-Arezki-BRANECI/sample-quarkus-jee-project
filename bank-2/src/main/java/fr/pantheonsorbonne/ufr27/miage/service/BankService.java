package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.Transaction;


public interface BankService {
    Account getAccountByEmailAndPassword(String email, String password) throws AccountNotFoundException;

    Transaction addTransaction(TransactionDTO transaction);

    Account changeAccountBalance(Account account);

    Bank getBankObject();

    Transaction handleReceivedTransaction(TransactionDTO transaction);

    Transaction handleSendTransaction(TransactionDTO transaction);

    Transaction handleSendAndReceiveTransaction(TransactionDTO transaction);

    Transaction findTransferWithReservationId(String reservationId, long fromAccountId, long fromAccountBankId);
}
