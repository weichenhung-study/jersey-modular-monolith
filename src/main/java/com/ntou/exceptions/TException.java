package com.ntou.exceptions;

import com.ntou.spec.SvcRes;
import com.ntou.tool.JsonTool;

public class TException extends Exception {
    public String rc;
    public String msg;
    public SvcRes res;

    public TException(SvcRes res){
        this.res = res;
        rc = res.getResCode();
        msg = JsonTool.format2Json(res);
    }
}
