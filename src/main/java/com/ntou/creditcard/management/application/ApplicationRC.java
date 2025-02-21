package com.ntou.creditcard.management.application;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApplicationRC {
      T1110("T1110" , "成功")
    , T111A("T111A" , "驗證有誤")
    , T111B("T111B" , "失敗")
    , T111C("T111C" , "新增失敗")
    , T111D("T111D" , "重複申請信用卡")

    ;
    private final String code;
    @Getter
    private final String content;

    @JsonValue
    public String getCode() {return code;}
}
