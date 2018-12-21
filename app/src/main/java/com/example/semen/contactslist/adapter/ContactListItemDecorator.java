package com.example.semen.contactslist.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ContactListItemDecorator extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private static final int OFFSET = 30;
    private static final int HALF_OFFSET = OFFSET / 2;

    public ContactListItemDecorator() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(OFFSET, OFFSET, OFFSET, OFFSET);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View view = parent.getChildAt(i);

            //Draw rectangle
            c.drawRect(view.getLeft() - HALF_OFFSET,
                    view.getTop() - HALF_OFFSET,
                    view.getRight() + HALF_OFFSET,
                    view.getBottom() + HALF_OFFSET,
                    paint);
        }
    }
}
