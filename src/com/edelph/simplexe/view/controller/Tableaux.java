package com.edelph.simplexe.view.controller;

import com.edelph.simplexe.util.Simplex;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

public class Tableaux {
    private Simplex simplex;
    private GridPane table;
    private int columnSize;
    private int rowSize;

    public static GridPane build(Simplex simplex) {
        Tableaux tableaux = new Tableaux(simplex);
        return tableaux.createAllTable();
    }
    public Tableaux(Simplex simplex) {
        this.simplex = simplex;
        this.columnSize = simplex.getMainSimplex_An().get(0).size();
        this.rowSize = simplex.getMainSimplex_An().size();
    }

    private VBox createTableAn(){
        GridPane title = new GridPane();
        GridPane tableAn = new GridPane();
        VBox An = new VBox();

        for (int c = 0; c < columnSize ; c++) {
            String th = "A" + (c+1);
            title.add(getText(th),c,0);
        }

        for (int r = 0; r < rowSize ; r++) {
            for (int c = 0; c < columnSize ; c++) {
                String td = simplex.getMainSimplex_An().get(r).get(c).get();

                tableAn.add(getText(td),c,r);
            }
        }
        setBorder(tableAn);
        An.setAlignment(Pos.CENTER);
        An.getChildren().addAll(title,tableAn);
        return An;
    }


    private VBox createTableCii(){
        VBox Cii = new VBox();
        GridPane title = new GridPane();
        GridPane tableCii = new GridPane();


        title.add(getText("Ci"),0,0);
        title.add(getText("i"),1,0);

        for (int r = 0; r < rowSize ; r++) {
            String tdCI = simplex.getCi().get(r).get();
            String tdI = simplex.getI().get(r).get();

            tableCii.add(getText(tdCI),0,r);
            tableCii.add(getText(tdI),1,r);
        }
        setBorder(tableCii);
        Cii.setAlignment(Pos.CENTER);
        Cii.getChildren().addAll(title,tableCii);
        return Cii;
    }

    private VBox createTableA0(){
        VBox A0 = new VBox();
        GridPane tableA0 = new GridPane();
        for (int r = 0; r < rowSize ; r++) {
            String tdA0 = simplex.getA0().get(r).get();
            tableA0.add(getText(tdA0),0,r);
        }
        setBorder(tableA0);
        A0.setAlignment(Pos.CENTER);
        A0.getChildren().addAll(getText("A0"),tableA0);
        return A0;
    }

    private GridPane createTableDeltaJCj(){
        GridPane tableDeltaJCj = new GridPane();
        for (int c = 0; c < columnSize ; c++) {
            String tdDeltatJ = simplex.getDeltaJ().get(c).get();
            String tdCj = simplex.getCj().get(c).get();

            tableDeltaJCj.add(getText(tdCj),c,0);
            tableDeltaJCj.add(getText(tdDeltatJ),c,1);
        }
        setBorder(tableDeltaJCj);
        return tableDeltaJCj;
    }


    private GridPane createAllTable(){
        VBox title = new VBox();

        Label ci = getText("Ci");
        Label deltatJ = getText("Deltat-J");
        title.setId("titleCiDeltaJ");
        title.getChildren().addAll(ci, deltatJ);


        table = new GridPane();
        table.add(createTableCii(),0,0);
        table.add(createTableAn(),1,0);
        table.add(createTableA0(),2,0);
        table.add(getText("Z = "+ simplex.getZ().get()),2,1);
        table.add(createTableDeltaJCj(),1,1);
        table.add(title,0,1);



        setStyle();
        return table;
    }

    private Label getText(String test){
        Label text = new Label(test);
        text.setStyle("-fx-font-size: 20px;");
        return text;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int getRowSize() {
        return rowSize;
    }

    private void setStyle(){
        table.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../style/table.css")).toExternalForm());
        table.setHgap(10);
        table.setVgap(15);
        table.setId("table");
        table.setMaxWidth(GridPane.USE_PREF_SIZE);
        table.setMaxHeight(GridPane.USE_PREF_SIZE);
    }

    private void setBorder(GridPane pane){
        pane.setVgap(2);
        pane.setHgap(2);
        pane.setGridLinesVisible(true);
    }

}
