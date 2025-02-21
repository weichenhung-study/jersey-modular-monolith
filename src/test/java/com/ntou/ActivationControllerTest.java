package com.ntou;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.ntou.creditcard.management.activation.*;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

public class ActivationControllerTest extends JerseyTest {

    private CuscreditSvc mockCuscreditSvc;

    @Override
    protected javax.ws.rs.core.Application configure() {
        mockCuscreditSvc = mock(CuscreditSvc.class);
        Activation activation = new Activation(mockCuscreditSvc);
        new ActivationController();
        return new ResourceConfig().register(new ActivationController(activation));
    }

    @Test
    public void whenCheckReqIsFalse_shouldThrowException() {
        ActivationReq mockReq = mock(ActivationReq.class);
        when(mockReq.checkReq()).thenReturn(false);

        Activation activation = new Activation(mockCuscreditSvc);
        assertThrows(TException.class, () -> activation.doAPI(mockReq));
    }

    @Test
    public void whenUpdateResultIsNotOne_shouldThrowTException() throws Exception {
        ActivationReq mockReq = mock(ActivationReq.class);
        CuscreditVO mockCuscreditVO = mock(CuscreditVO.class);

        when(mockReq.checkReq()).thenReturn(true);
        when(mockCuscreditSvc.selectKey(mockReq.getCid(), mockReq.getCardType())).thenReturn(mockCuscreditVO);
        when(mockCuscreditSvc.updateActivationRecord(any(CuscreditVO.class))).thenReturn(0); // 模擬 update 返回 0

        Activation activation = new Activation(mockCuscreditSvc);
        assertThrows(TException.class, () -> activation.doAPI(mockReq));
    }

    @Test
    public void testDoController_SuccessCase() throws Exception {
        ActivationReq request = new ActivationReq();
        setupRequestData(request);

        CuscreditVO mockCuscreditVO = new CuscreditVO();
        mockCuscreditVO.setEmail("tuluber@gmail.com");

        when(mockCuscreditSvc.selectKey(request.getCid(), request.getCardType())).thenReturn(mockCuscreditVO);
        when(mockCuscreditSvc.updateActivationRecord(any(CuscreditVO.class))).thenReturn(1); // 模擬 update 成功返回 1

        Response response = target("Activation")
                .request()
                .put(Entity.json(request));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        ActivationRes responseBody = response.readEntity(ActivationRes.class);
        assertEquals(ActivationRC.T1310.getCode(), responseBody.getResCode()); // 成功代碼
    }

    private void setupRequestData(ActivationReq request) {
        request.setCid("B253654321");
        request.setCardType("2");
    }
}
