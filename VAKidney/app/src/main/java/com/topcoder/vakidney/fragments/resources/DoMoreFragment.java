package com.topcoder.vakidney.fragments.resources;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.topcoder.vakidney.R;
import com.topcoder.vakidney.adapter.ResourcesAdapter;
import com.topcoder.vakidney.util.JsondataUtil;

/**
 * A simple {@link Fragment} subclass.
 * It is used to show list of resource articles
 */
public class DoMoreFragment extends Fragment {


    ListView resourceListView;
    public DoMoreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_resource, container, false);
        resourceListView=view.findViewById(R.id.resourceListView);
        ResourcesAdapter resourcesAdapter=new ResourcesAdapter(JsondataUtil.getResourcesDoMore(getActivity()), getActivity());
        resourceListView.setAdapter(resourcesAdapter);
        return view;
    }

}
