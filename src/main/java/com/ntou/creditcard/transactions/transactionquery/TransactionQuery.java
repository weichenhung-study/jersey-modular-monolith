package com.ntou.creditcard.transactions.transactionquery;

import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

/** 消費紀錄區間查詢 */
@Log4j2
public class TransactionQuery {
    private final BillrecordSvc billrecordSvc;
    public TransactionQuery(BillrecordSvc billrecordSvc) {
        this.billrecordSvc = billrecordSvc;
    }
    public Response doAPI(TransactionQueryReq req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        TransactionQueryRes res = new TransactionQueryRes();

        if(!req.checkReq())
            ResTool.regularThrow(res, TransactionQueryRC.T161A.getCode(), TransactionQueryRC.T161A.getContent(), req.getErrMsg());

        ArrayList<BillrecordVO> billList = billrecordSvc
                .selectCusBillAll(voBillrecordSelect(req), req.getStartDate(), req.getEndDate());

        ResTool.setRes(res, TransactionQueryRC.T1610.getCode(), TransactionQueryRC.T1610.getContent());
        res.setResult(billList);

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    private BillrecordVO voBillrecordSelect(TransactionQueryReq req){
        BillrecordVO vo = new BillrecordVO();
        vo.setCid		(req.getCid());
        vo.setCardType	(req.getCardType());
        return vo;
    }
}
