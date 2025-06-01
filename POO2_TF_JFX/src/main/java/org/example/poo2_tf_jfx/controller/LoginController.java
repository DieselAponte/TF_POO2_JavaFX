package org.example.poo2_tf_jfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btn_login;
    @FXML private Button btn_createAccount;

    @FXML
    private void onLoginClicked(){
        String user =txtUsername.getText();
        String pass =txtPassword.getText();

        if(!user.isEmpty() && !pass.isEmpty()){
            try{
                Parent nextRoot = FXMLLoader.load(getClass().getResource("/fxml/JobsListView.fxml"));
                Stage stage = (Stage) btn_login.getScene().getWindow();
                stage.setScene(new Scene(nextRoot));
            }catch (Exception e){
                e.printStackTrace();
            }
        } else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("hey!");
            alerta.setHeaderText("Campos imcompletos");
            alerta.setContentText("El usuario y contrasena no pueden estar vacios");
            alerta.showAndWait();
        }
    }

    @FXML
    private void onCrearCuentaCliked(){
        try{
            Parent nextRoot = FXMLLoader.load(getClass().getResource("/fxml/CreateAccountView.fxml"));
            Stage stage = (Stage) btn_createAccount.getScene().getWindow();
            stage.setScene(new Scene(nextRoot));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
