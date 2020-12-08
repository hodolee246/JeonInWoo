package com.rest.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardListResult extends CommonBoardResult {
    private List<Board> boardList;
}