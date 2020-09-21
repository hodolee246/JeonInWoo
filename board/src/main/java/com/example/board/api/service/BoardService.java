package com.example.board.api.service;

import com.example.board.api.BoardException;
import com.example.board.api.model.Board;
import com.example.board.api.repository.BoardRepository;
import com.example.board.api.specification.BoardSpecification;
import com.example.board.api.util.BoardStatusUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class BoardService {

    private final BoardRepository boardRepository;
    // 삭제된 게시판 상태 코드
    private static final int DELETE_BOARD_CODE = 0;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<Board> boardList(String category, String keyword, Pageable pageable) throws BoardException {
        // category, keyword like query 생성
        Specification<Board> specification = Specification.where(BoardSpecification.boardLike(category, keyword));
        try {
            return boardRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new BoardException(BoardStatusUtil.getServerErrorMessage(), BoardStatusUtil.getServerErrorCode());
        }
    }

    public void createBoard(Board board) throws BoardException {
        try {
            boardRepository.save(board);
        } catch (Exception e) {
            throw new BoardException(BoardStatusUtil.getServerErrorMessage(), BoardStatusUtil.getServerErrorCode());
        }
    }

    public Board readBoard(Long boardId) throws BoardException {
        try {
            Board board = boardRepository.findByBoardId(boardId);
            // 조회한 게시물이 삭제된 게시물이면 NullPointException을 발생시킨다.
            if(board.getStatus() == DELETE_BOARD_CODE) {
                throw new NullPointerException();
            } else {
                return board;
            }
        } catch (NullPointerException exception) {
            throw new BoardException(BoardStatusUtil.getNotFoundErrorMessage(), BoardStatusUtil.getNotFoundCode());
        } catch (Exception e) {
            throw new BoardException(BoardStatusUtil.getServerErrorMessage(), BoardStatusUtil.getServerErrorCode());
        }
    }

    public void updateBoard(Long boardId, Board board) throws BoardException {
        try {
            Board isBoard = boardRepository.findByBoardId(boardId);
            // 조회한 게시물이 삭제된 게시물이면 NullPointException을 발생시킨다.
            if(isBoard.getStatus() == DELETE_BOARD_CODE) {
                throw new NullPointerException();
            }
            boardRepository.save(board);
        } catch (NullPointerException exception) {
            throw new BoardException(BoardStatusUtil.getNotFoundErrorMessage(), BoardStatusUtil.getNotFoundCode());
        } catch (Exception e) {
            throw new BoardException(BoardStatusUtil.getServerErrorMessage(), BoardStatusUtil.getServerErrorCode());
        }
    }

    public void deleteBoard(Board board) throws BoardException {
        try {
            // 게시물 상태값 변경
            boardRepository.save(board);
        } catch (Exception e) {
            throw new BoardException(BoardStatusUtil.getServerErrorMessage(), BoardStatusUtil.getServerErrorCode());
        }
    }
}
