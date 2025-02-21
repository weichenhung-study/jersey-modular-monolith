package com.ntou;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.ntou.creditcard.management.application.*;
import com.ntou.db.cuscredit.CuscreditSvc;
import com.ntou.db.cuscredit.CuscreditVO;
import com.ntou.exceptions.TException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

public class ApplicationControllerTest extends JerseyTest {

    private CuscreditSvc mockCuscreditSvc;

    @Override
    protected javax.ws.rs.core.Application configure() {
        mockCuscreditSvc = mock(CuscreditSvc.class);
        Application mockApplication = new Application(mockCuscreditSvc);
        new ApplicationController();
        return new ResourceConfig().register(new ApplicationController(mockApplication));
    }

    private Application createApplication() {
        return new Application(mockCuscreditSvc);
    }

    @Test
    void whenInsertResultIsNotOne_shouldThrowTException() throws Exception {
        ApplicationReq mockReq = mock(ApplicationReq.class);
        when(mockReq.checkReq()).thenReturn(true);
        when(mockCuscreditSvc.selectKey(mockReq.getCid(), mockReq.getCardType())).thenReturn(null);
        when(mockCuscreditSvc.insert(any())).thenReturn(0);

        Application app = createApplication();
        assertThrows(TException.class, () -> app.doAPI(mockReq));
    }

    @Test
    void whenCusDateBillListIsNotNull_shouldThrowTException() throws Exception {
        ApplicationReq mockReq = mock(ApplicationReq.class);
        CuscreditVO mockCusDateBillList = mock(CuscreditVO.class);

        when(mockReq.checkReq()).thenReturn(true);
        when(mockCuscreditSvc.selectKey(mockReq.getCid(), mockReq.getCardType())).thenReturn(mockCusDateBillList);

        Application app = createApplication();
        assertThrows(TException.class, () -> app.doAPI(mockReq));
    }

    @Test
    void whenCheckReqIsFalse_shouldThrowException() {
        ApplicationReq mockReq = mock(ApplicationReq.class);
        when(mockReq.checkReq()).thenReturn(false);

        Application app = createApplication();
        assertThrows(TException.class, () -> app.doAPI(mockReq));
    }

    @Test
    public void testDoController() throws Exception {
        ApplicationReq request = new ApplicationReq();
        setupRequestData(request);

        when(mockCuscreditSvc.selectKey(request.getCid(), request.getCardType())).thenReturn(null);
        when(mockCuscreditSvc.insert(any(CuscreditVO.class))).thenReturn(1); // 模擬 insert 方法成功返回 1

        Response response = target("Application")
                .request()
                .post(Entity.json(request));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        ApplicationRes responseBody = response.readEntity(ApplicationRes.class);
        assertEquals(ApplicationRC.T1110.getCode(), responseBody.getResCode()); // 成功代碼
    }

    private void setupRequestData(ApplicationReq request) {
        request.setChName("馬尤有");
        request.setEnName("Zhang Xiuqin");
        request.setCid("A253654321");
        request.setCidReissueDate("12");
        request.setCidReissueLocation("2");
        request.setCidReissueStatus("5");
        request.setBirthDate("4");
        request.setMaritalStatus("77");
        request.setEducation("04");
        request.setCurrentResidentialAdd("8");
        request.setResidentialAdd("9");
        request.setCellphone("6");
        request.setEmail("tuluber@gmail.com");
        request.setCompanyName("asd");
        request.setCompanyIndustry("da");
        request.setOccupation("da");
        request.setDepartment("da");
        request.setJobTitle("da");
        request.setDateOfEmployment("asd");
        request.setCompanyAddress("asd");
        request.setCompanyPhoneNum("asd");
        request.setAnnualIncome("asd");
        request.setEventCode("1");
        request.setCardType("2");
        request.setRemark("333");
    }
}
