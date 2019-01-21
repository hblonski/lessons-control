package com.lessonscontrol.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.viewModel.LessonViewModel;
import com.lessonscontrol.utils.FormatUtil;
import com.lessonscontrol.utils.MoneyTextWatcher;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

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
                    //TODO remover esse toast e marcar os required na tela
                    Toast.makeText(
                            getApplicationContext(),
                            new String("One or more required fields are empty."),
                            Toast.LENGTH_LONG).show();
                    Log.e(EditLessonActivity.this.getClass().toString(),
                            "Required argument missing when trying to create lesson.");
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

        long nextClassDate = lesson.getNextDate();
        if (nextClassDate != Lesson.NO_DATE_SELECTED) {
            ((MaterialCalendarView) findViewById(R.id.edit_next_class)).setSelectedDate(new Date(nextClassDate));
        }

        long nextPaymentDate = lesson.getNextPayment();
        if (nextPaymentDate != Lesson.NO_DATE_SELECTED) {
            ((MaterialCalendarView) findViewById(R.id.edit_next_payment)).setSelectedDate(new Date());
        }
    }

    private void createOrUpdateLessonFromActivityData() throws InvalidParameterException {
        String type = ((EditText) findViewById(R.id.edit_type)).getText().toString();
        MaterialDayPicker materialDayPicker = findViewById(R.id.edit_days);
        List<MaterialDayPicker.Weekday> daysSelected = materialDayPicker.getSelectedDays();

        if (type == null || type.isEmpty()) {
            throw new InvalidParameterException("Lesson type is missing.");
        }
        if (daysSelected == null || daysSelected.isEmpty()) {
            throw new InvalidParameterException("Lesson schedule is missing.");
        }

        String value = ((EditText) findViewById(R.id.edit_price)).getText().toString().replace("$", "");
        if (value == null || value.isEmpty()) {
            throw new InvalidParameterException("Lesson price is missing.");
        }

        Double price = Double.valueOf(value);
        String daysOfWeek = FormatUtil.convertMaterialDayPickerListToString(daysSelected);
        long studentId = this.getIntent().getExtras().getLong(STUDENT_ID_KEY);

        CalendarDay nextClassSelectedDate = ((MaterialCalendarView) findViewById(R.id.edit_next_class)).getSelectedDate();
        Date nextClassDate = nextClassSelectedDate != null ? nextClassSelectedDate.getDate() : null;
        long nextClassDateAsMillis = nextClassDate != null ? nextClassDate.getTime() : Lesson.NO_DATE_SELECTED;

        CalendarDay nextPaymentSelectedDate = ((MaterialCalendarView) findViewById(R.id.edit_next_payment)).getSelectedDate();
        Date nextPaymentDate = nextPaymentSelectedDate != null ? nextPaymentSelectedDate.getDate() : null;
        long nextPaymentDateAsMillis = nextPaymentDate != null ? nextPaymentDate.getTime() : Lesson.NO_DATE_SELECTED;

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
