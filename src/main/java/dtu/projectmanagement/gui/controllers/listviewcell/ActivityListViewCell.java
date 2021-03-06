package dtu.projectmanagement.gui.controllers.listviewcell;

import dtu.projectmanagement.domain.Activity;
import dtu.projectmanagement.gui.ManagementAppGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ActivityListViewCell extends ListCell<Activity> {

    @FXML private Label lblCellActivityName;
    @FXML private Label lblCellActivityType;
    @FXML private VBox cell;

    public ActivityListViewCell() {
        super();
    }

    @Override
    protected void updateItem(Activity act, boolean empty) {
        super.updateItem(act, empty);

        if (empty || act == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("components/actListCell-comp.fxml"));
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            lblCellActivityName.setText(act.getActivityName());
            if (act.getActivityType() == Activity.PROJECT_TYPE)
                lblCellActivityType.setText("In Project: " + act.getParentProject().getProjectNum());
            else
                lblCellActivityType.setText("Personal Activity for " + act.getParentEmployee().getUsername());

            setCursor(Cursor.HAND);

            setText(null);
            setGraphic(cell);
        }
    }
}
