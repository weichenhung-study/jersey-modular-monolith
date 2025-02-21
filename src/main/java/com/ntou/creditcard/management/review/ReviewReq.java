package com.ntou.creditcard.management.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntou.tool.JsonTool;
import com.ntou.tool.RegexpTool;
import lombok.Data;

@Data
public class ReviewReq {
    private String cid  					= "";//VARCHAR(20)	使用者身分證字號(限本國人士)
    private String cardType					= "";//VARCHAR(5)	卡別
    private String cardApprovalStatus       = "";//VARCHAR(2)   信用卡審核狀態；00：處理中、01：審核過、02：審核不通過
    private String applyRemark				= "";//VARCHAR(20)	信用卡申請狀態備註，如審核不通過的原因

    @JsonIgnore
    private String errMsg;
    public boolean checkReq(){
        if(!RegexpTool.CID.matcher(cid).matches()){this.errMsg = "使用者身分證字號(限本國人士)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cardType).matches()){this.errMsg = "卡別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_2.matcher(cardApprovalStatus).matches()){this.errMsg = "信用卡審核狀態"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_20.matcher(applyRemark).matches()){this.errMsg = "信用卡申請狀態備註，如審核不通過的原因"+ RegexpTool.C_INVALID_NUM_LEN;return false;}

        return true;
    }

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
