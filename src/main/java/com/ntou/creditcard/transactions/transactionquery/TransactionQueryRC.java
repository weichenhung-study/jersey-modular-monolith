package com.ntou.creditcard.transactions.transactionquery;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TransactionQueryRC {
      T1610("T1610" , "成功")
    , T161A("T161A" , "驗證有誤")
    , T161B("T161B" , "失敗")
    , T161C("T161C" , "更新註記失敗")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
