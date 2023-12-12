package fr.pantheonsorbonne.ufr27.miage.resources;


import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.service.BankService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/bank")
public class BankResource {

    @Inject
    protected BankService accountService;

    @Path("createTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransaction(TransactionDTO request) {
        // Validate user credentials and perform transaction creation logic
        String email = request.getEmail();
        String password = request.getPassword();
        long toAccount = request.getToAccount();
        long bankId = request.getBankId();
        double amount = request.getAmount();



        // Perform authentication and transaction creation logic here
        // get BankAccount
        try {
            Account fromAccount = accountService.getAccountByEmailAndPassword(email, password);

        }catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        }


        // For demonstration purposes, returning a simple response
        String responseMessage = "Transaction created successfully for toAccount number: " + toAccount;
        return Response.status(Response.Status.OK)
                .entity(responseMessage)
                .build();
    }
}