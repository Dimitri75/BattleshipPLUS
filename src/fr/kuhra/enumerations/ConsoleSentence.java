package fr.kuhra.enumerations;

/**
 * Created by Dimitri on 07/10/2015.
 */
public enum ConsoleSentence {
    WELCOME ("Bienvenue sur Battleship+ !"),
    MAP_OF ("Carte de : "),
    MAP_AFTER_MOVEMENT ("Carte après déplacement."),
    CANT_SHOOT_HERE ("Vous ne pouvez pas tirer ici."),
    ERROR ("Oops, une erreur est survenue !"),
    ENTER_NAME ("Entrez votre nom : "),
    CHOOSE_MAP_SIZE ("Choisissez la taille de votre carte."),
    CHOOSE_X ("Choisissez un point d'abscisse (x) : "),
    CHOOSE_Y ("Choisissez un point d'ordonnée (y) : "),
    CHOOSE_POSITION ("Choisissez une position"),
    BAD_SHIP_LOCATION ("Votre navire est mal positionné."),
    X_SHOOT_OF ("Tir en x de : "),
    Y_SHOOT_OF ("Tir en y de : "),
    ASK_SHIP_MOVEMENT ("Voulez-vous déplacer un navire ?"),
    WHAT_SHIP_TO_MOVE ("Quel navire souhaitez-vous déplacer ?"),
    WHICH_DIRECTION ("Dans quelle direction ?"),
    HOW_MANY_TILES ("De combien de cases ?"),
    INCORRECT_MOVEMENT ("Déplacement incorrect."),
    INCORRECT_POSITION ("Position incorrecte."),
    WIN ("Vous avez gagné !"),
    LOOSE ("Vous avez perdu !"),
    MISSED ("Raté."),
    TOUCHED ("Touché."),
    WHAT_YOU_DISPOSE ("Vous disposez de cinq bateaux (taille, portée), entrez leurs coordonnées (x, y) ainsi que leur position (verticale/horizontale)"),
    HERE_IS_YOUR_MAP ("Parfait, voici votre terrain actuel.");

    private String name = "";

    ConsoleSentence(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
