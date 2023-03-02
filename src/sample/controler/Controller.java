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

    private List<Carte> paireATester;

    @FXML
    private GridPane gridPaneCenter;

    @FXML
    private void actionBStart(javafx.event.ActionEvent evt) {

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
        paireATester = new ArrayList<Carte>();

        int iGrid = -1;
        int jGrid = 0;
        for(Carte card : lCarte) {
            iGrid ++;
            if(iGrid >= 4) {
                iGrid = 0;
                jGrid ++;
            }

            ImageView n = new ImageView();
            Image i = new Image("file:ressource/img/truc.png");
            n.setImage(card.getVisible());
            n.setOnMouseClicked(new EventHandler() {

                @Override
                public void handle(Event event) {

                    if(paireATester.size() == 2) {
                        // on retourne les 2 cartes précédente qui ne font pas une paire
                        paireATester.get(0).setBack();
                        paireATester.get(1).setBack();
                        paireATester.clear();
                    }

                    paireATester.add(card);
                    card.setFace();

                    if(paireATester.size() == 2) {
                        // s'il y a une paire
                        if(paireATester.get(0).equals(paireATester.get(1))) {
                            paireATester.clear();
                        }
                    }
                    actualiserImageView(lCarte);

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

    /**
     * Actualise les ImageViews de gridPaneCenter avec les images des cartes pris en argument
     * @param lCarte dont les ImageView affiche les images
     */
    private void actualiserImageView(List<Carte> lCarte) {
        int i = 0;
        for(Carte card : lCarte) {
            ((ImageView) gridPaneCenter.getChildren().get(i)).setImage(card.getVisible());
            i ++;
        }
    }

}
