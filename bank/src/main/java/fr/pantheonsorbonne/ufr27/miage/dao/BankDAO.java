package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.Transaction;

import java.util.Collection;

public interface BankDAO {
    Collection<Account> getAllAccounts();

    Account getAccount(long accountId);

    Transaction save(TransactionDTO transaction);

    Account changeAccountBalance(Account account);

    Bank getBankObject();

    Transaction findTransferWithReservationId(/*ReservationDTO reservation*/ String reservationId, long fromAccountId, long fromAccountBankId);
}
