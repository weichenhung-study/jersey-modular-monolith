package com.ntou.creditcard.billing.feepayment;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FeePaymentRC {
      T1710("T1710" , "成功")
    , T171A("T171A" , "驗證有誤")
    , T171B("T171B" , "失敗")
    , T171C("T171C" , "更新註記失敗")
    , T171D("T171D" , "已有出帳帳單，請洽客服更正帳單內容")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
