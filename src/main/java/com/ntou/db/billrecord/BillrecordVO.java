package com.ntou.db.billrecord;

import com.ntou.tool.JsonTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillrecordVO {
    private String uuid				= ""; //VARCHAR(36)	, --交易編號UUID
    private String buyChannel		= ""; //VARCHAR(2) 	, --消費通路(00:實體, 01:線上)
    private String buyDate			= ""; //VARCHAR(23)	, --消費時間yyyy/MM/dd HH:MM:ss.SSS
    private String reqPaymentDate	= ""; //VARCHAR(23)	, --請款時間yyyy/MM/dd HH:MM:ss.SSS
    private String cardType			= ""; //VARCHAR(5)	, --卡別
    private String shopId			= ""; //VARCHAR(20)	, --消費店家(統編)
    private String cid				= ""; //VARCHAR(10)	, --消費者(身分證)
    private String buyCurrency		= ""; //VARCHAR(10)	, --消費幣別
    private String buyAmount		= ""; //VARCHAR(10)	, --消費金額
    private String disputedFlag		= ""; //VARCHAR(2) 	, --爭議款項註記(00:正常,01異常)
    private String status			= ""; //VARCHAR(2) 	, --狀態(00:正常,99:註銷)
    private String actuallyDate		= ""; //VARCHAR(23)	, --交易完成的時間yyyy/MM/dd HH:MM:ss.SSS
    private String remark			= ""; //VARCHAR(50)	, --備註
    private String issuingBank		= ""; //VARCHAR(50)	, --發卡銀行(swiftCode)
    private String cardNum			= ""; //VARCHAR(20)	, --卡號
    private String securityCode		= ""; //VARCHAR(10)	, --安全碼

    public static String encodeFormSQL(String s) {return s != null && !s.isEmpty() ? s : "";}

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
