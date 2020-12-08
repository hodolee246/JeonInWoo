package com.rest.api.service;

import com.rest.api.BoardException;
import com.rest.api.model.Board;
import com.rest.api.model.BoardListResult;
import com.rest.api.model.BoardSingleResult;
import com.rest.api.model.CommonBoardResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    // 500 메시지
    private final String SERVER_ERROR_MESSAGE = "서버의 예기치 못한 오류로 인하여 요청에 실패했습니다.";
    // 성공 메시지
    private final String SUCCESS_MESSAGE = "정상적으로 요청을 처리하였습니다.";
    // 200 상태 코드
    private final int SUCCESS_CODE = 200;
    // 201 상태 코드
    private final int CREATE_CODE = 201;
    // 204 상태 코드
    private final int NO_CONTENT_CODE = 204;
    // 500 상태 코드
    private final int SERVER_ERROR_CODE = 500;

    // SingleResult
    public BoardSingleResult getBoardSingleResult(Board data) {
        BoardSingleResult boardSingleResult = new BoardSingleResult();
        boardSingleResult.setBoard(data);
        getSuccessBoardResult(boardSingleResult);

        return boardSingleResult;
    }
    // ListResult
    public BoardListResult getBoardListResult(List<Board> data) {
        BoardListResult boardListResult = new BoardListResult();
        boardListResult.setBoardList(data);
        getSuccessBoardResult(boardListResult);

        return boardListResult;
    }
    // FailBoardErrorResult
    public CommonBoardResult getFailBoardErrorResult(BoardException data) {
        CommonBoardResult commonBoardResult = new CommonBoardResult();
        commonBoardResult.setStatusCode(data.getErrorCode());
        commonBoardResult.setStatusMessage(data.getMessage());

        return commonBoardResult;
    }
    // FailServerErrorResult
    public CommonBoardResult getFailServerErrorResult() {
        CommonBoardResult commonBoardResult = new CommonBoardResult();
        commonBoardResult.setStatusCode(SERVER_ERROR_CODE);
        commonBoardResult.setStatusMessage(SERVER_ERROR_MESSAGE);

        return commonBoardResult;
    }
    // Success Code, Message
    public CommonBoardResult getSuccessBoardResult(CommonBoardResult commonBoardResult) {
        commonBoardResult.setStatusCode(SUCCESS_CODE);
        commonBoardResult.setStatusMessage(SUCCESS_MESSAGE);

        return commonBoardResult;
    }
    // Created Code, Message
    public CommonBoardResult getCreatedBoardResult() {
        CommonBoardResult commonBoardResult = new CommonBoardResult();
        commonBoardResult.setStatusCode(CREATE_CODE);
        commonBoardResult.setStatusMessage(SUCCESS_MESSAGE);

        return commonBoardResult;
    }
    // NoContent Code, Message
    public CommonBoardResult getNoContentBoardResult() {
        CommonBoardResult commonBoardResult = new CommonBoardResult();
        commonBoardResult.setStatusCode(NO_CONTENT_CODE);
        commonBoardResult.setStatusMessage(SUCCESS_MESSAGE);

        return commonBoardResult;
    }
}