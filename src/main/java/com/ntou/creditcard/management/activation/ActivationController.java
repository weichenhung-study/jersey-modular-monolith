package com.ntou.creditcard.management.activation;

import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditImpl;
import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Activation")
public class ActivationController {
    private final Activation activation;

    public ActivationController() {
        this.activation = new Activation(
                new CuscreditImpl(new CuscreditDAO()));
    }
    public ActivationController(Activation activation) {
        this.activation = activation;
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(ActivationReq req) throws Exception {
        return activation.doAPI(req);
    }
}