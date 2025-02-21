package com.ntou.creditcard.transactions.transaction;

import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordImpl;
import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditImpl;
import com.ntou.tool.Common;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Transaction")
public class TransactionController {
    private final Transaction transaction;
    public TransactionController() {
        this.transaction = new Transaction(
                new BillrecordImpl(new BillrecordDAO())
                , new CuscreditImpl(new CuscreditDAO()));
    }
    public TransactionController(Transaction transaction) {
        this.transaction = transaction;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(TransactionReq req) throws Exception {
        return transaction.doAPI(req);
    }
}