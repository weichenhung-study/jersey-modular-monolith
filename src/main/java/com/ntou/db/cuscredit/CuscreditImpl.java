package com.ntou.db.cuscredit;

public class CuscreditImpl implements CuscreditSvc {
    private final CuscreditDAO cuscreditDAO;

    public CuscreditImpl(CuscreditDAO cuscreditDAO) {
        this.cuscreditDAO = cuscreditDAO;
    }

    @Override
    public int insert(CuscreditVO vo) throws Exception {
        return cuscreditDAO.insert(vo);
    }

    @Override
    public CuscreditVO selectKey(String cid, String num) throws Exception {
        return cuscreditDAO.selectKey(cid, num);
    }

    @Override
    public CuscreditVO selectCardHolderActivated(String cid, String cardType, String cardNum, String securityCode) throws Exception {
        CuscreditVO cuscreditVO  = cuscreditDAO.selectKey(cid, cardType);
        if(cuscreditVO.getCardNum().equals(cardNum) && cuscreditVO.getSecurityCode().equals(securityCode)) {
            return cuscreditVO;
        }
        return null;
    }

    @Override
    public int updateCardApprovalStatus(CuscreditVO vo) throws Exception {
        return cuscreditDAO.updateCardApprovalStatus(vo);
    }

    @Override
    public int updateActivationRecord(CuscreditVO vo) throws Exception {
        return cuscreditDAO.updateActivationRecord(vo);
    }
}
