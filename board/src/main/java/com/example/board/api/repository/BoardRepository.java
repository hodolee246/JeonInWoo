package com.example.board.api.repository;

import com.example.board.api.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {

    Page<Board> findAll(Specification<Board> specification, Pageable pageable);

    Board findByBoardId(Long boardId);
}
