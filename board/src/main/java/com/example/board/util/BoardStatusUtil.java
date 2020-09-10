package com.example.board.util;

public class BoardStatusUtil {
    // 404 메시지
    private static final String NOT_FOUND_ERROR_MESSAGE = "요청하신 게시물은 삭제되었거나 존재하지 않는 게시물입니다.";
    // 500 메시지
    private static final String SERVER_ERROR_MESSAGE = "서버의 예기치 못한 오류로 인하여 요청에 실패했습니다.";
    // 404 상태 코드
    private static final int NOT_FOUND_CODE = 404;
    // 500 상태 코드
    private static final int SERVER_ERROR_CODE = 500;

    public static String getNotFoundErrorMessage() {
        return NOT_FOUND_ERROR_MESSAGE;
    }

    public static String getServerErrorMessage() {
        return SERVER_ERROR_MESSAGE;
    }

    public static int getNotFoundCode() {
        return NOT_FOUND_CODE;
    }

    public static int getServerErrorCode() {
        return SERVER_ERROR_CODE;
    }
}
