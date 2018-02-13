package com.topcoder.vakidney.Util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


/**
 * Created by abina on 2/9/2018.
 */

public class DialogManager {

    public interface DoTask{
        void perform();
    }

    public interface OnYesClicked{
        void YesClicked();
    }


    public interface OnNoClicked{
        void NoClicked();
    }

    /**
     * Shows Yes No Dialog with corresponding field
     * @param activity The activity it was originated from
     * @param message The message for the dialog
     * @param yesString Text for Yes Button in Dialog
     * @param noString Text for No Button in Dialog
     * @param onYesClicked interface for the event when Yes is Clicked
     * @param onNoClicked interface for the event when NO is Clicked
     */
    public static void showYesNoDialog(Activity activity, String message, String yesString, String noString, final OnYesClicked onYesClicked, final OnNoClicked onNoClicked){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if(onYesClicked!=null) {
                            onYesClicked.YesClicked();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        if(onNoClicked!=null) {
                            onNoClicked.NoClicked();
                        }
                        break;
                }
                dialog.dismiss();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton(yesString, dialogClickListener)
                .setNegativeButton(noString, dialogClickListener).show();
    }

    /**
     * Shows a dialog with corresponding field
     * @param activity Activity it was originated from
     * @param message message to show in the dialog
     * @param onYesClicked interface for the event when OK is Clicked
     */
    public static void showOkDialog(Activity activity, String message, final OnYesClicked onYesClicked){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if(onYesClicked!=null) {
                            onYesClicked.YesClicked();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
                dialog.dismiss();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("OK", dialogClickListener).show();
    }




}
