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

public class MangaListTableController implements Initializable {


    @FXML
    private TextField title_text;
    @FXML
    private TextField Author_text;
    @FXML
    private TextField Publish_Year_text;
    @FXML
    private TextField Rating_text;




    @FXML
    private TableColumn<Read_List, String> Title_col;

    @FXML
    private TableColumn<Read_List, String> Author_col;

    @FXML
    private TableColumn<Read_List, String> Genre_col;

    @FXML
    private TableColumn<Read_List, String> Publish_Year_col;

    @FXML
    private TableColumn<Read_List, String> Rating_col;


    @FXML
    private ComboBox<String> GenreComboBox;
    ObservableList<String> GenreList = FXCollections.observableArrayList("Animation","Adventure","Action","Biography","Comedy","Crime","Documentary","Drama","Family","Fantasy","History","Horror","Mystery","Reality-TV","Romance","Sci-Fi","Sport","Thriller","War");


    @FXML
    private Button Insert_btn;

    @FXML
    private BorderPane Manga_List_Anchor;

    @FXML
    private TableView<Read_List> Manga_Table;



    @FXML
    private Button Search_btn;

    @FXML
    private ComboBox<String> Search_type_comboBox;
    ObservableList<String> columntpye = FXCollections.observableArrayList("Genre","Title","Author");

    @FXML
    private TextField Search_value;



    static ObservableList<Read_List> Mangalist= FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        GenreComboBox.setItems(GenreList);
        Search_type_comboBox.setItems(columntpye);
        Manga_Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DB_Connection dbAction = new DB_Connection();
        try{
            MangaListTableController.Mangalist=  dbAction.getAllManga();


        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(MangaListTableController.class.getName()).log(Level.SEVERE, null, e);
        }

        Title_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Title"));
        Author_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Author"));
        Publish_Year_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Publish_Year"));
        Genre_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Genre"));
        Rating_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Rating"));

        Manga_Table.setItems(MangaListTableController.Mangalist);
    }





    @FXML
    private void InsertAction(ActionEvent event) throws SQLException, Exception {
        String Title = title_text.getText();
        String Author = Author_text.getText();
        String Publish_Year = Publish_Year_text.getText();
        String Genre = GenreComboBox.getValue();
        String Rating = Rating_text.getText();
        DB_Connection dbAction = new DB_Connection();
        try
        {

            Read_List Manga = new Read_List(Title,Author,Publish_Year,Genre,Rating);
            dbAction.insertManga(Manga);

            Rating_text.clear();
            Publish_Year_text.clear();
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

        ObservableList<Read_List> selectedManga=FXCollections.observableArrayList();
        selectedManga=Manga_Table.getSelectionModel().getSelectedItems();
        DB_Connection dbAction=new DB_Connection();
        dbAction.deleteManga(selectedManga);
        MangaListTableController.Mangalist.removeAll(selectedManga);



    }

    @FXML
    private void Search_Action(ActionEvent event) throws SQLException {
        String Type=Search_type_comboBox.getValue();
        String value=Search_value.getText();
        ObservableList<Read_List> selectedManga=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedManga  = dbAction.searchManga(Type, value);


        Manga_Table.setItems(selectedManga);


    }
    @FXML
    private void UpdateAction(ActionEvent event) throws SQLException, Exception {
        String Title = title_text.getText();
        String Publish_Year = Publish_Year_text.getText();
        String Genre = GenreComboBox.getValue();
        String Rating = Rating_text.getText();
        String Author = Author_text.getText();


        if (Title.equals("") || Publish_Year.equals("") || Genre.equals("") || Rating.equals("") || Author.equals("")) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Error");
            al.setContentText("Please Fillup All Information Correctly!");
            al.showAndWait();
        } else {
            try
            {

                Read_List manga = new Read_List(Title,Publish_Year,Genre,Rating,Author);
                DB_Connection dbAction = new DB_Connection();
                dbAction.UpdateManga(manga);

                Rating_text.clear();
                Publish_Year_text.clear();
                Author_text.clear();
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
