package com.markmao.pullscrollview.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.markmao.pullscrollview.R;
import com.markmao.pullscrollview.ui.widget.PullScrollView;

/**
 * Pull down ScrollView demo.
 *
 * @author markmjw
 * @date 2014-04-30
 */
public class PulldownViewActivity extends Activity implements PullScrollView.OnTurnListener {
    private PullScrollView mScrollView;
    private ImageView mHeadImg;

    private TableLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pull_down);

        initView();

        showTable();
    }

    protected void initView() {
        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);

        mMainLayout = (TableLayout) findViewById(R.id.table_layout);

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
            textView.setText("Test pull down scroll view " + i);
            textView.setTextSize(22);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            mMainLayout.addView(tableRow);
        }
    }

    @Override
    public void onTurn() {

    }

}
