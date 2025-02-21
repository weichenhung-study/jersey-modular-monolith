package com.ntou.creditcard.billing.generatebill;

import com.ntou.db.billofmonth.BillofmonthDAO;
import com.ntou.db.billofmonth.BillofmonthImpl;
import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordImpl;
import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditImpl;

import javax.ws.rs.core.Response;

public class GenerateBillController {
    private final GenerateBill generateBill;
    public GenerateBillController() {
        this.generateBill = new GenerateBill(
                new BillofmonthImpl(new BillofmonthDAO())
                , new BillrecordImpl(new BillrecordDAO())
                , new CuscreditImpl(new CuscreditDAO())
        );
    }
    public GenerateBillController(GenerateBill generateBill) {
        this.generateBill = generateBill;
    }

    public Response doController() throws Exception {
        return generateBill.doAPI();
    }
}