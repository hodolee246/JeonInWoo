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

/**
 * Board TestCode 필독
 * DB ID값이 AutoIncrement 되도록 설계되어 있어 ID값을 하드코딩으로 사용하여 전체 테스트 실행이 아닌 개별 테스트 실행 시 테스트가 실패합니다.
 * @author JeonInWoo
 *  
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardServiceTest {

    private final int NOT_DELETE_BOARD_STATUS = 1;
    private final int DELETE_BOARD_STATUS = 0;
    private Board boardObject;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        boardRepository.deleteAll();
        boardObject = Board.builder().writer("boardWriter").title("boardTitle").content("boardContent").status(NOT_DELETE_BOARD_STATUS).build();
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

    public void assertionsCheck(Board board) {
        assertEquals(board.getWriter(), "boardWriter");
        assertEquals(board.getTitle(), "boardTitle");
        assertEquals(board.getContent(), "boardContent");
    }

    @Test
    @Order(0)
    @DisplayName("게시물 리스트 조회")
    public void getSearchBoardList() {
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
    @DisplayName("단일 게시물 조회")
    public void getBoard() {
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(6L);

        assertEquals(board.getBoardId(), 6L);
        assertionsCheck(board);
    }

    @Test
    @Order(2)
    @DisplayName("게시물 생성")
    public void createBoard() {
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(7L);

        assertEquals(board.getBoardId(), 7L);
        assertionsCheck(board);
    }

    @Test
    @Order(3)
    @DisplayName("게시물 수정")
    public void updateBoard() {
        boardService.createBoard(boardObject);
        Board board = boardService.readBoard(8L);

        assertEquals(board.getBoardId(), 8L);
        assertionsCheck(board);

        Board updateBoardObject = Board.builder()
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
    @DisplayName("게시물 삭제")
    public void deleteBoard() {
        boardService.createBoard(boardObject);
        Long notDeleteBoardCount = boardRepository.count();

        assertEquals(1, notDeleteBoardCount);

        boardService.deleteBoard(9L);
        Long deleteBoardCount = boardRepository.countByStatus(NOT_DELETE_BOARD_STATUS);

        assertEquals(0, deleteBoardCount);
    }

    @Test
    @Order(5)
    @DisplayName("예외 테스트")
    public void boardException() {
        Board boardObject2 = Board.builder()
                .boardId(10L)
                .writer("boardWriter")
                .title("boardTitle")
                .content("boardContent")
                .status(DELETE_BOARD_STATUS)
                .build();
        assertThrows(BoardRunTimeException.class, () -> {
            boardService.createBoard(boardObject2);
            boardService.readBoard(10L);
        });
        assertThrows(BoardRunTimeException.class, () -> {
            boardService.updateBoard(10L, boardObject2);
        });
        assertThrows(BoardRunTimeException.class, () -> {
            boardService.deleteBoard(10L);
        });

    }
}
