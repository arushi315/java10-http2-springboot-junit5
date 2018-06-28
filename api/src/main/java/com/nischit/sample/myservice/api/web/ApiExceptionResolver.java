package com.nischit.sample.myservice.api.web;

import com.nischit.sample.myservice.localization.LocalizationParamValueException;
import com.nischit.sample.myservice.localization.Messages;
import com.nischit.sample.myservice.localization.MsgKey;
import com.nischit.sample.myservice.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.nischit.sample.myservice.localization.Messages.BAD_REQUEST_ERROR;
import static com.nischit.sample.myservice.localization.Messages.SERVER_ERROR_KEY;
import static com.nischit.sample.myservice.localization.Messages.UNSUPPORTED_ACCEPT_HEADER_ERROR;
import static com.nischit.sample.myservice.localization.Messages.UNSUPPORTED_MEDIA_TYPE_ERROR;
import static java.util.Objects.nonNull;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class ApiExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionResolver.class);

    private final Messages messages;

    @Autowired
    public ApiExceptionResolver(final Messages messages) {
        this.messages = messages;
    }

    @ExceptionHandler(LocalizationParamValueException.class)
    public ResponseEntity<String> handleApplicationException(final HttpServletRequest request, final HttpServletResponse response, final Exception exception) {

        final LocalizationParamValueException ex = (LocalizationParamValueException) exception;
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

        final MsgKey msgKey = AnnotationUtils.findAnnotation(ex.getClass(), MsgKey.class);
        final String errorCode = nonNull(msgKey) ? msgKey.value() : SERVER_ERROR_KEY;

        final HttpStatus responseCode = responseStatus.value();
        final Object[] msgArgs = ex.getArgs();
        final String localizedMessage = getLocalizedMessage(request, errorCode, msgArgs);
        return handleAllExceptions(request, response, responseCode, errorCode, localizedMessage, ex);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class, MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleBadRequestException(final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
        return handleAllExceptions(request, response, BAD_REQUEST, BAD_REQUEST_ERROR, ex);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(final HttpServletRequest request, final HttpServletResponse response, final HttpMediaTypeNotSupportedException ex) {
        return handleAllExceptions(request, response, UNSUPPORTED_MEDIA_TYPE, UNSUPPORTED_MEDIA_TYPE_ERROR, ex);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(final HttpServletRequest request, final HttpServletResponse response, final HttpMediaTypeNotAcceptableException ex) {
        return handleAllExceptions(request, response, NOT_ACCEPTABLE, UNSUPPORTED_ACCEPT_HEADER_ERROR, ex);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleAnyGenericException(final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
        return handleAllExceptions(request, response, INTERNAL_SERVER_ERROR, SERVER_ERROR_KEY, ex);
    }

    private ResponseEntity<String> handleAllExceptions(final HttpServletRequest request, final HttpServletResponse response, final HttpStatus responseCode, final String errorCode, final String localizedMessage, final Exception ex) {
        final ApiRequest apiRequest = (ApiRequest) request.getAttribute(ApiRequest.API_REQUEST_CONTEXT);
        printError(request, apiRequest, responseCode, errorCode, localizedMessage, ex);

        // Send error response back to user
        final ApiErrorResponse apiErrorResponse = new ApiErrorResponse(errorCode, localizedMessage);
        request.setAttribute(ERROR_STATUS_CODE, responseCode);
        request.setAttribute(ERROR_MESSAGE, createErrorJson(apiErrorResponse));
        request.setAttribute(ERROR_EXCEPTION_TYPE, ex.getClass());
        request.setAttribute(ERROR_EXCEPTION, ex);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity<>(createErrorJson(apiErrorResponse), httpHeaders, responseCode);
    }

    private void printError(final HttpServletRequest request, final ApiRequest apiRequest, final HttpStatus responseCode, final String errorCode, final String localizedMessage, final Exception ex) {
        if (nonNull(apiRequest)) {
            final long elapsedTime = System.currentTimeMillis() - apiRequest.getRequestStartTime();
            LOGGER.error("RequestId {} meant for request URI {} returned in {}ms, it resulted with return code {}, mapped to error code {} and, error is: ", apiRequest.getRequestId(), apiRequest.getRequestUrl(), elapsedTime, responseCode, errorCode, ex);
        } else {
            LOGGER.error("Request for request URI {} resulted in an error with return code {}, mapped to error code {}  and, error is: ", request.getRequestURI(), responseCode, errorCode, ex);
        }
        LOGGER.error("Since request failed, client will be reported http status code: {}, error code {} and error message {}", responseCode, errorCode, localizedMessage);
    }

    private String createErrorJson(final ApiErrorResponse apiErrorResponse) {
        return JsonUtils.convertToJson(apiErrorResponse);
    }

    private ResponseEntity<String> handleAllExceptions(final HttpServletRequest request, final HttpServletResponse response, final HttpStatus responseCode, final String errorCode, final Exception ex) {
        final ApiRequest apiRequest = (ApiRequest) request.getAttribute(ApiRequest.API_REQUEST_CONTEXT);
        Object[] msgArgs = nonNull(apiRequest) ? new Object[]{apiRequest.getRequestId()} : new Object[0];
        final String localizedMessage = getLocalizedMessage(request, errorCode, msgArgs);
        return handleAllExceptions(request, response, responseCode, errorCode, localizedMessage, ex);
    }

    private String getLocalizedMessage(final HttpServletRequest request, final String errorCode, Object... msgArgs) {
        ApiRequest apiRequest = (ApiRequest) request.getAttribute(ApiRequest.API_REQUEST_CONTEXT);
        Locale locale = nonNull(apiRequest) ? apiRequest.getLocale() : request.getLocale();
        String msgKey = nonNull(errorCode) ? errorCode : SERVER_ERROR_KEY;
        return messages.getLocalizedErrorMessage(msgKey, locale, msgArgs);
    }
}
