package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.BankTransfer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;

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
    public Account getAccount(long accountId){
        return em.find(Account.class, accountId);
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


    @Override
    @Transactional
    public Bank getBankObject(){
        List<Bank> resultList = em.createNamedQuery("getBankObject", Bank.class).getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
