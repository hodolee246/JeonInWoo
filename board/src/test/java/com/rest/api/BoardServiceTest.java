package com.rest.api;

import static org.junit.jupiter.api.Assertions.*;

import com.rest.api.model.Board;
import com.rest.api.repository.BoardRepository;
import com.rest.api.service.BoardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardServiceTest {

    private final int NOT_DELETE_BOARD_STATUS = 1;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        boardRepository.deleteAll();
    }

    public List<Board> getList() {
        List<Board> boardList = new ArrayList();
        boardList.add(Board.builder().writer("writerA").title("titleA").content("contentA").status(NOT_DELETE_BOARD_STATUS).build());
        boardList.add(Board.builder().writer("writerB").title("titleB").content("contentB").status(NOT_DELETE_BOARD_STATUS).build());
        boardList.add(Board.builder().writer("writerB").title("titleB").content("contentB").status(NOT_DELETE_BOARD_STATUS).build());
        boardList.add(Board.builder().writer("writerA").title("titleA").content("contentA").status(NOT_DELETE_BOARD_STATUS).build());
        boardList.add(Board.builder().writer("writerB").title("titleB").content("contentB").status(NOT_DELETE_BOARD_STATUS).build());

        return boardList;
    }

    @Test
    @Order(0)
    public void getSearchBoard() throws BoardException{
        String category = "title";
        String keywordA = "titleA";
        String keywordB = "titleB";
        Pageable pageable = PageRequest.of(0, 2);
        List<Board> boardList = getList();

        for(Board board : boardList) {
            boardService.createBoard(board);
        }
        Page<Board> boardA = boardService.boardList(category, keywordA, pageable);
        Page<Board> boardB = boardService.boardList(category, keywordB, pageable);

        assertEquals(boardA.getTotalPages(), 1);
        assertEquals(boardA.getContent().get(1).getWriter(), "writerA");
        assertEquals(boardA.getContent().get(1).getTitle(), "titleA");
        assertEquals(boardA.getContent().get(1).getContent(), "contentA");
        assertEquals(boardB.getTotalPages(), 2);
        assertEquals(boardB.getContent().get(1).getWriter(), "writerB");
        assertEquals(boardB.getContent().get(1).getTitle(), "titleB");
        assertEquals(boardB.getContent().get(1).getContent(), "contentB");
    }

    @Test
    @Order(1)
    public void getBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("boardWriter")
                .title("boardTitle")
                .content("boardContent")
                .status(NOT_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(6L);

        assertEquals(board.getBoardId(), 6L);
        assertEquals(board.getWriter(), "boardWriter");
        assertEquals(board.getTitle(), "boardTitle");
        assertEquals(board.getContent(), "boardContent");
    }

    @Test
    @Order(2)
    public void createBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("boardWriter")
                .title("boardTitle")
                .content("boardContent")
                .status(NOT_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(7L);

        assertEquals(board.getBoardId(), 7L);
        assertEquals(board.getWriter(), "boardWriter");
        assertEquals(board.getTitle(), "boardTitle");
        assertEquals(board.getContent(), "boardContent");
    }

    @Test
    @Order(3)
    public void updateBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("boardWriter")
                .title("boardTitle")
                .content("boardContent")
                .status(NOT_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(8L);

        assertEquals(board.getBoardId(), 8L);
        assertEquals(board.getWriter(), "boardWriter");
        assertEquals(board.getTitle(), "boardTitle");
        assertEquals(board.getContent(), "boardContent");

        Board updateBoardObject = new Board()
                .builder()
                .boardId(board.getBoardId())
                .writer("updateBoardWriter")
                .title("updateBoardTitle")
                .content("updateBoardContent")
                .status(NOT_DELETE_BOARD_STATUS)
                .build();
        boardService.updateBoard(board.getBoardId(), updateBoardObject);

        assertEquals(board.getBoardId(), updateBoardObject.getBoardId());
        assertEquals(updateBoardObject.getWriter(), "updateBoardWriter");
        assertEquals(updateBoardObject.getTitle(), "updateBoardTitle");
        assertEquals(updateBoardObject.getContent(), "updateBoardContent");
    }

    @Test
    @Order(4)
    public void deleteBoard() throws BoardException {
        Board boardObject = new Board()
                .builder()
                .writer("boardWriter")
                .title("boardTitle")
                .content("boardContent")
                .status(NOT_DELETE_BOARD_STATUS)
                .build();
        boardService.createBoard(boardObject);
        Long notDeleteBoardCount = boardRepository.count();

        assertEquals(1, notDeleteBoardCount);

        boardService.deleteBoard(9L);
        Long deleteBoardCount = boardRepository.countByStatus(NOT_DELETE_BOARD_STATUS);

        assertEquals(0, deleteBoardCount);
    }
}
