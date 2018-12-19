package com.example.semen.contactslist.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ContactListItemDecorator extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private final int offset;
    private final int halfOffset;

    public ContactListItemDecorator(int offset) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        this.offset = offset;
        this.halfOffset = offset / 2;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(offset, offset, offset, offset);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View view = parent.getChildAt(i);

            //Draw rectangle
            c.drawRect(view.getLeft() - halfOffset,
                    view.getTop() - halfOffset,
                    view.getRight() + halfOffset,
                    view.getBottom() + halfOffset,
                    paint);
        }
    }
}
