package com.topcoder.vakidney.Fragments;

import android.support.v4.app.Fragment;

/**
 * Created by abina on 2/2/2018.
 */

public class WelcomeBaseFragment extends Fragment {

    /**
     * Used to scale the fragment in order to show cool expand animation
     * @param scaleX
     */
    public void scale(float scaleX)
    {
        getView().setScaleY(scaleX);
        getView().invalidate();
    }
}
