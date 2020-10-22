package com.lc.game.minesweeper.auto;

import com.lc.game.minesweeper.constants.GlobalConfig;
import com.lc.game.minesweeper.entity.Coordinate;
import com.lc.game.minesweeper.utils.CommonUtil;
import com.lc.game.minesweeper.utils.functions.DoubleForeach;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自动扫雷机器人
 *
 * @author yz_luochong
 * @date 2020/10/16
 */
@Data
public class AutoRobot {

    private final static int unopened = -1;

    private final static int mine = -2;

    private Integer[][] openData;

    public AutoRobot() {
        openData = new Integer[GlobalConfig.maxRows][GlobalConfig.maxCols];
        DoubleForeach.arrayForeach(openData, (t, i, j) -> openData[i][j] = unopened);
    }

    /**
     * 计算下一步坐标
     *
     * @param openMap
     * @return
     */
    public Coordinate nextCoordinate(Map<Coordinate, Integer> openMap) {
        if (CommonUtil.isEmpty(openMap)) {
//            return new Coordinate()
//                    .setX((int) (Math.random() * GlobalConfig.maxRows))
//                    .setY((int) (Math.random() * GlobalConfig.maxCols));

            return new Coordinate().setX(1).setY(1);
        }

        openMap.forEach((key, value) -> key.setValue(openData, value));

        // 根据坐标判断附近地雷数量
        List<Coordinate> list = openMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> nextCoordinate(entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (CommonUtil.isEmpty(list)) {
            return null;
        }

        System.out.println("推荐位置：" + list.get(0));
        return list.get(0);
    }

    public List<Coordinate> nextCoordinate(Coordinate coordinate, Integer mineNum) {

        List<Coordinate> offset = coordinate.getOffset();
        long unopenedCount = offset.stream().filter(t -> Objects.equals(t.getValue(openData), unopened)).count();
        if (Objects.equals(unopenedCount, mineNum)) {
            offset.forEach(t -> t.setValue(openData, mine));
        }

        // 如果附近确认都是雷，则可以打开未打开格子
        long mineCount = offset.stream().filter(t -> Objects.equals(t.getValue(openData), mine)).count();
        if (Objects.equals(mineCount, mineNum.longValue())) {
            return offset.stream()
                    .filter(t -> Objects.equals(t.getValue(openData), unopened))
                    .collect(Collectors.toList());
        }

        return null;
    }
}
