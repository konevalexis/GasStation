package controller;

import animationModule.MoveController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MainController {
    MoveController moveController;
    @FXML
    private ImageView imageView1;

    public void initialize(){
        moveController = new MoveController();
        this.imageView1 = moveController.getImageView();
        imageView1.setViewport(moveController.getViewports()[0]);
    }
}
