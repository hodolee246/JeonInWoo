package com.example.board.service;

import com.example.board.BoardException;
import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.specification.BoardSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    private static final String NOT_FOUND_ERROR_MESSAGE = "요청하신 게시물은 삭제되었거나 존재하지 않는 게시물입니다.";
    private static final String SERVER_ERROR_MESSAGE = "서버의 예기치 못한 오류로 인하여 요청에 실패했습니다.";
    private static final int NOT_FOUND_CODE = 404;
    private static final int SERVER_ERROR_CODE = 500;

    public Page<Board> boardList(String category, String keyword, Pageable pageable) throws BoardException {

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

        Optional<Board> OptionalBoard = boardRepository.findById(boardId);
        Board board = OptionalBoard.orElseThrow(() -> new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE));

        if(board.getStatus() == 0) {
            throw new BoardException(NOT_FOUND_ERROR_MESSAGE, NOT_FOUND_CODE);
        }else {
         return board;
        }
    }

    public void updateBoard(Board board) throws BoardException {

        try {
            boardRepository.save(board);
        } catch (Exception e) {
            throw new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }

    public void deleteBoard(Long boardId) throws BoardException {

        try {
            boardRepository.deleteBoard(boardId);
        } catch (Exception e) {
            throw new BoardException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }
}
