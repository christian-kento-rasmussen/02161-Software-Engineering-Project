package dtu.projectmanagement.gui.controllers.listviewcell;

import dtu.projectmanagement.domain.Project;
import dtu.projectmanagement.gui.ManagementAppGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProjectListViewCell extends ListCell<Project> {

    //@FXML private FontAwesomeIconView
    @FXML private Label lblCellProjectName;
    @FXML private Label lblCellProjectNum;
    @FXML private VBox cell;

    public ProjectListViewCell() {
        super();
    }

    @Override
    protected void updateItem(Project proj, boolean empty) {
        super.updateItem(proj, empty);

        if (empty || proj == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("components/projListCell-comp.fxml"));
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            lblCellProjectNum.setText(proj.getProjectNum());
            lblCellProjectName.setText(proj.getProjectName());
            setCursor(Cursor.HAND);

            setText(null);
            setGraphic(cell);
        }
    }
}
