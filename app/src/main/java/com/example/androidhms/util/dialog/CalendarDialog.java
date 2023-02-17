package com.example.androidhms.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.example.androidhms.databinding.DialogCalendarBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.sql.Timestamp;
import java.util.Calendar;

public class CalendarDialog {

    private DialogCalendarBinding bind;
    private Dialog dialog;
    private final Calendar calendar = Calendar.getInstance();

    public CalendarDialog(Context context, LayoutInflater inflater, SetDateClickListener callback) {
        dialog = new Dialog(context);
        bind = DialogCalendarBinding.inflate(inflater);
        dialog.setContentView(bind.getRoot());
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 100;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(0.3f);

        bind.calendar.setOnDateChangedListener((widget, date, selected) ->
                callback.setDateClick(date, CalendarDialog.this));

        // 토요일
        bind.calendar.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                calendar.set(Calendar.YEAR, day.getYear());
                calendar.set(Calendar.MONTH, day.getMonth() - 1);
                calendar.set(Calendar.DATE, day.getDay());
                int weekday = calendar.get(Calendar.DAY_OF_WEEK);
                return weekday == Calendar.SATURDAY;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.BLUE));
            }
        });

        // 일요일
        bind.calendar.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                calendar.set(Calendar.YEAR, day.getYear());
                calendar.set(Calendar.MONTH, day.getMonth() - 1);
                calendar.set(Calendar.DATE, day.getDay());
                int weekday = calendar.get(Calendar.DAY_OF_WEEK);
                return weekday == Calendar.SUNDAY;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.RED));
            }
        });

    }

    public CalendarDialog setDate(String date) {
        if (!date.equals("")) {
            String[] dateArr = date.split("-");
            int year = Integer.parseInt(dateArr[0]);
            int month = Integer.parseInt(dateArr[1]);
            int day = Integer.parseInt(dateArr[2]);
            bind.calendar.setSelectedDate(CalendarDay.from(year, month, day));
            bind.calendar.state().edit().commit();
        }
        return this;
    }

    public CalendarDialog setMaxDate(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        bind.calendar.state().edit().setMaximumDate(
                CalendarDay.from(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH))).commit();
        bind.calendar.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return Timestamp.valueOf(day.getYear() + "-" + day.getMonth() + "-" + day.getDay() + " 00:00:00")
                        .compareTo(Timestamp.valueOf(cal.get(Calendar.YEAR)
                                + "-" + (cal.get(Calendar.MONTH) + 1)
                                + "-" + cal.get(Calendar.DAY_OF_MONTH)
                                + " 00:00:00")) > 0;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.GRAY));
            }
        });
        return this;
    }

    public CalendarDialog setMinDate(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        bind.calendar.state().edit().setMinimumDate(
                CalendarDay.from(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH))).commit();
        bind.calendar.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return Timestamp.valueOf(day.getYear() + "-" + day.getMonth() + "-" + day.getDay() + " 00:00:00")
                        .compareTo(Timestamp.valueOf(cal.get(Calendar.YEAR)
                                + "-" + (cal.get(Calendar.MONTH) + 1)
                                + "-" + cal.get(Calendar.DAY_OF_MONTH)
                                + " 00:00:00")) < 0;
            }
            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.GRAY));
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public interface SetDateClickListener {
        void setDateClick(CalendarDay date, CalendarDialog dialog);
    }
}
