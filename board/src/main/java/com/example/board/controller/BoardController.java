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

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> readBoard(@PathVariable Long boardId) throws BoardException {

        log.info("BoardController readBoard / boardId : {}", boardId);
        Board board = boardService.readBoard(boardId);

        return ok().body(board);
    }

    @PostMapping("/board")
    public ResponseEntity<?> createBoard(@RequestBody Board board) throws BoardException {

        log.info("BoardController writeBoard / board : {}", board.toString());
        boardService.createBoard(board);

        return ok().build();
    }

    @PutMapping("/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId,
                                        @RequestBody Board board) throws BoardException {

        log.info("BoardController updateBoard / board : {}", board.toString());
        boardService.updateBoard(board);

        return ok().build();
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) throws BoardException {

        log.info("BoardController deleteBoard / boardId : {}", boardId);
        boardService.deleteBoard(boardId);

        return ok().build();
    }
}
