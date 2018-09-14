package com.topcoder.vakidney.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.topcoder.vakidney.BuildConfig;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.adapter.DropDownItemAdapter;
import com.topcoder.vakidney.api.NDBRestClient;
import com.topcoder.vakidney.api.NDBServiceAPI;
import com.topcoder.vakidney.databinding.PopupAddMealdrugBinding;
import com.topcoder.vakidney.model.MealDrug;
import com.topcoder.vakidney.R;
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
    private final String[] unitMealSpinnerItems = {"Select", "oz (mass)", "oz (fluid)", "g", "mg", "L", "mL", "lb", "st", "cups", "pints"};
    private final String[] unitDrugSpinnerItems = {"Select", "g", "mg"};
    private ImageView btnClose;
    private Button btnAddNewMeal;
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
        binding = PopupAddMealdrugBinding.bind(view);
        binding.mealOrliquidField.setMinHeight(ViewUtil.dpToPx(35, getContext()));
        adapter = new DropDownItemAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, suggestions);

        binding.mealOrliquidField.setAdapter(adapter);
        if (mMode == POPUP_MODE_DRUG) {
            binding.textTitle.setText("Drug name");
            unitSpinnerItems = unitDrugSpinnerItems;
        } else {
            unitSpinnerItems = unitMealSpinnerItems;
        }
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMealDrugPopup.this.dismiss();
            }
        });
        btnAddNewMeal = findViewById(R.id.btnAddNewMeal);
        String btnLabel = mAction == POPUP_ACTION_ADD ? "Add " : "Edit ";
        btnLabel = btnLabel + (mMode == POPUP_MODE_DRUG ? "Drug" : "Meal");
        btnAddNewMeal.setText(btnLabel);
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

                Editable mealOrLiquidField = binding.mealOrliquidField.getText();
                Editable amountField = binding.amountField.getText();

                boolean isValid = true;
                if (TextUtils.isEmpty(mealOrLiquidField)) {
                    binding.mealOrliquidFieldErrorTv.setVisibility(View.VISIBLE);
                    binding.mealOrliquidField.setBackgroundResource(R.drawable.bg_round_white_error);
                    isValid = false;
                }

                if (TextUtils.isEmpty(amountField) ) {
                    binding.amountFieldErrorTv.setVisibility(View.VISIBLE);
                    binding.amountField.setBackgroundResource(R.drawable.bg_round_white_error);
                    isValid = false;
                } else {
                    try {
                        Double.parseDouble(amountField.toString());
                    } catch (Exception ex) {
                        binding.amountFieldErrorTv.setVisibility(View.VISIBLE);
                        binding.amountField.setBackgroundResource(R.drawable.bg_round_white_error);
                        isValid = false;
                    }
                }

                if(binding.unitSpinner.getSelectedItemPosition()==0){
                    binding.unitSpinnerErroTv.setVisibility(View.VISIBLE);
                    binding.unitSpinner.setBackgroundResource(R.drawable.bg_round_white_error);
                    isValid = false;
                }

                if (amount == 0) {
                    binding.amountFieldErrorTv.setVisibility(View.VISIBLE);
                    binding.amountField.setBackgroundResource(R.drawable.bg_round_white_error);
                    binding.amountFieldErrorTv.setText("Please input valid amount");
                    isValid = false;
                }

                if (suggestions.size() > 0 && !suggestions.contains(mealOrLiquidField.toString())
                        && POPUP_MODE_MEAL == mMode ) {
                    binding.mealOrliquidFieldErrorTv.setVisibility(View.VISIBLE);
                    binding.mealOrliquidFieldErrorTv.setBackgroundResource(R.drawable.bg_round_white_error);
                    binding.mealOrliquidFieldErrorTv.setText("Choose from suggestions below");
                } else if(isValid) {
                    if (mAction == POPUP_ACTION_ADD) {
                        MealDrug mealDrug = new MealDrug();
                        applyMealDrugFields(mealDrug, mealOrLiquidField, amount);
                        if (mListener != null) {
                            mListener.onAdded(mealDrug);
                        }
                    } else if (mAction == POPUP_ACTION_EDIT) {
                        applyMealDrugFields(mealDrug, mealOrLiquidField, amount);
                        mSavedMealDrug.save();
                        if (mListener != null) {
                            mListener.onEdited(parentView, mSavedMealDrug);
                        }
                    }
                    AddMealDrugPopup.this.dismiss();
                }
            }

            private void applyMealDrugFields(MealDrug mealDrug, Editable mealOrLiquidField, double amount) {
                mealDrug.setAmount(amount);
                mealDrug.setName(mealOrLiquidField.toString());
                mealDrug.setUnit(unitSpinnerItems[binding.unitSpinner.getSelectedItemPosition()]);
                mealDrug.setType(mMode == POPUP_MODE_DRUG ? MealDrug.TYPE_DRUG : MealDrug.TYPE_MEAL);
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
                if (mListener != null) mListener.onDelete(parentView, mSavedMealDrug);
                AddMealDrugPopup.this.dismiss();
            }
        });

        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, unitSpinnerItems);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.unitSpinner.setAdapter(gameKindArray);
        binding.unitSpinner.setSelection(getUnitsIndex(unitSpinnerItems, mealDrug));
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
                if (!TextUtils.isEmpty(charSequence)) {
                    binding.mealOrliquidFieldErrorTv.setVisibility(View.GONE);
                }

                if (charSequence.toString().length() >= AUTO_COMPLETION_THRESHOLD && POPUP_MODE_MEAL == mMode) {
                    final NDBServiceAPI ndbServiceAPI = NDBRestClient.getService(NDBServiceAPI.class, getContext());
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

                                    adapter = new DropDownItemAdapter(getContext(),
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

                } else if (charSequence.toString().length() >= AUTO_COMPLETION_THRESHOLD && POPUP_MODE_DRUG == mMode) {
                    final NDBServiceAPI ndbServiceAPI = NDBRestClient.getService(NDBServiceAPI.class, getContext());
                    Call<String> result = ndbServiceAPI.searchDrug(BuildConfig.NDB_API_KEY, charSequence.toString());
                    Log.d("TOPCODER", "call searchDrugRecommendation ");
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

                                    adapter = new DropDownItemAdapter(getContext(),
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
            }
        });

        binding.amountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    binding.amountFieldErrorTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(binding.unitSpinner.getSelectedItemPosition()!=0){
                    binding.unitSpinnerErroTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public interface AddMealDrugPopupListener {
        void onAdded(MealDrug mealDrug);

        void onCanceled();

        void onDelete(View view, MealDrug mealDrug);

        void onEdited(View view, MealDrug mealDrug);
    }

    private int getUnitsIndex(String[] units, MealDrug mealDrug) {
        if (mealDrug == null || TextUtils.isEmpty(mealDrug.getUnit())) {
            return 0;
        }
        for (int i = 0; i < units.length; i++) {
            if (units[i].equals(mealDrug.getUnit())) {
                return i;
            }
        }
        return 0;
    }
}
