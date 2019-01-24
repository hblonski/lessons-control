package com.lessonscontrol.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.viewModel.LessonViewModel;
import com.lessonscontrol.utils.FormatUtil;
import com.lessonscontrol.utils.MoneyTextWatcher;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class EditLessonActivity extends AppCompatActivity {

    /**
     * Request code for this activity. This code will be returned when the activity exits.
     * @see android.support.v4.app.FragmentActivity#startActivityForResult
     */
    public static final int EDIT_LESSON_ACTIVITY_REQUEST_CODE = 3;

    public static final String STUDENT_ID_KEY = "STUDENT_ID";

    private LessonViewModel lessonViewModel;

    private Lesson lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);
        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);;

        EditText priceInput = findViewById(R.id.edit_price);
        priceInput.addTextChangedListener(new MoneyTextWatcher(priceInput));

        Object receivedLesson = this.getIntent().getExtras().get(Lesson.LESSON_EXTRA_KEY);
        if (receivedLesson != null) {
            lesson = (Lesson) receivedLesson;
            populateViewWithReceivedLessonInfo();
        }

        configureCalendarListener((MaterialCalendarView) findViewById(R.id.edit_next_class));
        configureCalendarListener((MaterialCalendarView) findViewById(R.id.edit_next_payment));

        FloatingActionButton doneFAB = findViewById(R.id.edit_lesson_form_done_fab);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                try {
                    createOrUpdateLessonFromActivityData();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } catch (InvalidParameterException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.required_fields_empty),
                            Toast.LENGTH_LONG).show();
                    Log.e(EditLessonActivity.this.getClass().toString(),
                            "Required argument missing when trying to create lesson.");
                }
            }
        });
    }

    private void configureCalendarListener(MaterialCalendarView materialCalendarView) {
        final CalendarDay today = CalendarDay.today();
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                if (calendarDay.isBefore(today)) {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.date_must_not_be_in_the_past,
                            Toast.LENGTH_LONG).show();
                    materialCalendarView.clearSelection();
                }
            }
        });
    }

    private void populateViewWithReceivedLessonInfo() {
        ((EditText) findViewById(R.id.edit_type)).setText(lesson.getType());

        String price = String.format("$%s", FormatUtil.convertDoubleToMoney(lesson.getPrice()));
        ((EditText) findViewById(R.id.edit_price)).setText(price);

        MaterialDayPicker materialDayPicker = findViewById(R.id.edit_days);
        materialDayPicker.setSelectedDays(FormatUtil.convertStringToMaterialDayPickerList(lesson.getDays()));

        Long nextClassDate = lesson.getNextDate();
        if (nextClassDate != null) {
            ((MaterialCalendarView) findViewById(R.id.edit_next_class)).setSelectedDate(new Date(nextClassDate));
        }

        Long nextPaymentDate = lesson.getNextPayment();
        if (nextPaymentDate != null) {
            ((MaterialCalendarView) findViewById(R.id.edit_next_payment)).setSelectedDate(new Date());
        }
    }

    private void createOrUpdateLessonFromActivityData() throws InvalidParameterException {
        String type = ((EditText) findViewById(R.id.edit_type)).getText().toString();
        MaterialDayPicker materialDayPicker = findViewById(R.id.edit_days);
        List<MaterialDayPicker.Weekday> daysSelected = materialDayPicker.getSelectedDays();

        if (type == null || type.isEmpty()) {
            ((TextInputLayout) findViewById(R.id.input_layout_type)).setError(getString(R.string.required_field));
            throw new InvalidParameterException("Lesson type is missing.");
        }
        if (daysSelected == null || daysSelected.isEmpty()) {
            ((TextView) findViewById(R.id.edit_days_label)).setError(getString(R.string.required_field));
            throw new InvalidParameterException("Lesson schedule is missing.");
        }

        String value = ((EditText) findViewById(R.id.edit_price)).getText().toString().replace("$", "");
        if (value == null || value.isEmpty()) {
            ((TextInputLayout) findViewById(R.id.input_layout_price)).setError(getString(R.string.required_field));
            throw new InvalidParameterException("Lesson price is missing.");
        }

        Double price = Double.valueOf(value);
        String daysOfWeek = FormatUtil.convertMaterialDayPickerListToString(daysSelected);
        long studentId = this.getIntent().getExtras().getLong(STUDENT_ID_KEY);

        CalendarDay nextClassSelectedDate = ((MaterialCalendarView) findViewById(R.id.edit_next_class)).getSelectedDate();
        if (nextClassSelectedDate == null) {
            ((TextView) findViewById(R.id.label_next_class)).setError(getString(R.string.required_field));
            throw new InvalidParameterException("Lesson date is missing.");
        }
        Long nextClassDateAsMillis = nextClassSelectedDate.getDate().getTime();

        CalendarDay nextPaymentSelectedDate = ((MaterialCalendarView) findViewById(R.id.edit_next_payment)).getSelectedDate();
        if (nextPaymentSelectedDate == null) {
            ((TextView) findViewById(R.id.label_nextPayment)).setError(getString(R.string.required_field));
            throw new InvalidParameterException("Lesson payment date is missing.");
        }
        Long nextPaymentDateAsMillis = nextPaymentSelectedDate.getDate().getTime();

        if (lesson == null) {
            lesson = new Lesson(studentId, daysOfWeek, price, type, nextPaymentDateAsMillis, nextClassDateAsMillis);
            lessonViewModel.insert(lesson);
        } else {
            lesson.setDays(daysOfWeek);
            lesson.setPrice(price);
            lesson.setType(type);
            lesson.setNextDate(nextClassDateAsMillis);
            lesson.setNextPayment(nextPaymentDateAsMillis);
            lessonViewModel.update(lesson);
        }
    }
}
