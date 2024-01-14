package fr.pantheonsorbonne.ufr27.miage.service;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.Transaction;
import fr.pantheonsorbonne.ufr27.miage.resources.DBPopulation;
import fr.pantheonsorbonne.ufr27.miage.resources.TestData;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class BankServiceImplTest {

    @Inject
    BankServiceImp bankService;

    @Inject
    DBPopulation pop;

    TestData testData;

    @BeforeEach
    @Transactional
    public void setup() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        pop.truncateAllTables();
        testData = pop.createMaterial();
    }

    @Test
    void getAccountFail() throws AccountNotFoundException{

        assertThrows(AccountNotFoundException.class, () -> bankService.getAccountByEmailAndPassword("wrongEmail","test"));
    }



    @Test
    void getAccount() throws AccountNotFoundException{
      Account account = bankService.getAccountByEmailAndPassword("test","test");
      assertEquals(99, account.getAccount_id());
      assertEquals(99, account.getBank().getBankId());
      assertEquals("TestBank", account.getBank().getBankname());
    }

    @Test
    void addTransaction(){
        TransactionDTO transaction = new TransactionDTO(testData.email(), testData.password(), testData.toAccount(), testData.toBank(), testData.fromAccount(), testData.fromBank(), testData.amount(),testData.transactionPurpose(),testData.reservationId());
        Transaction bankTransfer = bankService.addTransaction(transaction);
        assertEquals(99, bankTransfer.getFromBankId());
        assertEquals(99, bankTransfer.getFromAccountId());
        assertEquals(1, bankTransfer.getToAccountId());
        assertEquals(1, bankTransfer.getToAccountId());
        assertEquals(100, bankTransfer.getAmount());
        assertEquals("1", bankTransfer.getReservationId());

    }

    @Test
    void changeBalance() throws AccountNotFoundException{
        Account account = bankService.getAccountByEmailAndPassword("test","test");
        double amountBeforeChange = account.getBalance();
        account.setBalance(account.getBalance() + testData.amount());

        Account changedBalanceAccount = bankService.changeAccountBalance(account);
        assertEquals(amountBeforeChange, changedBalanceAccount.getBalance()-testData.amount());

    }

    @Test
    void getBankIdentification() throws AccountNotFoundException{
        Account account = bankService.getAccountByEmailAndPassword("test","test");
        Bank bank = bankService.getBankObject();

        assertEquals(bank.getBankId(), account.getBank().getBankId());
        assertEquals(bank.getBankname(), account.getBank().getBankname());
    }
}