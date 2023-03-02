package sample.controler;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import sample.model.Carte;
import javafx.beans.Observable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

public class Controller {
    @FXML
    private Button bStart;

    @FXML
    private GridPane gridPaneCenter;

    @FXML
    private void actionBStart(javafx.event.ActionEvent evt) {

        System.out.println("Carapuce");
        List<Carte> lCarte = createListCarte();

        /* On creer une grille de 6 par 4 qu'on place au centre du BorderPane */
        gridPaneCenter.getColumnConstraints().clear();
        gridPaneCenter.getRowConstraints().clear();

        for(int i = 0; i < 4; i ++) {
            gridPaneCenter.getColumnConstraints().add(new ColumnConstraints());
        }

        for(int j = 0; j < 6; j ++){
            gridPaneCenter.getRowConstraints().add(new RowConstraints());
        }

        /* On rempli toutes les cases par des bouttons avec des images de "?" */
        shuffle(lCarte);

        int iGrid = -1;
        int jGrid = 0;
        for(Carte card : lCarte) {
            iGrid ++;
            if(iGrid >= 4) {
                iGrid = 0;
                jGrid ++;
            }

            ImageView n = new ImageView();
            n.setImage(new Image("file:ressource/img/Inconnu.png", 55, 55,false,false));
            n.setOnMouseClicked(new EventHandler() {

                @Override
                public void handle(Event event) {
                    n.setImage(card.getFace());
                }
            });
            gridPaneCenter.add(n, iGrid, jGrid);
        }




        /* On lance le chrono */
    }

    private List<Carte> createListCarte() {
        List lCarte = new ArrayList<Carte>();
        for(int i = 1; i <= 12; i ++) {
            final String path = i < 10 ? "Stade1_00" + i + ".png" : "Stade1_0" + i + ".png";
            lCarte.add(new Carte(path));
            lCarte.add(new Carte(path));
        }
        return lCarte;
    }

}
