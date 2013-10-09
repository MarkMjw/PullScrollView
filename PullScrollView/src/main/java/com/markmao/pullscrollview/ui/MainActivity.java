package com.markmao.pullscrollview.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.markmao.pullscrollview.ui.widget.PullScrollView;
import com.markmao.pullscrollview.R;

/**
 * Demo
 *
 * @author MarkMjw
 * @date 13-9-17.
 */
public class MainActivity extends Activity implements PullScrollView.OnTurnListener,
        View.OnClickListener {
    private PullScrollView mScrollView;
    private ImageView mHeadImg;

    private TextView mAddAttention;
    private TableLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_layout);

        initView();

        showTable();
    }

    protected void initView() {
        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);

        mAddAttention = (TextView) findViewById(R.id.attention_user);
        mMainLayout = (TableLayout) findViewById(R.id.table_layout);

        mAddAttention.setOnClickListener(this);
        mScrollView.setOnTurnListener(this);

        mScrollView.init(mHeadImg);
    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams
                .MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("Test pull scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(10, 10, 10, 10);

            tableRow.addView(textView, layoutParams);
            mMainLayout.addView(tableRow);
        }
    }

    @Override
    public void onTurn() {
        Log.e("mjw", "onTurn.............");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attention_user:
                Intent intent = new Intent(this, ElasticityViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
