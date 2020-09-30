package com.rest.api;

import com.rest.api.util.BoardStatusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

import static org.springframework.http.ResponseEntity.ok;

@ControllerAdvice
@Slf4j
public class BoardExceptionAdvice {

    /** Service에서 발생하는 예외를 핸들링 해준다.
     *  서비스에서 예외가 발생한 경우 에러메시지와 에러코드를 클라이언트로 전송한다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BoardException.class)
    public ResponseEntity<?> boardException(BoardException e) {
        log.error("BoardExceptionAdvice boardException()");
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", e.getErrorCode());
        resultMap.put("message", e.getMessage());
        responseMap.put("result", resultMap);

        return ok().body(responseMap);
    }

    /** 예상치 못한 모든 에러를 핸들링 해준다.
     *  로직 수행중 로직에서 처리할 수 없는 에러가 발생할 경우 에러메시지와 500코드값을 클라이언트로 전송한다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ServerException(Exception e) {
        log.error("BoardExceptionAdvice boardException()");
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", BoardStatusUtil.getServerErrorCode());
        resultMap.put("errorMessage", e.getMessage());
        responseMap.put("result", resultMap);

        return ok().body(responseMap);
    }
}
