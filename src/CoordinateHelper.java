/**
 * Created by ChenLetian on 3/20/16.
 */
public class CoordinateHelper {

    private Point origin;
    private Size size;

    private final int rowNumber = 9;
    private final int columnNumber = 9;

    public CoordinateHelper(Point origin, Size size) {
        this.origin = origin;
        this.size = size;
    }

    public Rectangle getCoordinateOfGrid(int row, int column) {

        Size resultSize = new Size(size.width / columnNumber, size.height / columnNumber);
        int originY = origin.y + resultSize.height * row;
        int originX = origin.x + size.width - (column + 2) * resultSize.width;
        return new Rectangle(new Point(originX, originY), resultSize);

    }

}