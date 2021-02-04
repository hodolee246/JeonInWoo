package com.rest.api;

import com.rest.api.model.CommonBoardResult;
import com.rest.api.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice
@RestController
public class BoardExceptionAdvice {

    private final ResponseService responseService;

    public BoardExceptionAdvice(ResponseService responseService) {
        this.responseService = responseService;
    }

    // 서버에서 발생한 BoardException을 처리한다.
    @ExceptionHandler(BoardException.class)
    public CommonBoardResult boardException(BoardException e) {
        log.error("BoardExceptionAdvice boardException() Exception : '{}', '{}'", e.getErrorCode(), e.getMessage());

        return responseService.getFailBoardErrorResult(e);
    }

    // 서버에서 발생한 모든 Exception 및 BoardRunTimeException 을 처리한다.
    @ExceptionHandler({BoardRunTimeException.class, Exception.class})
    public CommonBoardResult ServerException(Exception e) {
        log.error("BoardExceptionAdvice boardException() Exception : '{}'", e.getMessage());

        return responseService.getFailServerErrorResult();
    }
}