package com.desire.android.deskalauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by desire on 12/12/2018.
 */

public class HomeActivity extends Activity {
    private ImageButton btn;
    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.home_activity_layout);
        btn = (ImageButton)findViewById(R.id.list_app_btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View View)
            {
                Intent intent = new Intent(getApplicationContext(), DeskaLauncherActivity.class);
                startActivity(intent);
            }
        });
    }
}
