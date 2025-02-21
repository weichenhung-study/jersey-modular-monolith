package com.ntou.creditcard.dispute.disputenotation;

import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordImpl;
import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("DisputeNotation")
public class DisputeNotationController {
    private final DisputeNotation disputeNotation;

    public DisputeNotationController() {
        this.disputeNotation = new DisputeNotation(
                new BillrecordImpl(new BillrecordDAO()));
    }

    public DisputeNotationController(DisputeNotation disputeNotation) {
        this.disputeNotation = disputeNotation;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(DisputeNotationReq req) throws Exception {
        return disputeNotation.doAPI(req);
    }
}