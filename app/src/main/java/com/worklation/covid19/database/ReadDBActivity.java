package com.worklation.covid19.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.worklation.covid19.R;
import com.worklation.covid19.model.Collector;

import java.util.List;

public class ReadDBActivity extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_dbactivity);

        textView = findViewById(R.id.textView);
        // Reading all contacts
        Log.e("Reading: ", "Reading all symptoms..");
        Collector cn = db.getAllSymptomss();
        String log = " Id: " + cn.getID() + " ,Resp Rate: " + cn.getRespRate() + " ,Hear rate: " +
                cn.getHeartRate() + " ,NAUSEA: " + cn.getNausea() +" ,Headache: " + cn.getHeadache()
                + " DIARRHEA " + cn.getDiarrhea() + " ,Shortness of breath: " + cn.getShortnessOfBreath()
                +" ,Fever: " + cn.getFever() + " ,Muscle Ache: " + cn.getMuscleAche() + " ,Loss of taste: " + cn.getLossOfSmell()
                +" ,cough: " + cn.getCough() + " ,Shortness of breath: " + cn.getShortnessOfBreath()
                + " ,Feeling tired: " + cn.getFeelingTired();

if (log.contains("null")){
    textView.setText("Please enter all data");
}else {
    textView.setText(log);

}
                ;
        // Writing Contacts to log
        Log.e("Name: ", log);
    }
}