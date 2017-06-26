package test.workoutproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Pépère on 11/06/2017.
 * Handler de la base de données "historique de séances".
 * Crée et remplis l'historique
 * Permet la communication avec cette DB
 */

public class WODBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION    = 1;
    public static final String DATABASE_NAME    = "workouts.db";

    public static final String TABLE_WORKOUTS   = "workouts";

    public static final String COLUMN_ID            = "_id";
    public static final String COLUMN_DATE          = "date";
    public static final String COLUMN_SETNB         = "setNb";
    public static final String COLUMN_EXERCISENB    = "exerciseNb";
    public static final String COLUMN_NSETS         = "nSets";
    public static final String COLUMN_SETRESTTIME   = "setRest";
    public static final String COLUMN_EXERCISE      = "exID";
    public static final String COLUMN_NREPS         = "nReps";
    public static final String COLUMN_WEIGHTKG      = "weightKg";
    public static final String COLUMN_WEIGHTRM      = "weightRM";
    public static final String COLUMN_EXRESTTIME    = "exRest";

    public WODBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_WORKOUTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DATE + " DATE NOT NULL," +
                COLUMN_SETNB + " SMALLINT UNSIGNED NOT NULL," +
                COLUMN_EXERCISENB + " SMALLINT UNSIGNED NOT NULL," +
                COLUMN_NSETS + " SMALLINT UNSIGNED NOT NULL," +
                COLUMN_SETRESTTIME + " SMALLINT UNSIGNED NOT NULL," +
                COLUMN_EXERCISE + " SMALLINT NOT NULL," +
                COLUMN_NREPS + " SMALLINT UNSIGNED," +
                COLUMN_WEIGHTKG + " FLOAT," +
                COLUMN_WEIGHTRM + " FLOAT," +
                COLUMN_EXRESTTIME + " SMALLINT NOT NULL DEFAULT 0" +
                ");";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(sqLiteDatabase);
    }

    // Add a new row to database
    public void addWorkOut(WorkOut WO){
        SQLiteDatabase db = getWritableDatabase();
        Date WOdate = WO.get_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = dateFormat.format(WOdate);
        int NSets = WO.get_NSets();
        ArrayList<Set> Sets = WO.get_Sets();
        for (int i_set=0; i_set<NSets; i_set++){
            int NExercises = Sets.get(i_set).get_NExercises();
            ArrayList<Integer> NReps = Sets.get(i_set).get_NReps();
            ArrayList<Double> Weightkg = Sets.get(i_set).get_Weightskg();
            ArrayList<Double> WeightRM = Sets.get(i_set).get_WeightsRM();
            ArrayList<Integer> WorkTime = Sets.get(i_set).get_WorkTime();
            ArrayList<Integer> RestTimeForExercise = Sets.get(i_set).get_RestTimeForExercises();
            for (int i_ex=0; i_ex<NExercises; i_ex++){
                ContentValues values = new ContentValues();
                values.put(COLUMN_DATE,date);
                values.put(COLUMN_SETNB,i_set+1);
                values.put(COLUMN_NSETS,NSets);
                values.put(COLUMN_SETRESTTIME,Sets.get(i_set).get_RestTimeForSet());
                values.put(COLUMN_EXERCISE,Sets.get(i_set).get_Exercise(i_ex).get_id());
                values.put(COLUMN_EXERCISENB,i_ex+1);
                values.put(COLUMN_NREPS,NReps.get(i_ex));
                values.put(COLUMN_WEIGHTKG,Weightkg.get(i_ex));
                values.put(COLUMN_WEIGHTRM,WeightRM.get(i_ex));
                values.put(COLUMN_EXRESTTIME,RestTimeForExercise.get(i_ex));
                db.insert(TABLE_WORKOUTS, null, values);
            }
        }
    db.close();
    }

    // Print DataBase to String
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_WORKOUTS + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            dbString += c.getString(c.getColumnIndex(COLUMN_DATE));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_EXERCISE));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_NSETS));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_NREPS));
            dbString += '\n';
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }
}
