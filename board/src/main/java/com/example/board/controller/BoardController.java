package com.example.board.controller;

import com.example.board.BoardException;
import com.example.board.model.Board;
import com.example.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Slf4j
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /** Service에서 발생하는 예외를 핸들링 해준다.
     *  서비스에서 예외가 발생한 경우 에러메시지와 에러코드를 클라이언트로 전송한다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BoardException.class)
    public ResponseEntity<?> boardException(BoardException e) {

        log.info("BoardController boardException");
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> error = new HashMap<>();
        error.put("code", e.getErrorCode());
        error.put("message", e.getMessage());
        resultMap.put("error", error);

        return ok().body(resultMap);
    }

    /** 예상치 못한 모든 에러를 핸들링 해준다.
     *  로직 수행중 로직에서 처리할 수 없는 에러가 발생할 경우 에러메시지와 500코드값을 클라이언트로 전송한다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ServerException(Exception e) {

        log.info("BoardController Exception");
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> error = new HashMap<>();
        error.put("code", 500);
        error.put("message", e.getMessage());
        resultMap.put("error", error);

        return ok().body(resultMap);
    }

    /** 존재하는 모든 게시물을 조회하여 클라이언트로 전송한다.
     *  서버사이드 랜더링을 이용하여 사용자가 입력, 선택한 키워드와 카테고리로 게시물을 조회한다.
     *
     * @param pageable
     * @param category
     * @param keyword
     * @return
     * @throws BoardException
     */
    @GetMapping("/board")
    public ResponseEntity<?> boardList(@PageableDefault(size = 5) Pageable pageable,
                                       @RequestParam(name = "category", defaultValue = "") String category,
                                       @RequestParam(name = "keyword", defaultValue = "") String keyword) throws BoardException {

        log.info("BoardController boardList / page : {} / category : {} / keyword : {}", pageable.toString(), category, keyword);
        Page<Board> boardPage = boardService.boardList(category, keyword, pageable);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("boardList", boardPage.getContent());
        resultMap.put("totalPages", boardPage.getTotalPages());
        resultMap.put("page", boardPage.getNumber());

        return ok().body(resultMap);
    }

    /** 단일 게시물을 조회하여 클라이언트로 전송한다.
     *  사용자가 요청한 주소값으로 게시물을 조회한다.
     *
     * @param boardId
     * @return
     * @throws BoardException
     */
    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> readBoard(@PathVariable Long boardId) throws BoardException {

        log.info("BoardController readBoard / boardId : {}", boardId);
        Board board = boardService.readBoard(boardId);

        return ok().body(board);
    }

    /** 새로운 게시물을 생성한다.
     *  사용자가 입력한 정보로 새로운 게시물을 생성한다.
     *
     * @param board
     * @return
     * @throws BoardException
     */
    @PostMapping("/board")
    public ResponseEntity<?> createBoard(@RequestBody Board board) throws BoardException {

        log.info("BoardController writeBoard / board : {}", board.toString());
        boardService.createBoard(board);

        return ok().build();
    }

    /** 게시물을 수정한다.
     *  사용자가 요청한 게시물을 입력한 정보로 변경한다.
     *
     * @param boardId
     * @param board
     * @return
     * @throws BoardException
     */
    @PutMapping("/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId,
                                        @RequestBody Board board) throws BoardException {

        log.info("BoardController updateBoard / board : {}", board.toString());
        boardService.updateBoard(boardId, board);

        return ok().build();
    }

    /** 게시물을 삭제한다.
     *  사용자가 요청한 정보의 게시물을 삭제한다.
     *  삭제시 데이터베이스의 레이블을 삭제하는 것이 아닌 상태값을 0으로 변경해준다.
     *
     * @param boardId
     * @return
     * @throws BoardException
     */
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) throws BoardException {

        log.info("BoardController deleteBoard / boardId : {}", boardId);
        boardService.deleteBoard(boardId);

        return ok().build();
    }
}
