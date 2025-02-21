package com.ntou;

import com.ntou.creditcard.management.review.*;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewControllerTest extends JerseyTest  {

    private CuscreditSvc mockCuscreditSvc;

    @Override
    protected javax.ws.rs.core.Application configure() {
        mockCuscreditSvc = mock(CuscreditSvc.class);
        Review review = new Review(mockCuscreditSvc);
        new ReviewController();
        return new ResourceConfig().register(new ReviewController(review));
    }
    private Review createReview() { // 每次測試前設置共用的 Application 物件
        return new Review(mockCuscreditSvc);
    }
    @Test
    void whenCheckReqIsFalse_shouldThrowException() {
        ReviewReq mockReq = mock(ReviewReq.class);
        when(mockReq.checkReq()).thenReturn(false);
        Review review = createReview();
        assertThrows(TException.class, () -> review.doAPI(mockReq));
    }

    @Test
    void whenSelectKeyReturnsNull_shouldThrowException() throws Exception {
        ReviewReq mockReq = mock(ReviewReq.class);
        when(mockReq.checkReq()).thenReturn(true);
        when(mockReq.getCid()).thenReturn("123");
        when(mockReq.getCardType()).thenReturn("VISA");
        when(mockCuscreditSvc.selectKey("123", "VISA")).thenReturn(null);

        Review review = createReview();
        assertThrows(TException.class, () -> review.doAPI(mockReq));
    }

    @Test
    void whenCardApprovalStatusIsPass_shouldSendPassMail() throws Exception {
        ReviewReq mockReq = mock(ReviewReq.class);
        CuscreditVO mockCuscredit = new CuscreditVO();
        mockCuscredit.setEmail("test@gmail.com");

        when(mockReq.checkReq()).thenReturn(true);
        when(mockReq.getCid()).thenReturn("123");
        when(mockReq.getCardType()).thenReturn("VISA");
        when(mockReq.getCardApprovalStatus()).thenReturn(CuscreditVO.CardApprovalStatus.PASS.getValue());
        when(mockCuscreditSvc.selectKey("123", "VISA")).thenReturn(mockCuscredit);
        when(mockCuscreditSvc.updateCardApprovalStatus(any())).thenReturn(1);

        Review review = createReview();
        Response response = review.doAPI(mockReq);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void whenupdateCardApprovalStatus_shouldThrowException() throws Exception {
        ReviewReq mockReq = mock(ReviewReq.class);
        CuscreditVO mockCuscredit = new CuscreditVO();
        mockCuscredit.setEmail("test@gmail.com");

        when(mockReq.checkReq()).thenReturn(true);
        when(mockReq.getCid()).thenReturn("123");
        when(mockReq.getCardType()).thenReturn("VISA");
        when(mockReq.getCardApprovalStatus()).thenReturn(CuscreditVO.CardApprovalStatus.PASS.getValue());
        when(mockCuscreditSvc.selectKey("123", "VISA")).thenReturn(mockCuscredit);
        when(mockCuscreditSvc.updateCardApprovalStatus(any())).thenReturn(0);

        Review review = createReview();
        assertThrows(TException.class, () -> review.doAPI(mockReq));
    }

    @Test
    void whenCardApprovalStatusIsNotPass_shouldSendFailMail() throws Exception {
        ReviewReq mockReq = mock(ReviewReq.class);
        CuscreditVO mockCuscredit = new CuscreditVO();
        mockCuscredit.setEmail("test@gmail.com");

        when(mockReq.checkReq()).thenReturn(true);
        when(mockReq.getCid()).thenReturn("123");
        when(mockReq.getCardType()).thenReturn("VISA");
        when(mockReq.getCardApprovalStatus()).thenReturn(CuscreditVO.CardApprovalStatus.NOTPASS.getValue());
        when(mockCuscreditSvc.selectKey("123", "VISA")).thenReturn(mockCuscredit);
        when(mockCuscreditSvc.updateCardApprovalStatus(any())).thenReturn(1);

        Review review = createReview();
        Response response = review.doAPI(mockReq);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void whenCVVLengthIsInvalid_shouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Review.genCVV(5));
        assertEquals("CVV length must be 3 or 4 digits", exception.getMessage());
    }

    @Test
    void shouldGenerateValidCreditCardNumber() {
        String cardNumber = Review.genCreditCardNum();
        assertNotNull(cardNumber);
        assertEquals(16, cardNumber.length());
    }

    @Test
    void testDoController() throws Exception {
        ReviewReq request = new ReviewReq();
        setRequestData(request);
        CuscreditVO mockCuscredit = new CuscreditVO();
        when(mockCuscreditSvc.selectKey(request.getCid(), request.getCardType())).thenReturn(mockCuscredit);
        when(mockCuscreditSvc.updateCardApprovalStatus(any())).thenReturn(1);

        Response response = target("Review")
                .request()
                .put(Entity.json(request));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        ReviewRes responseBody = response.readEntity(ReviewRes.class);
        assertEquals(ReviewRC.T1210.getCode(), responseBody.getResCode()); // 成功代碼
    }
    private void setRequestData(ReviewReq request){
        request.setCid("B187654321");
        request.setCardType("2");
        request.setCardApprovalStatus("01");
        request.setApplyRemark("");
    }
}



