package fr.efrei.maudarsene.lasertagtracker.services.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "matches.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "matches";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "id TEXT PRIMARY KEY, " +
                    "userId TEXT NOT NULL, " +
                    "playerName TEXT NOT NULL," +
                    "rank INTEGER NOT NULL," +
                    "score INTEGER NOT NULL," +
                    "precision REAL NOT NULL," +
                    "teamScore INTEGER NOT NULL," +
                    "date TEXT NOT NULL," +

                    "chestGiven INTEGER NOT NULL," +
                    "backGiven INTEGER NOT NULL," +
                    "shouldersGiven INTEGER NOT NULL," +
                    "gunGiven INTEGER NOT NULL," +

                    "chestReceived INTEGER NOT NULL," +
                    "backReceived INTEGER NOT NULL," +
                    "shouldersReceived INTEGER NOT NULL," +
                    "gunReceived INTEGER NOT NULL," +

                    "latitude REAL NOT NULL,"  +
                    "longitude REAL NOT NULL,"  +
                    "address TEXT NOT NULL"  +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("Example", "Upgrading database, this will drop tables and recreate.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
