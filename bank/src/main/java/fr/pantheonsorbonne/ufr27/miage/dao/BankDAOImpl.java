package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.BankTransfer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Collection;

@ApplicationScoped
public class BankDAOImpl implements BankDAO{

    @Inject
    EntityManager em;

    @Override
    public Collection<Account> getAllAccounts() {
        return em.createNamedQuery("getAllAccounts", Account.class).getResultList();
    }


    @Override
    @Transactional
    public BankTransfer save(TransactionDTO transaction){
        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setToBankId(transaction.getToBankId());
        bankTransfer.setToAccountId(transaction.getToAccountId());
        bankTransfer.setFromBankId(transaction.getFromBankId());
        bankTransfer.setFromAccountId(transaction.getFromAccountId());
        bankTransfer.setAmount(transaction.getAmount());

        em.persist(bankTransfer);

        return bankTransfer;
    }

    @Override
    @Transactional
    public Account changeAccountBalance(Account account){
       return em.merge(account);
    }
}
