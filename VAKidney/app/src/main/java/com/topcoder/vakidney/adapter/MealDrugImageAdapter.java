package com.topcoder.vakidney.adapter;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.topcoder.vakidney.databinding.ItemAddMealDrugImageBinding;
import com.topcoder.vakidney.databinding.ItemMealDrugImageBinding;
import com.topcoder.vakidney.model.MealDrugImage;

import java.util.ArrayList;
import java.util.List;

public class MealDrugImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MealDrugImage> mealDrugImages = new ArrayList<>();
    private final OnMealDrugActions onMealDrugActions;

    public MealDrugImageAdapter(
            @NonNull List<MealDrugImage> mealDrugImages,
            OnMealDrugActions onMealDrugActions) {
        this.onMealDrugActions = onMealDrugActions;
        this.mealDrugImages.addAll(mealDrugImages);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ViewType.VIEW_TYPE_ADD:
                ItemAddMealDrugImageBinding addMealDrugImageBinding =
                        ItemAddMealDrugImageBinding.inflate(inflater, parent, false);
                return new AddMealDrugImageViewHolder(addMealDrugImageBinding);
            case ViewType.VIEW_TYPE_IMAGE:
                ItemMealDrugImageBinding mealDrugImageBinding =
                        ItemMealDrugImageBinding.inflate(inflater, parent, false);
                return new MealDrugImageViewHolder(mealDrugImageBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ViewType.VIEW_TYPE_ADD:
                AddMealDrugImageViewHolder addMealDrugImageViewHolder = (AddMealDrugImageViewHolder) holder;
                addMealDrugImageViewHolder.binding
                        .setViewModel(new AddMealDrugImageViewModel(onMealDrugActions));
                break;
            case ViewType.VIEW_TYPE_IMAGE:
                final MealDrugImageViewHolder mealDrugImageViewHolder = (MealDrugImageViewHolder) holder;
                mealDrugImageViewHolder.binding
                        .setViewModel(new MealDrugImageViewModel(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int position = mealDrugImageViewHolder.getAdapterPosition();
                                MealDrugImage mealDrugImage = mealDrugImages.remove(position - 1);
                                notifyItemRemoved(position);
                                onMealDrugActions.onRemoveMealDrugImage(mealDrugImage);
                            }
                        }));
                MealDrugImage mealDrugImage = mealDrugImages.get(position - 1);
                Glide.with(holder.itemView.getContext())
                        .load(mealDrugImage.getUrl())
                        .into(mealDrugImageViewHolder.binding.addedImage);
                break;
        }
    }

    @Override
    @ViewType
    public int getItemViewType(int position) {
        return position == 0 ? ViewType.VIEW_TYPE_ADD : ViewType.VIEW_TYPE_IMAGE;
    }

    @Override
    public int getItemCount() {
        return 1 + mealDrugImages.size();
    }

    public void addMealDrugImage(MealDrugImage mealDrugImage) {
        mealDrugImages.add(mealDrugImage);
        notifyItemInserted(mealDrugImages.size() + 1);
    }

    class AddMealDrugImageViewHolder extends RecyclerView.ViewHolder {

        private ItemAddMealDrugImageBinding binding;

        public AddMealDrugImageViewHolder(ItemAddMealDrugImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class MealDrugImageViewHolder extends RecyclerView.ViewHolder {

        private ItemMealDrugImageBinding binding;

        public MealDrugImageViewHolder(ItemMealDrugImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class AddMealDrugImageViewModel extends BaseObservable {

        private OnAddMealDrugImageAction onAddMealDrugImageAction;

        public AddMealDrugImageViewModel(OnAddMealDrugImageAction onAddMealDrugImageAction) {
            this.onAddMealDrugImageAction = onAddMealDrugImageAction;
        }

        public void onItemClick(View view) {
            if (onAddMealDrugImageAction == null) {
                return;
            }
            onAddMealDrugImageAction.onAddMealDrugImage();
        }
    }

    public static class MealDrugImageViewModel extends BaseObservable {

        private View.OnClickListener onRemoveMealDrugImageClickListener;

        public MealDrugImageViewModel(View.OnClickListener onRemoveMealDrugImageClickListener) {
            this.onRemoveMealDrugImageClickListener = onRemoveMealDrugImageClickListener;
        }

        public void onRemoveItemClick(View view) {
            if (onRemoveMealDrugImageClickListener == null) {
                return;
            }
            onRemoveMealDrugImageClickListener.onClick(view);
        }

    }

    public interface OnMealDrugActions
            extends OnAddMealDrugImageAction, OnRemoveMealDrugImageAction {


        void onRemoveMealDrugImage(MealDrugImage mealDrug);
    }

    public interface OnAddMealDrugImageAction {
        void onAddMealDrugImage();
    }

    public interface OnRemoveMealDrugImageAction {
        void onRemoveMealDrugImage(MealDrugImage mealDrug);
    }

    @interface ViewType {
        int VIEW_TYPE_ADD = 0;
        int VIEW_TYPE_IMAGE = 1;
    }
}