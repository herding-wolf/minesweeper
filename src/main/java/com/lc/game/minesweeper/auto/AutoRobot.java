package com.lc.game.minesweeper.auto;

import com.lc.game.minesweeper.entity.Coordinate;
import com.lc.game.minesweeper.service.MineCellService;
import com.lc.game.minesweeper.utils.CommonUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 自动扫雷机器人
 *
 * @author yz_luochong
 * @date 2020/10/16
 */
@Data
public class AutoRobot {

    private MineCellService mineCellService = new MineCellService();

    public AutoRobot() {
        MineCellService.initMineCell();
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

        // 设置单元格值
        openMap.forEach(mineCellService::setMineCellValue);
        openMap.entrySet().stream().filter(t -> t.getValue() > 0).forEach(t -> mineCellService.handleOffset(t.getKey(), t.getValue()));

        // 找到附近下一步可打开位置
        List<Coordinate> list = mineCellService.getAllNotMineCoordinate();
        if (CommonUtil.isEmpty(list)) {
            return null;
        }
        System.out.println("推荐位置：" + list);
        return list.get(0);
    }

}
