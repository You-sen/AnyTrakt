package com.example.anytrakt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {

    @FXML
    private AnchorPane Create_Account_Anchor;
    @FXML
    private TextField Password_Text;

    @FXML
    private TextField User_Text;

    @FXML
    private Button login_btn;

    @FXML
    private TextField AccountType_Text;
    @FXML
    private Button Create_Account_btn;

    @FXML
    void onHelloButtonClick3(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Create_Account_Anchor.getChildren().setAll(pane);
    }

    @FXML
    private void Log_in(ActionEvent event) throws SQLException, Exception {
        String Username = User_Text.getText();
        String Password = Password_Text.getText();
        String AccountType = AccountType_Text.getText();
        DB_Connection dbAction = new DB_Connection();
        try
        {
            User user = new User(Username, Password, AccountType);
            dbAction.insertUser(user);
            Parent pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            Create_Account_Anchor.getChildren().setAll(pane);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
