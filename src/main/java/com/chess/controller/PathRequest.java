package com.chess.controller;

import lombok.Data;

@Data
public class PathRequest {
    private String start;
    private String end;
    private boolean withUniq;
}
