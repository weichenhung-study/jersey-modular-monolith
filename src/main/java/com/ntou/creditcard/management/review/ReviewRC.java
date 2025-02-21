package com.ntou.creditcard.management.review;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReviewRC {
      T1210("T1210" , "成功")
    , T121A("T121A" , "驗證有誤")
    , T121B("T121B" , "失敗")
    , T121C("T121C" , "新增失敗")
    , T121D("T121D" , "查詢失敗")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
