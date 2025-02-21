package com.ntou.db.cuscredit;

import com.ntou.db.ConnControl;
import com.ntou.tool.Common;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Log4j2
public class CuscreditDAO extends ConnControl {
    final String TABLE_CUSCREDIT	        ="cuscredit";
    final String COL_CHNAME					="chName";               //VARCHAR(50)	中文姓名
    final String COL_ENNAME					="enName";               //VARCHAR(50)	VARCHAR(50)
    final String COL_CID  					="cid";                  //VARCHAR(20)	使用者身分證字號(限本國人士)
    final String COL_CIDREISSUEDATE			="cidReissueDate";       //VARCHAR(10)	身分證換發日期 民國yyy/mm/dd
    final String COL_CIDREISSUELOCATION		="cidReissueLocation";   //VARCHAR(10)	身分證換發地點
    final String COL_CIDREISSUESTATUS		="cidReissueStatus";     //VARCHAR(5)	身分證換發狀態
    final String COL_BIRTHDATE				="birthDate";            //VARCHAR(10)	出生日期西元年月日 yyyy/mm/dd
    final String COL_MARITALSTATUS			="maritalStatus";        //VARCHAR(2)	婚姻；已婚：01、未婚：02
    final String COL_EDUCATION				="education";            //VARCHAR(2)	學歷；00：無、01：國小、02：國中、03：高中、04：大學、05：碩士、06：博士
    final String COL_CURRENTRESIDENTIALADD	="currentResidentialAdd";//VARCHAR(255)	現居住址
    final String COL_RESIDENTIALADD			="residentialAdd";       //VARCHAR(255)	戶籍地址
    final String COL_CELLPHONE				="cellphone";            //VARCHAR(10)	連絡手機號碼
    final String COL_EMAIL					="email";                //VARCHAR(100)	連絡電子信箱
    final String COL_COMPANYNAME			="companyName";          //VARCHAR(100)	申請人公司名稱
    final String COL_COMPANYINDUSTRY		="companyIndustry";      //VARCHAR(2)	申請人行業別(01：服務業、02：工業、03：金融保險業)
    final String COL_OCCUPATION				="occupation";           //VARCHAR()	申請人職業
    final String COL_DEPARTMENT				="department";           //VARCHAR()	申請人部門
    final String COL_JOBTITLE				="jobTitle";             //VARCHAR()	申請人職稱
    final String COL_DATEOFEMPLOYMENT		="dateOfEmployment";     //VARCHAR()	到職日
    final String COL_COMPANYADDRESS			="companyAddress";       //VARCHAR(255)	公司地址
    final String COL_COMPANYPHONENUM		="companyPhoneNum";      //VARCHAR(20)	電話021234567#3654
    final String COL_ANNUALINCOME			="annualIncome";         //VARCHAR(5)	年收入/萬
    final String COL_CARDAPPROVALSTATUS		="cardApprovalStatus";   //VARCHAR(2)	信用卡審核狀態；00：處理中、01：審核過、02：審核不通過
    final String COL_APPLYREMARK			="ApplyRemark";          //VARCHAR(20)	信用卡申請狀態備註，如審核不通過的原因
    final String COL_ACTIVATIONRECORD		="activationRecord";     //VARCHAR(2)	信用卡開卡紀錄(00：未開卡、01：已開卡)
    final String COL_EVENTCODE				="eventCode";            //VARCHAR(10)	活動代碼
    final String COL_REGIDATE 				="regidate";             //VARCHAR(23)	信用卡銀行通過(核發)時間yyyy/MM/dd HH:mm:ss.SSS
    final String COL_ISSUING_BANK 			="issuing_bank";         //VARCHAR(15)	核卡銀行(swiftCode)
    final String COL_CARDNUM				="cardNum";              //VARCHAR(20)	信用卡號碼
    final String COL_SECURITYCODE			="securityCode";         //VARCHAR(5)	信用卡安全碼
    final String COL_STATUS					="status";               //VARCHAR(2)	信用卡狀態(00：正常,99：註銷)
    final String COL_CARDTYPE               = "cardType";            //VARCHAR(5)	卡別

    final String COL_REMARK					="remark";               //VARCHAR(20)	備註

    public int insert(CuscreditVO vo) throws Exception{
        log.info(Common.ARROW + "insert" + Common.START_B);
        log.info("CuscreditVO:" + vo);
        Connection conn = getConnection();
        String SQL = String.format("INSERT INTO %s"
                + " (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

                ,TABLE_CUSCREDIT
                ,COL_CHNAME
                ,COL_ENNAME
                ,COL_CID
                ,COL_CIDREISSUEDATE
                ,COL_CIDREISSUELOCATION
                ,COL_CIDREISSUESTATUS
                ,COL_BIRTHDATE
                ,COL_MARITALSTATUS
                ,COL_EDUCATION
                ,COL_CURRENTRESIDENTIALADD
                ,COL_RESIDENTIALADD
                ,COL_CELLPHONE
                ,COL_EMAIL
                ,COL_COMPANYNAME
                ,COL_COMPANYINDUSTRY
                ,COL_OCCUPATION
                ,COL_DEPARTMENT
                ,COL_JOBTITLE
                ,COL_DATEOFEMPLOYMENT
                ,COL_COMPANYADDRESS
                ,COL_COMPANYPHONENUM
                ,COL_ANNUALINCOME
                ,COL_CARDAPPROVALSTATUS
                ,COL_APPLYREMARK
                ,COL_ACTIVATIONRECORD
                ,COL_EVENTCODE
                ,COL_REGIDATE
                ,COL_ISSUING_BANK
                ,COL_CARDNUM
                ,COL_SECURITYCODE
                ,COL_STATUS
                ,COL_CARDTYPE
                ,COL_REMARK
        );
        PreparedStatement pstmt = null;
        int code;
        int j = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++j , vo.getChName               ());
            pstmt.setString(++j , vo.getEnName               ());
            pstmt.setString(++j , vo.getCid  			     ());
            pstmt.setString(++j , vo.getCidReissueDate       ());
            pstmt.setString(++j , vo.getCidReissueLocation   ());
            pstmt.setString(++j , vo.getCidReissueStatus     ());
            pstmt.setString(++j , vo.getBirthDate            ());
            pstmt.setString(++j , vo.getMaritalStatus        ());
            pstmt.setString(++j , vo.getEducation            ());
            pstmt.setString(++j , vo.getCurrentResidentialAdd());
            pstmt.setString(++j , vo.getResidentialAdd       ());
            pstmt.setString(++j , vo.getCellphone            ());
            pstmt.setString(++j , vo.getEmail                ());
            pstmt.setString(++j , vo.getCompanyName          ());
            pstmt.setString(++j , vo.getCompanyIndustry      ());
            pstmt.setString(++j , vo.getOccupation           ());
            pstmt.setString(++j , vo.getDepartment           ());
            pstmt.setString(++j , vo.getJobTitle             ());
            pstmt.setString(++j , vo.getDateOfEmployment     ());
            pstmt.setString(++j , vo.getCompanyAddress       ());
            pstmt.setString(++j , vo.getCompanyPhoneNum      ());
            pstmt.setString(++j , vo.getAnnualIncome         ());
            pstmt.setString(++j , vo.getCardApprovalStatus   ());
            pstmt.setString(++j , vo.getApplyRemark          ());
            pstmt.setString(++j , vo.getActivationRecord     ());
            pstmt.setString(++j , vo.getEventCode            ());
            pstmt.setString(++j , vo.getRegidate 		     ());
            pstmt.setString(++j , vo.getIssuing_bank 	     ());
            pstmt.setString(++j , vo.getCardNum			     ());
            pstmt.setString(++j , vo.getSecurityCode         ());
            pstmt.setString(++j , vo.getStatus			     ());
            pstmt.setString(++j , vo.getCardType			 ());
            pstmt.setString(++j , vo.getRemark			     ());

            code = pstmt.executeUpdate();
            pstmt.clearParameters();
            log.info(Common.RESULT + code);
        } finally {
            closePS(pstmt);
            log.info(Common.ARROW + "insert" + Common.END_B);
        }
        return code;
    }
    public CuscreditVO selectKey(String cid, String num) throws Exception{
        log.info(Common.ARROW + "selectKey" + Common.START_B);
        log.info("cid:" + cid + " ,num:" + num);
        Connection conn = getConnection();
        String SQL = String.format("select * from %s where %s=? and %s=?"
                ,TABLE_CUSCREDIT
                ,COL_CID
                ,COL_CARDTYPE
        );
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CuscreditVO out = null;
        int i = 0;
        int j = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i , cid);
            pstmt.setString(++i , num);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                out = new CuscreditVO(
                        CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                        , CuscreditVO.encodeFormSQL(rs.getString(++j).trim())
                );
                j = 0;
            }
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            closeRS(rs);
            log.info(Common.ARROW + "selectKey" + Common.END_B);
        }
        return out;
    }
    public int updateCardApprovalStatus(CuscreditVO vo) throws Exception{
        log.info(Common.ARROW + "updateCardApprovalStatus" + Common.START_B);
        log.info(Common.VO + vo);
        Connection conn = getConnection();
        String SQL = String.format("update %s set %s=? ,%s=? ,%s=? ,%s=? ,%s=?" +
                        "where %s=? and %s=?"
                ,TABLE_CUSCREDIT
                ,COL_CARDAPPROVALSTATUS
                ,COL_CARDNUM
                ,COL_SECURITYCODE
                ,COL_APPLYREMARK
                ,COL_STATUS

                ,COL_CID
                ,COL_CARDTYPE
        );
        PreparedStatement pstmt = null;
        int out;
        int i = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i , vo.getCardApprovalStatus());
            pstmt.setString(++i , vo.getCardNum());
            pstmt.setString(++i , vo.getSecurityCode());
            pstmt.setString(++i , vo.getApplyRemark());
            pstmt.setString(++i , vo.getStatus());

            pstmt.setString(++i , vo.getCid());
            pstmt.setString(++i , vo.getCardType());

            out = pstmt.executeUpdate();
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            log.info(Common.ARROW + "updateCardApprovalStatus" + Common.END_B);
        }
        return out;
    }
    public int updateActivationRecord(CuscreditVO vo) throws Exception{
        log.info(Common.ARROW + "updateActivationRecord" + Common.START_B);
        log.info(Common.VO + vo);
        Connection conn = getConnection();
        String SQL = String.format("update %s set %s=? where %s=? and %s=?"
                ,TABLE_CUSCREDIT
                ,COL_ACTIVATIONRECORD

                ,COL_CID
                ,COL_CARDTYPE
        );
        PreparedStatement pstmt = null;
        int out;
        int i = 0;
        try {
            log.info(Common.SQL + SQL);
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(++i , vo.getActivationRecord());
            pstmt.setString(++i , vo.getCid());
            pstmt.setString(++i , vo.getCardType());

            out = pstmt.executeUpdate();
            pstmt.clearParameters();
            log.info(Common.RESULT + out);
        } finally {
            closePS(pstmt);
            log.info(Common.ARROW + "updateActivationRecord" + Common.END_B);
        }
        return out;
    }
}
