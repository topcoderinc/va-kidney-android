package com.topcoder.vakidney.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.databinding.DropdownItemBinding;

import java.util.List;

/*
*  Used for displaying auto complete results
* */
public class DropDownItemAdapter extends ArrayAdapter<String> {
    List<String> objects;
    Context context;

    public DropDownItemAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.dropdown_item, null);
        final DropdownItemBinding itemBinding=DropdownItemBinding.bind(convertView);

        itemBinding.text1.setText(objects.get(position));
        itemBinding.text1.setSingleLine(false);

        return convertView;
    }
}
