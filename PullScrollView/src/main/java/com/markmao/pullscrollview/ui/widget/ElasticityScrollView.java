package com.markmao.pullscrollview.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import com.markmao.pullscrollview.util.PixelUtil;

/**
 * 上下回弹效果的ScrollView
 *
 * @author MarkMjw
 * @date 13-9-12.
 */
public class ElasticityScrollView extends ScrollView {
    protected Context mContext;

    private View mView;
    private float mTouchY;
    private int mScrollY = 0;
    private boolean mHandleStop = false;
    private int mEachStep = 0;

    /**
     * 最大滑动距离
     */
    private static final int MAX_SCROLL_HEIGHT = PixelUtil.dp2px(200);
    /**
     * 阻尼系数,越小阻力就越大
     */
    private static final float SCROLL_RATIO = 0.4f;

    private Handler mResetPositionHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (mScrollY != 0 && mHandleStop) {
                mScrollY -= mEachStep;
                if ((mEachStep < 0 && mScrollY > 0) || (mEachStep > 0 && mScrollY < 0)) {
                    mScrollY = 0;
                }
                mView.scrollTo(0, mScrollY);
                this.sendEmptyMessageDelayed(0, 20);
            }
        }
    };

    public ElasticityScrollView(Context context) {
        super(context);

        init(context);
    }

    public ElasticityScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public ElasticityScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    private void init(Context context) {
        this.mContext = context;

        // 去掉滚动到边界的效果
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            this.mView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            mTouchY = arg0.getY();
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView == null) {
            return super.onTouchEvent(ev);
        } else {
            commonOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void commonOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (mView.getScrollY() != 0) {
                    mHandleStop = true;
                    animation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                int deltaY = (int) (mTouchY - nowY);
                mTouchY = nowY;
                if (isNeedMove()) {
                    int offset = mView.getScrollY();
                    if (offset < MAX_SCROLL_HEIGHT && offset > -MAX_SCROLL_HEIGHT) {
                        mView.scrollBy(0, (int) (deltaY * SCROLL_RATIO));
                        mHandleStop = false;
                    }
                }
                break;

            default:
                break;
        }
    }

    private boolean isNeedMove() {
        int viewHeight = mView.getMeasuredHeight();
        int scrollHeight = getHeight();
        int offset = viewHeight - scrollHeight;
        int scrollY = getScrollY();

        return scrollY == 0 || scrollY == offset;
    }

    private void animation() {
        mScrollY = mView.getScrollY();
        mEachStep = mScrollY / 10;
        mResetPositionHandler.sendEmptyMessage(0);
    }
}