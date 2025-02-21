package com.ntou.creditcard.billing.feepayment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntou.tool.JsonTool;
import com.ntou.tool.RegexpTool;
import lombok.Data;

@Data
public class FeePaymentReq {
    private String cid          = "";//VARCHAR(10)	消費者(身分證)
    private String cardType		= "";//VARCHAR(5)	卡別
    private String payDate      = "";//繳費日期
    private String payAmt       = "";//繳款金額

    @JsonIgnore
    private String errMsg;
    public boolean checkReq(){
        if(!RegexpTool.CID.matcher(cid    ).matches()){this.errMsg = "消費者(身分證)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cardType).matches()){this.errMsg = "卡別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_7.matcher(payDate).matches()){this.errMsg = "繳費日期"+ RegexpTool.C_INVALID_LEN;return false;}
        return true;
    }

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
