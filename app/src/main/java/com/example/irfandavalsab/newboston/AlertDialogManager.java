package com.example.irfandavalsab.newboston;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Irfandavalsab on 3/9/2015.
 */
public class AlertDialogManager {
    public void showAlertDialog(Context context, String title, String message, Boolean status){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        //Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            //Setting Alert Dialog Icon
            //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

            //Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            // Showing Alert Message
            alertDialog.show();
    }
}
