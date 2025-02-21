package com.ntou;

import com.ntou.creditcard.billing.generatebill.GenerateBill;
import com.ntou.creditcard.billing.generatebill.GenerateBillController;
import com.ntou.creditcard.billing.generatebill.GenerateBillRC;
import com.ntou.creditcard.billing.generatebill.GenerateBillRes;
import com.ntou.db.billofmonth.BillofmonthSvc;
import com.ntou.db.billofmonth.BillofmonthVO;
import com.ntou.db.billrecord.BillrecordSvc;
import com.ntou.db.billrecord.BillrecordVO;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GenerateBillControllerTest {

    private GenerateBill generateBill;

    @Mock
    private BillofmonthSvc mockBillofmonthSvc;

    @Mock
    private BillrecordSvc mockBillrecordSvc;

    @Mock
    private CuscreditSvc mockCuscreditSvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        new GenerateBillController();
        generateBill = new GenerateBill(mockBillofmonthSvc, mockBillrecordSvc, mockCuscreditSvc);
        new GenerateBillController(generateBill).doController();
    }

    @Test
    public void testDoAPI_successfulResponse() throws Exception {
        // 模擬 billList
        ArrayList<BillrecordVO> billList = new ArrayList<>();
        BillrecordVO billRecord = new BillrecordVO();
        billRecord.setCid("A123456789");
        billRecord.setCardType("1");
        billRecord.setBuyAmount("5000");
        billRecord.setBuyDate("2023-01-01");
        billList.add(billRecord);
        when(mockBillrecordSvc.selectCusBill(any(), any(), any())).thenReturn(billList);

        CuscreditVO cuscredit = new CuscreditVO();
        cuscredit.setEmail("tuluber@gmail.com");
        when(mockCuscreditSvc.selectKey(anyString(), anyString())).thenReturn(cuscredit);

        // 模擬 billofmonthSvc 返回空的帳單清單
        when(mockBillofmonthSvc.findBills(any())).thenReturn(new ArrayList<>());
        when(mockBillofmonthSvc.insertBill(any())).thenReturn(1);

        // 執行測試方法
        Response response = generateBill.doAPI();
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        // 驗證返回的代碼
        GenerateBillRes res = (GenerateBillRes) response.getEntity();
        assertEquals(GenerateBillRC.T1510.getCode(), res.getResCode());
    }

    @Test
    public void testDoAPI_withExistingBill_throwsException() throws Exception {
        // 模擬 billList 和 CuscreditVO
        ArrayList<BillrecordVO> billList = new ArrayList<>();
        BillrecordVO billRecord = new BillrecordVO();
        billRecord.setCid("A123456789");
        billRecord.setCardType("1");
        billRecord.setBuyAmount("5000");
        billList.add(billRecord);
        when(mockBillrecordSvc.selectCusBill(any(), any(), any())).thenReturn(billList);

        CuscreditVO cuscredit = new CuscreditVO();
        cuscredit.setEmail("tuluber@gmail.com");
        when(mockCuscreditSvc.selectKey(anyString(), anyString())).thenReturn(cuscredit);

        // 模擬存在相同帳單情境
        ArrayList<BillofmonthVO> existingBills = new ArrayList<>();
        existingBills.add(new BillofmonthVO());
        when(mockBillofmonthSvc.findBills(any())).thenReturn(existingBills);

        // 驗證是否拋出 TException
        assertThrows(TException.class, () -> generateBill.doAPI());
    }

//    @Test
//    public void testDoAPI_invalidEmail_throwsException() throws Exception {
//        // 模擬 billList
//        ArrayList<BillrecordVO> billList = new ArrayList<>();
//        BillrecordVO billRecord = new BillrecordVO();
//        billRecord.setCid("A123456789");
//        billRecord.setCardType("1");
//        billRecord.setBuyAmount("5000");
//        billList.add(billRecord);
//        when(mockBillrecordSvc.selectCusBill(any(), any(), any())).thenReturn(billList);
//
//        // 模擬 CuscreditVO 無 email
//        CuscreditVO cuscredit = new CuscreditVO();
//        when(mockCuscreditSvc.selectKey(anyString(), anyString())).thenReturn(cuscredit);
//
//        // 驗證拋出 NullPointerException
//        assertThrows(NullPointerException.class, () -> generateBill.doAPI());
//    }xxx

    @Test
    public void testDoAPI_withEmptyBillList_returnsEmptyResponse() throws Exception {
        // 模擬空的 billList
        when(mockBillrecordSvc.selectCusBill(any(), any(), any())).thenReturn(new ArrayList<>());

        // 執行測試方法
        Response response = generateBill.doAPI();

        // 檢查回應狀態碼和內容
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        GenerateBillRes res = (GenerateBillRes) response.getEntity();
        assertEquals(GenerateBillRC.T1510.getCode(), res.getResCode());
    }
}
