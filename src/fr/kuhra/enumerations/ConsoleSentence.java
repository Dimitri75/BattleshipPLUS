package fr.kuhra.enumerations;

/**
 * Created by Dimitri on 07/10/2015.
 */
public enum ConsoleSentence {
    WELCOME ("Bienvenue sur Battleship+ !"),
    MAP_OF ("Carte de : "),
    MAP_AFTER_MOVEMENT ("Carte apr�s d�placement."),
    CANT_SHOOT_HERE ("Vous ne pouvez pas tirer ici."),
    ERROR ("Oops, une erreur est survenue !"),
    ENTER_NAME ("Entrez votre nom."),
    CHOOSE_MAP_SIZE ("Choisissez la taille de votre carte."),
    CHOOSE_X ("Choisissez un point d'abscisse (x) : "),
    CHOOSE_Y ("Choisissez un point d'ordonn�e (y) : "),
    CHOOSE_POSITION ("Choisissez une position"),
    BAD_SHIP_LOCATION ("Votre navire est mal positionn�."),
    X_SHOOT_OF ("Tir en x de : "),
    Y_SHOOT_OF ("Tir en y de : "),
    ASK_SHIP_MOVEMENT ("Voulez-vous d�placer un navire ?"),
    WHAT_SHIP_TO_MOVE ("Quel navire souhaitez-vous d�placer ?"),
    WHICH_DIRECTION ("Dans quelle direction ?"),
    HOW_MANY_TILES ("De combien de cases ?"),
    INCORRECT_MOVEMENT ("D�placement incorrect."),
    INCORRECT_POSITION ("Position incorrecte."),
    MISSED ("Rat�."),
    TOUCHED ("Touch�."),
    WHAT_YOU_DISPOSE ("Vous disposez de cinq bateaux (taille, port�e), entrez leurs coordonn�es (x, y) ainsi que leur position (verticale/horizontale)"),
    HERE_IS_YOUR_MAP ("Parfait, voici votre terrain actuel.");

    private String name = "";

    ConsoleSentence(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
