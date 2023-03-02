package sample.controler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import sample.model.Carte;
import javafx.beans.Observable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

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

        for(int i = 1; i < 4; i ++) {
            gridPaneCenter.getColumnConstraints().add(new ColumnConstraints());
        }

        for(int j = 1; j < 6; j ++){
            gridPaneCenter.getRowConstraints().add(new RowConstraints());
        }

        /* On rempli toutes les cases par des bouttons avec des images de "?" */
        for(int i = 1; i < 4; i ++) {
            for (int j = 1; j < 6; j++) {
                //final int nbAlea = (int)(Math.random() * ((lCarte.size())));
                ImageView n = new ImageView();
                n.setImage(new Image("file:///C:/Users/r.palayret/IdeaProjects/Memory/ressource/img/Stade1_001.png"));
                gridPaneCenter.add(n, i, j);
                System.out.println(n);
                //gridPaneCenter.add(new ImageView(lCarte.get(nbAlea).getFace()), i, j);
                //lCarte.remove(nbAlea);
            }
        }


        /* On lance le chrono */
    }

    private List<Carte> createListCarte() {
        List lCarte = new ArrayList<Carte>();
        for(int i = 1; i < 12; i ++) {
            final String path = "Stade1_00" + i;
            lCarte.add(new Carte(path));
            lCarte.add(new Carte(path));
        }
        return lCarte;
    }

}
