package com.ntou.creditcard.transactions.transaction;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TransactionRC {
      T1410("T1410" , "成功")
    , T141A("T141A" , "驗證有誤")
    , T141B("T141B" , "失敗")
    , T141C("T141C" , "更新失敗")
    , T141D("T141D" , "信用卡未開卡或不存在此信用卡")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
