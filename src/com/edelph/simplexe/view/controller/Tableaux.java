package com.edelph.simplexe.view.controller;

import com.edelph.simplexe.util.Simplex;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Tableaux {
    private Simplex simplex;
    private GridPane table;
    private int columnSize;
    private int rowSize;
    private static int rowPivot;

    public static GridPane build(Simplex simplex, int rowPivot) {
        Tableaux tableaux = new Tableaux(simplex);
        Tableaux.rowPivot = rowPivot;
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
            title.add(getText(th,null),c,0);
        }

        for (int r = 0; r < rowSize ; r++) {
            for (int c = 0; c < columnSize ; c++) {
                String td = simplex.getMainSimplex_An().get(r).get(c).get();
                Label ltd = getText(td,null);
                if(simplex.getLinePivot().isPresent()){
                    if(simplex.getColumnPivot().isPresent() &&
                            c == simplex.getColumnPivot().get() &&
                            r == simplex.getLinePivot().get()
                    ){
                        ltd.setId("pivot");
                    }
                }
                if(r == rowPivot) ltd.getStyleClass().add("linePivot");
                tableAn.add(ltd,c,r);
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


        title.add(getText("Ci",null),0,0);
        title.add(getText("i",null),1,0);

        for (int r = 0; r < rowSize ; r++) {
            String tdCI = simplex.getCi().get(r).get();
            String tdI = simplex.getI().get(r).get();

            tableCii.add(getText(tdCI,null),0,r);
            tableCii.add(getText(tdI, null),1,r);
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
            if(simplex.getLinePivot().isPresent() && simplex.getLinePivot().get() == r){
                tableA0.add(getText(tdA0, "cellPivot"),0,r);
            }else tableA0.add(getText(tdA0, null),0,r);

        }
        setBorder(tableA0);
        A0.setAlignment(Pos.CENTER);
        A0.getChildren().addAll(getText("A0",null),tableA0);
        return A0;
    }

    private GridPane createTableDeltaJCj(){
        GridPane tableDeltaJCj = new GridPane();
        for (int c = 0; c < columnSize ; c++) {
            String tdDeltatJ = simplex.getDeltaJ().get(c).get();
            String tdCj = simplex.getCj().get(c).get();
            tableDeltaJCj.add(getText(tdCj, null),c,0);
            if(simplex.getColumnPivot().isPresent() && simplex.getColumnPivot().get() == c){
                tableDeltaJCj.add(getText(tdDeltatJ, "cellPivot"),c,1);
            }else  tableDeltaJCj.add(getText(tdDeltatJ, null),c,1);
        }
        setBorder(tableDeltaJCj);
        return tableDeltaJCj;
    }


    private GridPane createAllTable(){
        VBox title = new VBox();

        Label ci = getText("Ci",null);
        Label deltatJ = getText("Deltat-J",null);
        title.setId("titleCiDeltaJ");
        title.getChildren().addAll(ci, deltatJ);


        table = new GridPane();
        table.add(createTableCii(),0,0);
        table.add(createTableAn(),1,0);
        table.add(createTableA0(),2,0);
        table.add(getText("Z = "+ simplex.getZ().get(), null),2,1);
        table.add(createTableDeltaJCj(),1,1);
        table.add(title,0,1);



        setStyle();
        return table;
    }

    private Label getText(String test, String id){
        Label text = new Label(test);
        if(id != null) text.setId(id);
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
