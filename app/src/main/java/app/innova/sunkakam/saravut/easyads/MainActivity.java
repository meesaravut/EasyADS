package app.innova.sunkakam.saravut.easyads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.innova.sunkakam.saravut.easyads.fragment.Mainfragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFragtMain, new Mainfragment()).commit();




        }
    }//main method


//    Add Fragment to activity


}// main class
