package com.example.patrondebloqueoJava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.patrondebloqueo.R;

import java.util.List;

import io.paperdb.Paper;

public class ActivityDos extends AppCompatActivity {


    String save_pattern_key = "pattern_code";
    PatternLockView mPatternLockView;
    String final_pattern = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        if(save_pattern != null && !save_pattern.equals("null"))
        {
            setContentView(R.layout.activity_dos);
            mPatternLockView = (PatternLockView)findViewById(R.id.patron);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
                    if(final_pattern.equals(save_pattern)){
                        Toast.makeText(ActivityDos.this, "Password Correct!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityDos.this, ActivityTres.class);
                        onBackPressed();
                        startActivity(intent);



                    }else{ Toast.makeText(ActivityDos.this, "Password Incorrecta!", Toast.LENGTH_SHORT).show();}


                }

                @Override
                public void onCleared() {

                }
            });
        }
    }
}
