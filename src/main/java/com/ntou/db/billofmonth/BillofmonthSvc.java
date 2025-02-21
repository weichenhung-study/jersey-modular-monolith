package com.ntou.db.billofmonth;

import java.util.ArrayList;

public interface BillofmonthSvc {
    int insertBill(BillofmonthVO vo) throws Exception;
    int updatePayDate(BillofmonthVO vo) throws Exception;
    ArrayList<BillofmonthVO> findBills(BillofmonthVO vo) throws Exception;
}
