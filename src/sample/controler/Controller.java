package sample.controler;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import sample.model.Carte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.shuffle;

public class Controller {
    @FXML
    private Button bStart;

    @FXML
    private Button bStart2;


    @FXML
    private GridPane gridPaneCenter;

    @FXML
    private Label lTimer;
    @FXML
    private Label lVictoire;

    @FXML
    private Label lScore;

    private int nbPaireRestante; // le nombre de paire restante (a 0, la partie est gagnée)

    private List<Carte> paireATester; // les cartes retournée par le joueur (2 au maximum)

    private Timeline tm = null; // le timer d'une partie;

    private int score; // le score de la partie

    private int nbCarteTotale = 40; // le nombre de carte depend de la difficulté

    private boolean defaite; // true si la partie est perdue (donc si le temps est écoulé)

    MediaPlayer mpVictoire; // media player de la musique de Victoire

    @FXML
    public void initialize(){
        mpVictoire = new MediaPlayer(new Media(new File("ressource/snd/Victoire.mp3").toURI().toString()));
    }

    @FXML
    private void actionBStartEasy(javafx.event.ActionEvent evt) throws IOException {
        nbCarteTotale = 24;
        actionBStart(evt);
    }

    @FXML
    private void actionBStartHard(javafx.event.ActionEvent evt) throws IOException {
        nbCarteTotale = 40;
        actionBStart(evt);
    }
    @FXML
    private void actionBStart(javafx.event.ActionEvent evt) {

        score = 0;
        majScore(0);
        defaite = false;
        mpVictoire.stop();

        List<Carte> lCarte = createListCarte();


        nbPaireRestante = nbCarteTotale / 2;

        // Gestion du Timer :
        Date depart = new Date();
        if(tm != null) {
            tm.stop();
        }
        tm = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            showTime(depart);
        }));


        tm.setCycleCount(Animation.INDEFINITE);
        tm.play();

        lVictoire.setText("");


        /* On creer une grille de 6 par 4 qu'on place au centre du BorderPane */
        gridPaneCenter.getChildren().clear();
        gridPaneCenter.getColumnConstraints().clear();
        gridPaneCenter.getRowConstraints().clear();

        for(int i = 0; i < 4; i ++) {
            gridPaneCenter.getColumnConstraints().add(new ColumnConstraints());
        }

        for(int j = 0; j < nbCarteTotale / 4; j ++){
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

                    if(card.isVisible() && !defaite) {
                        if (paireATester.size() == 2) {
                            // on retourne les 2 cartes précédentes qui ne font pas une paire
                            paireATester.get(0).setBack();
                            paireATester.get(1).setBack();
                            paireATester.clear();
                            majScore(-1);
                        }

                        paireATester.add(card);
                        card.setFace();

                        if (paireATester.size() == 2) {
                            // s'il y a une paire
                            if (paireATester.get(0).equals(paireATester.get(1))) {
                                nbPaireRestante -= 1;
                                paireATester.clear();
                                majScore(2);


                                // si on a gagné
                                if (nbPaireRestante == 0) {
                                    tm.stop();
                                    lVictoire.setText("Victoire !");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            mpVictoire.play();
                                        }
                                    });
                                } else {
                                    // effet sonore (cri du pokémon)
                                    (new MediaPlayer(card.getCry())).play();
                                }

                            }
                        }
                        actualiserImageView(lCarte);
                    }

                }
            });
            gridPaneCenter.add(n, iGrid, jGrid);
        }




        /* On lance le chrono */
    }

    private List<Carte> createListCarte() {
        List lCarte = new ArrayList<Carte>();
        for(int i = 1; i <= 12; i ++) {
            final String path = i < 10 ? "Stade1_00" + i : "Stade1_0" + i;
            lCarte.add(new Carte(path));
            lCarte.add(new Carte(path));
        }

        if(nbCarteTotale == 40) {
            for(int i = 1; i <= 8; i ++) {
                final String path = i < 10 ? "Stade2_00" + i : "Stade2_0" + i;
                lCarte.add(new Carte(path));
                lCarte.add(new Carte(path));
            }
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

    /**
     * Met à jour le label Timer
     * est appellée toutes les secondes.
     * @param depart l'heure a laquelle le timer a commencé
     */
    private void showTime(Date  depart) {
        long tempsRestant = (500 - TimeUnit.SECONDS.convert((new Date()).getTime() - depart.getTime(),TimeUnit.MILLISECONDS));

        if(tempsRestant <= 0) {
            tempsRestant = 0;
            lVictoire.setText("Défaite !");
            defaite = true;
        }

        lTimer.setText( "Timer : " + String.valueOf(tempsRestant) + "s");

    }

    /**
     * Met à jour le score avec l'increment passé en paramètre
     * Le score ne peut pas être négatif
     * @param increment ce qu'il faut ajouter au score
     */
    private void majScore(int  increment) {
        if(score + increment >= 0) {score += increment;}
        lScore.setText("Score : " + score);
    }


}
