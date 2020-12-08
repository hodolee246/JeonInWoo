package com.rest.api;

import static org.junit.jupiter.api.Assertions.*;

import com.rest.api.model.Board;
import com.rest.api.repository.BoardRepository;
import com.rest.api.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void getBoard() throws BoardException {
        Board board = boardService.readBoard(6L);
        assertEquals(board.getBoardId(), 6L);
    }

    @Test
    @Transactional
    public void createBoard() throws BoardException {
        boardService.createBoard(Board.builder()
                .boardId(60L)
                .writer("jeoninwoo60")
                .title("title60")
                .content("content60")
                .status(1)
                .build());
        Board board = boardService.readBoard(60L);
        assertEquals(board.getBoardId(), 60L);
    }

    @Test
    @Transactional
    public void updateBoard() throws BoardException {
        boardService.updateBoard(6L, Board.builder()
                .boardId(6L)
                .writer("jeoninwoo6")
                .title("title6")
                .content("content6")
                .status(1)
                .build());
        Board board = boardService.readBoard(6L);
        assertEquals(board.getWriter(), "jeoninwoo6");
    }

    @Test
    @Transactional
    public void deleteBoard() throws BoardException {
        boardService.deleteBoard(6L);
        Board board = boardRepository.findByBoardId(6L);
        assertEquals(board.getStatus(), 0);
    }
}
