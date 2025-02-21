package com.ntou;

import com.ntou.creditcard.transactions.transaction.*;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionControllerTest extends JerseyTest {

    private BillrecordSvc billrecordSvc;
    private CuscreditSvc cuscreditSvc;

    @Override
    protected javax.ws.rs.core.Application configure() {
        billrecordSvc = mock(BillrecordSvc.class);
        cuscreditSvc = mock(CuscreditSvc.class);
        Transaction transaction = new Transaction(billrecordSvc,cuscreditSvc);
        new TransactionController();
        return new ResourceConfig().register(new TransactionController(transaction));
    }
    private Transaction createTransaction() {
        return new Transaction(billrecordSvc,cuscreditSvc);
    }
    @Test
    void whenCheckReqIsFalse_shouldThrowException() {
        TransactionReq mockReq = mock(TransactionReq.class);
        when(mockReq.checkReq()).thenReturn(false);

        Transaction transaction = createTransaction();
        assertThrows(TException.class, () -> transaction.doAPI(mockReq));
    }

    @Test
    void whenCardHolderIsNull() throws Exception {
        TransactionReq mockReq = mock(TransactionReq.class);
        when(mockReq.checkReq()).thenReturn(true);
        when(cuscreditSvc.selectKey(mockReq.getCid(), mockReq.getCardNum())).thenReturn(null);

        Transaction transaction = createTransaction();
        assertThrows(TException.class, () -> transaction.doAPI(mockReq));
    }

    @Test
    void whenInsertBillRecordFails() throws Exception {
        TransactionReq mockReq = mock(TransactionReq.class);
        CuscreditVO mockCuscredit = new CuscreditVO();
        mockCuscredit.setEmail("test@example.com");

        when(mockReq.checkReq()).thenReturn(true);
        when(mockReq.getCid()).thenReturn("123");
        when(mockReq.getCardType()).thenReturn("VISA");
        when(mockReq.getCardNum()).thenReturn("4111111111111111");
        when(cuscreditSvc.selectKey("123", "4111111111111111")).thenReturn(mockCuscredit);
        when(billrecordSvc.insertCusDateBill(any())).thenReturn(0);

        Transaction transaction = createTransaction();
        assertThrows(TException.class, () -> transaction.doAPI(mockReq));
    }
    @Test
    void testDoController() throws Exception {
        TransactionReq request = new TransactionReq();
        setRequestData(request);
        CuscreditVO mockCuscredit = new CuscreditVO();
        when(cuscreditSvc.selectKey(request.getCid(),request.getCardNum())).thenReturn(mockCuscredit);
        when(billrecordSvc.insertCusDateBill(any())).thenReturn(1);

        Response response = target("Transaction")
                .request()
                .post(Entity.json(request));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        TransactionRes responseBody = response.readEntity(TransactionRes.class);
        assertEquals(TransactionRC.T1410.getCode(), responseBody.getResCode());
    }
    private void setRequestData(TransactionReq request){
        request.setBuyChannel("00");
        request.setBuyDate("2024/10/23 11:50:00.000");
        request.setReqPaymentDate("2024/08/16 12:00:00.000");
        request.setCardType("2");
        request.setShopId("倉鼠");
        request.setCid("B187654321");
        request.setBuyCurrency("NTD");
        request.setBuyAmount("1000");
        request.setDisputedFlag("00");
        request.setStatus("00");
        request.setRemark("");
        request.setIssuingBank("00");
        request.setCardNum("0542075205993832");
        request.setSecurityCode("447");
    }
}