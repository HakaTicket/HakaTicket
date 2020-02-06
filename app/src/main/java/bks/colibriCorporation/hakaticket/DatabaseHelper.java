package bks.colibriCorporation.hakaticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tickets.db";
    public static final String TABLE_NAME_TICKET = "ticket";
    public static final String COL_0 = "idTicket";
    public static final String COL_1 = "numTransac";
    public static final String COL_2 = "enseigne";
    public static final String COL_3 = "adresse";
    public static final String COL_4 = "dateAchat";
    public static final String COL_5 = "heureAchat";
    public static final String COL_6 = "prixHTC";
    public static final String COL_7 = "prixTTC";
    public static final String COL_8 = "reduction";
    public static final String COL_9 = "moyenPayement";
    public static final String COL_10 = "infoCarte";
    public static final String COL_11 = "renduMonnaie";

    public static final String TABLE_NAME_PRODUIT = "produit";
    public static final String COL_PRODUIT_0 = "idProduit";
    public static final String COL_PRODUIT_1 = "idTicket";
    public static final String COL_PRODUIT_2 = "nomProduit";
    public static final String COL_PRODUIT_3 = "marqueProduit";
    public static final String COL_PRODUIT_4 = "quantiteProduit";
    public static final String COL_PRODUIT_5 = "prixUnitaireProduit";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_TICKET + "("+COL_0+"INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_1+ "TEXT, " +COL_2+ "TEXT, " +COL_3+ "TEXT, " +COL_4+ "TEXT, " +COL_5+ "TEXT, " +COL_6+ "TEXT, " +COL_7+ "TEXT, " +COL_8+ "TEXT, " +COL_9+ "TEXT, " +COL_10+ "TEXT, " +COL_11+ "TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_PRODUIT + "("+COL_PRODUIT_0+"INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_PRODUIT_1+ "INTEGER, " +COL_PRODUIT_2+ "TEXT, " +COL_PRODUIT_3+ "TEXT, " +COL_PRODUIT_4+ "TEXT, " +COL_PRODUIT_5+ "INTEGER, FOREIGN KEY("+COL_PRODUIT_1+ ")REFERENCES ticket("+COL_1+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TICKET);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUIT);
        onCreate(sqLiteDatabase);
    }

    public boolean insertDataIntoTicket(String numeroTransaction, String enseigne, String adresse, String dateAchat, String heureAchat, String prixHTC, String prixTTC, String reduction,  String moyenPayment, String infocarte, String renduMonnaie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, numeroTransaction);
        contentValues.put(COL_2, enseigne);
        contentValues.put(COL_3, adresse);
        contentValues.put(COL_4, dateAchat);
        contentValues.put(COL_5, heureAchat);
        contentValues.put(COL_6, prixHTC);
        contentValues.put(COL_7, prixTTC);
        contentValues.put(COL_8, reduction);
        contentValues.put(COL_9, moyenPayment);
        contentValues.put(COL_10, infocarte);
        contentValues.put(COL_11, renduMonnaie);
        long result = db.insert(TABLE_NAME_TICKET,null,contentValues);
        return result != -1;
    }

    public boolean insertDataInto(int idTicket, String nomProduit, String marqueProduit, String quantiteProduit, String prixUnitaireProduit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRODUIT_1, idTicket);
        contentValues.put(COL_PRODUIT_2, nomProduit);
        contentValues.put(COL_PRODUIT_3, marqueProduit);
        contentValues.put(COL_PRODUIT_4, quantiteProduit);
        contentValues.put(COL_PRODUIT_5, prixUnitaireProduit);
        long result = db.insert(TABLE_NAME_PRODUIT,null,contentValues);
        return result != -1;
    }

    

}
