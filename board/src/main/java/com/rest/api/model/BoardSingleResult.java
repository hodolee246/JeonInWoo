package com.rest.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSingleResult extends CommonBoardResult {
    private Board board;
}