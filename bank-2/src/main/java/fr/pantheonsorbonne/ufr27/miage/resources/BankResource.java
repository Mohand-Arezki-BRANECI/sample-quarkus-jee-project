package fr.pantheonsorbonne.ufr27.miage.resources;


import fr.pantheonsorbonne.ufr27.miage.dto.AccountDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.exception.NotEnoughMoneyException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
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

            // do the transfer
            // deduct amount from balance
            fromAccount.setBalance(fromAccountBalance-amount);
            service.changeAccountBalance(fromAccount);

            // add transaction to DB
            TransactionDTO bankToBankTransaction = new TransactionDTO(email,password,toAccount,toBankId,fromAccount.getAccount_id(), fromAccount.getBank().getBankId(), amount);
            service.addTransaction(bankToBankTransaction);

            // send Transaction to the Topic to distribute to all Banks
            transactionGateway.sendTransaction(bankToBankTransaction);


            // return to client on booking
            String responseMessage = "Your payment has been successful";
            return Response.status(Response.Status.OK)
                    .entity(responseMessage)
                    .build();


        }catch (AccountNotFoundException e){
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        } catch (NotEnoughMoneyException e) {
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.PAYMENT_REQUIRED)
                    .entity(responseMessage)
                    .build();
        }



    }

    @Path("loginToAccount")
    @GET
    public Response loginToAccount( @QueryParam("email") String email,
                                    @QueryParam("password") String password) {
        // Validate user credentials and perform transaction creation logic



        // Perform authentication and transaction creation logic here

        try {
            // get BankAccount
            Account fromAccount = service.getAccountByEmailAndPassword(email, password);

            if (fromAccount != null) {
                // Map Account to AccountDTO
                AccountDTO responseData = new AccountDTO();
                responseData.setAccountId(fromAccount.getAccount_id());
                responseData.setBankId(fromAccount.getBank().getBankId());
                responseData.setEmail(fromAccount.getEmail());
                responseData.setOwnerFirstName(fromAccount.getOwnerFirstName());
                responseData.setOwnerLastName(fromAccount.getOwnerLastName());

                // Return AccountDTO in the response
                return Response.ok(responseData).build();
            } else {
                // Handle the case where the account is not found
                String errorMessage = "Login failed. Invalid email or password.";
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(errorMessage)
                        .build();
            }
        }catch (AccountNotFoundException e){
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        }
    }
}