package app.innova.sunkakam.saravut.easyads.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import app.innova.sunkakam.saravut.easyads.R;

/**
 * Created by Mee R&D on 11/10/2560.
 */

public class MyAlert {

    private Context context;

    public MyAlert(Context context) {
        this.context = context;
    }


    public void myDialog(String strTile,String strmassage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.bell);
        builder.setTitle(strTile);
        builder.setMessage(strmassage);
        builder.setPositiveButton("ปิดสิคะ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();


    }





}//main Class
