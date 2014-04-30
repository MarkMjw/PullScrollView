package com.markmao.pullscrollview.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.markmao.pullscrollview.R;

/**
 * Demo
 *
 * @author markmjw
 * @date 2014-04-30
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        findViewById(R.id.pulldown_scrollview_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PulldownViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.stretch_scrollview_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StretchViewActivity.class);
                startActivity(intent);
            }
        });
    }

}
