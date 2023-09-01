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

public class ComicListTableController implements Initializable {


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
    private BorderPane Comic_List_Anchor;

    @FXML
    private TableView<Read_List> Comic_Table;



    @FXML
    private Button Search_btn;

    @FXML
    private ComboBox<String> Search_type_comboBox;
    ObservableList<String> columntpye = FXCollections.observableArrayList("Genre","Title","Author");

    @FXML
    private TextField Search_value;



    static ObservableList<Read_List> Comiclist= FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        GenreComboBox.setItems(GenreList);
        Search_type_comboBox.setItems(columntpye);
        Comic_Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DB_Connection dbAction = new DB_Connection();
        try{
            ComicListTableController.Comiclist=  dbAction.getAllComic();


        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(ComicListTableController.class.getName()).log(Level.SEVERE, null, e);
        }

        Title_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Title"));
        Author_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Author"));
        Publish_Year_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Publish_Year"));
        Genre_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Genre"));
        Rating_col.setCellValueFactory(new PropertyValueFactory<Read_List,String>("Rating"));

        Comic_Table.setItems(ComicListTableController.Comiclist);
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

            Read_List Comic = new Read_List(Title,Author,Publish_Year,Genre,Rating);
            dbAction.insertComic(Comic);

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

        ObservableList<Read_List> selectedComic=FXCollections.observableArrayList();
        selectedComic=Comic_Table.getSelectionModel().getSelectedItems();
        DB_Connection dbAction=new DB_Connection();
        dbAction.deleteComic(selectedComic);
        ComicListTableController.Comiclist.removeAll(selectedComic);



    }

    @FXML
    private void ComicSearch_Action(ActionEvent event) throws SQLException {
        String Type=Search_type_comboBox.getValue();
        String value=Search_value.getText();
        ObservableList<Read_List> selectedComic=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedComic  = dbAction.searchComic(Type, value);


        Comic_Table.setItems(selectedComic);


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

                Read_List comic = new Read_List(Title,Publish_Year,Genre,Rating,Author);
                DB_Connection dbAction = new DB_Connection();
                dbAction.UpdateComic(comic);

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
