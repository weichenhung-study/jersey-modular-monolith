package com.ntou.creditcard.transactions.transactionquery;

import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordImpl;
import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("TransactionQuery")
public class TransactionQueryController {
    private final TransactionQuery transactionQuery;
    public TransactionQueryController() {
        this.transactionQuery = new TransactionQuery(
                new BillrecordImpl(new BillrecordDAO()));
    }
    public TransactionQueryController(TransactionQuery transactionQuery) {
        this.transactionQuery = transactionQuery;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(
            @QueryParam("cid") String cid,
            @QueryParam("cardType") String cardType,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) throws Exception {
        TransactionQueryReq req = new TransactionQueryReq();
        req.setCid(cid);
        req.setCardType(cardType);
        req.setStartDate(startDate);
        req.setEndDate(endDate);
        return transactionQuery.doAPI(req);
    }
}