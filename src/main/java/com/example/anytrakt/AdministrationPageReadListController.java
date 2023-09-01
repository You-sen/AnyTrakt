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

public class AdministrationPageReadListController implements Initializable {

    @FXML
    private Button Manga_btn;

    @FXML
    private Button Comic_btn;

    @FXML
    private Button Fiction_btn;

    @FXML
    private Button Nonfiction_btn;

    @FXML
    private AnchorPane show_table_pane;
    @FXML
    private BorderPane AdministrationPage_Pane;
    @FXML
    void onManga_btnClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Manga_List_Table.fxml"));
        show_table_pane.getChildren().setAll(pane);
    }

    @FXML
    void onComic_btnClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Comic_List_Table.fxml"));
        show_table_pane.getChildren().setAll(pane);
    }

    @FXML
    void onFiction_btnClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Fiction_List_Table.fxml"));
        show_table_pane.getChildren().setAll(pane);
    }

    @FXML
    void onNonfiction_btnClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Nonfiction_List_Table.fxml"));
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
