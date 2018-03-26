package com.topcoder.vakidney.popup;

import android.app.Activity;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.topcoder.vakidney.model.MealDrug;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.DialogManager;

/**
 * This popup shows add meal or drug in the Add New Meal Activity
 */

public class AddMealDrugPopup extends BasePopup implements View.OnClickListener {

    public final static int POPUP_MODE_MEAL = 0x00000001;
    public final static int POPUP_MODE_DRUG = 0x00000002;

    public final static int POPUP_ACTION_ADD = 0x00000001;
    public final static int POPUP_ACTION_EDIT = 0x00000002;

    private EditText addMealOrLiquidField, amountField;
    private TextView mealOrLiquidFieldErrorTv, amountFieldErrorTv, unitSpinnerTv;
    private TextView titleTv;
    private Spinner unitSpinner;
    private final String[] unitSpinnerItems = {"g", "mg"};
    private Button btnAddNewMeal;

    private int mMode = POPUP_MODE_MEAL;
    private int mAction = POPUP_ACTION_ADD;
    private AddMealDrugPopupListener mListener;

    private MealDrug mSavedMealDrug;

    public AddMealDrugPopup(
            final Activity context,
            int mode,
            int action,
            MealDrug mealDrug) {
        super(context, R.layout.popup_add_mealdrug);

        mMode = mode;
        mAction = action;

        mealOrLiquidFieldErrorTv = getContentView().findViewById(R.id.mealOrliquidFieldErrorTv);
        amountFieldErrorTv = getContentView().findViewById(R.id.amountFieldErrorTv);
        unitSpinnerTv = getContentView().findViewById(R.id.unitSpinnerErroTv);
        addMealOrLiquidField = getContentView().findViewById(R.id.mealOrliquidField);
        amountField = getContentView().findViewById(R.id.amountField);
        unitSpinner = getContentView().findViewById(R.id.unitSpinner);
        titleTv = getContentView().findViewById(R.id.textTitle);

        if (mMode == POPUP_MODE_DRUG) {
           titleTv.setText("Drug/medications");
        }
        btnAddNewMeal = getContentView().findViewById(R.id.btnAddNewMeal);
        String btnLabel = mAction == POPUP_ACTION_ADD ? "Add " : "Edit ";
        btnLabel = btnLabel + (mMode == POPUP_MODE_DRUG ? "Drug" : "Meal");
        btnAddNewMeal.setText(btnLabel);
        btnAddNewMeal.setEnabled(false);
        btnAddNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addMealOrLiquidField.getText().toString().isEmpty()
                        || amountField.getText().toString().isEmpty()) {
                    mealOrLiquidFieldErrorTv.setVisibility(View.GONE);
                    mealOrLiquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white);
                    amountFieldErrorTv.setVisibility(View.GONE);
                    amountFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white);
                    unitSpinnerTv.setVisibility(View.GONE);
                    unitSpinnerTv.setBackgroundResource(R.drawable.bg_round_white);
                    if (addMealOrLiquidField.getText().toString().isEmpty()) {
                        mealOrLiquidFieldErrorTv.setVisibility(View.VISIBLE);
                        mealOrLiquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                    if (amountField.getText().toString().isEmpty()) {
                        amountFieldErrorTv.setVisibility(View.VISIBLE);
                        amountField.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                    if (unitSpinner.getSelectedItemPosition() == 0) {
                        unitSpinnerTv.setVisibility(View.VISIBLE);
                        unitSpinner.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                } else {
                    String message = mMode == POPUP_MODE_DRUG ? "New Drug Saved" : "New Meal Saved";
                    DialogManager.showOkDialog(context, message, new DialogManager.OnYesClicked() {
                        @Override
                        public void YesClicked() {
                            if (mAction == POPUP_ACTION_ADD) {
                                MealDrug mealDrug = new MealDrug();
                                mealDrug.setAmount(Double.parseDouble(amountField.getText().toString()));
                                mealDrug.setName(addMealOrLiquidField.getText().toString());
                                mealDrug.setUnit(unitSpinnerItems[unitSpinner.getSelectedItemPosition()]);
                                mealDrug.setType(mMode == POPUP_MODE_DRUG ? MealDrug.TYPE_DRUG : MealDrug.TYPE_MEAL);
                                if (mListener != null) mListener.onAdded(mealDrug);
                            }
                            else if (mAction == POPUP_ACTION_EDIT) {
                                mSavedMealDrug.setAmount(Double.parseDouble(amountField.getText().toString()));
                                mSavedMealDrug.setName(addMealOrLiquidField.getText().toString());
                                mSavedMealDrug.setUnit(unitSpinnerItems[unitSpinner.getSelectedItemPosition()]);
                                mSavedMealDrug.setType(mMode == POPUP_MODE_DRUG ? MealDrug.TYPE_DRUG : MealDrug.TYPE_MEAL);
                                mSavedMealDrug.save();
                                if (mListener != null) mListener.onEdited(AddMealDrugPopup.this, mSavedMealDrug);
                            }
                            AddMealDrugPopup.this.dismiss();
                        }
                    });
                }
            }
        });

        Button btnDelete = getContentView().findViewById(R.id.btnDeleteMeal);
        if (mAction == POPUP_ACTION_EDIT) {
            btnDelete.setVisibility(View.VISIBLE);
            mSavedMealDrug = mealDrug;
            addMealOrLiquidField.setText(mSavedMealDrug.getName());
            amountField.setText(String.valueOf(mSavedMealDrug.getAmount()));
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSavedMealDrug.delete();
                if (mListener != null) mListener.onDeleted(AddMealDrugPopup.this);
                AddMealDrugPopup.this.dismiss();
            }
        });

        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, unitSpinnerItems);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(gameKindArray);
        unitSpinner.setSelection(0);

        enableDisableAddMealButton();
    }

    public void setListener(AddMealDrugPopupListener listener) {
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
        addMealOrLiquidField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    if (!amountField.getText().toString().isEmpty()) {
                        if (unitSpinner.getSelectedItemPosition() != 0) {
                            btnAddNewMeal.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        amountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    if (!addMealOrLiquidField.getText().toString().isEmpty()) {
                        btnAddNewMeal.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    if (!addMealOrLiquidField.getText().toString().isEmpty()) {
                        if (!amountField.getText().toString().isEmpty()) {
                            btnAddNewMeal.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public interface AddMealDrugPopupListener {
        void onAdded(MealDrug mealDrug);
        void onCanceled();
        void onDeleted(AddMealDrugPopup parent);
        void onEdited(AddMealDrugPopup parent, MealDrug mealDrug);
    }

}
