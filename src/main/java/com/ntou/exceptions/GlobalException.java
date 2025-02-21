package com.ntou.exceptions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import com.ntou.spec.SvcRes;
import com.ntou.tool.Common;
import com.ntou.tool.JsonTool;
import com.ntou.tool.ResTool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GlobalException {
    @AllArgsConstructor
    public enum RC {
        SVE9("SVE9", "Throwable")
        , SVE8("SVE8", "UnrecognizedPropertyException")
        , SVE7("SVE7", "NullPointerException")
        , SVE6("SVE6", "NotFoundException")
        , SVE5("SVE5", "NotAllowedException")
        , SVE4("SVE4", "JsonParseException")
        , SVE3("SVE3", "Exception")
        , SVE2("SVE2", "DateTimeParseException")

        , SVEA("SVEA", "JsonMappingException")
        , SVEB("SVEB", "IOException")
        , SVEC("SVEC", "JsonProcessingException")
        , SVED("SVED", "ValidationException")
        , SVEE("SVEE", "WebApplicationException")
        , SVEG("SVEG", "MismatchedInputException")
        , SVEH("SVEH", "InvocationTargetException")
        ;
        private final String code;
        @Getter
        private final String content;
        @JsonValue
        public String getCode() {return code;}
    }
    private static Response genResponse(TException e) {
        log.warn(Common.EXCEPTION,e);

        SvcRes output = new SvcRes();
        ResTool.setRes(output, e.res.getResCode(), e.res.getResMsg());

        log.info(Common.RES + output);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(JsonTool.format2Json(output)).build();
    }
    private static Response genResponse(RC rc, final Throwable e, Status status) {
        log.error(Common.EXCEPTION,e);

        SvcRes output = new SvcRes();
        ResTool.setRes(output, rc.getCode(), rc.getContent());

        log.info(Common.RES + output);
        log.info(Common.API_DIVIDER + Common.END_B + Common.API_DIVIDER);
        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(JsonTool.format2Json(output)).build();
    }
    @Provider public static class EM_Throwable implements ExceptionMapper<Throwable> {
        @Override public Response toResponse(Throwable e) {return genResponse(RC.SVE9, e, Status.INTERNAL_SERVER_ERROR);}
    }
    @Provider public static class EM_UnrecognizedPropertyException implements ExceptionMapper<UnrecognizedPropertyException> {
        @Override public Response toResponse(UnrecognizedPropertyException e) {return genResponse(RC.SVE8, e, Status.BAD_REQUEST);}
    }
    @Provider public static class EM_NullPointerException implements ExceptionMapper<NullPointerException>{
        @Override public Response toResponse(NullPointerException e) {return genResponse(RC.SVE7, e, Status.INTERNAL_SERVER_ERROR);}
    }
    @Provider public static class EM_NotFoundException implements ExceptionMapper<NotFoundException> {
        @Override public Response toResponse(NotFoundException e) {return genResponse(RC.SVE6, e, Status.NOT_FOUND);}
    }
    @Provider public static class EM_NotAllowedException implements ExceptionMapper<NotAllowedException>{
        @Override public Response toResponse(NotAllowedException e) {return genResponse(RC.SVE5, e, Status.METHOD_NOT_ALLOWED);}
    }
    @Provider public static class EM_JsonParseException implements ExceptionMapper<JsonParseException> {
        @Override public Response toResponse(JsonParseException e) {return genResponse(RC.SVE4, e, Status.BAD_REQUEST);}
    }
    @Provider public static class EM_JsonMappingException implements ExceptionMapper<JsonMappingException> {
        @Override public Response toResponse(JsonMappingException e) {return genResponse(RC.SVEA, e, Status.BAD_REQUEST);}
    }
    @Provider public static class EM_Exception implements ExceptionMapper<Exception> {
        @Override public Response toResponse(Exception e) {return genResponse(RC.SVE3, e, Status.INTERNAL_SERVER_ERROR);}
    }
    @Provider public static class EM_TException implements ExceptionMapper<TException> {
        @Override public Response toResponse(TException e) {return genResponse(e);}
    }
    @Provider public static class EM_DateTimeParseException implements ExceptionMapper<DateTimeParseException> {
        @Override public Response toResponse(DateTimeParseException e) {return genResponse(RC.SVE2, e, Status.BAD_REQUEST);}
    }
    @Provider public static class IOExceptionMapper implements ExceptionMapper<IOException> {
        @Override public Response toResponse(IOException e) {return genResponse(RC.SVEB, e, Status.INTERNAL_SERVER_ERROR);}
    }
    @Provider public static class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {
        @Override public Response toResponse(JsonProcessingException e) {return genResponse(RC.SVEC, e, Status.BAD_REQUEST);}
    }
    @Provider public static class ValidationExceptionMapper implements ExceptionMapper<javax.validation.ValidationException> {
        @Override public Response toResponse(javax.validation.ValidationException e) {return genResponse(RC.SVED, e, Status.BAD_REQUEST);}
    }
    @Provider public static class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
        @Override public Response toResponse(WebApplicationException e) {return genResponse(RC.SVEE, e, Status.INTERNAL_SERVER_ERROR);}
    }
    @Provider public static class MismatchedInputExceptionMapper implements ExceptionMapper<MismatchedInputException> {
        @Override public Response toResponse(MismatchedInputException e) {return genResponse(RC.SVEG, e, Status.BAD_REQUEST);}
    }
    @Provider public static class EM_InvocationTargetException implements ExceptionMapper<InvocationTargetException> {
        @Override public Response toResponse(InvocationTargetException e) {return genResponse(RC.SVEH, e, Status.INTERNAL_SERVER_ERROR);}
    }
}
