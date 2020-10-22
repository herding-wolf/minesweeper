package com.lc.game.minesweeper.entity;

import com.lc.game.minesweeper.constants.GlobalConstants;
import com.lc.game.minesweeper.constants.GlobalConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 坐标
 *
 * @author yz_luochong
 * @date 2020/10/21
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coordinate {

    private int x;

    private int y;

    public <T> T getValue(T[][] arr) {
        return arr[x][y];
    }

    public <T> void setValue(T[][] arr, T value) {
        arr[x][y] = value;
    }

    public List<Coordinate> getOffset() {
        return Arrays.stream(GlobalConstants.offset)
                .map(t -> new Coordinate(x + t[0], y + t[1]))
                .filter(Coordinate::verify)
                .collect(Collectors.toList());
    }

    public void forOffset(Consumer<Coordinate> consumer) {
        getOffset().forEach(consumer::accept);
    }

    private boolean verify() {
        return x >= 0 && x < GlobalConfig.maxRows && y >= 0 && y < GlobalConfig.maxCols;
    }
}
