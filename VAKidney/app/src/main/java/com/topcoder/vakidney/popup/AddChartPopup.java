package com.topcoder.vakidney.popup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.topcoder.vakidney.model.ChartData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.DialogManager;
import com.topcoder.vakidney.constant.ChartType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This popup shows add chart record data
 */

public class AddChartPopup extends BasePopup implements
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    private EditText amountField;
    private Spinner unitSpinner;
    private Button btnAddChartData;

    private int mChartType;
    private AddChartPopupListener mListener;

    private TextView mTextDate;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;

    public AddChartPopup(final Activity context, int chartType) {
        super(context, R.layout.popup_add_chart);

        mChartType = chartType;

        amountField = getContentView().findViewById(R.id.amountField);
        unitSpinner = getContentView().findViewById(R.id.unitSpinner);

        btnAddChartData = getContentView().findViewById(R.id.addBtn);
        btnAddChartData.setEnabled(false);
        btnAddChartData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!amountField.getText().toString().isEmpty()) {
                    String message = "Record Data Added";
                    DialogManager.showOkDialog(context, message, new DialogManager.OnYesClicked() {
                        @Override
                        public void YesClicked() {
                            ChartData data = new ChartData(
                                    Double.parseDouble(amountField.getText().toString()),
                                    mChartType,
                                    mCalendar.getTime().getTime()
                            );
                            data.save();

                            if (mListener != null) mListener.onAdded(data);
                            AddChartPopup.this.dismiss();
                        }
                    });
                }
            }
        });

        String[] unitSpinnerItems = {
                ChartType.getChartUnit(mChartType)
        };
        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unitSpinnerItems);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(gameKindArray);
        unitSpinner.setSelection(0);

        mDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        Date date = new Date();
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        mTextDate = getContentView().findViewById(R.id.dateText);
        mTextDate.setText(mDateFormat.format(date));
        mTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mContext,
                        AddChartPopup.this,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        enableDisableAddMealButton();
    }

    public void setListener(AddChartPopupListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getWidth() {
        Display display = mContext.getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        return displaySize.x - 100;
    }

    @Override
    public int getHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getLocationX() {
        return 0;
    }

    @Override
    public int getLocationY() {
        return 0;
    }

    private void enableDisableAddMealButton() {

        amountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    btnAddChartData.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mTextDate.setText(mDateFormat.format(mCalendar.getTime()));
    }

    public interface AddChartPopupListener {
        void onAdded(ChartData chartData);
        void onCanceled();
    }

}
