package com.chess.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Path {
    private List<String> path;
    private int length;
    private List<Integer> boardIndexes;

    public Path(){
        this.path = new ArrayList<>();
        this.boardIndexes = new ArrayList<>();
    }
}
