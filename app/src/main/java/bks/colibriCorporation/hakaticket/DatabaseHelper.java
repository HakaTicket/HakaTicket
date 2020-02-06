package bks.colibriCorporation.hakaticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_TICKET + "("+COL_0+"INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_1+ "INTEGER, " +COL_2+ "TEXT, " +COL_3+ "TEXT, " +COL_4+ "DATE, " +COL_5+ "DATE, " +COL_6+ "FLOAT, " +COL_7+ "FLOAT, " +COL_8+ "FLOAT, " +COL_9+ "TEXT, " +COL_10+ "TEXT, " +COL_11+ "FLOAT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_PRODUIT + "("+COL_PRODUIT_0+"INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_PRODUIT_1+ "INTEGER, " +COL_PRODUIT_2+ "TEXT, " +COL_PRODUIT_3+ "TEXT, " +COL_PRODUIT_4+ "INTEGER, " +COL_PRODUIT_5+ "FLOAT, FOREIGN KEY("+COL_PRODUIT_1+ ")REFERENCES ticket("+COL_1+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TICKET);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUIT);
        onCreate(sqLiteDatabase);
    }

    /*public boolean insertDataIntoTicket(int numeroTransaction, String name, String surname, String mark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(,);
    }

    public boolean insertDataInto(String name, String surname, String mark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,);
    }*/
}
