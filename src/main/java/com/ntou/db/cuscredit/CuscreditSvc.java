package com.ntou.db.cuscredit;

public interface CuscreditSvc {
    int insert(CuscreditVO vo) throws Exception;
    CuscreditVO selectKey(String cid, String num) throws Exception;
    CuscreditVO selectCardHolderActivated(String cid, String cardType, String cardNum, String securityCode) throws Exception;
    int updateCardApprovalStatus(CuscreditVO vo) throws Exception;
    int updateActivationRecord(CuscreditVO vo) throws Exception;

}
