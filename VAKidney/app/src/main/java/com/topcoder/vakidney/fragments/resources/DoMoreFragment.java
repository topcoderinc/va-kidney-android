package com.topcoder.vakidney.fragments.resources;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.adapter.ResourcesAdapter;
import com.topcoder.vakidney.databinding.FragmentResourceBinding;
import com.topcoder.vakidney.util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 * It is used to show list of resource articles
 */
public class DoMoreFragment extends Fragment {

    FragmentResourceBinding binder;

    public DoMoreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_resource, container, false);
        final View view = binder.getRoot();
        ResourcesAdapter resourcesAdapter = new ResourcesAdapter(JsondataUtil.getResourcesDoMore(getActivity()), getActivity());
        binder.resourceListView.setAdapter(resourcesAdapter);
        return view;
    }

}
