package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.BankDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.BankTransfer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.io.IOException;


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
        throw new AccountNotFoundException("User not found for email: " + email);
    }

    @Override
    public BankTransfer addTransaction(TransactionDTO transaction){
        BankTransfer bankTransfer = bankDAO.save(transaction);

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
    public Account handleReceivedTransaction(TransactionDTO transaction){
        // save transaction to Bank
        bankDAO.save(transaction);

        // get toAccount and change its balance
        Account toAccount = bankDAO.getAccount(transaction.getToAccountId());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

        try (ProducerTemplate producer = context.createProducerTemplate()) {
            producer.sendBody("sjms2:topic:paymentReceived", "Payed");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bankDAO.changeAccountBalance(toAccount);
    }
}
