package com.example.board.api.specification;

import com.example.board.api.model.Board;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BoardSpecification {

    public static Specification<Board> boardLike(String category, String keyword) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (category.equals("writer") || category.equals("content") || category.equals("title")) {
                predicates.add(criteriaBuilder.like(root.get(category), "%" + keyword + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
