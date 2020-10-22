package com.lc.game.minesweeper.utils.functions;

import java.util.Objects;

/**
 * @author yz_luochong
 * @date 2020/10/19
 */
@FunctionalInterface
public interface DoubleForeach<T> {

    void foreach(T t, int i, int j);

    static <T> void arrayForeach(T[][] array, DoubleForeach<T> doubleForeach)  {
        if (Objects.nonNull(array)) {
            for (int  i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    doubleForeach.foreach(array[i][j], i, j);
                }
            }
        }
    }

    static <T> void arrayForeach(T[][] array, SimpleDoubleForeach<T> simpleDoubleForeach)  {
        if (Objects.nonNull(array)) {
            for (int  i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    simpleDoubleForeach.foreach(array[i][j]);
                }
            }
        }
    }
}
