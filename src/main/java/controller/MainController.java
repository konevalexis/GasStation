package controller;

import animation.MoveController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {
//    MoveController moveController;
    private MoveController moveController;
    @FXML
    private ImageView imageView;

    public void initialize(){
        moveController = new MoveController(new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString()));
        imageView.setImage(moveController.getImage());
        imageView.setViewport(moveController.getViewports()[2]);
    }
}
