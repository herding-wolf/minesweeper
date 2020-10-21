package com.lc.game.minesweeper.auto;

import com.lc.game.minesweeper.constants.GlobalConfig;
import com.lc.game.minesweeper.constants.GlobalConstants;
import com.lc.game.minesweeper.entity.Coordinate;
import com.lc.game.minesweeper.utils.functions.DoubleForeach;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yz_luochong
 * @date 2020/10/16
 */
public class Test {

    static Boolean[][] openFlag;

    static {
        openFlag = new Boolean[GlobalConfig.maxRows][GlobalConfig.maxCols];
        DoubleForeach.arrayForeach(openFlag, (t, i, j) -> openFlag[i][j] = false);
    }

    public static void main(String[] args) {
        String[][] labels = new String[8][10];
        init(labels);
        randomBomb(labels, 8);
        writeNumber(labels);
        print(labels);
        try {
            autoTest(labels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init(String[][] labels) {
        DoubleForeach.arrayForeach(labels, (label, i, j) -> labels[i][j] = "0");
    }

    // 产生bombCount个炸弹,并在labels中用"*"标注出来
    private static void randomBomb(String[][] labels, int bombCount) {
        for (int i = 0; i < bombCount; i++) {
            // 生成一个随机数表示行坐标
            int rRow = (int) (Math.random() * labels.length);
            // 生成一个随机数表示列坐标
            int rCol = (int) (Math.random() * labels[0].length);
            while ("*".equals(labels[rRow][rCol])) {
                rRow = (int) (Math.random() * labels.length);
                rCol = (int) (Math.random() * labels[0].length);
            }
            // 根据坐标确定JLabel的位置,并显示*
            labels[rRow][rCol] = "*";
        }
    }

    // 将炸弹的周围标注上数字
    private static void writeNumber(String[][] labels) {
        DoubleForeach.arrayForeach(labels, (label, i, j) -> {
            // 如果是炸弹,不标注任何数字
            if ("*".equals(labels[i][j])) {
                return;
            }
            // 如果不是炸弹,遍历它周围的八个方块,将炸弹的总个数标注在这个方格上
            // 方块周围的8个方块中炸弹个数
            int bombCount = 0;
            // 通过偏移量数组循环遍历8个方块
            for (int[] off: GlobalConstants.offset) {
                int row = i + off[1];
                int col = j + off[0];
                // 判断是否越界,是否为炸弹
                if (verify(labels, row, col) && "*".equals(labels[row][col])) {
                    bombCount++;
                }
            }
            // 如果炸弹的个数不为0,标注出来
            if (bombCount > 0) {
                labels[i][j] = String.valueOf(bombCount);
            }
        });
    }

    /**
     * 打开地雷格子
     *
     * @param labels
     * @param coordinate
     * @return
     */
    private static List<Coordinate> open(String[][] labels, Coordinate coordinate) {
        if ("*".equals(coordinate.getValue(labels))) {
            throw new RuntimeException("结束");
        }

        coordinate.setValue(openFlag, true);
        List<Coordinate> result = new ArrayList<>();
        if ("0".equals(coordinate.getValue(labels))) {
            coordinate.forOffset(t -> {
                System.out.println();
                if (!t.getValue(openFlag)) {
                    result.addAll(open(labels, t));
                }
            });
        }

        result.add(coordinate);
        return result;
    }

    private static void autoTest(String[][] labels) {
        AutoRobot autoRobot = new AutoRobot();
        Map<Coordinate, Integer> map = null;
        while (true) {
            Coordinate coordinate = autoRobot.nextCoordinate(map);
            if (coordinate == null) {
                print(autoRobot.getOpenData());
                throw new RuntimeException("推荐失败");
            }
            List<Coordinate> open = open(labels, coordinate);
            map = open.stream().collect(Collectors.toMap(
                            Function.identity(),
                            t -> Integer.parseInt(t.getValue(labels)),
                            (k1, k2) -> k1));
        }
    }

    private static void print(Object[][] labels) {
        DoubleForeach.arrayForeach(labels, t -> System.out.println(), t -> System.out.print(t + "\t"));
    }

    private static boolean verify(String[][] labels, int row, int col) {
        return row >= 0 && row < labels.length && col >= 0 && col < labels[0].length;
    }
}
