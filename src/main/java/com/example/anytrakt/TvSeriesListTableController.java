package com.example.anytrakt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TvSeriesListTableController implements Initializable {

    @FXML
    private TextField Release_Year_text;
    @FXML
    private TextField IMDb_Rating_text;
    @FXML
    private TextField title_text;


    @FXML
    private ComboBox<String> GenreComboBox;
    ObservableList<String> GenreList = FXCollections.observableArrayList("Animation","Adventure","Action","Biography","Comedy","Crime","Documentary","Drama","Family","Fantasy","History","Horror","Mystery","Reality-TV","Romance","Sci-Fi","Sport","Thriller","War");


    @FXML
    private TableColumn<Watch_List, String> Genre;

    @FXML
    private TableColumn<Watch_List, String> IMDb_Rating_col;

    @FXML
    private Button Insert_btn;

    @FXML
    private BorderPane Tv_Series_List_Anchor;

    @FXML
    private TableView<Watch_List> Tv_Series_Table;

    @FXML
    private TableColumn<Watch_List, String> Release_Year_col;

    @FXML
    private Button Search_btn;

    @FXML
    private ComboBox<String> Search_type_comboBox;
    ObservableList<String> columntpye = FXCollections.observableArrayList("Genre","Title");
    //ObservableList<String> columntpye = FXCollections.observableArrayList("Release Year", "Genre","IMDb Rating", "Title");
    @FXML
    private TextField Search_value;

    @FXML
    private TableColumn<Watch_List, String> Title_col1;

    static ObservableList<Watch_List> Tv_Serieslist= FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        GenreComboBox.setItems(GenreList);
        Search_type_comboBox.setItems(columntpye);
        Tv_Series_Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DB_Connection db = new DB_Connection();
        try{
            TvSeriesListTableController.Tv_Serieslist=  db.getAllTv_Serieslist();


        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(TvSeriesListTableController.class.getName()).log(Level.SEVERE, null, e);
        }

        Title_col1.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Title"));
        Release_Year_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Release_Year"));
        Genre.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Genre"));
        IMDb_Rating_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("IMDb_Rating"));

        Tv_Series_Table.setItems(TvSeriesListTableController.Tv_Serieslist);
    }





    @FXML
    private void InsertAction(ActionEvent event) throws SQLException, Exception {
        String Title = title_text.getText();
        String Release_Year = Release_Year_text.getText();
        String Genre = GenreComboBox.getValue();
        String IMDb_Rating = IMDb_Rating_text.getText();
        DB_Connection dbAction = new DB_Connection();
        try
        {

            Watch_List Tv_Series = new Watch_List(Title,Release_Year,Genre,IMDb_Rating);
            dbAction.insertTv_Series(Tv_Series);

            IMDb_Rating_text.clear();
            Release_Year_text.clear();
            title_text.clear();
            GenreComboBox.getSelectionModel().clearSelection();
            Search_value.clear();
            Search_type_comboBox.getSelectionModel().clearSelection();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Delete_button_Action(ActionEvent event) throws SQLException {

        ObservableList<Watch_List> selectedTv_Series=FXCollections.observableArrayList();
        selectedTv_Series=Tv_Series_Table.getSelectionModel().getSelectedItems();
        DB_Connection dbAction=new DB_Connection();
        dbAction.deleteTv_Series(selectedTv_Series);
        TvSeriesListTableController.Tv_Serieslist.removeAll(selectedTv_Series);



    }

    @FXML
    private void Search_Action(ActionEvent event) throws SQLException {
        String Type=Search_type_comboBox.getValue();
        String value=Search_value.getText();
        ObservableList<Watch_List> selectedTv_Series=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedTv_Series  = dbAction.searchTv_Series(Type, value);


        Tv_Series_Table.setItems(selectedTv_Series);


    }

    @FXML
    private void UpdateAction(ActionEvent event) throws SQLException, Exception {
        String Title = title_text.getText();
        String Release_Year = Release_Year_text.getText();
        String Genre = GenreComboBox.getValue();
        String IMDb_Rating = IMDb_Rating_text.getText();



        if (Title.equals("") || Release_Year.equals("") || Genre.equals("") || IMDb_Rating.equals("") ) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Error");
            al.setContentText("Please Fillup All Information Correctly!");
            al.showAndWait();
        } else {
                try
                {

                    Watch_List tv_Series = new Watch_List(Title,Release_Year,Genre,IMDb_Rating);
                    DB_Connection dbAction = new DB_Connection();
                    dbAction.UpdateTv_Series(tv_Series);

                    IMDb_Rating_text.clear();
                    Release_Year_text.clear();
                    title_text.clear();
                    GenreComboBox.getSelectionModel().clearSelection();
                    Search_value.clear();
                    Search_type_comboBox.getSelectionModel().clearSelection();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        




        }
}