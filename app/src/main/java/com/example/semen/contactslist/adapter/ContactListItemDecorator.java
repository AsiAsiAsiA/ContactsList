package com.example.semen.contactslist.adapter;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.semen.contactslist.R;
import com.example.semen.contactslist.app.App;

public class ContactListItemDecorator extends RecyclerView.ItemDecoration {
    private final Paint paint;
    private final int offset;
    private final int halfOffset;

    public ContactListItemDecorator() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Resources resources = App.getContext().getResources();
        paint.setStrokeWidth(resources.getDimensionPixelSize(R.dimen.stroke_width));
        offset = resources.getDimensionPixelSize(R.dimen.offset);
        halfOffset = offset / 2;
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
