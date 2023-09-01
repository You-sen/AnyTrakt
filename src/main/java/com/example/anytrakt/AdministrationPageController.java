package com.example.anytrakt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministrationPageController implements Initializable {


    @FXML
    private BorderPane AdministrationPage_Pane;

    @FXML
    private Button ReadList_btn;

    @FXML
    private Button WatchList_btn;


    @FXML
    protected void AdministrationPage_WatchList(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("AdministrationPageWatchList.fxml"));
        AdministrationPage_Pane.getChildren().setAll(pane);
    }

    @FXML
    protected void AdministrationPage_ReadList(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("AdministrationPageReadList.fxml"));
        AdministrationPage_Pane.getChildren().setAll(pane);
    }

    @FXML
    void OnBack_btn_Action(ActionEvent event)throws IOException {
        Parent pane=FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        AdministrationPage_Pane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
