package com.ntou.db.billrecord;

import java.util.ArrayList;

public class BillrecordImpl implements BillrecordSvc {
    private final BillrecordDAO billrecordDAO;

    public BillrecordImpl(BillrecordDAO billrecordDAO) {
        this.billrecordDAO = billrecordDAO;
    }

    @Override
    public int insertCusDateBill(BillrecordVO vo) throws Exception {
        return billrecordDAO.insertCusDateBill(vo);
    }

    @Override
    public ArrayList<BillrecordVO> selectCusBill(BillrecordVO vo, String startDate, String endDate) throws Exception {
        return billrecordDAO.selectCusBill(vo, startDate, endDate);
    }

    @Override
    public ArrayList<BillrecordVO> selectCusBillAll(BillrecordVO vo, String startDate, String endDate) throws Exception {
        return billrecordDAO.selectCusBillAll(vo, startDate, endDate);
    }

    @Override
    public int updateDisputedFlag(BillrecordVO vo) throws Exception {
        return billrecordDAO.updateDisputedFlag(vo);
    }
}
