package com.ntou.creditcard.management.review;

import com.ntou.db.cuscredit.CuscreditDAO;
import com.ntou.db.cuscredit.CuscreditImpl;
import com.ntou.tool.Common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Review")
public class ReviewController {
    private final Review review;
    public ReviewController() {
        this.review = new Review(
                new CuscreditImpl(new CuscreditDAO()));
    }
    public ReviewController(Review review) {
        this.review = review;
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    @Produces(MediaType.APPLICATION_JSON + Common.CHARSET_UTF8)
    public Response doController(ReviewReq req) throws Exception {
        return review.doAPI(req);
    }
}