package com.ntou.creditcard.billing.generatebill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntou.tool.JsonTool;
import com.ntou.tool.RegexpTool;
import lombok.Data;

@Data
public class GenerateBillReq {
    private String cid          = "";//VARCHAR(10)	消費者(身分證)
    private String cardType		= "";//VARCHAR(5)	卡別

    @JsonIgnore
    private String errMsg;
    public boolean checkReq(){
        if(!RegexpTool.CID.matcher(cid     ).matches()){this.errMsg = "消費者(身分證)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cardType ).matches()){this.errMsg = "卡別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        return true;
    }

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
