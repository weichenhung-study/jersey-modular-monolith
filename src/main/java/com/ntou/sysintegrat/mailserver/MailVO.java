package com.ntou.sysintegrat.mailserver;

import com.ntou.tool.JsonTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailVO {
    private String emailAddr    = "";
    private String subject      = "";
    private String content      = "";

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
