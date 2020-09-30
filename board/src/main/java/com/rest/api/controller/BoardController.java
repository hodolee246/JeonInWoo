package com.rest.api.controller;

import com.rest.api.BoardException;
import com.rest.api.model.Board;
import com.rest.api.service.BoardService;
import com.rest.api.util.BoardStatusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import static org.springframework.http.ResponseEntity.ok;

@Api(tags = {"1. Board"})
@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation(value = "게시물 전체 조회", notes = "선택한 카테고리와 키워드로 모든 게시물을 조회한다.")
    @GetMapping("/board")
    public ResponseEntity<?> boardList(
                                        @ApiParam(value = "페이지")
                                        @PageableDefault(size = 5) Pageable pageable,
                                        @ApiParam(value = "카테고리")
                                        @RequestParam(defaultValue = "") String category,
                                        @ApiParam(value = "키워드")
                                        @RequestParam(defaultValue = "") String keyword) throws BoardException {
        log.info("BoardController boardList / page : {} / category : {} / keyword : {}", pageable.toString(), category, keyword);
        Page<Board> boardPage = boardService.boardList(category, keyword, pageable);

        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", BoardStatusUtil.getOkCode());
        resultMap.put("boardList", boardPage.getContent());
        resultMap.put("totalPages", boardPage.getTotalPages());
        responseMap.put("result", resultMap);

        return ok().body(responseMap);
    }

    @ApiOperation(value = "게시물 조회", notes = "선택한 게시물을 조회한다.")
    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> readBoard(@PathVariable Long boardId) throws BoardException {
        log.info("BoardController readBoard / boardId : {}", boardId);
        Board board = boardService.readBoard(boardId);

        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", BoardStatusUtil.getOkCode());
        resultMap.put("board", board);
        responseMap.put("result", resultMap);

        return ok().body(responseMap);
    }

    @ApiOperation(value = "게시물 생성", notes = "입력한 게시물을 생성한다.")
    @PostMapping("/board")
    public ResponseEntity<?> createBoard(
                                        @ApiParam(value = "게시물", required = true)
                                        @RequestBody Board board) throws BoardException {
        log.info("BoardController writeBoard / board : {}", board.toString());
        boardService.createBoard(board);

        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", BoardStatusUtil.getCreateCode());
        responseMap.put("result", resultMap);

        return ok().body(responseMap);
    }

    @ApiOperation(value = "게시물 수정", notes = "선택한 게시물을 수정한다.")
    @PutMapping("/board/{boardId}")
    public ResponseEntity<?> updateBoard(
                                        @PathVariable Long boardId,
                                        @ApiParam(value = "게시물", required = true)
                                        @RequestBody Board board) throws BoardException {
        log.info("BoardController updateBoard / board : {}", board.toString());
        boardService.updateBoard(boardId, board);

        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", BoardStatusUtil.getCreateCode());
        responseMap.put("result", resultMap);

        return ok().body(responseMap);
    }

    @ApiOperation(value = "게시물 삭제", notes = "선택한 게시물을 삭제한다. 삭제시 게시물의 상태값을 0으로 변경한다.")
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) throws BoardException {
        log.info("BoardController deleteBoard / boardId : {}", boardId);
        boardService.deleteBoard(boardId);

        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", BoardStatusUtil.getNoContentCode());
        responseMap.put("result", resultMap);

        return ok().body(responseMap);
    }
}
