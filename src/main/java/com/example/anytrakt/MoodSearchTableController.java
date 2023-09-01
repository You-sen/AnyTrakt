package com.example.anytrakt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoodSearchTableController implements Initializable {
    @FXML
    private TableColumn<Watch_List,String> Genre_col;

    @FXML
    private TableColumn<Watch_List,String> IMDb_Rating_col;

    @FXML
    private TableView<Watch_List> Movie_Table;


    @FXML
    private TableColumn<Watch_List,String> Release_Year_col;

    @FXML
    private TableColumn<Watch_List,String> Title_col;


    static ObservableList<Watch_List> UserOnMoodMovielist= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Movie_Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DB_Connection dbAction = new DB_Connection();
        try{
            MoodSearchTableController.UserOnMoodMovielist=  dbAction.getAllMoviesOnMood();


        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(UserMovieStatusTableController.class.getName()).log(Level.SEVERE, null, e);
        }

        Title_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Title"));

        Release_Year_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Release_Year"));
        Genre_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Genre"));
        IMDb_Rating_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("IMDb_Rating"));

        Movie_Table.setItems(MoodSearchTableController.UserOnMoodMovielist);
    }
    @FXML
    void GroupByDepressed(ActionEvent event) throws SQLException {

        ObservableList<Watch_List> selectedMovie= FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedMovie =  dbAction.getAllMovieGroupByDepressed();

        Movie_Table.setItems(selectedMovie);


    }

    @FXML
    void GroupByAngry(ActionEvent event) throws SQLException{

        ObservableList<Watch_List> selectedMovie=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedMovie =  dbAction.getAllMovieGroupByAngry();

        Movie_Table.setItems(selectedMovie);

    }

    @FXML
    void GroupBySad(ActionEvent event) throws SQLException{

        ObservableList<Watch_List> selectedMovie=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedMovie =  dbAction.getAllMovieGroupBySad();

        Movie_Table.setItems(selectedMovie);

    }

    @FXML
    void GroupByHappy(ActionEvent event) throws SQLException{

        ObservableList<Watch_List> selectedMovie=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedMovie =  dbAction.getAllMovieGroupByHappy();

        Movie_Table.setItems(selectedMovie);

    }



}
