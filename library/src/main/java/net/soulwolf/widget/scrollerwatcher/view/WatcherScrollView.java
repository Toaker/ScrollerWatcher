/*******************************************************************************
 * Copyright 2015-2019 Toaker ScrollerWatcher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package net.soulwolf.widget.scrollerwatcher.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import net.soulwolf.widget.scrollerwatcher.ScrollerWatcher;
import net.soulwolf.widget.scrollerwatcher.callback.ScrollerWatcherCallBack;


/**
 * Decorator for ScrollerWatcher
 *
 * @author Toaker [Toaker](ToakerQin@gmail.com)
 *         [Toaker](http://www.toaker.com)
 * @Time Create by 2015/5/14 11:16
 */
public class WatcherScrollView extends ScrollView implements ScrollerWatcher {

    protected int mCurrentScrollX;

    protected int mCurrentScrollY;

    protected ScrollerWatcherCallBack mScrollerWatcherCallBack;

    protected ViewGroup mInterceptTouchEventViewGroup;

    private boolean mIntercepted;

    private MotionEvent mPrevMoveEvent;

    public WatcherScrollView(Context context) {
        super(context);
    }

    public WatcherScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WatcherScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WatcherScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setScrollerWatcherCallBack(ScrollerWatcherCallBack callBack) {
        this.mScrollerWatcherCallBack = callBack;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mScrollerWatcherCallBack != null){
            mCurrentScrollX = l;
            mCurrentScrollY = t;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollerWatcherCallBack != null) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIntercepted = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mPrevMoveEvent == null) {
                        mPrevMoveEvent = ev;
                    }
                    float diffY = ev.getY() - mPrevMoveEvent.getY();
                    mPrevMoveEvent = MotionEvent.obtainNoHistory(ev);
                    if (getCurrentScrollY() - diffY <= 0) {
                        if (mIntercepted) {
                            return false;
                        }
                        final ViewGroup parent;
                        if (mInterceptTouchEventViewGroup == null) {
                            parent = (ViewGroup) getParent();
                        } else {
                            parent = mInterceptTouchEventViewGroup;
                        }
                        float offsetX = 0;
                        float offsetY = 0;
                        for (View v = this; v != null && v != parent; v = (View) v.getParent()) {
                            offsetX += v.getLeft() - v.getScrollX();
                            offsetY += v.getTop() - v.getScrollY();
                        }
                        final MotionEvent event = MotionEvent.obtainNoHistory(ev);
                        event.offsetLocation(offsetX, offsetY);

                        if (parent.onInterceptTouchEvent(event)) {
                            mIntercepted = true;
                            event.setAction(MotionEvent.ACTION_DOWN);
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    parent.dispatchTouchEvent(event);
                                }
                            });
                            return false;
                        }
                        return super.onTouchEvent(ev);
                    }
                    break;
            }
            mScrollerWatcherCallBack.onTouchEvent(this,ev);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public int getCurrentScrollY() {
        return mCurrentScrollY;
    }

    @Override
    public int getCurrentScrollX() {
        return mCurrentScrollX;
    }

    @Override
    public void setInterceptTouchEvent(ViewGroup viewGroup) {
        this.mInterceptTouchEventViewGroup = viewGroup;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
