package top.net.resource;


import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/bank")
@RegisterRestClient(configKey = "bank-api")
public interface BankService {

    @Path("createTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransaction(TransactionDTO transaction);

    @Path("loginToAccount")
    @GET
    public Response loginToBankAccount(@QueryParam("email") String email,
                                       @QueryParam("password") String password);


}
