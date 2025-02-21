package com.ntou.creditcard.management.activation;

import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;

/** 信用卡開卡 */
@Log4j2
public class Activation {
    private final CuscreditSvc cuscreditSvc;
    public Activation(CuscreditSvc cuscreditSvc) {
        this.cuscreditSvc = cuscreditSvc;
    }
    public Response doAPI(ActivationReq req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        ActivationRes res = new ActivationRes();

        if(!req.checkReq())
            ResTool.regularThrow(res, ActivationRC.T131A.getCode(), ActivationRC.T131A.getContent(), req.getErrMsg());

        CuscreditVO voCuscredit = cuscreditSvc.selectKey(
                req.getCid(), req.getCardType());

        int updateResult = cuscreditSvc.updateActivationRecord(voCuscreditUpdate(req));
        MailVO vo = new MailVO();
        if(updateResult !=1) {
            ResTool.setRes(res, ActivationRC.T131C.getCode(), ActivationRC.T131C.getContent());

            vo.setEmailAddr(voCuscredit.getEmail());
            vo.setSubject("信用卡開卡開卡失敗");
            vo.setContent("<h1>請聯繫客服</h1><h2>02-1234567</h2>");
            throw new TException(res);
        }
        vo.setEmailAddr(voCuscredit.getEmail());
        vo.setSubject("信用卡開卡完成");
        vo.setContent("<h1>您申請的信用卡已開卡完成</h1><h2>歡迎使用!</h2>");
        new JavaMail().sendMail(vo);

        ResTool.setRes(res, ActivationRC.T1310.getCode(), ActivationRC.T1310.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    private CuscreditVO voCuscreditUpdate(ActivationReq req){
        CuscreditVO vo = new CuscreditVO();
        vo.setCid  		(req.getCid  	 ());
        vo.setCardType  (req.getCardType ());
        vo.setActivationRecord(CuscreditVO.ActivationRecord.OPEN.getValue());
        return vo;
    }
}
