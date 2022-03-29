package com.chess.service;

import com.chess.service.dto.Path;

import java.util.List;

public interface PathService {

    List<Path> findPaths(String startPoint, String endPoint, boolean withUniq);
}
