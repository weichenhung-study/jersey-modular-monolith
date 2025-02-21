package com.ntou.creditcard.billing.generatebill;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import javax.ws.rs.core.Response;

public class GenerateBillTask implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        try {
            try (Response response = new GenerateBill().doAPI()) {}

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
