package fr.pantheonsorbonne.ufr27.miage.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;


import fr.pantheonsorbonne.ufr27.miage.dto.AccountDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.UserNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import fr.pantheonsorbonne.ufr27.miage.service.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/reservation")
public class LoginResource {

        @Inject
        protected LoginService service;

        @Path("loginToAccount")
        @GET
        public Response loginToUserAccount(@QueryParam("email") String email,
                                           @QueryParam("password") String password){

            try {
                User userAccount = service.getAccountByEmailAndPassword(email, password);

                return Response.ok(userAccount).build();

            } catch (UserNotFoundException e) {
                String responseMessage = e.getMessage();
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(responseMessage)
                        .build();
            }

        }
}

