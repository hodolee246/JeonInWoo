package com.rest.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonBoardResult {
    private int statusCode;
    private String statusMessage;
}