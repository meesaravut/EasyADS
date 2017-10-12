package app.innova.sunkakam.saravut.easyads.utility;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.speech.tts.Voice;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by Mee R&D on 12/10/2560.
 */

public class UploadValueToSaver extends AsyncTask<String, Voice, String> {

    private Context context;

    public UploadValueToSaver(Context context) {


        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", params[0])
                    .add("User", params[1])
                    .add("Password", params[2])
                    .add("Image", params[3])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[4]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();


        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }
}//main
