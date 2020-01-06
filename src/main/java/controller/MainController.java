package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public void initialize(){
    }

    public void createTopology() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/topologySize.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void downloadTopology() throws IOException {

    }

    public void developerInfo() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/developerInfo.fxml"));
        primaryStage.setTitle("О РАЗРАБОТЧИКЕ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void systemInfo() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/systemInfo.fxml"));
        primaryStage.setTitle("О СИСТЕМЕ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
