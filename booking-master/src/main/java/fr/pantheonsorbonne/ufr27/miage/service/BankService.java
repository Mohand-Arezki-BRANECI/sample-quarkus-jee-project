package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public interface BankService {

    @Path("createTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public abstract Response createTransaction(TransactionDTO transaction);

    @Path("loginToAccount")
    @GET
    public abstract Response loginToBankAccount(@QueryParam("email") String email,
                                       @QueryParam("password") String password);


    @Path("getBankTransferForReservation")
    @GET
    public abstract Response getBankTransfer(@QueryParam("reservationId") String reservationId,
                                    @QueryParam("fromAccountId") long fromAccountId,
                                    @QueryParam("fromAccountBankId") long fromAccountBankId );
}


