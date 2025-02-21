package com.ntou.creditcard.dispute.disputenotation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DisputeNotationRC {
      T1620("T1620" , "成功")
    , T162A("T162A" , "驗證有誤")
    , T162B("T162B" , "失敗")
    , T162C("T162C" , "更新註記失敗")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
