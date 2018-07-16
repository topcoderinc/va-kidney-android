package com.topcoder.vakidney.fragments;


import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.topcoder.vakidney.adapter.FoodAdapter;
import com.topcoder.vakidney.databinding.DialogFoodDrugFilterBinding;
import com.topcoder.vakidney.databinding.DialogFoodDrugTypeBinding;
import com.topcoder.vakidney.databinding.FragmentFoodBinding;
import com.topcoder.vakidney.model.Meal;
import com.topcoder.vakidney.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This class is used to show all the meals that are added by the users in gridView
 */
public class FoodFragment extends Fragment {


    private List<Meal> mealArrayList;
    AppCompatImageView filterIcon;
    boolean[] checkedItems;
    String[] filters = {"Select Date", "Select Type"};
    private Calendar myCalendar;
    private int filterYear, filterMonth, filterDay;
    private String filterType = "",filterQuery= "";
    private boolean filtersUsed[] = {false, false};
    private DatePickerDialog.OnDateSetListener datelistener;
    String[] typeLabels;
    BottomSheetDialog bottomDialog;
    FoodAdapter foodAdapter;
    private FragmentFoodBinding binder;
    boolean noCheckedItems = false;

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeLabels = new String[]{Meal.MEAL_TYPE_BREAKFAST, Meal.MEAL_TYPE_LUNCH, Meal.MEAL_TYPE_DINNER, Meal.MEAL_TYPE_SNACK
                , Meal.MEAL_TYPE_CASUAL};
        checkedItems = new boolean[typeLabels.length];
        Calendar myCalender = Calendar.getInstance();
        filterYear = myCalender.get(Calendar.YEAR);
        filterMonth = myCalender.get(Calendar.MONTH);
        filterDay = myCalender.get(Calendar.DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_food, container, false);
        final View view = binder.getRoot();
        filterIcon = getActivity().findViewById(R.id.filterIcon);
        filterIcon.setVisibility(View.VISIBLE);
        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilterDialog();
            }
        });
        myCalendar = Calendar.getInstance();
        datelistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                filterYear = year;
                filterMonth = monthOfYear;
                filterDay = dayOfMonth;
                filters[0] = "Filter by date: " + DateUtils.getMonthString(monthOfYear, DateUtils.LENGTH_SHORT)
                        + " " + dayOfMonth
                        + " " + year;
                filtersUsed[0] = true;
                TextView tvSelectDate=bottomDialog.findViewById(R.id.selectDateFilter);
                tvSelectDate.setText(filters[0]);
            }
        };
        mealArrayList = Meal.listAll(Meal.class);
        if (getActivity().getIntent().hasExtra("addmeal")) {
            Meal meal = (Meal) getActivity().getIntent().getSerializableExtra("meal");
            mealArrayList.add(meal);
        }

        foodAdapter = new FoodAdapter(mealArrayList, getActivity());
        binder.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binder.recyclerView.setAdapter(foodAdapter);
        return view;
    }

    private void displayFilterDialog() {

        bottomDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.dialog_food_drug_filter, null);
        final DialogFoodDrugFilterBinding binding=DialogFoodDrugFilterBinding.bind(sheetView);
        bottomDialog.setContentView(sheetView);
        binding.selectDateFilter.setText(filters[0]);
        binding.selectTypeFilter.setText(filters[1]);
        binding.cancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        binding.doneFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryUsingFilters();
                bottomDialog.dismiss();
            }
        });
        binding.selectTypeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectTypeDialog();
            }
        });
        binding.selectDateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDateDialog();
            }
        });

        bottomDialog.show();
    }

    private void displayDateDialog() {
        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), datelistener, filterYear, filterMonth,
                filterDay);
        pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        pickerDialog.show();
    }

    private void queryUsingFilters() {
        if (!filtersUsed[0] && !filtersUsed[1])
        {
            mealArrayList.clear();
            mealArrayList.addAll(Meal.listAll(Meal.class));
            foodAdapter.notifyDataSetChanged();
            return;
        }
        Date date = null;
        if (filtersUsed[0]) {
            Log.d("Filter Date", filterDay + " " + filterMonth + " " + filterYear);
            Calendar calendar = Calendar.getInstance();
            calendar.set(filterYear, filterMonth, filterDay);
            date = calendar.getTime();
        }
        if (filtersUsed[1]) {
            Log.d("Filter Type", filterQuery);
        }
        List<Meal> mealList = Meal.getMealUsingFilter(date, filterQuery);
        mealArrayList.clear();
        mealArrayList.addAll(mealList);
        foodAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        filterIcon.setVisibility(View.GONE);
        super.onDestroyView();
    }

    private void displaySelectTypeDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.dialog_food_drug_type, null);
        final DialogFoodDrugTypeBinding binding=DialogFoodDrugTypeBinding.bind(sheetView);
        dialog.setContentView(sheetView);
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        binding.mealBreakfast.setChecked(checkedItems[Meal.MEAL_BREAKFAST]);
        binding.mealLunch.setChecked(checkedItems[Meal.MEAL_LUNCH]);
        binding.mealDinner.setChecked(checkedItems[Meal.MEAL_DINNER]);
        binding.mealSnack.setChecked(checkedItems[Meal.MEAL_SNACK]);
        binding.mealCasual.setChecked(checkedItems[Meal.MEAL_CASUAL]);
        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noCheckedItems=false;
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilderquery = new StringBuilder();
                checkedItems[Meal.MEAL_BREAKFAST] = binding.mealBreakfast.isChecked();
                checkedItems[Meal.MEAL_LUNCH] = binding.mealLunch.isChecked();
                checkedItems[Meal.MEAL_DINNER] = binding.mealDinner.isChecked();
                checkedItems[Meal.MEAL_SNACK] = binding.mealSnack.isChecked();
                checkedItems[Meal.MEAL_CASUAL] = binding.mealCasual.isChecked();
                for (int i = 0; i < typeLabels.length; i++) {
                    if (checkedItems[i]) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append(",");
                            stringBuilderquery.append(",");
                        }
                        noCheckedItems = true;
                        stringBuilderquery.append("'"+typeLabels[i]+"'");
                        stringBuilder.append(typeLabels[i]);
                        filterType = stringBuilder.toString();
                        filterQuery= stringBuilderquery.toString();
                        filters[1] = "Filter by type: " + filterType;
                        filtersUsed[1] = true;
                    }
                }
                if (!noCheckedItems) {
                    filters[1] = "Select type: ";
                    filtersUsed[1] = false;
                }
                TextView tvSelectType=bottomDialog.findViewById(R.id.selectTypeFilter);
                tvSelectType.setText(filters[1]);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
