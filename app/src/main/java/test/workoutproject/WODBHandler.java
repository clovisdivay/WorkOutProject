package test.workoutproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    protected Context _context;

    public static final int DATABASE_VERSION    = 1;
    public static final String DATABASE_NAME    = "workouts.db";

    // Tableau de l'historique des séances
    public static final String TABLE_WORKOUTS          = "workouts";
    public static final String COLUMN_WO_ID            = "_id";
    public static final String COLUMN_WO_DATE          = "date";
    public static final String COLUMN_WO_SETNB         = "setNb";
    public static final String COLUMN_WO_EXERCISENB    = "exerciseNb";
    public static final String COLUMN_WO_NSETS         = "nSets";
    public static final String COLUMN_WO_SETRESTTIME   = "setRest";
    public static final String COLUMN_WO_EXERCISEID    = "exID";
    public static final String COLUMN_WO_NREPS         = "nReps";
    public static final String COLUMN_WO_WEIGHTKG      = "weightKg";
    public static final String COLUMN_WO_WEIGHTRM      = "weightRM";
    public static final String COLUMN_WO_EXRESTTIME    = "exRest";

    private final String StrCreateWOTable = "CREATE TABLE " +
            TABLE_WORKOUTS + "(" +
            COLUMN_WO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_WO_DATE + " DATE NOT NULL," +
            COLUMN_WO_SETNB + " SMALLINT UNSIGNED NOT NULL," +
            COLUMN_WO_EXERCISENB + " SMALLINT UNSIGNED NOT NULL," +
            COLUMN_WO_NSETS + " SMALLINT UNSIGNED NOT NULL," +
            COLUMN_WO_SETRESTTIME + " SMALLINT UNSIGNED NOT NULL," +
            COLUMN_WO_EXERCISEID + " SMALLINT NOT NULL," +
            COLUMN_WO_NREPS + " SMALLINT UNSIGNED," +
            COLUMN_WO_WEIGHTKG + " FLOAT," +
            COLUMN_WO_WEIGHTRM + " FLOAT," +
            COLUMN_WO_EXRESTTIME + " SMALLINT NOT NULL DEFAULT 0" +
            "FOREIGN KEY (" + COLUMN_WO_EXERCISEID + ") REFERENCES "
            + TABLE_GROUP + "("  + COLUMN_EX_ID + ")"+
            ");";

    // Tableau des différents groupes musculaires
    public static final String TABLE_GROUP                = "muscles";
    public static final String COLUMN_GROUP_ID            = "_id";
    public static final String COLUMN_GROUP_NAME          = "name";
    private final String StrCreateGroupTable = "CREATE TABLE " +
            TABLE_MUSCLES + "(" +
            COLUMN_MUSCLES_ID +      " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_MUSCLES_NAME +    " VARCHAR(100) NOT NULL );";

    // Tableau des différents muscles
    public static final String TABLE_MUSCLES                = "muscles";
    public static final String COLUMN_MUSCLES_ID            = "_id";
    public static final String COLUMN_MUSCLES_NAME          = "name";
    public static final String COLUMN_MUSCLES_GROUPID       = "groupID";
    private final String StrCreateMusclesTable = "CREATE TABLE " +
                    TABLE_MUSCLES + "(" +
                    COLUMN_MUSCLES_ID +      " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_MUSCLES_NAME +    " VARCHAR(100) NOT NULL," +
                    COLUMN_MUSCLES_GROUPID + " SMALLINT UNSIGNED NOT NULL," +
                    "FOREIGN KEY (" + COLUMN_MUSCLES_GROUPID + ") REFERENCES "
                        + TABLE_GROUP + "("  + COLUMN_GROUP_ID + ")"+
                    ");";

    // Tableau des différents exercices
    public static final String TABLE_EXERCISES          = "exercises";
    public static final String COLUMN_EX_ID             = "_id";
    public static final String COLUMN_EX_NAME           = "name";
    public static final String COLUMN_EX_DESCRIPTION    = "description";
    public static final String COLUMN_EX_RM             = "RM";
    private final String StrCreateExercisesTable = "CREATE TABLE " +
            TABLE_EXERCISES + "(" +
            COLUMN_EX_ID +          " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_EX_NAME +        " VARCHAR(100) NOT NULL," +
            COLUMN_EX_NAME +        " TEXT," +
            COLUMN_EX_RM +          " FLOAT NOT NULL " +
            ");";

    // Tableau des différents mucles par exercices
    public static final String TABLE_EXMUSCLE             = "exercisesMuscles";
    public static final String COLUMN_EXMUSCLE_ID         = "_id";
    public static final String COLUMN_EXMUSCLE_EXID       = "exID";
    public static final String COLUMN_EXMUSCLE_MUSCLEID   = "muscleID";
    private final String StrCreateExMuscleTable = "CREATE TABLE " +
            TABLE_EXERCISES + "(" +
            COLUMN_EXMUSCLE_ID +          " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_EXMUSCLE_EXID +        " INTEGER NOT NULL," +
            COLUMN_EXMUSCLE_MUSCLEID +    " INTEGER NOT NULL " +
            "FOREIGN KEY (" + COLUMN_EXMUSCLE_EXID + ") REFERENCES "
            + TABLE_EXERCISES + "("  + COLUMN_EX_ID + ")"+
            "FOREIGN KEY (" + COLUMN_EXMUSCLE_MUSCLEID + ") REFERENCES "
            + TABLE_MUSCLES + "("  + COLUMN_MUSCLES_ID + ")"+
            ");";


    public WODBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /** Create Tables in this specific order: Workout takes elements
         * from the Exercises table which in turn takes elements from
         * the Muscles table.
         */
        sqLiteDatabase.execSQL(StrCreateGroupTable);
        sqLiteDatabase.execSQL(StrCreateMusclesTable);
        sqLiteDatabase.execSQL(StrCreateExercisesTable);
        sqLiteDatabase.execSQL(StrCreateExMuscleTable);
        sqLiteDatabase.execSQL(StrCreateWOTable);
        FillMuscleGroupTable();
        readMusclesDBFile();
        readExercisesDBFile();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1>i) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSCLES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EXMUSCLE);
            onCreate(sqLiteDatabase);
        }
    }

    private int getID(String name, String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT _id FROM " + tableName + "WHERE name=" + name;
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        int ID = c.getInt(c.getColumnIndex("_id"));
        c.close();
        return ID;
    }


    // Add a new row to database
    public void addWorkOut(WorkOut WO){
        SQLiteDatabase db = this.getWritableDatabase();
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
                values.put(COLUMN_WO_DATE,date);
                values.put(COLUMN_WO_SETNB,i_set+1);
                values.put(COLUMN_WO_NSETS,NSets);
                values.put(COLUMN_WO_SETRESTTIME,Sets.get(i_set).get_RestTimeForSet());
                values.put(COLUMN_WO_EXERCISEID,Sets.get(i_set).get_Exercise(i_ex).get_id());
                values.put(COLUMN_WO_EXERCISENB,i_ex+1);
                values.put(COLUMN_WO_NREPS,NReps.get(i_ex));
                values.put(COLUMN_WO_WEIGHTKG,Weightkg.get(i_ex));
                values.put(COLUMN_WO_WEIGHTRM,WeightRM.get(i_ex));
                values.put(COLUMN_WO_EXRESTTIME,RestTimeForExercise.get(i_ex));
                db.insert(TABLE_WORKOUTS, null, values);
            }
        }
    db.close();
    }

    public void addMuscle(Muscle m){
        SQLiteDatabase db = this.getWritableDatabase();
        String Name = m.get_name();
        int groupID = m.get_groupID();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MUSCLES_NAME,Name);
        values.put(COLUMN_MUSCLES_GROUPID,groupID);
        db.insert(TABLE_MUSCLES, null, values);
        db.close();
    }

    public void addExercise(Exercise Ex){
        SQLiteDatabase db = this.getWritableDatabase();
        String Name = Ex.get_name();
        String Desc = Ex.get_description();
        Muscle[] muscles = Ex.get_muscles();
        double RM = Ex.get_RM();

        ContentValues values_ex = new ContentValues();
        values_ex.put(COLUMN_EX_NAME,Name);
        values_ex.put(COLUMN_EX_DESCRIPTION,Desc);
        values_ex.put(COLUMN_EX_RM,RM);
        db.insert(TABLE_EXERCISES, null, values_ex);

        ContentValues values_ME = new ContentValues();
        int Ex_ID = getID(Name,TABLE_EXERCISES);
        for (Muscle m : muscles) {
            int Muscle_ID = getID(m.get_name(), TABLE_MUSCLES);
            values_ME.put(COLUMN_EXMUSCLE_EXID, Ex_ID);
            values_ME.put(COLUMN_EXMUSCLE_MUSCLEID, Muscle_ID);
            db.insert(TABLE_EXMUSCLE,null,values_ME);
            values_ME.clear();
        }
        db.close();
    }

    private void FillMuscleGroupTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO" + TABLE_GROUP + " VALUES ('"+ _context.getString(R.string.bras) + "')");
        db.execSQL("INSERT INTO" + TABLE_GROUP + " VALUES ('"+ _context.getString(R.string.epaules) + "')");
        db.execSQL("INSERT INTO" + TABLE_GROUP + " VALUES ('"+ _context.getString(R.string.pecs) + "')");
        db.execSQL("INSERT INTO" + TABLE_GROUP + " VALUES ('"+ _context.getString(R.string.dos) + "')");
        db.execSQL("INSERT INTO" + TABLE_GROUP + " VALUES ('"+ _context.getString(R.string.abdos) + "')");
        db.execSQL("INSERT INTO" + TABLE_GROUP + " VALUES ('"+ _context.getString(R.string.fessiers) + "')");
        db.execSQL("INSERT INTO" + TABLE_GROUP + " VALUES ('"+ _context.getString(R.string.jambes) + "')");
        db.close();
    }

    private void readMusclesDBFile(){
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
                    Log.i("DBHandler","Filling muscle list: "+muscles.get(i).get_name());
                }
            }
            else{
                Log.e("DBHandler","Error: No muscle was found in file.");
            }
        }
        catch(Exception e){
            Log.e("DBHandler","Error while loading " + TABLE_MUSCLES +" from file.");
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

    private void readExercisesDBFile(){
        ArrayList<Exercise> exercises = new ArrayList<>();
        String line;
        String[] split;
        InputStream inputStream = null;
        InputStreamReader isreader = null;
        BufferedReader buffer = null;
        try {
            inputStream= _context.getResources().openRawResource(R.raw.exercises_db);
            isreader = new InputStreamReader(inputStream);
            buffer = new BufferedReader(isreader);
            while (true) {
                line = buffer.readLine();
                if (line != null) {
                    split = line.split(",");
                    //exercises.add(new Exercise(split[1], split[0]));
                }
                else {
                    break;
                }
            }
        }
        catch(Exception e){
            Log.e("DBHandler","Error while loading " + TABLE_EXERCISES +" from file.");
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
        String dbString = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_WORKOUTS + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            dbString += c.getString(c.getColumnIndex(COLUMN_WO_DATE));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_WO_EXERCISEID));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_WO_NSETS));
            dbString += ' ';
            dbString += c.getString(c.getColumnIndex(COLUMN_WO_NREPS));
            dbString += '\n';
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }
}
