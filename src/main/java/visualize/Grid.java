package visualize;

import frameModule.FrameAnimation;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static elements.ElementType.CASHBOX;
import static elements.ElementType.ENTRY;
import static elements.ElementType.EXIT;
import static elements.ElementType.FUELTANK;
import static elements.ElementType.PETROLSTATION;

@Setter
@Getter
public class Grid {
    private static int x0;
    private static int y0;
    private static int x;
    private static int y;
    private static int width;
    private static int height;

    private static GridElement gridElement;
    private static GridPane gridPane = new GridPane();

    private static GridElement[][] grid;
    private static Random rand = new Random();

    public static void initGrid(int topologyX0, int topologyY0,
                                int topologyWidth, int topologyHeight) {
        x0 = topologyX0;
        y0 = topologyY0;
        width = topologyWidth;
        height = topologyHeight;
        grid = new GridElement[width][height + 1];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height + 1; j++) {
                grid[i][j] = new GridElement(i, j, false);
                grid[i][j].setOnDragOver(event -> {
                    if (event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                });
                int finalJ = j;
                int finalI = i;
                grid[i][j].setOnDragDropped(event -> {
                    String[] dragboardStrings = event.getDragboard().getString().split(" ");
                    if(grid[finalI][finalJ].getMainStaticElement() == null) {
                        switch (dragboardStrings[0]) {
                            case "petrolStation":
                                grid[finalI][finalJ].createElement(PETROLSTATION);
                                break;
                            case "fuelTank":
                                grid[finalI][finalJ].createElement(FUELTANK);
                                break;
                            case "exit":
                                grid[finalI][finalJ].createElement(EXIT);
                                grid[finalI][finalJ].getFrameAnimation().getImageView().setRotate(Double.parseDouble(dragboardStrings[1]));
                                break;
                            case "entry":
                                grid[finalI][finalJ].createElement(ENTRY);
                                grid[finalI][finalJ].getFrameAnimation().getImageView().setRotate(Double.parseDouble(dragboardStrings[1]));
                                break;
                            case "cashBox":
                                grid[finalI][finalJ].createElement(CASHBOX);
                                break;
                        }
                        grid[finalI][finalJ].getChildren().add(grid[finalI][finalJ].getFrameAnimation().getImageView());
                    }

                });
                grid[i][j].setOnMouseClicked(event -> {
                    if(grid[finalI][finalJ].getMainStaticElement() != null){
                        grid[finalI][finalJ].getChildren().remove(grid[finalI][finalJ].getFrameAnimation().getImageView());
                        grid[finalI][finalJ].deleteElement();
                    }
                });
            }

        }
        setRoad();
        setStationRoad();
    }

    public static GridElement[][] getGrid () {
        return grid;
    }
    public static int getX0 () {
        return x0;
    }
    public static int getY0 () {
        return y0;
    }
    public static int getWidth () {
        return width;
    }
    public static int getHeight () {
        return height;
    }
    public static List<Line> getLineList () {
        List<Line> lineList = new ArrayList<>();
        for (int j = 0; j < height + 1; j++) {
            Line lineHorizontal = new Line( grid[0][j].getTranslateX(),
                                            grid[0][j].getTranslateY(),
                                            grid[width - 1][j].getTranslateX() + GridElement.getElementWidth(),
                                            grid[width - 1][j].getTranslateY());
            lineList.add(lineHorizontal);
        }
        Line lastLineHorizontal = new Line( grid[0][0].getTranslateX(),
                                            grid[0][height].getTranslateY() + GridElement.getElementHeight(),
                                            grid[width - 1][height].getTranslateX() + GridElement.getElementWidth(),
                                            grid[width - 1][height].getTranslateY() + GridElement.getElementHeight());
        lineList.add(lastLineHorizontal);
        for (int j = 0; j < width; j++) {
            Line lineVertical = new Line(grid[j][0].getTranslateX(),
                                        grid[j][0].getTranslateY(),
                                        grid[j][height].getTranslateX(),
                                        grid[j][height].getTranslateY() + GridElement.getElementHeight());
            lineList.add(lineVertical);
        }
        Line lastLineVertical = new Line(grid[width - 1][0].getTranslateX() + GridElement.getElementWidth(),
                grid[width - 1][0].getTranslateY(),
                grid[width - 1][height].getTranslateX() + GridElement.getElementWidth(),
                grid[width - 1][height].getTranslateY() + GridElement.getElementHeight());
        lineList.add(lastLineVertical);
        return lineList;
    }
    private static void setRoad(){
        for(int i = 0; i < width; i++){
            setImageRoad((int)(rand.nextDouble() * 3), i, height,0);
        }
        setImageRoad(4, width - 1, height,180);
        setImageRoad(4, width - 3, height,180);

    }

    private static void setStationRoad(){
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width - 3; j++) {
                setImageRoad(3, j, i, 0);
                setImageRoad(3, j, i, 0);
                setImageRoad(3, j, i,0);
            }
        setImageRoad(5, width - 3, 0,0);
        setImageRoad(5, 0, 0,0);
        setImageRoad(5, width - 1, 0,90);
        setImageRoad(5, width - 4, 0,90);
        for(int j = 1; j < width - 4; j++) {
            setImageRoad((int) (rand.nextDouble() * 3), j, 0, 0);
        }
        setImageRoad((int) (rand.nextDouble() * 3), width - 2, 0, 0);

        for(int i = 1; i < height; i++) {
            setImageRoad((int) (rand.nextDouble() * 3), width - 3, i, 90);
            setImageRoad((int) (rand.nextDouble() * 3), width - 1, i, 90);
            setImageRoad((int) (rand.nextDouble() * 3), width - 4, i, 90);
            setImageRoad((int) (rand.nextDouble() * 3), 0, i, 90);
            setImageRoad(3, width - 2, i,0);
        }

    }

    private static void setImageRoad(int imageOffsetX, int x, int y, double rotate){
        FrameAnimation road = new FrameAnimation(imageOffsetX,
                4,
                50,
                50,
                6);
        ImageView roadImage = road.getImageView();
        roadImage.setFitHeight(GridElement.getElementHeight());
        roadImage.setFitWidth(GridElement.getElementWidth());
        roadImage.setRotate(rotate);
        grid[x][y].getChildren().add(roadImage);
    }
}
