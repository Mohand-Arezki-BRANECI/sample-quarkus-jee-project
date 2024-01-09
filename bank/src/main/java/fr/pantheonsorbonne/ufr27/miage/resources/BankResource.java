package fr.pantheonsorbonne.ufr27.miage.resources;


import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.exception.NotEnoughMoneyException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.service.BankService;
import fr.pantheonsorbonne.ufr27.miage.service.TransactionGateway;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/bank")
public class BankResource {

    @Inject
    protected BankService service;

    @Inject
    TransactionGateway transactionGateway;

    @Path("createTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransaction(TransactionDTO request) {
        // Validate user credentials and perform transaction creation logic
        String email = request.getEmail();
        String password = request.getPassword();
        long toAccount = request.getToAccountId();
        long toBankId = request.getToBankId();
        double amount = request.getAmount();



        // Perform authentication and transaction creation logic here

        try {
            // get BankAccount
            Account fromAccount = service.getAccountByEmailAndPassword(email, password);

            // check if enough funds
            double fromAccountBalance = fromAccount.getBalance();
            if(fromAccountBalance < amount){
                throw new NotEnoughMoneyException("Your balance is not sufficient to pay the bill.");
            }

            Bank testBank = fromAccount.getBank();

            // do the transfer
            // deduct amount from balance
            fromAccount.setBalance(fromAccountBalance-amount);
            service.changeAccountBalance(fromAccount);

            // add transaction
            TransactionDTO bankToBankTransaction = new TransactionDTO(email,password,toAccount,toBankId,fromAccount.getAccount_id(), fromAccount.getBank().getBankId(), amount);
            service.addTransaction(bankToBankTransaction);



            transactionGateway.sendTransaction(bankToBankTransaction);


            //TODO: Add transaction on JMS to tell other bank that a transaction as been performed and add the amount in the transaction to the account


            // For demonstration purposes, returning a simple response
            String responseMessage = "Transaction created successfully for toAccount number: " + fromAccount.getAccount_id();
            return Response.status(Response.Status.OK)
                    .entity(responseMessage)
                    .build();


        }catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        } catch (NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.PAYMENT_REQUIRED)
                    .entity(responseMessage)
                    .build();
        }



    }
}