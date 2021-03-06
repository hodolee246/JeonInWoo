package com.rest.api.specification;

import com.rest.api.model.Board;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BoardSpecification {

    private final static int NOT_DELETE_BOARD_STATUS = 1;

    public static Specification<Board> boardLike(String category, String keyword) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (category.equals("writer") || category.equals("content") || category.equals("title")) {
                predicates.add(criteriaBuilder.like(root.get(category), "%" + keyword + "%"));
            }
            predicates.add(criteriaBuilder.equal(root.get("status"), NOT_DELETE_BOARD_STATUS));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
