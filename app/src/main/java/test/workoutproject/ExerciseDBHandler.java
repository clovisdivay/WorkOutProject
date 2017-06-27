package test.workoutproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Clovis on 26/06/2017.
 * Classe de création et remplissage de la base de données "exercice"
 */

public class ExerciseDBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION    = 1;
    public static final String DATABASE_NAME    = "exercises.db";
    public static final String TABLE_NAME   = "exercises";

    public static final String COLUMN_ID            = "_id";

    public ExerciseDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

}
