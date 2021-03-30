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

    // 서버에서 발생한 모든 BoardRunTimeException 을 처리한다.
    @ExceptionHandler({BoardRunTimeException.class})
    public CommonBoardResult ServerException(BoardRunTimeException e) {
        log.error("BoardExceptionAdvice boardException() ExceptionMessage : '{}', ExceptionCode : '{}'", e.getMessage(), e.getErrorCode());

        return responseService.getFailServerErrorResult();
    }
}