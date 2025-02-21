package com.ntou.db.billrecord;

import com.ntou.db.ConnControl;
import com.ntou.tool.Common;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Log4j2
public class BillrecordDAO extends ConnControl {
    final String TABLE_BILLRECORD       = "billrecord";
    final String COL_UUID				= "uuid"; 			//VARCHAR(36)	, --交易編號UUID
    final String COL_BUYCHANNEL			= "buyChannel"; 	//VARCHAR(2) 	, --消費通路(00:實體, 01:線上)
    final String COL_BUYDATE			= "buyDate"; 		//VARCHAR(23)	, --消費時間yyyy/MM/dd HH:MM:ss.SSS
    final String COL_REQPAYMENTDATE		= "reqPaymentDate"; //VARCHAR(23)	, --請款時間yyyy/MM/dd HH:MM:ss.SSS
    final String COL_CARDTYPE			= "cardType"; 		//VARCHAR(5)	, --卡別
    final String COL_SHOPID				= "shopId"; 		//VARCHAR(20)	, --消費店家(統編)
    final String COL_CID				= "cid";			//VARCHAR(10)	, --消費者(身分證)
    final String COL_BUYCURRENCY		= "buyCurrency"; 	//VARCHAR(10)	, --消費幣別
    final String COL_BUYAMOUNT			= "buyAmount"; 		//VARCHAR(10)	, --消費金額
    final String COL_DISPUTEDFLAG		= "disputedFlag"; 	//VARCHAR(2) 	, --爭議款項註記(00:正常,01異常)
    final String COL_STATUS				= "status"; 		//VARCHAR(2) 	, --狀態(00:正常,99:註銷)
    final String COL_ACTUALLYDATE		= "actuallyDate"; 	//VARCHAR(23)	, --交易完成的時間yyyy/MM/dd HH:MM:ss.SSS
    final String COL_REMARK				= "remark"; 		//VARCHAR(50)	, --備註
    final String COL_ISSUINGBANK		= "issuingBank"; 	//VARCHAR(50)	, --發卡銀行(swiftCode)
    final String COL_CARDNUM			= "cardNum"; 		//VARCHAR(20)	, --卡號
    final String COL_SECURITYCODE		= "securityCode"; 	//VARCHAR(10)	, --安全碼

    public int insertCusDateBill(BillrecordVO vo) throws Exception{
        log.info(Common.ARROW + "insertCusDateBill" + Common.START_B);
        log.info(Common.VO + vo);
        int result;
        Connection conn = getConnection();
        String SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                , TABLE_BILLRECORD
                , COL_UUID
                , COL_BUYCHANNEL
                , COL_BUYDATE
                , COL_REQPAYMENTDATE
                , COL_CARDTYPE
                , COL_SHOPID
                , COL_CID
                , COL_BUYCURRENCY
                , COL_BUYAMOUNT
                , COL_DISPUTEDFLAG
                , COL_STATUS
                , COL_ACTUALLYDATE
                , COL_REMARK
                , COL_ISSUINGBANK
                , COL_CARDNUM
                , COL_SECURITYCODE
        );
        PreparedStatement pstmt = null;
        int i = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i, vo.getUuid				());
            pstmt.setString(++i, vo.getBuyChannel		());
            pstmt.setString(++i, vo.getBuyDate			());
            pstmt.setString(++i, vo.getReqPaymentDate	());
            pstmt.setString(++i, vo.getCardType			());
            pstmt.setString(++i, vo.getShopId			());
            pstmt.setString(++i, vo.getCid				());
            pstmt.setString(++i, vo.getBuyCurrency		());
            pstmt.setString(++i, vo.getBuyAmount		());
            pstmt.setString(++i, vo.getDisputedFlag		());
            pstmt.setString(++i, vo.getStatus			());
            pstmt.setString(++i, vo.getActuallyDate		());
            pstmt.setString(++i, vo.getRemark			());
            pstmt.setString(++i, vo.getIssuingBank		());
            pstmt.setString(++i, vo.getCardNum			());
            pstmt.setString(++i, vo.getSecurityCode		());

            result = pstmt.executeUpdate();
            pstmt.clearParameters();
            log.info(Common.RESULT + result);
        } finally {
            closePS(pstmt);
            log.info(Common.ARROW + "insertCusDateBill" + Common.END_B);
        }
        return result;
    }

    public ArrayList<BillrecordVO> selectCusBill(BillrecordVO vo,String startDate, String endDate) throws Exception{
        log.info(Common.ARROW + "selectCusBill" + Common.START_B);
        log.info(Common.VO + vo + " ,startDate: " + startDate + " ,endDate: " + endDate);
        Connection conn = getConnection();
        String SQL = String.format("select * from %s where substring(%s,1,10) between ? and ? and %s='00' order by %s desc"
                , TABLE_BILLRECORD
                , COL_BUYDATE
                , COL_DISPUTEDFLAG

                , COL_BUYDATE
        );
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BillrecordVO> out = new ArrayList<>();
        int i = 0;
        int j = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i, startDate);
            pstmt.setString(++i, endDate);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                out.add(new BillrecordVO(
                        BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                ));
                j = 0;
            }
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            closeRS(rs);
            log.info(Common.ARROW + "selectCusBill" + Common.END_B);
        }
        return out;
    }
    public ArrayList<BillrecordVO> selectCusBillAll(BillrecordVO vo,String startDate, String endDate) throws Exception{
        log.info(Common.ARROW + "selectCusBill" + Common.START_B);
        log.info(Common.VO + vo + " ,startDate: " + startDate + " ,endDate: " + endDate);
        Connection conn = getConnection();
        String SQL = String.format("select * from %s where %s=? and %s=? and substring(%s,1,10) between ? and ? order by %s desc"
                , TABLE_BILLRECORD
                , COL_CID
                , COL_CARDTYPE
                , COL_BUYDATE

                , COL_BUYDATE
        );
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<BillrecordVO> out = new ArrayList<>();
        int i = 0;
        int j = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i, vo.getCid());
            pstmt.setString(++i, vo.getCardType());
            pstmt.setString(++i, startDate);
            pstmt.setString(++i, endDate);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                out.add(new BillrecordVO(
                        BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                        , BillrecordVO.encodeFormSQL(rs.getString(++j).trim())
                ));
                j = 0;
            }
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            closeRS(rs);
            log.info(Common.ARROW + "selectCusBill" + Common.END_B);
        }
        return out;
    }
    public int updateDisputedFlag(BillrecordVO vo) throws Exception{
        log.info(Common.ARROW + "updateDisputedFlag" + Common.START_B);
        log.info(Common.VO + vo);
        Connection conn = getConnection();
        String SQL = String.format("update %s set %s='01' where %s=?"
                ,TABLE_BILLRECORD
                ,COL_DISPUTEDFLAG

                ,COL_UUID
        );
        PreparedStatement pstmt = null;
        int out;
        int i = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i , vo.getUuid());

            out = pstmt.executeUpdate();
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            log.info(Common.ARROW + "updateDisputedFlag" + Common.END_B);
        }
        return out;
    }
}