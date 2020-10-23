package com.lc.game.minesweeper.service;

import com.lc.game.minesweeper.constants.GlobalConfig;
import com.lc.game.minesweeper.entity.Coordinate;
import com.lc.game.minesweeper.entity.MineCell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 单元格服务
 *
 * @author yz_luochong
 * @date 2020/10/23
 */
public class MineCellService {

    public static Map<Coordinate, MineCell> mineCellMap = new HashMap<>();

    /**
     * 初始化所有单元格
     */
    public static void initMineCell() {
        for (int i = 0; i < GlobalConfig.maxRows; i++) {
            for (int j = 0; j < GlobalConfig.maxCols; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                mineCellMap.put(coordinate, new MineCell(coordinate));
            }
        }
    }

    public void setMineCellValue(Coordinate coordinate, Integer value) {
        mineCellMap.get(coordinate).setValue(value);
    }

    public void handleOffset(Coordinate coordinate, Integer value) {

        if (value.equals(getOffsetUnopened(coordinate))) {
            coordinate.getOffset().stream()
                    .filter(t -> getMineCell(t).isUnopened())
                    .forEach(t -> setMineCellValue(t, MineCell.mine));
        }

        if (value.equals(getOffsetMine(coordinate))) {
            coordinate.getOffset().stream()
                    .filter(t -> getMineCell(t).isUnopened())
                    .forEach(t -> setMineCellValue(t, MineCell.notMine));
        }
    }

    public List<Coordinate> getAllNotMineCoordinate() {
        return mineCellMap.values().stream()
                .filter(t -> MineCell.notMine.equals(t.getValue()))
                .map(MineCell::getCoordinate)
                .collect(Collectors.toList());
    }

    /**
     * 获取单元格附近的雷数量
     */
    public Integer getOffsetMine(Coordinate coordinate) {
        return (int) coordinate.getOffset().stream().filter(t -> getMineCell(t).isMine()).count();
    }

    /**
     * 获取单元格附近的未打开格子数量
     */
    public Integer getOffsetUnopened(Coordinate coordinate) {
        return (int) coordinate.getOffset().stream().filter(t -> getMineCell(t).isUnopened()).count();
    }

    /**
     * 根据坐标获取单元格
     */
    public MineCell getMineCell(Coordinate coordinate) {
        return mineCellMap.get(coordinate);
    }

}
