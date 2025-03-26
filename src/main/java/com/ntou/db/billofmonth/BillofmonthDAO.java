package com.ntou.db.billofmonth;

import com.ntou.db.ConnControl;
import com.ntou.tool.Common;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Log4j2
public class BillofmonthDAO extends ConnControl {
    final String TABLE_BILLOFMONTH      = "billofmonth";
    final String COL_UUID				="uuid";//VARCHAR(36)	交易編號UUID
    final String COL_CID				="cid";//VARCHAR(10)	消費者(身分證)
    final String COL_CARDTYPE			="cardType";//VARCHAR(5)	卡別
    final String COL_WRITEDATE			="writeDate";//VARCHAR(23)	寫入時間yyyy/MM/dd HH:MM:ss.SSS
    final String COL_BILLDATA			="billData";//VARCHAR(MAX)	當月所有帳單資訊
    final String COL_BILLMONTH          ="billMonth";//VARCHAR(7)	帳單月份yyyy/mm
    final String COL_AMT			    ="amt";//VARCHAR(MAX)	當月所有帳單資訊
    final String COL_PAIDAMOUNT			="paidAmount";//VARCHAR(MAX)	當月繳款金額
    final String COL_NOTPAIDAMOUNT		="notPaidAmount";//VARCHAR(MAX)	剩餘未繳金額
    final String COL_CYCLERATE			="cycleRate";//VARCHAR(100)	循環利率
    final String COL_CYCLEAMT			="cycleAmt";//VARCHAR(MAX)	循環金額
    final String COL_SPACECYCLERATE		="spaceCycleRate";//VARCHAR(100)	討論循環利率
    final String COL_SPACEAMT			="spaceAmt";//VARCHAR(MAX)	討論金額
    final String COL_PAYDATE            ="payDate";
    public int insertBill(BillofmonthVO vo) throws Exception{
        log.info(Common.ARROW + "insertBill" + Common.START_B);
        log.info(Common.VO + vo);
        int result;
        Connection conn = getConnection();
        String SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                , TABLE_BILLOFMONTH
                , COL_UUID
                , COL_CID
                , COL_CARDTYPE
                , COL_WRITEDATE
                , COL_BILLDATA
                , COL_BILLMONTH
                , COL_AMT
                , COL_PAIDAMOUNT
                , COL_NOTPAIDAMOUNT
                , COL_CYCLERATE
                , COL_CYCLEAMT
                , COL_SPACECYCLERATE
                , COL_SPACEAMT
                , COL_PAYDATE
        );
        PreparedStatement pstmt = null;
        int i = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i, vo.getUuid				());
            pstmt.setString(++i, vo.getCid				());
            pstmt.setString(++i, vo.getCardType			());
            pstmt.setString(++i, vo.getWriteDate		());
            pstmt.setString(++i, vo.getBillData			());
            pstmt.setString(++i, vo.getBillMonth		());
            pstmt.setString(++i, vo.getAmt		        ());
            pstmt.setString(++i, vo.getPaidAmount		());
            pstmt.setString(++i, vo.getNotPaidAmount	());
            pstmt.setString(++i, vo.getCycleRate		());
            pstmt.setString(++i, vo.getCycleAmt			());
            pstmt.setString(++i, vo.getSpaceCycleRate	());
            pstmt.setString(++i, vo.getSpaceAmt			());
            pstmt.setString(++i, vo.getPayDate());

            result = pstmt.executeUpdate();
            pstmt.clearParameters();
            log.info(Common.RESULT + result);
        } finally {
            closePS(pstmt);
            closeConn(conn);
            log.info(Common.ARROW + "insertBill" + Common.END_B);
        }
        return result;
    }
    public int updatePayDate(BillofmonthVO vo) throws Exception{
        log.info(Common.ARROW + "updatePayDate" + Common.START_B);
        log.info(Common.VO + vo);
        Connection conn = getConnection();
        String SQL = String.format("update %s set %s=?,%s=?,%s=? where %s=? and %s=? and %s=?"
                ,TABLE_BILLOFMONTH
                ,COL_PAIDAMOUNT
                ,COL_NOTPAIDAMOUNT
                ,COL_PAYDATE

                ,COL_CID
                ,COL_CARDTYPE
                ,COL_BILLMONTH
        );
        PreparedStatement pstmt = null;
        int out;
        int i = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i , vo.getPaidAmount());
            pstmt.setString(++i , vo.getNotPaidAmount());
            pstmt.setString(++i , vo.getPayDate());

            pstmt.setString(++i , vo.getCid());
            pstmt.setString(++i , vo.getCardType());
            pstmt.setString(++i , vo.getBillMonth());

            out = pstmt.executeUpdate();
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            closeConn(conn);
            log.info(Common.ARROW + "updatePayDate" + Common.END_B);
        }
        return out;
    }
    public ArrayList<BillofmonthVO> findBills(BillofmonthVO vo) throws Exception{
        log.info(Common.ARROW + "findBills" + Common.START_B);
        log.info("vo:" + vo);
        Connection conn = getConnection();
        String SQL = String.format("select * from %s where %s=? and %s=? and %s=?"
                ,TABLE_BILLOFMONTH
                ,COL_CID
                ,COL_CARDTYPE
                ,COL_BILLMONTH
        );
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BillofmonthVO> out = new ArrayList<>();
        int i = 0;
        int j = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i , vo.getCid());
            pstmt.setString(++i , vo.getCardType());
            pstmt.setString(++i , vo.getBillMonth());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                out.add(new BillofmonthVO(
                        BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillofmonthVO.encodeFormSQL(rs.getString(++j).trim())
                ));
                j = 0;
            }
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            closeRS(rs);
            closeConn(conn);
            log.info(Common.ARROW + "findBills" + Common.END_B);
        }
        return out;
    }
}
