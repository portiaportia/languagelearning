package com.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.language.App;
import com.narraration.*;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        Narriator.playSound("Je mapell Portia!");
        App.setRoot("secondary");
    }
}
