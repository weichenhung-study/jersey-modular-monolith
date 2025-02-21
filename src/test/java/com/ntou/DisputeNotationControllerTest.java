package com.ntou;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.ntou.creditcard.dispute.disputenotation.*;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.exceptions.TException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

public class DisputeNotationControllerTest extends JerseyTest {

    private BillrecordSvc mockBillrecordSvc;

    @Override
    protected javax.ws.rs.core.Application configure() {
        mockBillrecordSvc = mock(BillrecordSvc.class);
        DisputeNotation disputeNotation = new DisputeNotation(mockBillrecordSvc); // 使用 Mock 物件建立 DisputeNotation 實例
        new DisputeNotationController();
        return new ResourceConfig().register(new DisputeNotationController(disputeNotation));
    }

    @Test
    public void whenCheckReqIsFalse_shouldThrowException() {
        DisputeNotationReq mockReq = mock(DisputeNotationReq.class);
        when(mockReq.checkReq()).thenReturn(false);

        DisputeNotation disputeNotation = new DisputeNotation(mockBillrecordSvc);
        assertThrows(TException.class, () -> disputeNotation.doAPI(mockReq));
    }

    @Test
    public void whenUpdateResultIsNotOne_shouldThrowTException() throws Exception {
        DisputeNotationReq mockReq = mock(DisputeNotationReq.class);

        when(mockReq.checkReq()).thenReturn(true);
        when(mockBillrecordSvc.updateDisputedFlag(any(BillrecordVO.class))).thenReturn(0); // 模擬 update 返回 0

        DisputeNotation disputeNotation = new DisputeNotation(mockBillrecordSvc);
        assertThrows(TException.class, () -> disputeNotation.doAPI(mockReq));
    }

    @Test
    public void testDoController_SuccessCase() throws Exception {
        DisputeNotationReq request = new DisputeNotationReq();
        setupRequestData(request);

        when(mockBillrecordSvc.updateDisputedFlag(any(BillrecordVO.class))).thenReturn(1); // 模擬 update 成功返回 1

        Response response = target("DisputeNotation")
                .request()
                .put(Entity.json(request));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        DisputeNotationRes responseBody = response.readEntity(DisputeNotationRes.class);
        assertEquals(DisputeNotationRC.T1620.getCode(), responseBody.getResCode()); // 成功代碼
    }

    private void setupRequestData(DisputeNotationReq request) {
        request.setUuid("fdc0c036-b1b0-4a6b-bc37-b5a83bc0a4f3");
    }
}
