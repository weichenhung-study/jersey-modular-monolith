package com.ntou.creditcard.transactions.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntou.tool.JsonTool;
import com.ntou.tool.RegexpTool;
import lombok.Data;

@Data
public class TransactionReq {
    private String buyChannel     ="";//VARCHAR(2) 	消費通路(00:實體, 01:線上)
    private String buyDate        ="";//VARCHAR(23)	消費時間yyyy/MM/dd HH:MM:ss.SSS
    private String reqPaymentDate ="";//VARCHAR(23)	請款時間yyyy/MM/dd HH:MM:ss.SSS
    private String cardType       ="";//VARCHAR(5)	卡別
    private String shopId         ="";//VARCHAR(20)	消費店家(統編)
    private String cid            ="";//VARCHAR(10)	消費者(身分證)
    private String buyCurrency    ="";//VARCHAR(10)	消費幣別
    private String buyAmount      ="";//VARCHAR(10)	消費金額
    private String disputedFlag   ="";//VARCHAR(2) 	爭議款項註記(00:正常,01異常)
    private String status         ="";//VARCHAR(2) 	狀態(00:正常,99:註銷)
    private String remark         ="";//VARCHAR(50)	備註
    private String issuingBank    ="";//VARCHAR(50)	發卡銀行(swiftCode)
    private String cardNum        ="";//VARCHAR(20)	卡號
    private String securityCode   ="";//VARCHAR(10)	安全碼

    @JsonIgnore
    private String errMsg;
    public boolean checkReq(){
        if(!RegexpTool.LENGTH_2.matcher(buyChannel    ).matches()){this.errMsg = "消費通路(00:實體, 01:線上)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_23.matcher(buyDate       ).matches()){this.errMsg = "消費時間yyyy/MM/dd HH:MM:ss.SSS"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_23.matcher(reqPaymentDate).matches()){this.errMsg = "請款時間yyyy/MM/dd HH:MM:ss.SSS"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cardType      ).matches()){this.errMsg = "卡別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_20.matcher(shopId        ).matches()){this.errMsg = "消費店家(統編)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.CID.matcher(cid           ).matches()){this.errMsg = "消費者(身分證)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(buyCurrency   ).matches()){this.errMsg = "消費幣別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(buyAmount     ).matches()){this.errMsg = "消費金額"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_2.matcher(disputedFlag  ).matches()){this.errMsg = "爭議款項註記(00:正常,01異常)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_2.matcher(status        ).matches()){this.errMsg = "狀態(00:正常,99:註銷)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_50.matcher(remark        ).matches()){this.errMsg = "備註"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_50.matcher(issuingBank   ).matches()){this.errMsg = "發卡銀行(swiftCode)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_20.matcher(cardNum       ).matches()){this.errMsg = "卡號"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(securityCode  ).matches()){this.errMsg = "安全碼"+ RegexpTool.C_INVALID_NUM_LEN;return false;}

        return true;
    }

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
