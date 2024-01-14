package fr.pantheonsorbonne.ufr27.miage.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

public class LoginResource {
    @Path("loginToAccount")
    @GET
    public Response loginToUserAccount(@QueryParam("email") String email,
                                       @QueryParam("password") String password){

        return null;

    }
}
