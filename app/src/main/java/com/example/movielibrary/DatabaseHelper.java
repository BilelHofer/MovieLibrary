package com.example.movielibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class qui permet de sauvegarder l'id des films aimés
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "movies";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_MOVIE_ID = "movie_id";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MOVIE_ID + " INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Ajoute une film dans les favories
     * @param movieId
     */
    public void addMovie(int movieId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movieId);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Enleve un film des favories
     */
    public void removeMovie(int movieId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_MOVIE_ID + " = ?", new String[]{String.valueOf(movieId)});
        db.close();
    }

    /**
     * Retourne un curseur contenant tous les likes
     * @return Cursor contenant tous les scores
     */
    public Cursor getAllLike() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    /**
     * Retourne un boolean si le film est dans les favories
     */
    public boolean isMovieLiked(int movieId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ID + " = " + movieId;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount() > 0;
    }

    /**
     * Supprime la base de données et la recrée
     */
    public void resetDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Retourne le nombre de films dans les favories
     */
    public int getNumberOfMovies() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public boolean updateLike(int movieId) {
        boolean movieLiked = false;

        if (getNumberOfMovies() == 0) {
            addMovie(movieId);
        } else {

            Cursor cursor = getAllLike();
            while (cursor.moveToNext()) {

                if (movieId == cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_MOVIE_ID))) {
                    movieLiked = true;
                }

                if (movieLiked) {
                    removeMovie(movieId);
                } else {
                    addMovie(movieId);
                }
            }
            cursor.close();
        }
        return !movieLiked;
    }
}
