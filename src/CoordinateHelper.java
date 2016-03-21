/**
 * Created by ChenLetian on 3/20/16.
 */

/**
 * 块坐标帮助类
 */
public class CoordinateHelper {

    /**
     * 需要建立块坐标的区域的原点
     */
    private Point origin;
    /**
     * 需要建立块坐标的的区域的尺寸
     */
    private Size size;

    /**
     * 块的行数
     */
    private final int rowNumber = 9;
    /**
     * 块的列数
     */
    private final int columnNumber = 9;

    /**
     * 初始化块坐标帮助类
     * @param origin 原点坐标
     * @param size 尺寸
     */
    public CoordinateHelper(Point origin, Size size) {
        this.origin = origin;
        this.size = size;
    }

    /**
     * 取得块区域某一块的坐标
     * @param row 行数
     * @param column 列数
     * @return 那一块的坐标
     */
    public Rectangle getCoordinateOfGrid(int row, int column) {

        Size resultSize = new Size(size.width / columnNumber, size.height / columnNumber);
        int originY = origin.y + resultSize.height * row;
        int originX = origin.x + size.width - (column + 2) * resultSize.width;
        return new Rectangle(new Point(originX, originY), resultSize);

    }

}