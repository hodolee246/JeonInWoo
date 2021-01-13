package com.rest.api;

import static org.junit.jupiter.api.Assertions.*;

import com.rest.api.model.Board;
import com.rest.api.repository.BoardRepository;
import com.rest.api.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    private final int NONE_DELETE_BOARD_STATUS = 1;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        boardRepository.deleteAll();
    }

    @Test
    public void getBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("getBoardWriter")
                .title("getBoardTitle")
                .content("getBoardContent")
                .status(NONE_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(1L);
        assertEquals(board.getBoardId(), 1L);
    }

    @Test
    public void createBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("createBoardWriter")
                .title("createBoardTitle")
                .content("createBoardContent")
                .status(NONE_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(1L);
        assertEquals(board.getBoardId(), 1L);
    }

    @Test
    public void updateBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("boardWriter")
                .title("boardTitle")
                .content("boardContent")
                .status(NONE_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(1L);
        assertEquals(board.getBoardId(), 1L);

        Board updateBoardObject = new Board()
                .builder()
                .boardId(board.getBoardId())
                .writer("updateBoardWriter")
                .title("updateBoardTitle")
                .content("updateBoardContent")
                .status(NONE_DELETE_BOARD_STATUS)
                .build();
        boardService.updateBoard(board.getBoardId(), updateBoardObject);
        assertEquals(board.getBoardId(), updateBoardObject.getBoardId());
        assertEquals(updateBoardObject.getWriter(), "updateBoardWriter");
        assertEquals(updateBoardObject.getTitle(), "updateBoardTitle");
        assertEquals(updateBoardObject.getContent(), "updateBoardContent");
    }

    @Test
    public void deleteBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("createBoardWriter")
                .title("createBoardTitle")
                .content("createBoardContent")
                .status(NONE_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Long createBoardCount = boardRepository.count();
        assertEquals(1, createBoardCount);

        boardService.deleteBoard(1L);
        Long deleteBoardCount = boardRepository.countByStatus(NONE_DELETE_BOARD_STATUS);
        assertEquals(0, deleteBoardCount);
    }
}
