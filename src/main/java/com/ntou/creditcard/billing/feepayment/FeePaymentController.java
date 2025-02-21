package com.ntou.creditcard.billing.feepayment;

import com.ntou.db.billofmonth.BillofmonthDAO;
import com.ntou.db.billofmonth.BillofmonthImpl;
import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditImpl;
import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("FeePayment")
public class FeePaymentController {
    private final FeePayment feePayment;
    public FeePaymentController(){
        this.feePayment = new FeePayment(
                new BillofmonthImpl(new BillofmonthDAO())
                , new CuscreditImpl(new CuscreditDAO()));
    }
    public FeePaymentController(FeePayment feePayment){
        this.feePayment = feePayment;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(FeePaymentReq req) throws Exception {
        return feePayment.doAPI(req);
    }
}