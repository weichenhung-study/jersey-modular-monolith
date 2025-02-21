package com.ntou;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.ntou.creditcard.billing.feepayment.*;
import com.ntou.db.billofmonth.BillofmonthSvc;
import com.ntou.db.billofmonth.BillofmonthVO;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FeePaymentControllerTest extends JerseyTest {

    private BillofmonthSvc mockBillofmonthSvc;
    private CuscreditSvc mockCuscreditSvc;

    @Override
    protected javax.ws.rs.core.Application configure() {
        mockBillofmonthSvc = mock(BillofmonthSvc.class);
        mockCuscreditSvc = mock(CuscreditSvc.class);
        FeePayment feePayment = new FeePayment(mockBillofmonthSvc, mockCuscreditSvc);
        new FeePaymentController();
        return new ResourceConfig().register(new FeePaymentController(feePayment));
    }

    @Test
    public void whenCheckReqIsFalse_shouldThrowException() {
        FeePaymentReq mockReq = mock(FeePaymentReq.class);
        when(mockReq.checkReq()).thenReturn(false);

        FeePayment feePayment = new FeePayment(mockBillofmonthSvc, mockCuscreditSvc);
        assertThrows(TException.class, () -> feePayment.doAPI(mockReq));
    }

    @Test
    public void whenUpdateResultIsNotOne_shouldThrowException() throws Exception {
        FeePaymentReq mockReq = mock(FeePaymentReq.class);
        when(mockReq.checkReq()).thenReturn(true);
        when(mockReq.getPayAmt()).thenReturn("18000");

        // 模擬返回的 BillofmonthVO，並設置可解析的 amt 值
        BillofmonthVO mockBill = new BillofmonthVO();
        mockBill.setAmt("18000");
        ArrayList<BillofmonthVO> billList = new ArrayList<>();
        billList.add(mockBill);

        when(mockBillofmonthSvc.findBills(any(BillofmonthVO.class))).thenReturn(billList);
        when(mockBillofmonthSvc.updatePayDate(any(BillofmonthVO.class))).thenReturn(0); // 模擬更新失敗

        FeePayment feePayment = new FeePayment(mockBillofmonthSvc, mockCuscreditSvc);

        assertThrows(TException.class, () -> feePayment.doAPI(mockReq));
    }

    @Test
    public void whenBillListSizeNotOne_shouldThrowException() throws Exception {
        FeePaymentReq mockReq = mock(FeePaymentReq.class);
        when(mockReq.checkReq()).thenReturn(true);
        when(mockBillofmonthSvc.findBills(any(BillofmonthVO.class))).thenReturn(new ArrayList<>());

        FeePayment feePayment = new FeePayment(mockBillofmonthSvc, mockCuscreditSvc);
        assertThrows(TException.class, () -> feePayment.doAPI(mockReq));
    }

    @Test
    public void testDoController_SuccessCase() throws Exception {
        FeePaymentReq request = new FeePaymentReq();
        setupRequestData(request);

        when(mockBillofmonthSvc.findBills(any(BillofmonthVO.class))).thenReturn(singleBillList());
        when(mockBillofmonthSvc.updatePayDate(any(BillofmonthVO.class))).thenReturn(1);
        when(mockCuscreditSvc.selectKey(request.getCid(), request.getCardType())).thenReturn(mockCuscreditVO());

        Response response = target("FeePayment")
                .request()
                .post(Entity.json(request));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        FeePaymentRes responseBody = response.readEntity(FeePaymentRes.class);
        assertEquals(FeePaymentRC.T1710.getCode(), responseBody.getResCode());
    }

    private void setupRequestData(FeePaymentReq request) {
        request.setCid("B253654321");
        request.setCardType("2");
        request.setPayDate("2024/10");
        request.setPayAmt("18000");
    }

    private ArrayList<BillofmonthVO> singleBillList() {
        BillofmonthVO bill = new BillofmonthVO();
        bill.setAmt("18000");
        ArrayList<BillofmonthVO> list = new ArrayList<>();
        list.add(bill);
        return list;
    }

    private CuscreditVO mockCuscreditVO() {
        CuscreditVO cuscredit = new CuscreditVO();
        cuscredit.setEmail("tuluber@gmail.com");
        return cuscredit;
    }
}
