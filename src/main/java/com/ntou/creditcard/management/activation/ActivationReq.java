package com.ntou.creditcard.management.activation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntou.tool.JsonTool;
import com.ntou.tool.RegexpTool;
import lombok.Data;

@Data
public class ActivationReq {
    private String cid  					= "";//VARCHAR(20)	使用者身分證字號(限本國人士)
    private String cardType					= "";//VARCHAR(5)	卡別

    @JsonIgnore
    private String errMsg;
    public boolean checkReq(){
        if(!RegexpTool.CID.matcher(cid  	 ).matches()){this.errMsg = "使用者身分證字號(限本國人士)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cardType).matches()){this.errMsg = "卡別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        return true;
    }

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
