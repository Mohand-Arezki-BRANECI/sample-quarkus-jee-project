package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.BankDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;


@ApplicationScoped
public class BankServiceImp implements BankService {

    @Inject
    CamelContext context;
    @Inject
    BankDAO bankDAO;

    @Override
    public Account getAccountByEmailAndPassword(String email, String password) throws AccountNotFoundException {
        for (Account account : bankDAO.getAllAccounts()) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return account;
            }
        }
        throw new AccountNotFoundException("User not found for email: " + email + " \nPlease check email and password.");
    }

    @Override
    public Transaction addTransaction(TransactionDTO transaction){
        Transaction bankTransfer = bankDAO.save(transaction);

        return bankTransfer;
    }

    @Override
    public Account changeAccountBalance(Account account){
        Account updatedAccount = bankDAO.changeAccountBalance(account);

        return updatedAccount;
    }

    @Override
    public  Bank getBankObject(){
        Bank bank = bankDAO.getBankObject();
        return bank;
    }

    @Override
    public Transaction findTransferWithReservationId(String reservationId, long fromAccountId, long fromAccountBankId) {
            Transaction bankTransfer = bankDAO.findTransferWithReservationId(reservationId, fromAccountId, fromAccountBankId);
            return bankTransfer;
    }

    @Override
    public Transaction handleReceivedTransaction(TransactionDTO transaction){
        Transaction transfer= bankDAO.save(transaction);

        Account toAccount = bankDAO.getAccount(transaction.getToAccountId());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

        bankDAO.changeAccountBalance(toAccount);
        return transfer;
    }

    @Override
    public Transaction handleSendTransaction(TransactionDTO transaction){
        Transaction transfer= bankDAO.save(transaction);

        Account fromAccount = bankDAO.getAccount(transaction.getFromAccountId());
        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());

        bankDAO.changeAccountBalance(fromAccount);
        return transfer;
    }

    @Override
    public Transaction handleSendAndReceiveTransaction(TransactionDTO transaction){
        Account fromAccount = bankDAO.getAccount(transaction.getFromAccountId());
        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());

        bankDAO.changeAccountBalance(fromAccount);

        Account toAccount = bankDAO.getAccount(transaction.getToAccountId());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

        bankDAO.changeAccountBalance(toAccount);

        Transaction transfer = bankDAO.save(transaction);

        return transfer;
    }
}
