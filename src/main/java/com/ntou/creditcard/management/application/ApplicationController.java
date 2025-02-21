package com.ntou.creditcard.management.application;

import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditImpl;
import com.ntou.tool.Common;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Application")
public class ApplicationController {
    private final Application application;
    public ApplicationController() {
        this.application = new Application(
                new CuscreditImpl(new CuscreditDAO()));
    }
    public ApplicationController(Application application) {
        this.application = application;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(ApplicationReq req) throws Exception {
        return application.doAPI(req);
    }
}