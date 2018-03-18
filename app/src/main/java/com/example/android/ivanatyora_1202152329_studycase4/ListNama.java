package com.example.android.ivanatyora_1202152329_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListNama extends AppCompatActivity {

    //Deklarasi atribut yang akan digunakan
    private ListView lv_ListMahasiswa;
    private Button mStartAsyncTask;
    private ProgressBar mProgressBar;

    //data array yang akan ditampilkan
    private String[] mahasiswaArray = {
            "Ivana", "Fadia", "Paundra Dewa", "Kylie", "Kendall", "Arrivan", "Zarginda", "Alyssa", "Lana Del Rey"
            , "Radit", "Jani"};

    private AddItemToListView mAddItemToListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama);

        //mengambil atribut yang akan digunakan berdasarkan id di layout
        lv_ListMahasiswa = findViewById(R.id.listMahasiswa);
        mProgressBar = findViewById(R.id.progressBar);
        mStartAsyncTask = findViewById(R.id.buttonMulai);

        lv_ListMahasiswa.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));

        mStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //process adapter with asyncTask
                mAddItemToListView = new AddItemToListView();
                mAddItemToListView.execute();
            }
        });
    }

    public class AddItemToListView extends AsyncTask<Void, String, Void> {

        private ArrayAdapter<String> mAdapter;
        private int counter = 1;
        ProgressDialog mProgressDialog = new ProgressDialog(ListNama.this);

        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) lv_ListMahasiswa.getAdapter(); //casting suggestion
            //isi progress dialog
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data"); //Loading data mengambil list nama dari array
            mProgressDialog.setCancelable(false); //cancel loading
            mProgressDialog.setMessage("Please wait....");
            mProgressDialog.setProgress(0);
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAddItemToListView.cancel(true);
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show(); //menampilkan hasil loading
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (String item : mahasiswaArray) { //proses mengambil list nama
                publishProgress(item);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (isCancelled()) { //kondisi jika proses di cancel
                    mAddItemToListView.cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            Integer current_status = (int) ((counter / (float) mahasiswaArray.length) * 100);
            mProgressBar.setProgress(current_status);

            //set progress only working for horizontal loading
            mProgressDialog.setProgress(current_status);

            //set message will not working when using horizontal loading
            mProgressDialog.setMessage(String.valueOf(current_status + "%"));
            counter++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //hide progreebar
            mProgressBar.setVisibility(View.GONE);

            //remove progress dialog
            mProgressDialog.dismiss();
            lv_ListMahasiswa.setVisibility(View.VISIBLE);
        }
    }
}
