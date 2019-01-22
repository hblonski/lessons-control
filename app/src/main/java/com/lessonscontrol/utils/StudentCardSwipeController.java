package com.lessonscontrol.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.MotionEvent;
import android.view.View;

import com.lessonscontrol.activities.R;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

enum ButtonsState {
    GONE,
    LEFT_VISIBLE
}

public class StudentCardSwipeController extends Callback {

    private boolean swipeBack = false;

    private ButtonsState buttonShowedState = ButtonsState.GONE;

    private RecyclerView.ViewHolder currentItemViewHolder = null;

    //private SwipeControllerActions buttonsActions;

    private static final float buttonWidth = 200;
//
//    public StudentCardSwipeController(SwipeControllerActions buttonsActions) {
//        this.buttonsActions = buttonsActions;
//    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                super.onChildDraw(c, recyclerView, viewHolder, buttonWidth, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        if (buttonShowedState == ButtonsState.GONE) {
            dX = Math.min(dX, buttonWidth);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack) {
                    if (dX > buttonWidth) {
                        buttonShowedState = ButtonsState.LEFT_VISIBLE;
                    }

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    StudentCardSwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

//                    if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
//                        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
//                            buttonsActions.onLeftClicked(viewHolder.getAdapterPosition());
//                        }
//                    }
                    buttonShowedState = ButtonsState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButton(Canvas c, RecyclerView.ViewHolder viewHolder, Resources resources) {
        float corners = 16;
        float offSet = 10;
        View itemView = viewHolder.itemView;
        Paint buttonPaint = new Paint();
        RectF buttonRectangle = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidth + offSet, itemView.getBottom());
        buttonPaint.setColor(Color.RED);
        c.drawRoundRect(buttonRectangle, corners, corners, buttonPaint);
        Paint iconPaint = new Paint();
        iconPaint.setColor(Color.WHITE);
        Bitmap buttonIcon = FormatUtil.convertVectorDrawableToBitMap(VectorDrawableCompat.create(resources, R.drawable.ic_delete_white_32dp,null));
        c.drawBitmap(buttonIcon, (buttonRectangle.width() - buttonIcon.getWidth() - offSet - corners), buttonRectangle.height()/2 - corners, iconPaint);
    }

    public void onDraw(Canvas c, Resources resources) {
        if (currentItemViewHolder != null) {
            drawButton(c, currentItemViewHolder, resources);
        }
    }
}
