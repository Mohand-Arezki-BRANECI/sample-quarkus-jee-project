package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dto.AccountDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class DBPopulation {
    public EntityManager getEm() {
        return em;
    }

    @Inject
    EntityManager em;

    @Transactional
    public void truncateAllTables() {
        // Disable referential integrity for H2
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        // Query to find all tables
        List<String> tableNames = em.createNativeQuery(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                        "WHERE TABLE_SCHEMA='PUBLIC'").getResultList();

        // Truncate each table
        for (String tableName : tableNames) {
            em.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        // Re-enable referential integrity for H2
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
    @Transactional
    public TestData createMaterial() {

        String fromBankName="TestBank";
        long fromBank = 99;

        Bank testBank = new Bank();
        testBank.setBankId(fromBank);
        testBank.setBankname(fromBankName);
        em.persist(testBank);

        long fromAccount = 99;

        String firstName = "tester99";
        String lastName = "Test";
        String email = "test";
        String password = "test";
        double accountBalance = 2000;

        Account testAccount= new Account();
        testAccount.setBalance(accountBalance);
        testAccount.setAccountId(fromAccount);
        testAccount.setOwnerFirstName(firstName);
        testAccount.setOwnerLastName(lastName);
        testAccount.setEmail(email);
        testAccount.setPassword(password);
        testAccount.setBank(testBank);

        em.persist(testAccount);


        long toAccount = 1;
        long toBank  = 1;
        double amount = 100;
        String transactionPurpose = "Payment";
        String reservationId = "1";

        return new TestData(fromAccount,fromBank,toAccount,toBank,email,password,amount,transactionPurpose,reservationId);


    }


}
