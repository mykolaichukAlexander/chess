package com.chess.service;

import com.chess.service.dto.Path;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PathServiceImplTest {

    private PathServiceImpl pathService;

    private List<Path> b3A1uniqPaths;

    @BeforeEach
    public void init() {
        pathService = new PathServiceImpl();
        pathService.init();
        b3A1uniqPaths = Lists.list(
                Path.builder()
                        .boardIndexes(Lists.list(41, 56))
                        .path(Lists.list("B3", "A1"))
                        .length(1)
                        .build(),
                Path.builder()
                        .boardIndexes(Lists.list(41, 35, 50, 56))
                        .path(Lists.list("B3", "D4", "C2", "A1"))
                        .length(3)
                        .build()
                );
    }


    @Test
    public void shouldReturnValidCollectionValues() {
        List<Path> paths = pathService.findPaths("b3", "a1", true);
        assertEquals(paths, b3A1uniqPaths);
    }


    @Test
    public void shouldReturnValidCollection() {
        int size = pathService.findPaths("b3", "a1", false).size();
        assertEquals(7, size);
    }

    @Test
    public void shouldReturnValidUniqCollection() {
        int size = pathService.findPaths("b3", "a1", true).size();
        assertEquals(2, size);
    }

    @Test
    public void shouldReturnEmptyCollection() {
        int size = pathService.findPaths("b3", "h8", false).size();
        assertEquals(0, size);
    }

    @Test
    public void shouldThrowException() {

        assertThrows(RuntimeException.class, () -> pathService.findPaths("b3asd", "h8", false));
    }
}
