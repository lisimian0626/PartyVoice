package pl.sugl.common.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class PreGridLayoutManager extends GridLayoutManager {
    private int mChildPerLines;
    private int mMaxLine = -1;
    private int[] mMeasuredDimension = new int[2];

    public PreGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        this.mChildPerLines = spanCount;
    }

    public PreGridLayoutManager(Context context, int spanCount, int maxLine) {
        super(context, spanCount);
        this.mChildPerLines = spanCount;
        this.mMaxLine = maxLine;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);
        int height = 0;
        int lineIndex = 0;
        for (int i = 0; i < getItemCount(); ) {
            measureScrapChild(recycler, i,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    mMeasuredDimension);
            height = height + mMeasuredDimension[1];
            i = i + mChildPerLines;
            height += handleMaxLineHiehgt(lineIndex++, mMeasuredDimension[1]);
        }

        // If child view is more than screen size, there is no need to make it wrap content. We can use original onMeasure() so we can scroll view.
        if (height > heightSize || mMaxLine != -1) {
            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = mMaxLine == -1 ? heightSize : 0;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
                default:
            }

            setMeasuredDimension(widthSize, height);
        } else {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }
    }

    private int handleMaxLineHiehgt(int lineIndex, int lineHeight) {
        if (mMaxLine != -1 && lineIndex < mMaxLine) {
            return 0;
        }

        return -lineHeight;
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                   int heightSpec, int[] measuredDimension) {

        int viewCount = getChildCount();
        if (position <= viewCount - 1) {
            View view = recycler.getViewForPosition(position);

            // For adding Item Decor Insets to view
            super.measureChildWithMargins(view, 0, 0);

            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                    getPaddingLeft() + getPaddingRight() + getDecoratedLeft(view) + getDecoratedRight(view), p.width);
            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                    getPaddingTop() + getPaddingBottom() + getPaddingBottom() + getDecoratedBottom(view), p.height);
            view.measure(childWidthSpec, childHeightSpec);

            // Get decorated measurements
            measuredDimension[0] = getDecoratedMeasuredWidth(view) + p.leftMargin + p.rightMargin;
            measuredDimension[1] = getDecoratedMeasuredHeight(view) + p.bottomMargin + p.topMargin;
            recycler.recycleView(view);
        }
    }
}