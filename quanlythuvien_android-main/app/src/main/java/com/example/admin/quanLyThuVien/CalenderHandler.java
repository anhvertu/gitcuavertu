package com.example.admin.quanLyThuVien;

import android.content.Context;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.Calendar;

public class CalenderHandler {

    private Context context;

    public CalenderHandler(Context current) {
        this.context = current;
    }

    public void setDefaultDate(String date, CalendarView mCalendarView) {
        String parts[] = date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]) - 1;
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milliTime = calendar.getTimeInMillis();
        mCalendarView.setDate(milliTime, true, true);
    }

    public String setDate(int year, int month, int day) {
        String d = "", m = "";
        month += 1;
        if (month < 10) {
            m += "0" + month;
        } else {
            m += month;
        }
        if (day < 10) {
            d += "0" + day;
        } else {
            d += day;
        }
        return (d + "/" + m + "/" + year);
    }

    public void validateDate(CharSequence s, int start, int before, int count, EditText editText) {
        String working = s.toString();
        boolean isValid = true;
        if (working.length() == 2 && before == 0) {
            if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 31) {
                isValid = false;
            } else {
                working += "/";
                editText.setText(working);
                editText.setSelection(working.length());
            }
        } else if (working.length() == 5 && before == 0) {
            String enterMonth = working.substring(3);
            if (Integer.parseInt(enterMonth) < 1 || Integer.parseInt(enterMonth) > 12) {
                isValid = false;
            } else {
                working += "/";
                editText.setText(working);
                editText.setSelection(working.length());
            }
        } else if (working.length() == 10 && before == 0) {
            String enteredYear = working.substring(6);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (Integer.parseInt(enteredYear) > currentYear || Integer.parseInt(enteredYear) < 1900) {
                isValid = false;
            }
        } else if (working.length() != 10) {
            isValid = false;
        }

        if (!isValid) {
            editText.setError(context.getResources().getString(R.string.sai_dinh_dang_ngay));
        } else {
            editText.setError(null);
        }
    }
}
