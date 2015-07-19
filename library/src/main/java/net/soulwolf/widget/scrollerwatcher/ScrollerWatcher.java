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
package net.soulwolf.widget.scrollerwatcher;

import android.view.ViewGroup;

import net.soulwolf.widget.scrollerwatcher.callback.ScrollerWatcherCallBack;


/**
 * Decorator for ScrollerWatcher
 *
 * @author Toaker [Toaker](ToakerQin@gmail.com)
 *         [Toaker](http://www.toaker.com)
 * @Time Create by 2015/5/14 10:23
 */
public interface ScrollerWatcher {

    /**
     * Set scroll view callback!
     *
     * @param callBack {@link ScrollerWatcherCallBack} instance!
     */
    public void setScrollerWatcherCallBack(ScrollerWatcherCallBack callBack);

    /**
     * According to offset the scroll view!
     *
     * @param offsetX X-axis offset view!
     * @param offsetY Y-axis offset view!
     */
    public void scrollBy(int offsetX,int offsetY);

    /**
     * Scroll the view to a specified coordinate location!
     *
     * @param x Scroll to the X-axis location!
     * @param y Scroll to the Y-axis location!
     */
    public void scrollTo(int x,int y);

    /**
     * Get current view scroll X-axis Coordinate!
     *
     * @return Return current X-axis Coordinate!
     */
    public int getCurrentScrollY();

    /**
     * Get current view scroll Y-axis Coordinate!
     *
     * @return Return current Y-axis Coordinate!
     */
    public int getCurrentScrollX();

}
