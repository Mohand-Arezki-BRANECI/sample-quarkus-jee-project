package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.resources.DBPopulation;
import fr.pantheonsorbonne.ufr27.miage.resources.TestData;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class TransactionGatewayImpTest {

    @Inject
    TransactionGatewayImpl transactionGateway;

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
    void isSendingBank() {
        TransactionDTO transaction = new TransactionDTO(testData.email(), testData.password(), testData.toAccount(), testData.fromBank(), testData.fromAccount(), testData.fromBank(), testData.amount(), testData.transactionPurpose(),testData.reservationId());
        boolean shouldProcess = transactionGateway.isSendingBank(transaction);
        assertTrue(shouldProcess);
    }

    @Test
    void shouldNotProcess() {
        TransactionDTO transaction = new TransactionDTO(testData.email(), testData.password(), testData.toAccount(), testData.toBank(), testData.fromAccount(), testData.fromBank(), testData.amount(), testData.transactionPurpose(),testData.reservationId());
        boolean shouldProcess = transactionGateway.isReceivingBank(transaction);
        assertFalse(shouldProcess);
    }



}