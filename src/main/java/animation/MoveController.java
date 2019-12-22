package animation;

import elements.ExponentialDistribution;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import topologyObjects.TransportVehicle;
import animation.framePackage.FrameAnimation;
import topologyObjects.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lombok.Getter;
import visualize.Grid;

import java.io.IOException;
import java.util.ArrayList;

@Getter
/*Class describes all animation module*/
public class MoveController {
    private Rectangle2D[] viewports;
    private ImageView imageView;

    private int topologyHeight;
    private int topologyWidth;
    private FrameAnimation frameAnimation;
    private double poao = 0.1;
    private TransportVehicle vehicle;

    private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
    private ArrayList<Vehicle> vehicleArrayList = new ArrayList<Vehicle>();

    public MoveController(int topologyHeight, int topologyWidth) {
        this.topologyHeight = topologyHeight;
        this.topologyWidth = topologyWidth;
        frameAnimation = new FrameAnimation(0, 0);
        viewports = frameAnimation.getViewports();
    }

    public void go() throws IOException {
        Stage primaryStage = new Stage();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        int x0 = 270;
        int y0 = 25;
        Grid.setGrid(x0, y0, topologyHeight, topologyWidth);
        for (Line line : Grid.getLineList()) {
            root.getChildren().add(line);
        }

        imageView = frameAnimation.getImageView();
        vehicle = new Vehicle(Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getX(),
                Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getY(), 0.1);
        imageView.setX(vehicle.getX());
        imageView.setY(vehicle.getY());

        root.getChildren().addAll(imageView);

        ExponentialDistribution exponentialDistribution = new ExponentialDistribution(1);
        final double[] i = {0};
        final int[] j = {0};
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(i[0] > 400){
                    i[0] = 0;
                }
                if((int)(exponentialDistribution.getTimes()[j[0]] * 100) == i[0]){
                    vehicleArrayList.add(new Vehicle(Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getX(),
                            Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getY(), 0.1));
                    imageViewList.add(frameAnimation.getImageView());
                    j[0]++;
                }
                for(int k = 0; k < imageViewList.size(); k++){
                    Vehicle tempVehicle = vehicleArrayList.get(k);
                    tempVehicle.moveX(-1);
                    vehicleArrayList.set(k, tempVehicle);

                    ImageView tempImageView = imageViewList.get(k);
                    tempImageView.setTranslateX(vehicleArrayList.get(k).getTranslateX());
                    tempImageView.setTranslateY(vehicleArrayList.get(k).getTranslateY());
                    imageViewList.set(k, tempImageView);
                }
/*                vehicle.moveX(-1);
                imageView.setTranslateX(vehicle.getTranslateX());
                imageView.setTranslateY(vehicle.getTranslateY());*/
                i[0]++;
            }
        };
        animationTimer.start();

        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
