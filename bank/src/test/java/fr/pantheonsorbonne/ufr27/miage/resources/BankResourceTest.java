package fr.pantheonsorbonne.ufr27.miage.resources;
import fr.pantheonsorbonne.ufr27.miage.dto.*;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.exception.NotEnoughMoneyException;
import fr.pantheonsorbonne.ufr27.miage.service.BankService;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class BankResourceTest {

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
    void createTransaction() throws AccountNotFoundException , NotEnoughMoneyException {
        TransactionDTO transaction = new TransactionDTO(testData.email(), testData.password(), testData.toAccount(), testData.toBank(), testData.fromAccount(), testData.fromBank(), testData.amount(), testData.transactionPurpose(), testData.reservationId());
        TransactionDTO transactionBoggusEmail = new TransactionDTO("wrongEmail", testData.password(), testData.toAccount(), testData.toBank(), testData.fromAccount(), testData.fromBank(), testData.amount(),testData.transactionPurpose(), testData.reservationId());
        TransactionDTO transactionBoggusAmount = new TransactionDTO(testData.email(), testData.password(), testData.toAccount(), testData.toBank(), testData.fromAccount(), testData.fromBank(), 1000000000,testData.transactionPurpose(),testData.reservationId());

        given()
                .contentType("application/json")
                .body(transaction)
                .when()
                .post("bank/createTransaction")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .body(transactionBoggusEmail)
                .when()
                .post("bank/createTransaction")
                .then()
                .statusCode(404);

        given()
                .contentType("application/json")
                .body(transactionBoggusAmount)
                .when()
                .post("bank/createTransaction")
                .then()
                .statusCode(402);

    }
}