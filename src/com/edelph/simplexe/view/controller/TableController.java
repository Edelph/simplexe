package com.edelph.simplexe.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    @FXML
    private TableView<String> tabCii,tabA0,tabAn,tabCjDeltaj;
    @FXML
    private Label labelZ, labelCj, labelJ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setValueTabCii(Collection<?> Ci, Collection<?> i){

    }
    public void setValueTabAn(Collection<?> An){

    }
    public void setValueTabA0(Collection<?> A0){

    }
    public void setValueTabCjDeltaj(Collection<?> Cj, Collection<?> deltaJ){

    }

    public void setValueZ(String valueZ){
        this.labelZ.setText(" Z = "+valueZ);
    }
}
