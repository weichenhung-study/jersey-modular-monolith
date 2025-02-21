package com.ntou.db.billrecord;

import java.util.ArrayList;

public interface BillrecordSvc {
    int insertCusDateBill(BillrecordVO vo) throws Exception;
    ArrayList<BillrecordVO> selectCusBill(BillrecordVO vo, String startDate, String endDate) throws Exception;
    ArrayList<BillrecordVO> selectCusBillAll(BillrecordVO vo,String startDate, String endDate) throws Exception;
    int updateDisputedFlag(BillrecordVO vo) throws Exception;
}
