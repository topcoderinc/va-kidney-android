package com.topcoder.vakidney.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.topcoder.vakidney.util.ViewUtil;

/**
 * This is the base popup class.
 * All popup classes in this project should extend this base class.
 */

public abstract class BasePopup extends PopupWindow {

    protected final Activity mContext;
    private final LayoutInflater mInflater;
    private View mParent;

    // If the popup window init from other popup window,
    // Initiator popup should kept in this variabel.
    // This is useful to dismiss all popup in the stack
    private BasePopup mInitiatorPopup;

    public BasePopup(Activity context, int layoutResource) {
        super();
        mContext = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = mInflater.inflate(layoutResource, null);

        this.setContentView(layout);
        this.setWidth(getWidth());
        this.setHeight(getHeight());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable());
    }

    public void showAt(View root) {
        mParent = root;
        showAtLocation(root, getGravity(), getLocationX(), getLocationY());
        ViewUtil.dimBehind(this);
    }

    public View getParent() {
        return mParent;
    }

    public BasePopup getInitiatorPopup() {
        return mInitiatorPopup;
    }

    public void setInitiatorPopup(BasePopup mInitiatorPopup) {
        this.mInitiatorPopup = mInitiatorPopup;
    }

    public abstract int getGravity();
    public abstract int getWidth();
    public abstract int getHeight();

    public abstract int getLocationX();
    public abstract int getLocationY();

}
