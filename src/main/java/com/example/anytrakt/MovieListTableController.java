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
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class MovieListTableController implements Initializable {



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
    private BorderPane Movie_List_Anchor;

    @FXML
    private TableView<Watch_List> Movie_Table;

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

    static ObservableList<Watch_List> Movielist= FXCollections.observableArrayList();

    @FXML
    private Label L_Rating;

    @FXML
    private Label L_Title;

    @FXML
    private Label L_Year;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        GenreComboBox.setItems(GenreList);
        Search_type_comboBox.setItems(columntpye);
        Movie_Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DB_Connection dbAction = new DB_Connection();
        try{
            MovieListTableController.Movielist=  dbAction.getAllMovie();


        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(MovieListTableController.class.getName()).log(Level.SEVERE, null, e);
        }

        Title_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Title"));
        Release_Year_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Release_Year"));
        Genre.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("Genre"));
        IMDb_Rating_col.setCellValueFactory(new PropertyValueFactory<Watch_List,String>("IMDb_Rating"));

        Movie_Table.setItems(MovieListTableController.Movielist);
    }

    public static boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z ]*$");
    }

    public static boolean isDigit(String s) {
        return s != null && s.matches("^[1.00-9.00]*$");
    }


    public static boolean isDate(String s) {
        return s != null && s.matches("(0[1-9]|[12][0-9]|[3][01])-(0[1-9]|1[012])-\\d{4}");
    }
    int checker;


    @FXML
    private void InsertAction(ActionEvent event) throws SQLException, Exception {
        String Title = title_text.getText();
        String Release_Year = Release_Year_text.getText();
        String Genre = GenreComboBox.getValue();
        String IMDb_Rating = IMDb_Rating_text.getText();

        checker = 0;
        if (Title.equals("") || Release_Year.equals("") || Genre.equals("") || IMDb_Rating.equals("") ) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Error");
            al.setContentText("Please Fillup All Information Correctly!");
            al.showAndWait();
        } else {
            try {
                if (!isAlpha(Title)) {
                    L_Title.setText("Invalid Title");
                    checker = 1;
                }
                if (!isDate(Release_Year)) {
                    L_Year.setText("Invalid Year dd-mm-yyyy");
                    checker = 1;
                }
                if (!isDigit(IMDb_Rating)) {
                    L_Rating.setText("Invalid Rating Input(1-9)");
                    checker = 1;
                }

                if (checker == 1) {
                    throw new Exception();
                }


                DB_Connection dbAction = new DB_Connection();
                try {

                    Watch_List movie = new Watch_List(Title, Release_Year, Genre, IMDb_Rating);
                    dbAction.insertMovie(movie);

                    IMDb_Rating_text.clear();
                    Release_Year_text.clear();
                    title_text.clear();
                    GenreComboBox.getSelectionModel().clearSelection();
                    Search_value.clear();
                    Search_type_comboBox.getSelectionModel().clearSelection();
                    L_Rating.setText("");
                    L_Year.setText("");
                    L_Title.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}

    @FXML
    private void UpdateAction(ActionEvent event) throws SQLException, Exception {
        String Title = title_text.getText();
        String Release_Year = Release_Year_text.getText();
        String Genre = GenreComboBox.getValue();
        String IMDb_Rating = IMDb_Rating_text.getText();


        checker = 0;
        if (Title.equals("") || Release_Year.equals("") || Genre.equals("") || IMDb_Rating.equals("") ) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Error");
            al.setContentText("Please Fillup All Information Correctly!");
            al.showAndWait();
        } else {
            try {
                if (!isAlpha(Title)) {
                    L_Title.setText("Invalid Title");
                    checker = 1;
                }
                if (!isDate(Release_Year)) {
                    L_Year.setText("Invalid Year dd-mm-yyyy");
                    checker = 1;
                }
                if (!isDigit(IMDb_Rating)) {
                    L_Rating.setText("Invalid Rating Input(1-9)");
                    checker = 1;
                }

                if (checker == 1) {
                    throw new Exception();
                }




        DB_Connection dbAction = new DB_Connection();
        try
        {

            Watch_List movie = new Watch_List(Title,Release_Year,Genre,IMDb_Rating);
            dbAction.UpdateMovie(movie);

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}



    @FXML
    private void Delete_button_Action(ActionEvent event) throws SQLException {

        ObservableList<Watch_List> selectedMovies=FXCollections.observableArrayList();
        selectedMovies=Movie_Table.getSelectionModel().getSelectedItems();
        DB_Connection dbAction=new DB_Connection();
        dbAction.deleteMovie(selectedMovies);
        MovieListTableController.Movielist.removeAll(selectedMovies);



    }

    @FXML
    private void Search_Action(ActionEvent event) throws SQLException {
        String Type=Search_type_comboBox.getValue();
        String value=Search_value.getText();
        ObservableList<Watch_List> selectedMovies=FXCollections.observableArrayList();
        DB_Connection dbAction=new DB_Connection();
        selectedMovies  = dbAction.searchMovie(Type, value);


        Movie_Table.setItems(selectedMovies);


    }







}
