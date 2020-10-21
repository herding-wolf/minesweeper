package com.lc.game.minesweeper.auto;

import com.lc.game.minesweeper.constants.GlobalConfig;
import com.lc.game.minesweeper.entity.Coordinate;
import com.lc.game.minesweeper.utils.CommonUtil;
import com.lc.game.minesweeper.utils.functions.DoubleForeach;
import lombok.Data;

import java.util.Map;

/**
 * 自动扫雷机器人
 *
 * @author yz_luochong
 * @date 2020/10/16
 */
@Data
public class AutoRobot {

    private Integer[][] openData;

    public AutoRobot() {
        openData = new Integer[GlobalConfig.maxRows][GlobalConfig.maxCols];
        DoubleForeach.arrayForeach(openData, (t, i, j) -> openData[i][j] = -1);
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
        return null;
    }
}
