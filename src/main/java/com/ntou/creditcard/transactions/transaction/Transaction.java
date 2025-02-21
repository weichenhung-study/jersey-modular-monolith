package com.ntou.creditcard.transactions.transaction;

import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import com.ntou.tool.DateTool;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.UUID;

/** 使用信用卡購物 */
@Log4j2
public class Transaction {
    private final BillrecordSvc billrecordSvc;
    private final CuscreditSvc cuscreditSvc;
    public Transaction(BillrecordSvc billrecordSvc, CuscreditSvc cuscreditSvc) {
        this.billrecordSvc = billrecordSvc;
        this.cuscreditSvc = cuscreditSvc;
    }
    public Response doAPI(TransactionReq req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        TransactionRes res = new TransactionRes();

        if(!req.checkReq())
            ResTool.regularThrow(res, TransactionRC.T141A.getCode(), TransactionRC.T141A.getContent(), req.getErrMsg());

        CuscreditVO voCuscredit = cuscreditSvc.selectCardHolderActivated(
                req.getCid(), req.getCardType(), req.getCardNum(), req.getSecurityCode());

        //check客戶是否存在且開卡完成
        if(voCuscredit==null)
            ResTool.commonThrow(res, TransactionRC.T141D.getCode(), TransactionRC.T141D.getContent());

        int insertResult = billrecordSvc.insertCusDateBill(voBillrecordInsert(req));
        if(insertResult !=1)
            ResTool.commonThrow(res, TransactionRC.T141C.getCode(), TransactionRC.T141C.getContent());

        MailVO vo = new MailVO();
        vo.setEmailAddr(voCuscredit.getEmail());
        vo.setSubject("您今天有一筆信用卡消費");
        vo.setContent("<h1>您消費紀錄如下</h1>" +
                "<h2>"
                + "消費時間：" + req.getBuyDate()     +"<br>"
                + "消費店家：" + req.getShopId()      +"<br>"
                + "消費幣別：" + req.getBuyCurrency() +"<br>"
                + "消費金額：" + req.getBuyAmount()   +"<br>"
                +"</h2>"
        );
        new JavaMail().sendMail(vo);

        ResTool.setRes(res, TransactionRC.T1410.getCode(), TransactionRC.T1410.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.CREATED).entity(res).build();
    }

    private BillrecordVO voBillrecordInsert(TransactionReq req){
        BillrecordVO vo = new BillrecordVO();
        vo.setUuid				(UUID.randomUUID().toString());//交易編號UUID
        vo.setBuyChannel		(req.getBuyChannel		());//消費通路(00:實體, 01:線上)
        vo.setBuyDate			(req.getBuyDate			());//消費時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setReqPaymentDate	(req.getReqPaymentDate	());//請款時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setCardType			(req.getCardType		());//卡別
        vo.setShopId			(req.getShopId			());//消費店家(統編)
        vo.setCid				(req.getCid				());//消費者(身分證)
        vo.setBuyCurrency		(req.getBuyCurrency		());//消費幣別
        vo.setBuyAmount			(req.getBuyAmount		());//消費金額
        vo.setDisputedFlag		(req.getDisputedFlag	());//爭議款項註記(00:正常,01異常)
        vo.setStatus			(req.getStatus			());//狀態(00:正常,99:註銷)
        vo.setActuallyDate		(DateTool.getDateTime());//交易完成的時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setRemark			(req.getRemark			());//備註
        vo.setIssuingBank		(req.getIssuingBank		());//發卡銀行(swiftCode)
        vo.setCardNum			(req.getCardNum			());//卡號
        vo.setSecurityCode		(req.getSecurityCode	());//安全碼
        return vo;
    }
}
