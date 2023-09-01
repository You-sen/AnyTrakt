package com.example.anytrakt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    BorderPane login_;

    @FXML
    private Button Create_Account_btn;

    @FXML
    private Button Login_btn;

    static String super_name;


    @FXML
    private TextField UserName;
    @FXML
    private PasswordField password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

   /* @FXML
    private void Log_in(ActionEvent event) throws SQLException, Exception {
        String Username = userid.getText();
        String Password = passid.getText();
        DB_Connection dbAction = new DB_Connection();
        try
        {
            Users user = new Users(Username, Password);
            dbAction.insertUser(user);
        }
        catch (Exception e) {

        }
    }*/


    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Search.fxml"));
        login_.getChildren().setAll(pane);
    }

    @FXML
    protected void onHelloButtonClick1(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Create_Account.fxml"));
        login_.getChildren().setAll(pane);
    }



    @FXML
    private void loginAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String name=UserName.getText();
        String pass=password.getText();
        super_name=UserName.getText();

        int c=0;
        ObservableList<User> userList= FXCollections.observableArrayList();
        DB_Connection dbAction = new DB_Connection();
        userList=dbAction.getAllUser();
        for(User user:userList){
            if(user.getUsername().equals(name)&&user.getPassword().equals(pass) && user.getAccountType().equals("Admin") ){
                Parent pane=FXMLLoader.load(getClass().getResource("AdministrationPage.fxml"));
                login_.getChildren().setAll(pane);
                c=1;
                break;
            }
            else if(user.getUsername().equals(name)&&user.getPassword().equals(pass) && user.getAccountType().equals("User")){
                Parent pane=FXMLLoader.load(getClass().getResource("User_HomePage.fxml"));
                login_.getChildren().setAll(pane);
                c=1;

            }

        }  if(name.equals("") || pass.equals("")  ){
            Alert al= new Alert(Alert.AlertType.WARNING);
            al.setTitle("Error");
            al.setContentText("Fill-up Username & password");
            al.showAndWait();
            // c=-10;

        }
        else if (c==0){
            Alert al= new Alert(Alert.AlertType.WARNING);
            al.setTitle("Error");
            al.setContentText("Invalid Username & password");
            al.showAndWait();


        }


      /*  if(name.equals("a")&& pass.equals("a"))   { Parent pane=FXMLLoader.load(getClass().getResource("User_HomePage.fxml"));
            mainPane.getChildren().setAll(pane);
        }

        }
        else{ Parent pane=FXMLLoader.load(getClass().getResource("Search.fxml"));
            mainPane.getChildren().setAll(pane);
        }*/
    }




}