package com.lessonscontrol.adapter;

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

        private LessonViewHolder(View itemView) {
            super(itemView);
            typeView = itemView.findViewById(R.id.view_type);
            daysView = itemView.findViewById(R.id.view_days);
            nextClassView = itemView.findViewById(R.id.view_next_class);
            nextPaymentView = itemView.findViewById(R.id.view_next_payment);
            priceView = itemView.findViewById(R.id.view_price);
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
    public void onBindViewHolder(LessonViewHolder studentViewHolder, int position) {
        if (this.lessons != null) {
            final Lesson current = this.lessons.get(position);
            studentViewHolder.typeView.setText(current.getType());
            studentViewHolder.daysView.setText(current.getDays());
            studentViewHolder.nextClassView.setText(FormatUtil.convertDateToString(current.getNextDate()));
            studentViewHolder.nextPaymentView.setText(FormatUtil.convertDateToString(current.getNextPayment()));
            studentViewHolder.priceView.setText(FormatUtil.convertDoubleToMoney(current.getPrice()));

            //TODO expandable card
        } else {
            // Covers the case of data not being ready yet.
            studentViewHolder.typeView.setText("Data not ready.");
        }
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        notifyDataSetChanged();
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
