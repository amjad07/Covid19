package com.worklation.covid19;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.worklation.covid19.database.DatabaseHandler;
import com.worklation.covid19.model.Collector;

import java.util.Optional;

public class SymtomsActivity extends AppCompatActivity implements View.OnClickListener{

    RatingBar f_bar;
    RatingBar h_bar;
    RatingBar d_bar;
    RatingBar s_t_bar;
    RatingBar n_bar;
    RatingBar m_a_bar;
    RatingBar l_s_bar;
    RatingBar c_bar;
    RatingBar s_b_bar;
    RatingBar f_t_bar;


    String f_bar_num;
    String h_bar_num;
    String d_bar_num;
    String s_t_bar_num;
    String n_bar_num;
    String m_a_bar_num;
    String l_s_bar_num;
    String c_bar_num;
    String s_b_bar_num;
    String f_t_bar_num;

    DatabaseHandler db = new DatabaseHandler(this);

    AppCompatButton upload_symtoms;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symtoms);
        f_bar = findViewById(R.id.fever_ratingbar);
        h_bar = findViewById(R.id.headache_RatingBar);
        d_bar = findViewById(R.id.diarrhea_RatingBar);
        s_t_bar = findViewById(R.id.soar_throat_RatingBar);
        n_bar = findViewById(R.id.nausea_RatingBar);
        m_a_bar = findViewById(R.id.muscle_ache_RatingBar);
        l_s_bar = findViewById(R.id.loss_smell_RatingBar);
        c_bar = findViewById(R.id.cough_RatingBar);
        s_b_bar = findViewById(R.id.breath_RatingBar);
        f_t_bar = findViewById(R.id.tired_RatingBar);
        upload_symtoms = findViewById(R.id.upload_symtoms);
        upload_symtoms.setOnClickListener(this::onClick);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void submit(){

        f_bar_num = f_bar.getRating()+"";
        h_bar_num = h_bar.getRating()+"";
        d_bar_num = d_bar.getRating()+"";
        s_t_bar_num =s_t_bar.getRating()+"";
        n_bar_num = n_bar.getRating()+"";
        m_a_bar_num = m_a_bar.getRating()+"";
        l_s_bar_num = l_s_bar.getRating()+"";
        c_bar_num = c_bar.getRating()+"";
        s_b_bar_num = s_b_bar.getRating()+"";
        f_t_bar_num = f_t_bar.getRating()+"";

        f_bar_num = Optional.ofNullable(f_bar_num).orElse("0");
        h_bar_num = Optional.ofNullable(h_bar_num).orElse("0");
        d_bar_num = Optional.ofNullable(d_bar_num).orElse("0");
        s_t_bar_num = Optional.ofNullable(s_t_bar_num).orElse("0");
        n_bar_num = Optional.ofNullable(n_bar_num).orElse("0");
        m_a_bar_num = Optional.ofNullable(m_a_bar_num).orElse("0");
        l_s_bar_num = Optional.ofNullable(l_s_bar_num).orElse("0");
        c_bar_num = Optional.ofNullable(c_bar_num).orElse("0");
        s_b_bar_num = Optional.ofNullable(s_b_bar_num).orElse("0");
        f_t_bar_num = Optional.ofNullable(f_t_bar_num).orElse("0");

        System.out.println("vale " +f_t_bar_num);
        db.updateSymptoms(new Collector(f_bar_num,n_bar_num,h_bar_num,
                d_bar_num,s_t_bar_num,m_a_bar_num,l_s_bar_num,c_bar_num,
                s_b_bar_num,f_t_bar_num));

        Toast.makeText(this,"Symptoms Data updated",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.upload_symtoms:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    submit();
                }
                break;


            //.... etc
        }
    }
}