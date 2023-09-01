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

public class AnimeListTableController implements Initializable {
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
    private BorderPane Anime_List_Anchor;

    @FXML
    private TableView<Watch_List> Anime_Table;

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
    private TableColumn<Watch_List, String> Title_col;

    static ObservableList<Watch_List> Animelist= FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        GenreComboBox.setItems(GenreList);
        Search_type_comboBox.setItems(columntpye);
        Anime_Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DB_Connection db = new DB_Connection();
        try{
            AnimeListTableController.Animelist=  db.getAllAnimelist();


        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(AnimeListTableController.class.getName()).log(Level.SEVERE, null, e);
        }

        Title_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Title"));
        Release_Year_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Release_Year"));
        Genre.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Genre"));
        IMDb_Rating_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("IMDb_Rating"));

        Anime_Table.setItems(AnimeListTableController.Animelist);
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

            Watch_List Anime = new Watch_List(Title,Release_Year,Genre,IMDb_Rating);
            dbAction.insertAnime(Anime);

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

        ObservableList<Watch_List> selectedAnime=FXCollections.observableArrayList();
        selectedAnime=Anime_Table.getSelectionModel().getSelectedItems();
        DB_Connection dbAction=new DB_Connection();
        dbAction.deleteAnime(selectedAnime);
        AnimeListTableController.Animelist.removeAll(selectedAnime);



    }

    @FXML
    private void AnimeSearch_Action(ActionEvent event) throws SQLException {
        String Type=Search_type_comboBox.getValue();
        String value=Search_value.getText();
        ObservableList<Watch_List> selectedAnime=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedAnime  = dbAction.searchAnime(Type, value);


        Anime_Table.setItems(selectedAnime);


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

                Watch_List anime = new Watch_List(Title,Release_Year,Genre,IMDb_Rating);
                DB_Connection dbAction = new DB_Connection();
                dbAction.UpdateAnime(anime);

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
