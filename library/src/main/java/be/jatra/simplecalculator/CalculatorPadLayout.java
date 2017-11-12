package be.jatra.simplecalculator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * A layout that places children in an evenly distributed grid based on the specified
 * {@link android.R.attr#columnCount} and {@link android.R.attr#rowCount} attributes.
 */
public class CalculatorPadLayout extends ViewGroup {

    private int rowCount;
    private int columnCount;

    public CalculatorPadLayout(Context context) {
        this(context, null);
    }

    public CalculatorPadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalculatorPadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                new int[]{android.R.attr.rowCount, android.R.attr.columnCount}, defStyle, 0);
        rowCount = a.getInt(0, 1);
        columnCount = a.getInt(1, 1);

        a.recycle();
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        final boolean isRTL = ViewCompat.getLayoutDirection(this) == LAYOUT_DIRECTION_RTL;
        final int columnWidth =
                Math.round((float) (right - left - paddingLeft - paddingRight)) / columnCount;
        final int rowHeight =
                Math.round((float) (bottom - top - paddingTop - paddingBottom)) / rowCount;

        int rowIndex = 0, columnIndex = 0;
        for (int childIndex = 0; childIndex < getChildCount(); ++childIndex) {
            final View childView = getChildAt(childIndex);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            final MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            final int childTop = paddingTop + lp.topMargin + rowIndex * rowHeight;
            final int childBottom = childTop - lp.topMargin - lp.bottomMargin + rowHeight;
            final int childLeft = paddingLeft + lp.leftMargin +
                    (isRTL ? (columnCount - 1) - columnIndex : columnIndex) * columnWidth;
            final int childRight = childLeft - lp.leftMargin - lp.rightMargin + columnWidth;

            final int childWidth = childRight - childLeft;
            final int childHeight = childBottom - childTop;
            if (childWidth != childView.getMeasuredWidth() ||
                    childHeight != childView.getMeasuredHeight()) {
                childView.measure(
                        MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
            }
            childView.layout(childLeft, childTop, childRight, childBottom);

            rowIndex = (rowIndex + (columnIndex + 1) / columnCount) % rowCount;
            columnIndex = (columnIndex + 1) % columnCount;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
