package top.net.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/reservation")
@RegisterRestClient(configKey = "reservation-api")

public interface LoginService {
    @Path("loginToAccount")
    @GET
    public Response loginToUserAccount(@QueryParam("email") String email,
                                       @QueryParam("password") String password);

}
