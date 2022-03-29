package com.chess.service;

import com.chess.service.dto.BoardPoint;
import com.chess.service.dto.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PathServiceImpl implements PathService {

    private Map<String, Integer> xAxis = new HashMap<>();


    @PostConstruct
    public void init() {
        xAxis.put("A", 1);
        xAxis.put("B", 2);
        xAxis.put("C", 3);
        xAxis.put("D", 4);
        xAxis.put("E", 5);
        xAxis.put("F", 6);
        xAxis.put("G", 7);
        xAxis.put("H", 8);
        log.info("Path service was initialize");
    }

    @Override
    public List<Path> findPaths(String startPointStr, String endPointStr, boolean withUniq) {
        BoardPoint startPoint = convertToBoardPoint(startPointStr);
        BoardPoint endPoint = convertToBoardPoint(endPointStr);

        List<BoardPoint> inputList = Collections.singletonList(startPoint);
        List<Path> resultPaths = findPathsRecursively(inputList, 3, new ArrayList<>(), endPoint);

        return withUniq ? filterUniqPaths(resultPaths) : resultPaths;
    }

    private List<Path> findPathsRecursively(List<BoardPoint> inputList,
                                            int movesLeft,
                                            List<Path> result,
                                            BoardPoint endPoint) {
        if (movesLeft == 0) {
            return result;
        } else {
            List<BoardPoint> variants = inputList.stream()
                    .flatMap(p -> p.getKnightPieceMoveVariants().stream())
                    .collect(Collectors.toList());


            List<BoardPoint> searchedPoints = variants.stream()
                    .filter(p -> p.getX().equals(endPoint.getX()) && p.getY().equals(endPoint.getY()))
                    .collect(Collectors.toList());

            int finalMovesLeft = movesLeft;
            result.addAll(searchedPoints.stream().map(p -> createPath(p, new Path(), finalMovesLeft)).collect(Collectors.toList()));

            variants.removeAll(searchedPoints);

            movesLeft = movesLeft - 1;
            return findPathsRecursively(variants, movesLeft, result, endPoint);
        }
    }


    private BoardPoint convertToBoardPoint(String strPoint) {
        if (strPoint.length() == 2) {
            String xStr = strPoint.substring(0, 1);
            String y = strPoint.substring(1);
            return BoardPoint.builder()
                    .x(xAxis.getOrDefault(xStr.toUpperCase(), -1))
                    .xStr(xStr.toUpperCase())
                    .y(Integer.parseInt(y))
                    .build();
        } else {
            throw new RuntimeException("Cant init point from string " + strPoint);
        }
    }

    private Path createPath(BoardPoint boardPoint, Path path, int movesLeft) {
        if (boardPoint.getParent() == null) {
            path.getPath().add(0, getLetterFromCode(boardPoint.getX()) + boardPoint.getY());
            path.setLength(4 - movesLeft);
            path.getBoardIndexes().add(0, calculateBoardIndex(boardPoint));
            return path;
        } else {
            path.getPath().add(0, getLetterFromCode(boardPoint.getX()) + boardPoint.getY());
            path.setLength(4 - movesLeft);
            path.getBoardIndexes().add(0, calculateBoardIndex(boardPoint));
            return createPath(boardPoint.getParent(), path, movesLeft);
        }
    }

    private Integer calculateBoardIndex(BoardPoint boardPoint){
        //Calculating position for UI displaying

        int i1 = (boardPoint.getY() - 1) * 8;
        int i2 = 8 - boardPoint.getX();
        int i3 = (i1 + i2) - 1;
        int i = 63 - i3;

        return i - 1 ;
    }

    private String getLetterFromCode(Integer code) {
        return xAxis.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(code))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("Negative");
    }

    private List<Path> filterUniqPaths(List<Path> paths) {
        return paths.stream()
                .filter(path ->
                        path
                                .getPath()
                                .stream()
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                .values()
                                .stream()
                                .allMatch(count -> count == 1))
                .collect(Collectors.toList());
    }

}
