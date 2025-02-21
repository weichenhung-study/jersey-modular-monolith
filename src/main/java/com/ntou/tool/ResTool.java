package com.ntou.tool;

import com.ntou.exceptions.TException;
import com.ntou.spec.SvcRes;

public class ResTool {
    public static void regularThrow(SvcRes res, String code , String content, String errMsg) throws TException {
        res.setResCode (code);
        res.setResMsg  (content + "(" + errMsg + ")");
        throw new TException(res);
    }
    public static void commonThrow(SvcRes res, String code , String content) throws TException {
        res.setResCode (code);
        res.setResMsg  (content);
        throw new TException(res);
    }
    public static void setRes(SvcRes res, String code , String content) {
        res.setResCode (code);
        res.setResMsg  (content);
    }
}
