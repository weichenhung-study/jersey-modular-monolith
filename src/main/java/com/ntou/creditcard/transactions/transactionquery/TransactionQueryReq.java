package com.ntou.creditcard.transactions.transactionquery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntou.tool.JsonTool;
import com.ntou.tool.RegexpTool;
import lombok.Data;

@Data
public class TransactionQueryReq {
    private String cid          = "";//VARCHAR(10)	消費者(身分證)
    private String cardType		= "";//VARCHAR(5)	卡別
    private String startDate    = "";//帳務查詢起日
    private String endDate      = "";//帳務查詢迄日

    @JsonIgnore
    private String errMsg;
    public boolean checkReq(){
        if(!RegexpTool.CID.matcher(cid    ).matches()){this.errMsg = "消費者(身分證)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cardType).matches()){this.errMsg = "卡別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.DATE_YYYYMMDD.matcher(startDate).matches()){this.errMsg = "帳務查詢起日"+ RegexpTool.C_INVALID_DATE;return false;}
        if(!RegexpTool.DATE_YYYYMMDD.matcher(endDate).matches()){this.errMsg = "帳務查詢迄日"+ RegexpTool.C_INVALID_DATE;return false;}
        return true;
    }

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
