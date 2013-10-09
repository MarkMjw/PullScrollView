package com.markmao.pullscrollview;

import android.app.Application;
import android.content.Context;

/**
 * @author MarkMjw
 * @date 13-9-17.
 */
public class CustomApplication extends Application {
    public static Context gContext;

    @Override
    public void onCreate() {
        super.onCreate();

        gContext = this;
    }
}
