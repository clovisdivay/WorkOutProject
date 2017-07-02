package test.workoutproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Clovis on 26/06/2017.
 *
 * !!!!!! OBSOLETE CLASS !!!!!!
 *
 * Classe de création et remplissage de la base de données "muscle"
 */

public class MuscleDBHandler extends SQLiteOpenHelper {
    protected Context _context;

    public static final int DATABASE_VERSION    = 1;
    public static final String DATABASE_NAME    = "muscles.db";
    public static final String TABLE_NAME   = "muscles";

    public static final String COLUMN_ID            = "_id";
    public static final String COLUMN_NAME          = "_name";
    public static final String COLUMN_GROUPID       = "_groupID";

    public MuscleDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this._context = context;
        readDBFile();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID +      " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME +    " VARCHAR(100) NOT NULL," +
                COLUMN_GROUPID + " SMALLINT UNSIGNED NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addMuscle(Muscle m){
        SQLiteDatabase db = this.getWritableDatabase();
        String Name = m.get_name();
        int groupID = m.get_groupID();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,Name);
        values.put(COLUMN_GROUPID,groupID);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void readDBFile(){
        ArrayList<Muscle> muscles = new ArrayList<>();
        String line;
        String[] split;
        InputStream inputStream = null;
        InputStreamReader isreader = null;
        BufferedReader buffer = null;
        try {
            inputStream= _context.getResources().openRawResource(R.raw.muscle_db);
            isreader = new InputStreamReader(inputStream);
            buffer = new BufferedReader(isreader);
            while (true) {
                line = buffer.readLine();
                if (line != null) {
                    split = line.split(",");
                    muscles.add(new Muscle(split[1], split[0]));
                }
                else {
                    break;
                }
            }

            if (!muscles.isEmpty()) {
                for (int i = 0; i < muscles.size(); i++) {
                    addMuscle(muscles.get(i));
                    Log.i("MuscleDBHandler","Filling muscle list: "+muscles.get(i).get_name());
                }
            }
            else{
                Log.e("MuscleDBHandler","Error: No muscle was found in file.");
            }
        }
        catch(Exception e){
            Log.e("MuscleDBHandler","Error while loading " + TABLE_NAME +" from file.");
            e.printStackTrace();
        }
        finally {
            try {
                buffer.close();
                isreader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Print DataBase to String
    public String databaseToString(){
        Log.d("MuscleDB","In the databaseToString method");
        String dbString = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            dbString += c.getString(c.getColumnIndex(COLUMN_ID));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_NAME));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_GROUPID));
            dbString += '\n';
            Log.d("MuscleDBHandler",c.getString(c.getColumnIndex(COLUMN_NAME)));
            c.moveToNext();
        }
        c.close();
        db.close();

        return dbString;
    }
}
