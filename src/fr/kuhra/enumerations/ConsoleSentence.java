package fr.kuhra.enumerations;

/**
 * Created by Dimitri on 07/10/2015.
 */
public enum ConsoleSentence {
    WELCOME ("Bienvenue sur Battleship+ !"),
    ERROR ("Oops, une erreur est survenue !"),
    ENTER_NAME ("Entrez votre nom."),
    CHOOSE_MAP_SIZE ("Choisissez la taille de votre carte."),
    CHOOSE_X ("Choisissez un point d'abscisse (x)"),
    CHOOSE_Y ("Choisissez un point d'ordonn�e (y)"),
    CHOOSE_POSITION ("Choisissez une position"),
    BAD_SHIP_LOCATION ("Votre navire est mal positionn�."),
    SHOOT_OF ("Tir de"),
    ASK_SHIP_MOVEMENT ("Voulez-vous d�placer un navire ?"),
    WHAT_SHIP_TO_MOVE ("Quel navire souhaitez-vous d�placer ?"),
    WHICH_DIRECTION ("Dans quelle direction ?"),
    HOW_MANY_TILES ("De combien de cases ?"),
    INCORRECT_MOVEMENT ("D�placement incorrect."),
    INCORRECT_POSITION ("Position incorrecte."),
    MISSED ("Rat�."),
    TOUCHED ("Touch�.");

    private String name = "";

    ConsoleSentence(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
