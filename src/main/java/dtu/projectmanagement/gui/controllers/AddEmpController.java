package dtu.projectmanagement.gui.controllers;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.gui.ManagementAppGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddEmpController {

    ManagementApp managementApp;

    @FXML
    private TextField tfUsername;
    @FXML private Label lblError;
    @FXML private Button btnCancel;

    @FXML
    public void initialize() {
        managementApp = ManagementAppGUI.managementApp;
    }



    @FXML
    public void onBtnCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onBtnOK() {
        lblError.setTextFill(Color.RED);
        if ((tfUsername.getLength() == 0) || tfUsername.getLength() > 4) {
            lblError.setText("The username needs to be between one and\nfour letters long.");
            return;
        }

        if (!tfUsername.getText().matches("^[a-zA-Z]+$")) {
            lblError.setText("The username cannot contain non-alphabetical\ncharacters (a-zA-Z).");
            return;
        }

        try {
            managementApp.addEmployee(tfUsername.getText().toLowerCase());
        } catch (OperationNotAllowedException e) {
            lblError.setText(e.getMessage());
            return;
        }
        lblError.setTextFill(Color.GREENYELLOW);
        lblError.setText(tfUsername.getText() + " successfully added");

        tfUsername.setText("");
        tfUsername.requestFocus();
    }
}
