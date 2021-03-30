package com.rest.api.controller;

import com.rest.api.model.Board;
import com.rest.api.model.BoardListResult;
import com.rest.api.model.BoardSingleResult;
import com.rest.api.model.CommonBoardResult;
import com.rest.api.service.BoardService;
import com.rest.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. Board"})
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @ApiOperation(value = "게시물 전체 조회", notes = "선택한 카테고리와 키워드로 모든 게시물을 조회한다.")
    @GetMapping("/board")
    public BoardListResult boardList(
                                        @ApiParam(value = "페이지")
                                        @PageableDefault(size = 5) Pageable pageable,
                                        @ApiParam(value = "카테고리")
                                        @RequestParam(defaultValue = "") String category,
                                        @ApiParam(value = "키워드")
                                        @RequestParam(defaultValue = "") String keyword) {
        log.info("BoardController boardList / page : '{}' / category : '{}' / keyword : '{}'", pageable.toString(), category, keyword);
        Page<Board> boardPage = boardService.boardList(category, keyword, pageable);

        return responseService.getBoardListResult(boardPage.getContent());
    }

    @ApiOperation(value = "게시물 조회", notes = "선택한 게시물을 조회한다.")
    @GetMapping("/board/{boardId}")
    public BoardSingleResult readBoard(@PathVariable Long boardId) {
        log.info("BoardController readBoard / boardId : '{}'", boardId);
        Board board = boardService.readBoard(boardId);

        return responseService.getBoardSingleResult(board);
    }

    @ApiOperation(value = "게시물 생성", notes = "입력한 게시물을 생성한다.")
    @PostMapping("/board")
    public CommonBoardResult createBoard(
                                        @ApiParam(value = "게시물", required = true)
                                        @RequestBody Board board) {
        log.info("BoardController writeBoard / board : '{}'", board.toString());
        boardService.createBoard(board);

        return responseService.getCreatedBoardResult();
    }

    @ApiOperation(value = "게시물 수정", notes = "선택한 게시물을 수정한다.")
    @PutMapping("/board/{boardId}")
    public CommonBoardResult updateBoard(
                                        @PathVariable Long boardId,
                                        @ApiParam(value = "게시물", required = true)
                                        @RequestBody Board board) {
        log.info("BoardController updateBoard / board : '{}'", board.toString());
        boardService.updateBoard(boardId, board);

        return responseService.getCreatedBoardResult();
    }

    @ApiOperation(value = "게시물 삭제", notes = "선택한 게시물을 삭제한다. 삭제시 게시물의 상태값을 0으로 변경한다.")
    @DeleteMapping("/board/{boardId}")
    public CommonBoardResult deleteBoard(@PathVariable Long boardId) {
        log.info("BoardController deleteBoard / boardId : '{}'", boardId);
        boardService.deleteBoard(boardId);

        return responseService.getNoContentBoardResult();
    }
}