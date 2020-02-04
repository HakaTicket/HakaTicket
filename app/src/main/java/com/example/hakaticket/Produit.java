package com.example.hakaticket;

public class Produit {
    private String nomProduit;
    private String marqueProduit;
    private String quantiteProduit;
    private String prixUnitaireProduit;

    public Produit(String nomProduit, String marqueProduit, String quantiteProduit, String prixUnitaireProduit){
        this.nomProduit = nomProduit;
        this.marqueProduit = marqueProduit;
        this.quantiteProduit = quantiteProduit;
        this.prixUnitaireProduit = prixUnitaireProduit;
    }
}
