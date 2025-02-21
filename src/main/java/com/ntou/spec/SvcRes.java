package com.ntou.spec;

import com.ntou.tool.JsonTool;
import com.ntou.tool.DateTool;
import lombok.Data;

@Data
public class SvcRes {
    private String resCode      ;
    private String resMsg       ;
    private final String resTime = DateTool.getDateTime();

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
