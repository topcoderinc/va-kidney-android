package com.topcoder.vakidney.fragments.welcome;

import android.support.v4.app.Fragment;

/**
 * Created by Abinash Neupane on 2/2/2018.
 * This fragment is used as a base fragment for scaling purpose
 */

public class WelcomeBaseFragment extends Fragment {

    /**
     * Used to scale the fragment in order to show cool expand animation
     *
     * @param scaleX
     */
    public void scale(float scaleX) {
        getView().setScaleY(scaleX);
        getView().invalidate();
    }
}
