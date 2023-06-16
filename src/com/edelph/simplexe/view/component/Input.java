package com.edelph.simplexe.view.component;

import javafx.scene.control.TextField;

public class Input extends TextField {
    public Input() {
        super();
    }
    public Input(String s) {
        super(s);
    }

    private void defaultConfig(){
        this.minWidth(20);
        this.minHeight(20);
    }
}
