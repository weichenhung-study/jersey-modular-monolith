package com.ntou.creditcard.management.review;

import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.Random;

/** 審核信用卡 */
@Log4j2
public class Review {
    private final CuscreditSvc cuscreditSvc;
    public Review(CuscreditSvc cuscreditSvc) {
        this.cuscreditSvc = cuscreditSvc;
    }
    public Response doAPI(ReviewReq req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        ReviewRes res = new ReviewRes();

         if(!req.checkReq())
             ResTool.regularThrow(res, ReviewRC.T121A.getCode(), ReviewRC.T121A.getContent(), req.getErrMsg());

         CuscreditVO voCuscredit = cuscreditSvc.selectKey(
                 req.getCid(), req.getCardType());

        String cusMail = "";
        if(voCuscredit == null)
            ResTool.commonThrow(res, ReviewRC.T121D.getCode(), ReviewRC.T121D.getContent());
        else
            cusMail = voCuscredit.getEmail();

         int updateCount = cuscreditSvc.updateCardApprovalStatus(voCuscreditUpdate(req));
         if(updateCount !=1)
            ResTool.commonThrow(res, ReviewRC.T121C.getCode(), ReviewRC.T121C.getContent());

        if(req.getCardApprovalStatus().equals(CuscreditVO.CardApprovalStatus.PASS.getValue())){
            MailVO vo = new MailVO();
            vo.setEmailAddr(cusMail);
            vo.setSubject("信用卡申請通過");
            vo.setContent("<h1>您申請的信用卡通過</h1><h2>請於30天內開卡</h2>");
            new JavaMail().sendMail(vo);

        } else if(req.getCardApprovalStatus().equals(CuscreditVO.CardApprovalStatus.NOTPASS.getValue())){
            MailVO vo = new MailVO();
            vo.setEmailAddr(cusMail);
            vo.setSubject("信用卡申請未通過");
            vo.setContent("<h1>您申請的信用卡未通過</h1><h2>期待您未來使用</h2>");
            new JavaMail().sendMail(vo);
        }

        ResTool.setRes(res, ReviewRC.T1210.getCode(), ReviewRC.T1210.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    private CuscreditVO voCuscreditUpdate(ReviewReq req){
        CuscreditVO vo = new CuscreditVO();
        vo.setCid  		(req.getCid  	 ());
        vo.setCardType  (req.getCardType ());

        vo.setCardApprovalStatus (req.getCardApprovalStatus());
        vo.setCardNum   (genCreditCardNum());
        vo.setSecurityCode(genCVV(3));
        vo.setApplyRemark   (req.getApplyRemark());
        vo.setStatus		(CuscreditVO.STATUS.OK.getValue());
        return vo;
    }

    public static String genCVV(int length) {
        final Random random = new Random();
        if (length != 3 && length != 4) {
            throw new IllegalArgumentException("CVV length must be 3 or 4 digits");
        }

        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < length; i++) {
            cvv.append(random.nextInt(10));
        }
        return cvv.toString();
    }
    public static String genCreditCardNum() {
        int[] cardNumber = new int[16];
        final Random random = new Random();
        // Generate the first 15 digits randomly
        for (int i = 0; i < 15; i++) {
            cardNumber[i] = random.nextInt(10);
        }

        // Calculate the Luhn check digit
        cardNumber[15] = getCheckDigit(cardNumber);

        // Convert the number array to a string
        StringBuilder cardNumberStr = new StringBuilder();
        for (int digit : cardNumber) {
            cardNumberStr.append(digit);
        }

        return cardNumberStr.toString();
    }

    private static int getCheckDigit(int[] cardNumber) {
        int sum = 0;

        // Start from the rightmost digit and process every second digit
        for (int i = 14; i >= 0; i -= 2) {
            int doubled = cardNumber[i] * 2;
            sum += (doubled > 9) ? doubled - 9 : doubled;
        }

        // Add the digits that were not doubled
        for (int i = 13; i >= 0; i -= 2) {
            sum += cardNumber[i];
        }

        // The check digit is the amount needed to make the sum a multiple of 10
        return (10 - (sum % 10)) % 10;
    }
}
