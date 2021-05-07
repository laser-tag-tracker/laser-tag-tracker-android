package fr.efrei.maudarsene.lasertagtracker.services.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;

public class MatchLocalRepositoryImpl implements MatchLocalRepository {

    private DatabaseHelper databaseHelper;

    public MatchLocalRepositoryImpl(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public void insertMatch(Match match) {

        try(SQLiteDatabase database = this.databaseHelper.getWritableDatabase()){ // Autoclosable resource : unmanged resource is closed by the try
            long count = DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE_NAME);

            if(count == 5){
                Cursor cursor = database.rawQuery(
                        String.format("SELECT * FROM %s WHERE userId = '%s' ORDER BY date",
                                DatabaseHelper.TABLE_NAME, match.getUserId()),
                        null
                );
                cursor.moveToFirst();
                String id = cursor.getString(cursor.getColumnIndex("id"));

                database.delete(DatabaseHelper.TABLE_NAME, "id = ?", new String[]{id});
            }

            ContentValues values = new ContentValues();
            values.put("id", match.getId());
            values.put("userId", match.getUserId());
            values.put("playerName", match.getPlayerName());
            values.put("rank", match.getRank());
            values.put("score", match.getScore());
            values.put("precision", match.getPrecision());
            values.put("teamScore", match.getTeamScore());
            values.put("date", match.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            values.put("chestGiven", match.getChestGiven());
            values.put("backGiven", match.getBackGiven());
            values.put("shouldersGiven", match.getShouldersGiven());
            values.put("gunGiven", match.getGunGiven());

            values.put("chestReceived", match.getChestReceived());
            values.put("backReceived", match.getBackReceived());
            values.put("shouldersReceived", match.getShouldersReceived());
            values.put("gunReceived", match.getGunReceived());

            database.insert(DatabaseHelper.TABLE_NAME, null, values);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Error inserting in database");
        }
    }


    @Override
    public List<Match> getMatchesForUser(String userId) {
        SQLiteDatabase database = this.databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(String.format("SELECT * FROM %s WHERE userId = '%s' ORDER BY date", DatabaseHelper.TABLE_NAME, userId), null);
        List<Match> matches = new LinkedList<Match>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Match match = new Match(
                    cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("playerName")),
                    cursor.getInt(cursor.getColumnIndex("rank")),
                    cursor.getInt(cursor.getColumnIndex("score")),
                    cursor.getDouble(cursor.getColumnIndex("precision")),
                    cursor.getInt(cursor.getColumnIndex("teamScore")),
                    LocalDate.parse(cursor.getString(cursor.getColumnIndex("date"))),

                    cursor.getInt(cursor.getColumnIndex("chestGiven")),
                    cursor.getInt(cursor.getColumnIndex("backGiven")),
                    cursor.getInt(cursor.getColumnIndex("shouldersGiven")),
                    cursor.getInt(cursor.getColumnIndex("gunGiven")),

                    cursor.getInt(cursor.getColumnIndex("chestReceived")),
                    cursor.getInt(cursor.getColumnIndex("backReceived")),
                    cursor.getInt(cursor.getColumnIndex("shouldersReceived")),
                    cursor.getInt(cursor.getColumnIndex("gunReceived")),

                    null, 0, 0
            );
            cursor.move(1);
            matches.add(match);
        }
        database.close();
        return matches;
    }
}
