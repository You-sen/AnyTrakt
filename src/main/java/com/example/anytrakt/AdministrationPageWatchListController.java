package com.example.anytrakt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministrationPageWatchListController implements Initializable {

    @FXML
    private Button Anime_btn;

    @FXML
    private Button Drama_btn;
    @FXML
    private BorderPane AdministrationPage_Pane;
    @FXML
    private Button Movie_btn;
    @FXML
    private Button Tv_btn;
    @FXML
    private AnchorPane show_table_pane;

    @FXML
    void onMovie_btnClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Movie_List_Table.fxml"));
        show_table_pane.getChildren().setAll(pane);
    }

    @FXML
    void onDrama_btnClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Drama_List_Table.fxml"));
        show_table_pane.getChildren().setAll(pane);
    }

    @FXML
    void onAnime_btnClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Anime_List_Table.fxml"));
        show_table_pane.getChildren().setAll(pane);
    }

    @FXML
    void onTv_btnClick(ActionEvent event)throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Tv_Series_List_Table.fxml"));
        show_table_pane.getChildren().setAll(pane);
    }


    @FXML
    void OnBack_btn_Action(ActionEvent event)throws IOException {
        Parent pane=FXMLLoader.load(getClass().getResource("AdministrationPage.fxml"));
        AdministrationPage_Pane.getChildren().setAll(pane);
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
