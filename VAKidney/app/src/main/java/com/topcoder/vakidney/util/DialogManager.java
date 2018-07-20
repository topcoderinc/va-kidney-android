package com.topcoder.vakidney.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.NumberPicker;


/**
 * Created by Abinash Neupane on 2/9/2018.
 * This class is used to show various dialogs
 */

public class DialogManager {


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

    public interface OnNumberPicked{
        void NumberPicked(int number);
    }

    /**
     * Shows a dialog with NumberPicker inside it
     * @param activity The activity this method was called from
     * @param message The message to be shown on the dialog
     * @param onNumberPicked The interface which defines the task when number is picked from numberpicker
     * @param minValue The minimum value for number picker
     * @param maxValue The maximum value for number picker
     * @param meanValue Initial Value for number picker
     */
    public static void showNumberPickerDialog(Activity activity, String message, final OnNumberPicked onNumberPicked, int minValue, int maxValue, int meanValue){
        final NumberPicker numberPicker=new NumberPicker(activity);
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setValue(meanValue);
        numberPicker.setWrapSelectorWheel(false);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        onNumberPicked.NumberPicked(numberPicker.getValue());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
                dialog.dismiss();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(message).setPositiveButton("OK", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener);
        AlertDialog alertDialog=builder.create();
        alertDialog.setView(numberPicker);
        alertDialog.show();
    }

    public interface OnOkClicked{
        void onclicked(String fieldString);
    }

    /**
     * Shows a dialog with Edittext inside it
     * @param activity   The activity this method was called from
     * @param message The message to be shown on the dialog
     * @param onOkClicked The interface which defines the task when ok button is clicked
     * @param defaultValue The default value for EditText
     */
    public static void showFieldDialog(Activity activity, String message, final OnOkClicked onOkClicked, String defaultValue){
        final EditText valueField=new EditText(activity);
        valueField.setText(defaultValue);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        onOkClicked.onclicked(valueField.getText().toString());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
                dialog.dismiss();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("OK", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener);
        AlertDialog alertDialog=builder.create();
        alertDialog.setView(valueField);
        alertDialog.show();
    }




}
