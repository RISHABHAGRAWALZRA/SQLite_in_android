package com.example.sqlite_in_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public ListView listvw;
    TextView textView;
    SimpleCursorAdapter adapter;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listvw=findViewById(R.id.listvw);
        textView=findViewById(R.id.textView2);

        DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask();
        databaseAsyncTask.execute();

        listvw.setAdapter(adapter);
        listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cursor.close();
        sqLiteDatabase.close();
    }

    private class DatabaseAsyncTask extends AsyncTask<Void,Void,String>{

        private SQLiteDatabaseHelper databaseHelper;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper=new SQLiteDatabaseHelper(MainActivity.this);
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                 sqLiteDatabase = databaseHelper.getReadableDatabase();
                //databaseHelper.insert(sqLiteDatabase,"students","sarah","sarah@gmail.com");
                //databaseHelper.delete(sqLiteDatabase,"students","sarah");
                //databaseHelper.updateName(sqLiteDatabase,"students","Bis","Tis");
//                Cursor cursor = sqLiteDatabase.query("students",null,
//                        null,null,null,null,"_ID");



               Cursor cursor = databaseHelper.read(sqLiteDatabase,"students");

//                String sqlStatment="SELECT * FROM students WHERE name=?";
//
//                Cursor cursor = sqLiteDatabase.rawQuery(sqlStatment,new String[]{"Tis"});

                String sqlStatment="SELECT * FROM students ";

//                 cursor = sqLiteDatabase.rawQuery(sqlStatment,null);


                String returnString="";
                if (cursor.moveToFirst()) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        for (int j = 0; j < cursor.getColumnCount(); j++) {
                            returnString += cursor.getColumnName(j) + " : " + cursor.getString(j) + "\n";
                        }
                        returnString += "**********\n";
                        cursor.moveToNext();
                    }

                }

                 adapter = new SimpleCursorAdapter(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[] {"name"},
                        new int[] {android.R.id.text1},
                        0
                );

                return returnString;

            }catch (SQLException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);
        }
    }
}
