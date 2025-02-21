package com.ntou;

import com.ntou.creditcard.transactions.transaction.TransactionRes;
import com.ntou.creditcard.transactions.transactionquery.TransactionQuery;
import com.ntou.creditcard.transactions.transactionquery.TransactionQueryController;
import com.ntou.creditcard.transactions.transactionquery.TransactionQueryRC;
import com.ntou.creditcard.transactions.transactionquery.TransactionQueryReq;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionQueryControllerTest extends JerseyTest {
    private BillrecordSvc billrecordSvc;

    @Override
    protected javax.ws.rs.core.Application configure() {
        billrecordSvc = mock(BillrecordSvc.class);
        TransactionQuery transactionQuery = new TransactionQuery(billrecordSvc);
        new TransactionQueryController();
        return new ResourceConfig().register(new TransactionQueryController(transactionQuery));
    }
    private TransactionQuery createTransactionQuery() {
        return new TransactionQuery(billrecordSvc);
    }
    @Test
    void whenRequestIsInvalid_shouldThrowException() {
        TransactionQueryReq mockReq = mock(TransactionQueryReq.class);
        when(mockReq.checkReq()).thenReturn(false);
        TransactionQuery transactionQuery = createTransactionQuery();
        assertThrows(TException.class, () -> transactionQuery.doAPI(mockReq));
    }
    @Test
    void testDoController() throws Exception {
        TransactionQueryReq request = new TransactionQueryReq();
        CuscreditVO mockCuscredit = new CuscreditVO();
        when(billrecordSvc.insertCusDateBill(any())).thenReturn(1);

        WebTarget target = target("TransactionQuery")
                .queryParam("cid", "B187654321")
                .queryParam("cardType", "2")
                .queryParam("startDate", "2024/01/01")
                .queryParam("endDate", "2024/11/30");
        Response response = target.request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        TransactionRes responseBody = response.readEntity(TransactionRes.class);
        assertEquals(TransactionQueryRC.T1610.getCode(), responseBody.getResCode()); // 成功代碼
    }
}