package com.example.board.repository;

import com.example.board.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {

    Page<Board> findAll(Specification<Board> specification, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Board b set b.status = 0 where b.boardId = :boardId")
    void deleteBoard(Long boardId);
}
