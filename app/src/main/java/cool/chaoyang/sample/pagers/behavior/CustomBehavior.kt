package cool.chaoyang.sample.pagers.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

/**
 * @author huangzhaoyang
 *
 */
class CustomBehavior<V : View> @JvmOverloads constructor(
    context: Context? = null,
    attributeSet: AttributeSet? = null
) : CoordinatorLayout.Behavior<V>(context, attributeSet) {

    companion object {
        const val TAG = "CustomBehavior"

        const val STATE_HIDDEN = 1
        const val STATE_DRAGGING = 2
        const val STATE_SETTLING = 3
        const val STATE_EXPANDED = 4
    }


    private var initialY: Int = 0
    private var maxExpandOffset: Int = 0
    private var activePointerId: Int = ViewDragHelper.INVALID_POINTER
    private val maximumVelocity: Float
    private var velocityTracker: VelocityTracker? = null

    init {
        val configuration = ViewConfiguration.get(context)
        maximumVelocity = configuration.scaledMaximumFlingVelocity.toFloat()
    }


    private val dragCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            Log.d(TAG, "tryCaptureView() called with: child = $child, pointerId = $pointerId")
            return activePointerId == pointerId
        }


        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            Log.d(
                TAG,
                "onViewCaptured() called with: capturedChild = $capturedChild, activePointerId = $activePointerId"
            )
            super.onViewCaptured(capturedChild, activePointerId)
        }


        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            Log.d(TAG, "onViewDragStateChanged() called with: state = $state")

        }

    }
    private var dragHelper: ViewDragHelper? = null


    private var state = STATE_EXPANDED


    var canDrag = true

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {

        if (dragHelper == null) {
            dragHelper = ViewDragHelper.create(parent, dragCallback)
        }


        parent.onLayoutChild(child, layoutDirection)


        //calculateMaxExpandedOffset
        val childWidth = child.width
        val parentWidth = parent.width

        maxExpandOffset = (parentWidth - childWidth).coerceAtLeast(0)

        if (state == STATE_EXPANDED) {
            ViewCompat.offsetLeftAndRight(child, getExpandOffset())
        } else if (state == STATE_HIDDEN) {
            ViewCompat.offsetLeftAndRight(child, childWidth)
        }

        return true
    }


    private fun getExpandOffset(): Int {
        return maxExpandOffset
    }


    override fun onAttachedToLayoutParams(params: CoordinatorLayout.LayoutParams) {
        super.onAttachedToLayoutParams(params)
        dragHelper = null
    }

    override fun onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams()
        dragHelper = null
    }


    private fun reset() {
        activePointerId = ViewDragHelper.INVALID_POINTER
        val vt = velocityTracker
        if (vt != null) {
            vt.recycle()
            velocityTracker = null
        }
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: V,
        event: MotionEvent
    ): Boolean {

        Log.d(
            TAG,
            "onInterceptTouchEvent() called with: parent = $parent, child = $child, ev = $event"
        )


//        if (!child.isShown || !draggable) {
//            ignoreEvents = true
//            return false
//        }
        val action: Int = event.actionMasked
        // Record the velocity
        // Record the velocity
        if (action == MotionEvent.ACTION_DOWN) {
            reset()
        }

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker?.addMovement(event)


        when (action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                touchingScrollingChild = false
                activePointerId = MotionEvent.INVALID_POINTER_ID
                // Reset the ignore flag
//                if (ignoreEvents) {
//                    ignoreEvents = false
//                    return false
//                }
                return false
            }
            MotionEvent.ACTION_DOWN -> {
                val initialX = event.x.toInt()
                initialY = event.y.toInt()
                // Only intercept nested scrolling events here if the view not being moved by the
                // ViewDragHelper.
                if (state != STATE_SETTLING) {
//                    val scroll: View? =
//                        if (nestedScrollingChildRef != null) nestedScrollingChildRef.get() else null
//                    if (scroll != null && parent.isPointInChildBounds(scroll, initialX, initialY)) {
                        activePointerId = event.getPointerId(event.actionIndex)
//                        touchingScrollingChild = true
//                    }
                }
//                ignoreEvents = (activePointerId == MotionEvent.INVALID_POINTER_ID
//                        && !parent.isPointInChildBounds(child, initialX, initialY))
            }
            else -> {
            }
        }

        val viewDragHelper = dragHelper ?: return false

        if (
            viewDragHelper.shouldInterceptTouchEvent(event)
        ) {
            return true
        }
        // We have to handle cases that the ViewDragHelper does not capture the bottom sheet because
        // it is not the top most view of its parent. This is not necessary when the touch event is
        // happening over the scrolling content as nested scrolling logic handles that case.
        // We have to handle cases that the ViewDragHelper does not capture the bottom sheet because
        // it is not the top most view of its parent. This is not necessary when the touch event is
        // happening over the scrolling content as nested scrolling logic handles that case.
//        val scroll: View? =
//            if (nestedScrollingChildRef != null) nestedScrollingChildRef.get() else null
        return (action == MotionEvent.ACTION_MOVE
                && state != BottomSheetBehavior.STATE_DRAGGING
                && viewDragHelper != null && Math.abs(initialY - event.getY()) > viewDragHelper.getTouchSlop())
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, ev: MotionEvent): Boolean {
        Log.d(TAG, "onTouchEvent: ${MotionEvent.actionToString(ev.action)}  | $ev | $child")

        val action: Int = ev.actionMasked
        if (state == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
            return true
        }

        val viewDragger = dragHelper ?: return false

        viewDragger.processTouchEvent(ev)

        // Record the velocity
        if (action == MotionEvent.ACTION_DOWN) {
            reset()
        }
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker?.addMovement(ev)

        // The ViewDragHelper tries to capture only the top-most View. We have to explicitly tell it
        // to capture the bottom sheet in case it is not captured and the touch slop is passed.
        if (action == MotionEvent.ACTION_MOVE) {
            if (Math.abs(initialY - ev.getY()) > viewDragger.touchSlop) {
                viewDragger.captureChildView(child, ev.getPointerId(ev.actionIndex))
            }
        }
        return true


    }

}