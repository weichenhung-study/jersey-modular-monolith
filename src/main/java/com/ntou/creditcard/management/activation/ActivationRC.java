package com.ntou.creditcard.management.activation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ActivationRC {
      T1310("T1310" , "成功")
    , T131A("T131A" , "驗證有誤")
    , T131B("T131B" , "失敗")
    , T131C("T131C" , "新增失敗")
    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
