package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@ApplicationScoped
@Path("/bank")
@RegisterRestClient(configKey = "bank-api-america")
public interface BankServiceAmerica extends BankService {

    @Path("createTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransaction(TransactionDTO transaction);

    @Path("loginToAccount")
    @GET
    public Response loginToBankAccount(@QueryParam("email") String email,
                                       @QueryParam("password") String password);

    @Path("getBankTransferForReservation")
    @GET
    public Response getBankTransfer(@QueryParam("reservationId") String reservationId,
                                    @QueryParam("fromAccountId") long fromAccountId,
                                    @QueryParam("fromAccountBankId") long fromAccountBankId );

}
