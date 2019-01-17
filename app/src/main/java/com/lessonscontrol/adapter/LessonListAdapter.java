package com.lessonscontrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lessonscontrol.activities.R;
import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.utils.FormatUtil;

import java.util.List;

/**
 * Adapter for a {@link Lesson} list.
 *
 * @author hblonski
 */
public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.LessonViewHolder> {

    class LessonViewHolder extends RecyclerView.ViewHolder {
        private final TextView typeView;
        private final TextView daysView;
        private final TextView priceView;
        private final TextView nextClassView;
        private final TextView nextPaymentView;

        private View lessonItemView;

        private LessonViewHolder(View itemView) {
            super(itemView);
            lessonItemView = itemView;
            typeView = itemView.findViewById(R.id.view_type);
            daysView = itemView.findViewById(R.id.view_days);
            nextClassView = itemView.findViewById(R.id.view_next_class);
            nextPaymentView = itemView.findViewById(R.id.view_next_payment);
            priceView = itemView.findViewById(R.id.view_price);
            //When the card is created, it is expanded, so we call this method to collapse it.
            expandOrCollapseCard();

            Activity activity = (Activity) layoutInflater.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandOrCollapseCard();
                }
            });
        }

        /**
         * Switches the visibility of some card components, expanding it if it is collapsed, or
         * collapsing if it is expanded.
         */
        private void expandOrCollapseCard() {
            switchVisibility(daysView);
            switchVisibility(nextPaymentView);
            switchVisibility(priceView);
            View lastSeparator = lessonItemView.findViewById(R.id.view_next_class_separator);
            switchVisibility(lastSeparator);
        }

        /**
         * Switches the field's visibility between visible and invisible. It will do the same for
         * the field label/separator, as seen on the lesson_details_card layout.
         * @param view the view.
         */
        private void switchVisibility(View view) {
            //Field label
            TextView label = (TextView) getViewByIdName(lessonItemView.getResources().getResourceName(view.getId()).concat("_label"));
            //Field separator
            View separator = getViewByIdName(lessonItemView.getResources().getResourceName(view.getId()).concat("_separator"));

            if (view.getVisibility() == View.GONE) {
                view.setVisibility(View.VISIBLE);

                if (label != null) {
                    label.setVisibility(View.VISIBLE);
                }
                if (separator != null) {
                    separator.setVisibility(View.VISIBLE);
                }
            } else {
                view.setVisibility(View.GONE);
                if (label != null) {
                    label.setVisibility(View.GONE);
                }
                if (separator != null) {
                    separator.setVisibility(View.GONE);
                }
            }
        }

        private View getViewByIdName(String idName) {
            return lessonItemView.findViewById(lessonItemView.getResources().getIdentifier(idName, "id", layoutInflater.getContext().getPackageName()));
        }
    }

    private final LayoutInflater layoutInflater;

    private List<Lesson> lessons;

    public LessonListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.lesson_details_card, parent, false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder lessonViewHolder, int position) {
        if (this.lessons != null) {
            final Lesson current = this.lessons.get(position);
            lessonViewHolder.typeView.setText(current.getType());
            lessonViewHolder.daysView.setText(current.getDays());
            lessonViewHolder.nextClassView.setText(FormatUtil.convertDateToString(current.getNextDate()));
            lessonViewHolder.nextPaymentView.setText(FormatUtil.convertDateToString(current.getNextPayment()));
            lessonViewHolder.priceView.setText(FormatUtil.convertDoubleToMoney(current.getPrice()));
        } else {
            // Covers the case of data not being ready yet.
            lessonViewHolder.typeView.setText("Data not ready.");
        }
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    public List<Lesson> getLessons() {
        return this.lessons;
    }

    // getItemCount() is called many times, and when it is first called,
    // lessons has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (this.lessons != null)
            return this.lessons.size();
        else return 0;
    }
}
