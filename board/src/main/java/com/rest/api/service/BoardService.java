package com.rest.api.service;

import com.rest.api.BoardException;
import com.rest.api.model.Board;
import com.rest.api.repository.BoardRepository;
import com.rest.api.specification.BoardSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class BoardService {

    private final BoardRepository boardRepository;
    // 삭제된 게시판 상태 코드
    private static final int DELETE_BOARD_STATUS = 0;
    // 404 상태 코드
    private final int NOT_FOUND_CODE = 404;
    // 500 상태 코드
    private final int SERVER_ERROR_CODE = 500;
    // 404 메시지
    private final String NOT_FOUND_ERROR_MESSAGE = "요청하신 게시물은 삭제되었거나 존재하지 않는 게시물입니다.";
    // 500 메시지
    private final String SERVER_ERROR_MESSAGE = "서버의 예기치 못한 오류로 인하여 요청에 실패했습니다.";

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<Board> boardList(String category, String keyword, Pageable pageable) throws BoardException {
        // category, keyword like query 생성
        Specification<Board> specification = Specification.where(BoardSpecification.boardLike(category, keyword));
        try {
            return boardRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }

    public void createBoard(Board board) throws BoardException {
        try {
            boardRepository.save(board);
        } catch (Exception e) {
            throw new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }

    public Board readBoard(Long boardId) throws BoardException {
        try {
            Board board = boardRepository.findByBoardId(boardId);
            // 조회한 게시물이 삭제된 게시물이면 NullPointException을 발생시킨다.
            if(board == null || board.getStatus() == DELETE_BOARD_STATUS) {
                throw new NullPointerException();
            } else {
                return board;
            }
        } catch (NullPointerException exception) {
            throw new BoardException(NOT_FOUND_ERROR_MESSAGE, NOT_FOUND_CODE);
        } catch (Exception e) {
            throw new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }

    public void updateBoard(Long boardId, Board board) throws BoardException {
        try {
            Board isBoard = boardRepository.findByBoardId(boardId);
            // 조회한 게시물이 삭제된 게시물이면 NullPointException을 발생시킨다.
            if(isBoard == null || board.getStatus() == DELETE_BOARD_STATUS) {
                throw new NullPointerException();
            }
            boardRepository.save(board);
        } catch (NullPointerException exception) {
            throw new BoardException(NOT_FOUND_ERROR_MESSAGE, NOT_FOUND_CODE);
        } catch (Exception e) {
            throw new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }

    public void deleteBoard(Long boardId) throws BoardException {
        try {
            Board board = boardRepository.findByBoardId(boardId);
            if(board == null || board.getStatus() == DELETE_BOARD_STATUS) {
                throw new NullPointerException();
            }
            // 게시물 상태값 변경
            board.setStatus(DELETE_BOARD_STATUS);
            boardRepository.save(board);
        } catch (NullPointerException exception) {
            throw new BoardException(NOT_FOUND_ERROR_MESSAGE, NOT_FOUND_CODE);
        } catch (Exception e) {
            throw new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }
}