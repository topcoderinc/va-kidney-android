package com.topcoder.vakidney.popup;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.ViewUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.topcoder.vakidney.AddNewMealActivity;
import com.topcoder.vakidney.BuildConfig;

import com.topcoder.vakidney.adapter.DropDownItemAdapter;
import com.topcoder.vakidney.api.NDBRestClient;
import com.topcoder.vakidney.api.NDBServiceAPI;
import com.topcoder.vakidney.databinding.PopupAddMealdrugBinding;
import com.topcoder.vakidney.model.MealDrug;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.util.DialogManager;
import com.topcoder.vakidney.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This popup shows add meal or drug in the Add New Meal Activity
 */

public class AddMealDrugPopup extends Dialog implements View.OnClickListener {

    public final static int POPUP_MODE_MEAL = 0x00000001;
    public final static int POPUP_MODE_DRUG = 0x00000002;

    public final static int POPUP_ACTION_ADD = 0x00000001;
    public final static int POPUP_ACTION_EDIT = 0x00000002;
    private static final int AUTO_COMPLETION_THRESHOLD = 3;

    private String[] unitSpinnerItems;
    private final String[] unitMealSpinnerItems = {"oz (mass)", "oz (fluid)", "g", "mg", "L", "mL", "lb", "st", "cups", "pints"};
    private final String[] unitDrugSpinnerItems = {"g", "mg"};
    private Button btnAddNewMeal;
    private Context mContext;
    private int mMode = POPUP_MODE_MEAL;
    private int mAction = POPUP_ACTION_ADD;
    private AddMealDrugPopupListener mListener;
    List<String> suggestions;
    private MealDrug mSavedMealDrug, mealDrug;
    DropDownItemAdapter adapter;
    View parentView;
    PopupAddMealdrugBinding binding;
    public AddMealDrugPopup(
            final Activity context,
            int mode,
            int action,
            MealDrug mealDrug, View view) {
        super(context);
        mContext = context;
        mMode = mode;
        mAction = action;
        parentView = view;
        suggestions = new ArrayList<>();
        this.mealDrug = mealDrug;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_add_mealdrug, null, false);
        setContentView(view);
        binding= PopupAddMealdrugBinding.bind(view);
        binding.mealOrliquidField.setMinHeight(ViewUtil.dpToPx(35, getContext()));
        adapter = new DropDownItemAdapter(mContext, android.R.layout.simple_dropdown_item_1line, suggestions);

        binding.mealOrliquidField.setAdapter(adapter);
        if (mMode == POPUP_MODE_DRUG) {
            binding.textTitle.setText("Drug/medications");
            unitSpinnerItems = unitDrugSpinnerItems;
        } else {
            unitSpinnerItems = unitMealSpinnerItems;
        }
        btnAddNewMeal = findViewById(R.id.btnAddNewMeal);
        String btnLabel = mAction == POPUP_ACTION_ADD ? "Add " : "Edit ";
        btnLabel = btnLabel + (mMode == POPUP_MODE_DRUG ? "Drug" : "Meal");
        btnAddNewMeal.setText(btnLabel);
        btnAddNewMeal.setEnabled(false);
        btnAddNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount = 0;
                try {
                    if (!binding.amountField.getText().toString().isEmpty()) {
                        amount = Double.parseDouble(binding.amountField.getText().toString());
                    }
                }
                catch (Exception e) {
                    amount = 0;
                }
                if (binding.mealOrliquidField.getText().toString().isEmpty()
                        || binding.amountField.getText().toString().isEmpty()) {
                    binding.mealOrliquidFieldErrorTv.setVisibility(View.GONE);
                    binding.mealOrliquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white);
                    binding.amountFieldErrorTv.setVisibility(View.GONE);
                    binding.amountFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white);
                    binding.unitSpinnerErroTv.setVisibility(View.GONE);
                    binding.unitSpinnerErroTv.setBackgroundResource(R.drawable.bg_round_white);
                    if (binding.mealOrliquidField.getText().toString().isEmpty()) {
                        binding.mealOrliquidFieldErrorTv.setVisibility(View.VISIBLE);
                        binding.mealOrliquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                    if (binding.amountField.getText().toString().isEmpty()) {
                        binding.amountFieldErrorTv.setVisibility(View.VISIBLE);
                        binding.amountField.setBackgroundResource(R.drawable.bg_round_white_error);
                    }
                    if (binding.unitSpinner.getSelectedItemPosition() == 0) {
                        binding.unitSpinnerErroTv.setVisibility(View.VISIBLE);
                        binding.unitSpinner.setBackgroundResource(R.drawable.bg_round_white_error);
                    }

                }
                else if (amount == 0) {
                    binding.amountFieldErrorTv.setVisibility(View.VISIBLE);
                    binding.amountField.setBackgroundResource(R.drawable.bg_round_white_error);
                    binding.amountFieldErrorTv.setText("Please input valid amount");
                }
                else if (suggestions.size() > 0 && !suggestions.contains(binding.mealOrliquidField.getText().toString())
                        && POPUP_MODE_MEAL == mMode ) {
                    binding.mealOrliquidFieldErrorTv.setVisibility(View.VISIBLE);
                    binding.mealOrliquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white_error);
                    binding.mealOrliquidFieldErrorTv.setText("Choose from suggestions below");
                } else {
                    if (mAction == POPUP_ACTION_ADD) {
                        MealDrug mealDrug = new MealDrug();
                        mealDrug.setAmount(Double.parseDouble(binding.amountField.getText().toString()));
                        mealDrug.setName(binding.mealOrliquidField.getText().toString());
                        mealDrug.setUnit(unitSpinnerItems[binding.unitSpinner.getSelectedItemPosition()]);
                        mealDrug.setType(mMode == POPUP_MODE_DRUG ? MealDrug.TYPE_DRUG : MealDrug.TYPE_MEAL);
                        if (mListener != null) mListener.onAdded(mealDrug);
                    } else if (mAction == POPUP_ACTION_EDIT) {
                        mSavedMealDrug.setAmount(amount);
                        mSavedMealDrug.setName(binding.mealOrliquidField.getText().toString());
                        mSavedMealDrug.setUnit(unitSpinnerItems[binding.unitSpinner.getSelectedItemPosition()]);
                        mSavedMealDrug.setType(mMode == POPUP_MODE_DRUG ? MealDrug.TYPE_DRUG : MealDrug.TYPE_MEAL);
                        mSavedMealDrug.save();
                        if (mListener != null) mListener.onEdited(parentView, mSavedMealDrug);
                    }
                    AddMealDrugPopup.this.dismiss();
                }
            }
        });

        Button btnDelete = findViewById(R.id.btnDeleteMeal);
        if (mAction == POPUP_ACTION_EDIT) {
            btnDelete.setVisibility(View.VISIBLE);
            mSavedMealDrug = mealDrug;
            binding.mealOrliquidField.setText(mSavedMealDrug.getName());
            binding.amountField.setText(String.valueOf(mSavedMealDrug.getAmount()));
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mSavedMealDrug.delete();
                } catch (Exception e) {
                }
                if (mListener != null) mListener.onDeleted(parentView, mSavedMealDrug);
                AddMealDrugPopup.this.dismiss();
            }
        });

        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, unitSpinnerItems);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.unitSpinner.setAdapter(gameKindArray);
        binding.unitSpinner.setSelection(0);

        enableDisableAddMealButton();
    }

    public void setListener(AddMealDrugPopupListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {

    }


    private void enableDisableAddMealButton() {

        binding.mealOrliquidField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() != 0) {
                    if (!binding.amountField.getText().toString().isEmpty()) {
                        btnAddNewMeal.setEnabled(true);
                    }
                }
                if (charSequence.toString().length() >= AUTO_COMPLETION_THRESHOLD && POPUP_MODE_MEAL == mMode) {
                    final NDBServiceAPI ndbServiceAPI = NDBRestClient.getService(NDBServiceAPI.class, mContext);
                    Call<String> result = ndbServiceAPI.searchFood(BuildConfig.NDB_API_KEY, charSequence.toString());
                    Log.d("TOPCODER", "call searchFoodRecommendation ");
                    result.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                            JSONObject jsonObject = null;

                            Log.d("TOPCODER", "response.body() " + response.body());

                            try {
                                jsonObject = new JSONObject(response.body());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                JSONArray items = jsonObject.getJSONObject("list").getJSONArray("item");
                                suggestions.clear();
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject jsonObject1 = items.getJSONObject(i);
                                    Log.d("JSONOBJECT", items.getJSONObject(i).toString());
                                    suggestions.add(jsonObject1.get("name").toString());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    //suggestions is the result of the http request with the suggestions

                                    adapter = new DropDownItemAdapter(mContext,
                                            android.R.layout.simple_dropdown_item_1line, suggestions);
                                    binding.mealOrliquidField.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            t.getStackTrace();
                        }
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.mealOrliquidFieldErrorTv.setVisibility(View.INVISIBLE);
            }
        });

        binding.amountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    if (!binding.mealOrliquidField.getText().toString().isEmpty()) {
                        btnAddNewMeal.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    if (!binding.mealOrliquidField.getText().toString().isEmpty()) {
                        if (!binding.amountField.getText().toString().isEmpty()) {
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

        void onDeleted(View view, MealDrug mealDrug);

        void onEdited(View view, MealDrug mealDrug);
    }

}
