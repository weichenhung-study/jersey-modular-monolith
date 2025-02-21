package com.ntou.creditcard.dispute.disputenotation;

import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.tool.Common;
import com.ntou.tool.ResTool;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;

/** 爭議款項申請:上註記 */
@Log4j2
public class DisputeNotation {
    private final BillrecordSvc billrecordSvc;

    public DisputeNotation(BillrecordSvc billrecordSvc) {
        this.billrecordSvc = billrecordSvc;
    }
    public Response doAPI(DisputeNotationReq req) throws Exception {
        log.info(Common.API_DIVIDER + Common.START_B + Common.API_DIVIDER);
        log.info(Common.REQ + req);
        DisputeNotationRes res = new DisputeNotationRes();

            if(!req.checkReq())
                ResTool.regularThrow(res, DisputeNotationRC.T162A.getCode(), DisputeNotationRC.T162A.getContent(), req.getErrMsg());

            int updateResult = billrecordSvc
                    .updateDisputedFlag(voBillrecordSelect(req));
            if(updateResult !=1)
                ResTool.commonThrow(res, DisputeNotationRC.T162C.getCode(), DisputeNotationRC.T162C.getContent());

        ResTool.setRes(res, DisputeNotationRC.T1620.getCode(), DisputeNotationRC.T1620.getContent());

        log.info(Common.RES + res);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    private BillrecordVO voBillrecordSelect(DisputeNotationReq req){
        BillrecordVO vo = new BillrecordVO();
        vo.setUuid		(req.getUuid());
        return vo;
    }
}
