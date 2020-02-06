package bks.colibriCorporation.hakaticket;

import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

public class Ticket {

    private static ArrayList<Ticket> listTicket = new ArrayList<Ticket>();

    private String numTransac;
    private String vendeur;
    private String adresse;
    private String dateAchat;
    private String heureAchat;
    private String prixHTC;
    private String prixTTC;
    private String reduction;
    private String moyenPayement;
    private String infoCarte;
    private String renduMonnaie;
    private ArrayList<Produit> listProduit;

    public Ticket(String numTransac, String vendeur, String adresse, String dateAchat, String heureAchat, String prixHTC, String prixTTC, String reduction, String moyenPayement, String infoCarte, String renduMonnaie, ArrayList<Produit> listProduit){
        this.numTransac = numTransac;
        this.vendeur = vendeur;
        this.adresse = adresse;
        this.dateAchat = dateAchat;
        this.heureAchat = heureAchat;
        this.prixHTC = prixHTC;
        this.prixTTC = prixTTC;
        this.reduction = reduction;
        this.moyenPayement = moyenPayement;
        this.infoCarte = infoCarte;
        this.renduMonnaie = renduMonnaie;
        this.listProduit = listProduit;
    }

    public static String createTicket(String resultScan) {
        listTicket = new ArrayList<Ticket>();
        if(resultScan.length() != 0){
            String [] infoTickets = resultScan.split(";");
            String numTransac = infoTickets[0];
            String vendeur = infoTickets[1];
            String adresse = infoTickets[2];
            String dateAchat = infoTickets[3];
            String heureAchat = infoTickets[4];
            String prixHTC = infoTickets[5];
            String prixTTC = infoTickets[6];
            String reduction = infoTickets[7];
            String moyenPayement = infoTickets[8];
            String infoCarte = infoTickets[9];
            String renduMonnaie = infoTickets[10];
            ArrayList<Produit> maListProduit = new ArrayList<Produit>();
            int i = 11;
            while (i < infoTickets.length){
                String [] splitProduit = infoTickets[i].split("#");
                Log.d("debug", infoTickets[i]);
                String leNomProduit = splitProduit[0];
                String laMarqueProduit = splitProduit[1];
                String laQuantiteeProduit = splitProduit[2];
                String lePrixUnitaire = splitProduit[3];
                Produit newProduit = new Produit(leNomProduit, laMarqueProduit, laQuantiteeProduit, lePrixUnitaire);
                maListProduit.add(newProduit);

                ++i;
            }
            Ticket TicketNouveau = new Ticket(numTransac, vendeur, adresse, dateAchat, heureAchat, prixHTC, prixTTC, reduction, moyenPayement, infoCarte, renduMonnaie, maListProduit);
            ajouterTicket(TicketNouveau);
            return "Le ticket à été ajouté";
        }
        else {
            return "Le ticket n'a pas été ajouté";
        }
    }

    private static void ajouterTicket(Ticket monTicket){
        listTicket.add(monTicket);
    }



    public static ArrayList<Ticket> getListTicket(){
        return listTicket;
    }

    public String getNumTransac() {
        return numTransac;
    }

    public String getVendeur() {
        return vendeur;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public String getHeureAchat() {
        return heureAchat;
    }

    public String getPrixHTC() {
        return prixHTC;
    }

    public String getPrixTTC() {
        return prixTTC;
    }

    public String getReduction() {
        return reduction;
    }

    public String getMoyenPayement() {
        return moyenPayement;
    }

    public String getInfoCarte() {
        return infoCarte;
    }

    public String getRenduMonnaie() {
        return renduMonnaie;
    }

    public ArrayList<Produit> getListProduit() {
        return listProduit;
    }
}