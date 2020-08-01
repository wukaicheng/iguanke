package cn.kaicity.app.iguangke.view

/*

Copyright (C) 2015 takahirom
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.webkit.WebView
import androidx.core.view.MotionEventCompat
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat


class NestedWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.webViewStyle
) : WebView(context, attrs, defStyleAttr),
    NestedScrollingChild {
    private val mScrollOffset = IntArray(2)
    private val mScrollConsumed = IntArray(2)
    private val mChildHelper = NestedScrollingChildHelper(rootView)
    private var mLastY: Int = 0

    private var mNestedYOffset = 0

    init {
        isNestedScrollingEnabled = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var result = false
        val trackedEvent = obtain(event)
        val action = MotionEventCompat.getActionMasked(event)
        if (action == ACTION_DOWN) {
            mNestedYOffset = 0
        }
        val y = event.y.toInt()
        event.offsetLocation(0f, mNestedYOffset.toFloat())
        when (action) {
            ACTION_DOWN -> {
                mLastY = y
                var nestedScrollAxis = ViewCompat.SCROLL_AXIS_VERTICAL
                nestedScrollAxis = nestedScrollAxis or ViewCompat.SCROLL_AXIS_HORIZONTAL //按位或运算

//                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                startNestedScroll(nestedScrollAxis)
                result = super.onTouchEvent(event)
            }
            ACTION_MOVE -> {
                var deltaY: Int = mLastY - y
                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed[1]
                    trackedEvent.offsetLocation(0f, mScrollOffset[1].toFloat())
                    mNestedYOffset += mScrollOffset[1]
                }
                val oldY = scrollY
                mLastY = y - mScrollOffset[1]
                val newScrollY = Math.max(0, oldY + deltaY)
                deltaY -= newScrollY - oldY
                if (dispatchNestedScroll(0, newScrollY - deltaY, 0, deltaY, mScrollOffset)) {
                    mLastY -= mScrollOffset[1]
                    trackedEvent.offsetLocation(0f, mScrollOffset[1].toFloat())
                    mNestedYOffset += mScrollOffset[1]
                }
                if (mScrollConsumed[1] == 0 && mScrollOffset[1] == 0) {
                    trackedEvent.recycle()
                    result = super.onTouchEvent(trackedEvent)
                }

            }
            ACTION_POINTER_DOWN, ACTION_UP, ACTION_CANCEL -> {
                stopNestedScroll()
                result = super.onTouchEvent(event)
            }
        }
        return result
    }


    // Nested Scroll implements
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun stopNestedScroll() {
        mChildHelper.stopNestedScroll()
    }

    override fun isNestedScrollingEnabled() = mChildHelper.isNestedScrollingEnabled
    override fun startNestedScroll(axes: Int) = mChildHelper.startNestedScroll(axes)
    override fun hasNestedScrollingParent() = mChildHelper.hasNestedScrollingParent()
    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ) = mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean) =
        mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float) =
        mChildHelper.dispatchNestedPreFling(velocityX, velocityY)

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ) = mChildHelper.dispatchNestedScroll(
        dxConsumed,
        dyConsumed,
        dxUnconsumed,
        dyUnconsumed,
        offsetInWindow
    )

}