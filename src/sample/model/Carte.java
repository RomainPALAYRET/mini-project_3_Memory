/*
 * mini projet 3 : Memory                  28/02/2023
 * 3IL Ingénieur
 */
package sample.model;

/**
 * Une carte d'un jeu mémory, cette carte possède une image qui la définie
 * @author Palayret Romain
 * @version 1.0.0
 */
public class Carte {

    private String path; // le chemin de l'image sur la carte;
    /**
     * Constructeur de la classe Carte
     * @param path le chemin de l'image
     */
    public Carte(String path) {
        this.path = path;
    }

    /**
     * @return le chemin de l'image de la carte
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Une carte est identique a une autre si leurs image sont les mêmes
     *
     * @param o2 la carte avec laquelle on veut comparer la carte courrante
     * @return True si les images des cartes sont les mêmes
     */
    @Override
    public boolean equals(Object o2) {
        if (o2 == null) {
            return false;
        }
        if (o2.getClass() == this.getClass()) {
            Carte c2 = (Carte) o2;
            return this.path.equals(c2.getPath());
        }
        return false;
    }
}
