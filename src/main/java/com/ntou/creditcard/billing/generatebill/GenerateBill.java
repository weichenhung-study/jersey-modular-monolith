package com.ntou.creditcard.billing.generatebill;

import com.ntou.db.billofmonth.BillofmonthDAO;
import com.ntou.db.billofmonth.BillofmonthImpl;
import com.ntou.db.billofmonth.BillofmonthSvc;
import com.ntou.db.billofmonth.BillofmonthVO;
import com.ntou.db.billrecord.BillrecordDAO;
import com.ntou.db.billrecord.BillrecordImpl;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditImpl;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.sysintegrat.mailserver.JavaMail;
import com.ntou.sysintegrat.mailserver.MailVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import com.ntou.tool.DateTool;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/** 結信用卡費：該月1日至月底之交易金額 */
@Log4j2
public class GenerateBill {
    private final BillofmonthSvc billofmonthSvc;
    private final BillrecordSvc billrecordSvc;
    private final CuscreditSvc cuscreditSvc;
    public GenerateBill(BillofmonthSvc billofmonthSvc, BillrecordSvc billrecordSvc, CuscreditSvc cuscreditSvc) {
        this.billofmonthSvc = billofmonthSvc;
        this.billrecordSvc = billrecordSvc;
        this.cuscreditSvc = cuscreditSvc;
    }
    public GenerateBill() {
        this.billofmonthSvc = new BillofmonthImpl(new BillofmonthDAO());
        this.billrecordSvc = new BillrecordImpl(new BillrecordDAO());
        this.cuscreditSvc = new CuscreditImpl(new CuscreditDAO());
    }
    public Response doAPI() throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        GenerateBillRes res = new GenerateBillRes();

//      1. 到資料庫找到要寄送帳單的所有客
        ArrayList<BillrecordVO> billList = billrecordSvc
                .selectCusBill(voBillrecordSelect(), DateTool.getFirstDayOfMonth(), DateTool.getLastDayOfMonth());
//      2. 整理,將資料以卡身分證和卡別分組
        Map<String, List<BillrecordVO>> groupedData = billList.stream()
                .collect(Collectors.groupingBy(t -> t.getCid() + t.getCardType()));

        String yyyymm = DateTool.getFirstDayOfMonth().substring(0,7);
        MailVO vo = new MailVO();
        for (Map.Entry<String, List<BillrecordVO>> entry : groupedData.entrySet()) {
            String key = entry.getKey();
            List<BillrecordVO> value = entry.getValue();
            String cid = key.substring(0, 10);
            String cardType = key.substring(10, 11);
            CuscreditVO voCuscredit = cuscreditSvc.selectKey(cid, cardType);

            vo.setEmailAddr(voCuscredit.getEmail());
            vo.setSubject(cid + "卡別" + cardType + "的" + yyyymm + "月信用卡帳單");
            StringBuilder resultStr = new StringBuilder("<h1>您" + yyyymm + "月帳單，消費紀錄如下</h1>");
            long amt = 0;

            for (BillrecordVO action : value) {
                String str = "消費時間：" + action.getBuyDate() + "<br>"
                        + "消費店家：" + action.getShopId() + "<br>"
                        + "消費幣別：" + action.getBuyCurrency() + "<br>"
                        + "消費金額：" + action.getBuyAmount() + "<br>"
                        + "<br>";
                resultStr.append(str);
                amt += Integer.parseInt(action.getBuyAmount());
            }
            resultStr.append("消費總金額為：").append(amt);
            vo.setContent(String.valueOf(resultStr));
            new JavaMail().sendMail(vo);

            BillofmonthVO voBillofmonth = setBillofmonthVO(cid, cardType, value, String.valueOf(amt), yyyymm);
            ArrayList<BillofmonthVO> bills = billofmonthSvc.findBills(voBillofmonth);
            if(!bills.isEmpty())
                ResTool.commonThrow(res, GenerateBillRC.T151C.getCode(), GenerateBillRC.T151C.getContent());

            billofmonthSvc.insertBill(setBillofmonthVO(cid, cardType, billList, String.valueOf(amt),yyyymm));
        }

        ResTool.setRes(res, GenerateBillRC.T1510.getCode(), GenerateBillRC.T1510.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.CREATED).entity(res).build();
    }

    private BillofmonthVO setBillofmonthVO(String cid, String cardType, List<BillrecordVO> billList, String amt, String yyyymm){
        BillofmonthVO vo = new BillofmonthVO();
        vo.setUuid	            (UUID.randomUUID().toString());//VARCHAR(36)	交易編號UUID
        vo.setCid	            (cid);//VARCHAR(10)	消費者(身分證)
        vo.setCardType	        (cardType);//VARCHAR(5)	卡別
        vo.setWriteDate	        (DateTool.getDateTime		());//VARCHAR(23)	寫入時間yyyy/MM/dd HH:MM:ss.SSS
        vo.setBillData	        (billList.toString      ());//VARCHAR(255)	當月所有帳單資訊
        vo.setBillMonth          (yyyymm);
        vo.setAmt	            (amt);//VARCHAR(255)	帳單金額
        vo.setPaidAmount	    ("");//VARCHAR(255)	當月繳款金額
        vo.setNotPaidAmount	    ("");//VARCHAR(255)	剩餘未繳金額
        vo.setCycleRate	        ("");//VARCHAR(100)	循環利率
        vo.setCycleAmt	        ("");//VARCHAR(255)	循環金額
        vo.setSpaceCycleRate	("");//VARCHAR(100)	討論循環利率
        vo.setSpaceAmt	        ("");//VARCHAR(255)	討論金額
        vo.setPayDate("");
        return vo;
    }
    private BillrecordVO voBillrecordSelect(){
        BillrecordVO vo = new BillrecordVO();
        vo.setDisputedFlag      ("00");
        return vo;
    }
}
