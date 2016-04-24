package sinjvf.testfromsinjvf.AuxiliaryClasses;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Класс для отрисовки отделительной черты между элементами RecyclerView
 * Автор Alexfu  ( https://gist.github.com/alexfu/0f464fc3742f134ccd1e), внесены с небольшие изменения
 * В отличие оригинала позволяет рисовать только горизонтальную черту (вертикальное разделение элементов)
 * и не учитывает поля родительского View
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };


    private Drawable mDivider;
    private int mOrientation;

    public DividerItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mOrientation = LinearLayoutManager.VERTICAL;;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent,RecyclerView.State state) {
        drawVertical(c, parent);
    }

    public void drawVertical(Canvas c, RecyclerView parent) {

        /*
        //original method.
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        */

        //In our example  dividing line doesn't have paddings
        final int left = 0;
        final int right = parent.getWidth();


        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());

    }
}