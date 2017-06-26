package test.workoutproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView List;
    WODBHandler wodbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List = (TextView)findViewById(R.id.WorkOutList);
        wodbHandler = new WODBHandler(this, null, null, 1);
        //this.deleteDatabase("workouts.db");
        Exercise chinups = new Exercise("Chin Ups","PULL",new Muscle("Lats",0));
        Exercise curl = new Exercise("Curl","PULL",new Muscle("Biceps",1));
        Exercise marteau = new Exercise("Curl Marteau","PULL",new Muscle("Biceps",1));
        chinups.set_RM(15.);
        curl.set_RM(50.);
        marteau.set_RM(20.);

        Set chinupSet = new Set(chinups,5,10,0.,90);
        Set curlSet = new Set(curl,5,10,30.,90);
        curlSet.Add_Exercise(marteau,5,12.);

        WorkOut WOTest = new WorkOut();
        WOTest.AddSet(chinupSet);
        WOTest.AddSet(curlSet);
        Log.d("1","about to mess with db");
        //List.setText(wodbHandler.getDatabaseName());
        wodbHandler.addWorkOut(WOTest);
        List.setText(wodbHandler.databaseToString());

    }
}
