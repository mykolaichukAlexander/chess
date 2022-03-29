package com.chess.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class BoardPoint {

    private BoardPoint parent;

    private Integer x;
    private String xStr;
    private Integer y;


    public List<BoardPoint> getKnightPieceMoveVariants(){
        List<BoardPoint> variants = new ArrayList<>();

        variants.add(BoardPoint.builder().x(this.getX()-2).y(this.getY()+1).parent(this).build());
        variants.add(BoardPoint.builder().x(this.getX()-2).y(this.getY()-1).parent(this).build());
        variants.add(BoardPoint.builder().x(this.getX()+2).y(this.getY()+1).parent(this).build());
        variants.add(BoardPoint.builder().x(this.getX()+2).y(this.getY()-1).parent(this).build());
        variants.add(BoardPoint.builder().y(this.getY()-2).x(this.getX()+1).parent(this).build());
        variants.add(BoardPoint.builder().y(this.getY()-2).x(this.getX()-1).parent(this).build());
        variants.add(BoardPoint.builder().y(this.getY()+2).x(this.getX()+1).parent(this).build());
        variants.add(BoardPoint.builder().y(this.getY()+2).x(this.getX()-1).parent(this).build());
        return variants.stream().filter(p -> p.getX() > 0 && p.getY() > 0 && p.getX() < 9 && p.getY() < 9)
                .collect(Collectors.toList());
    }

}
