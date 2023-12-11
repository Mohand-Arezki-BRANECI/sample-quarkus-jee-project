package top.net.resource;


import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Collection;


@Path("/bank")
@RegisterRestClient(configKey = "location-api")
public interface BankService {



    @Path("createTransaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransaction(TransactionDTO transaction);
}
