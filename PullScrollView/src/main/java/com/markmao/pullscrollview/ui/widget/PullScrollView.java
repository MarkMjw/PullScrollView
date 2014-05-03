package com.markmao.pullscrollview.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

/**
 * 自定义ScrollView
 *
 * @author markmjw
 * @date 2013-09-13
 */
public class PullScrollView extends ScrollView {
    /** 阻尼系数,越小阻力就越大. */
    private static final float SCROLL_RATIO = 0.35f;

    /** 滑动至翻转的距离. */
    private static final int TURN_DISTANCE = 50;

    /** 头部图片. */
    private ImageView mHeadImage;

    /** 头部图片显示高度. */
    private int mHeadImageH;

    /** 孩子View. */
    private View mChildView;

    /** ScrollView的孩子View矩形. */
    private Rect mRect = new Rect();

    /** 首次点击的Y坐标. */
    private float mTouchDownY;

    /** 是否关闭ScrollView的滑动. */
    private boolean mEnableTouch = false;

    /** 是否开始移动. */
    private boolean isMoving = false;

    /** 头部图片初始顶部和底部. */
    private int mInitTop, mInitBottom;

    /** 头部图片拖动时顶部和底部. */
    private int mCurrentTop, mCurrentBottom;

    /** 状态变化时的监听器. */
    private OnTurnListener mOnTurnListener;

    private enum State {
        /**顶部*/
        UP,
        /**底部*/
        DOWN,
        /**正常*/
        NORMAL
    }

    /** 状态. */
    private State state = State.NORMAL;

    public PullScrollView(Context context) {
        super(context);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 初始化
     *
     * @param imageView 头部图片
     */
    public void init(ImageView imageView) {
        mHeadImage = imageView;
        mHeadImageH = 94;
    }

    /**
     * 设置状态改变时的监听器
     *
     * @param turnListener
     */
    public void setOnTurnListener(OnTurnListener turnListener) {
        mOnTurnListener = turnListener;
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mChildView = getChildAt(0);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // 当滚动到顶部时，将状态设置为正常，避免先向上拖动再向下拖动到顶端后首次触摸不响应的问题
        if (getScrollY() == 0) {
            state = State.NORMAL;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mChildView != null) {
            doTouchEvent(ev);
        }

        // 禁止控件本身的滑动.
        if (mEnableTouch) {
            return true;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    /**
     * 触摸事件处理
     *
     * @param event
     */
    private void doTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownY = event.getY();
                mCurrentTop = mInitTop = mHeadImage.getTop();
                mCurrentBottom = mInitBottom = mHeadImage.getBottom();
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaY = event.getY() - mTouchDownY;

                doActionMove(deltaY);
                break;

            case MotionEvent.ACTION_UP:
                // 回滚动画
                if (isNeedAnimation()) {
                    rollBackAnimation();
                }

                if (getScrollY() == 0) {
                    state = State.NORMAL;
                }

                isMoving = false;
                mEnableTouch = false;
                break;

            default:
                break;
        }
    }

    /**
     * 执行移动动画
     *
     * @param deltaY
     */
    private void doActionMove(float deltaY) {
        // 对于首次Touch操作要判断方位：UP OR DOWN
        if (deltaY < 0 && state == State.NORMAL) {
            state = State.UP;
        } else if (deltaY > 0 && state == State.NORMAL) {
            state = State.DOWN;
        }

        if (state == State.UP) {
            deltaY = deltaY < 0 ? deltaY : 0;

            isMoving = false;
            mEnableTouch = false;

        } else if (state == State.DOWN) {
            if (getScrollY() <= deltaY) {
                mEnableTouch = true;
                isMoving = true;
            }
            deltaY = deltaY < 0 ? 0 : deltaY;
        }

        if (isMoving) {
            // 初始化头部矩形
            if (mRect.isEmpty()) {
                // 保存正常的布局位置
                mRect.set(mChildView.getLeft(), mChildView.getTop(), mChildView.getRight(),
                        mChildView.getBottom());
            }

            // 移动背景图(手势移动的距离*阻尼系数*0.5)
            float bgMoveH = deltaY * 0.5f * SCROLL_RATIO;
            mCurrentTop = (int) (mInitTop + bgMoveH);
            mCurrentBottom = (int) (mInitBottom + bgMoveH);
            mHeadImage.layout(mHeadImage.getLeft(), mCurrentTop, mHeadImage.getRight(),
                    mCurrentBottom);

            // 移动布局(手势移动的距离*阻尼系数)
            float childMoveH = deltaY * SCROLL_RATIO;

            // 修正移动的距离，避免超过图片的底边缘
            int top = mCurrentBottom - mHeadImageH;
            if (mRect.top + childMoveH > top) {
                childMoveH -= mRect.top + childMoveH - top;
            }

            mChildView.layout(mRect.left, (int) (mRect.top + childMoveH),
                    mRect.right, (int) (mRect.bottom + childMoveH));
        }
    }

    private void rollBackAnimation() {
        TranslateAnimation tranAnim = new TranslateAnimation(0, 0,
                Math.abs(mInitTop - mCurrentTop), 0);
        tranAnim.setDuration(200);
        mHeadImage.startAnimation(tranAnim);

        mHeadImage.layout(mHeadImage.getLeft(), mInitTop, mHeadImage.getRight(), mInitBottom);

        // 开启移动动画
        TranslateAnimation innerAnim = new TranslateAnimation(0, 0, mChildView.getTop(), mRect.top);
        innerAnim.setDuration(200);
        mChildView.startAnimation(innerAnim);
        mChildView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);

        mRect.setEmpty();

        // 回调监听器
        if (mCurrentTop > mInitTop + TURN_DISTANCE && mOnTurnListener != null){
            mOnTurnListener.onTurn();
        }
    }

    /**
     * 是否需要开启动画
     */
    private boolean isNeedAnimation() {
        return !mRect.isEmpty() && isMoving;
    }

    /**
     * 执行翻转
     *
     * @author markmjw
     */
    public interface OnTurnListener {
        /**
         * 翻转回调方法
         */
        public void onTurn();
    }
}
