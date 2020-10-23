package com.lc.game.minesweeper.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 扫雷单元格信息
 *
 * @author yz_luochong
 * @date 2020/10/23
 */
@Data
@Accessors(chain = true)
public class MineCell {

    // 未打开
    public final static Integer unopened = -1;

    // 标记雷
    public final static Integer mine = -2;

    // 标记不是雷
    public final static Integer notMine = -3;

    // 单元格值，-2 ：雷，-1 ：未打开，>0 ：附近雷的数量
    private Integer value = unopened;

    // 单元格坐标
    private Coordinate coordinate;

    public MineCell(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * 是否是雷
     */
    public boolean isMine() {
        return Objects.equals(value, mine);
    }

    /**
     * 是否未打开
     */
    public boolean isUnopened() {
        return Objects.equals(value, unopened);
    }

}
