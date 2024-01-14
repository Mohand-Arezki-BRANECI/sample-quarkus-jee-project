package fr.pantheonsorbonne.ufr27.miage.resources;


import fr.pantheonsorbonne.ufr27.miage.dto.AccountDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.exception.CancelReservationException;
import fr.pantheonsorbonne.ufr27.miage.exception.NotEnoughMoneyException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Transaction;
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
    public Response createTransaction(TransactionDTO transaction) {
        String email = transaction.getEmail();
        String password = transaction.getPassword();
        double amount = transaction.getAmount();

        try {
            Account fromAccount = service.getAccountByEmailAndPassword(email, password);
            if (fromAccount.getBalance() < amount) {
                throw new NotEnoughMoneyException("Balance is not sufficient to pay the invoice.");
            }
        } catch (AccountNotFoundException e) {
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

        String responseMessage = transactionGateway.makeTransaction(transaction);

        return Response.status(Response.Status.OK)
                .entity(responseMessage)
                .build();
    }

    @Path("loginToAccount")
    @GET
    public Response loginToAccount(@QueryParam("email") String email,
                                   @QueryParam("password") String password) {
        try {
            Account fromAccount = service.getAccountByEmailAndPassword(email, password);

            AccountDTO responseData = new AccountDTO();
            responseData.setAccountId(fromAccount.getAccount_id());
            responseData.setBankId(fromAccount.getBank().getBankId());
            responseData.setEmail(fromAccount.getEmail());
            responseData.setOwnerFirstName(fromAccount.getOwnerFirstName());
            responseData.setOwnerLastName(fromAccount.getOwnerLastName());

            return Response.ok(responseData).build();

        } catch (AccountNotFoundException e) {
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        }
    }


    @Path("getBankTransferForReservation")
    @GET
    public Response getBankTransfer(@QueryParam("reservationId") String reservationId,
                                    @QueryParam("fromAccountId") long fromAccountId,
                                    @QueryParam("fromAccountBankId") long fromAccountBankId) {

        try {
            Transaction transfer = service.findTransferWithReservationId(reservationId, fromAccountId,fromAccountBankId);

            if(transfer == null){
                throw new CancelReservationException("No Banktransfer associated with this ReservationId could be found. ");
            }

            return Response.status(Response.Status.OK)
                    .entity(transfer)
                    .build();

        }catch (CancelReservationException e) {
            String responseMessage = e.getMessage();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(responseMessage)
                    .build();
        }
    }
}