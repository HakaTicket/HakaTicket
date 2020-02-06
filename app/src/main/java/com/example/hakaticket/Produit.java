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

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public void setMarqueProduit(String marqueProduit) {
        this.marqueProduit = marqueProduit;
    }

    public void setQuantiteProduit(String quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public void setPrixUnitaireProduit(String prixUnitaireProduit) {
        this.prixUnitaireProduit = prixUnitaireProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public String getMarqueProduit() {
        return marqueProduit;
    }

    public String getQuantiteProduit() {
        return quantiteProduit;
    }

    public String getPrixUnitaireProduit() {
        return prixUnitaireProduit;
    }
}
