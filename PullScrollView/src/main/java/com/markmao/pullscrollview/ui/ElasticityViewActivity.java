package com.markmao.pullscrollview.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.markmao.pullscrollview.R;

/**
 * 上下回弹ScrollView Demo
 *
 * @author MarkMjw
 * @date 13-9-17.
 */
public class ElasticityViewActivity extends Activity {
    private TableLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_elasticity_layout);

        mMainLayout = (TableLayout) findViewById(R.id.table_layout);
        showTable();
    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams
                .MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        for (int i = 0; i < 40; i++) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("Test pull scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(10, 10, 10, 10);

            tableRow.addView(textView, layoutParams);
            mMainLayout.addView(tableRow);
        }
    }
}
