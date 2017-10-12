package app.innova.sunkakam.saravut.easyads.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

import app.innova.sunkakam.saravut.easyads.MainActivity;
import app.innova.sunkakam.saravut.easyads.R;
import app.innova.sunkakam.saravut.easyads.utility.MyAlert;
import app.innova.sunkakam.saravut.easyads.utility.Myconstant;

/**
 * Created by Mee R&D on 10/10/2560.
 */

public class RegisterFragment extends Fragment {

    //Explict
    private String nameString, userString, passString;
    private Uri uri;
    private ImageView imageView;
    private boolean aBoolean = true;
    private String tag = "11octV1";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;


    } // onCreateView


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // Toolbar_controller

        toolbar_controller();

        //Humen controller

        humenController();


    }//main method


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == getActivity().RESULT_OK) {




            Log.d(tag, "Result OK");

            aBoolean = false;

            try {
                uri = data.getData();
                Bitmap bitmap = BitmapFactory
                        .decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {


                Log.d(tag, "e ==> " + e.toString());

            }

        }//if


    }//onactivity

    private void humenController() {
        imageView = getView().findViewById(R.id.imvavanger);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "เลือกแอป"), 8);


            }//onclick
        });

    }

    private void toolbar_controller() {

        //config Toolbat
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);


        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.new_register));

        //Back controller
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().popBackStack();

            }
        });
        //saveController
        ImageView imageView = getView().findViewById(R.id.imvSave);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get Value From EditText
                EditText nameEditext = getView().findViewById(R.id.edtName);
                EditText passEditText = getView().findViewById(R.id.edtPassword);
                EditText userEditText = getView().findViewById(R.id.edtuser);

                //change Data type


                nameString = nameEditext.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passString = passEditText.getText().toString().trim();


                //check Space
                if (nameString.equals("") || userString.equals("") || passString.equals("")) {

                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("ข้อมูลของท่านไม่สมบูรณ์", "กรุณาตรวจสอบข้อมูลนะคะ");


                } else if (aBoolean) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("no Image", "ใส่รูปสิบักหำ");
                } else {

                    upLoadValueToSaver();
                }


            }//onclick
        });


    }

    private void upLoadValueToSaver() {

        //Finf Path Of Image
        String strPathImage = "";

        String[] strings = new String[]{MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().getContentResolver().query(uri, strings, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            strPathImage = cursor.getString(index);
        } else {
            strPathImage = uri.getPath();
        }


        Log.d(tag, "PATH OF IMAGE ==>"+ strPathImage);

        //Uploadfiletosaver

        try {


//            conected potocal FTP

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            SimpleFTP simpleFTP = new SimpleFTP();
            Myconstant myconstant = new Myconstant();
            simpleFTP.connect(
                    myconstant.getHostSting(),
                    myconstant.getPortAnInt(),
                    myconstant.getUserString(),
                    myconstant.getPasswordString()
            );
            simpleFTP.bin();
            simpleFTP.cwd("ImageMeeRD");
            simpleFTP.stor(new File(strPathImage));
            simpleFTP.disconnect();
            //UpdateonmySQL

        } catch (Exception e) {
            Log.d(tag, "e upload ==>" + e.toString());
        }

    }//UPload


} // Main Class
