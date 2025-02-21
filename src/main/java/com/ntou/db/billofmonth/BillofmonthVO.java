package com.ntou.db.billofmonth;

import com.ntou.tool.JsonTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillofmonthVO {
    private String uuid				="";//VARCHAR(36)	交易編號UUID
    private String cid				="";//VARCHAR(10)	消費者(身分證)
    private String cardType			="";//VARCHAR(5)	卡別
    private String writeDate		="";//VARCHAR(23)	寫入時間yyyy/MM/dd HH:MM:ss.SSS
    private String billData			="";//VARCHAR(255)	當月所有帳單資訊
    private String billMonth        ="";//VARCHAR(7)	帳單月份yyyy/mm
    private String amt			    ="";//VARCHAR(255)	當月所有帳單資訊
    private String paidAmount		="";//VARCHAR(255)	當月繳款金額
    private String notPaidAmount	="";//VARCHAR(255)	剩餘未繳金額
    private String cycleRate		="";//VARCHAR(100)	循環利率
    private String cycleAmt			="";//VARCHAR(255)	循環金額
    private String spaceCycleRate	="";//VARCHAR(100)	討論循環利率
    private String spaceAmt			="";//VARCHAR(255)	討論金額
    private String payDate          ="";//VARCHAR(23)	繳款時間yyyy/MM/dd HH:MM:ss.SSS

    public static String encodeFormSQL(String s) {return s != null && !s.isEmpty() ? s : "";}

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
