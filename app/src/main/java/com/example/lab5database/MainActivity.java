package com.example.lab5database;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.lab5database.Entities.UserRecord;
import com.example.lab5database.Infrastructure.UserDatabase;

public class MainActivity extends AppCompatActivity {

    private UserDatabase db;
    private TextView totalRecordsValue;
    private TextView foundRecordsValue;
    private SearchView searchRecords;
    private Button addRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = UserDatabase.getInstance(getApplicationContext());

        totalRecordsValue = findViewById(R.id.TotalRecordsValueText);
        foundRecordsValue = findViewById(R.id.FoundRecordsValueText);
        searchRecords = findViewById(R.id.SearchRecords);
        addRecordButton = findViewById(R.id.AddRecordButton);

        db.userDao().getAllRecords().observe(this, users -> {
            totalRecordsValue.setText(String.valueOf(users.size()));
        });


        searchRecords.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String name) {
                if (!name.equals(""))
                    findByNameOrSurname(db, name);
                else
                    foundRecordsValue.setText("0");
                return true;
            }
        });

        addRecordButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
            formActivityResultLauncher.launch(intent);
        });
    }


    private ActivityResultLauncher<Intent> formActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent resultData = result.getData();
                    if (resultData != null)
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                db.userDao().insert(new UserRecord(resultData.getStringExtra("NAME"),
                                        resultData.getStringExtra("SURNAME")));
                            }
                        });
                }
            });



    public void findByNameOrSurname(UserDatabase db, String name) {
        db.userDao().findByNameOrSurname(name).observe(this, users -> {
            foundRecordsValue.setText(String.valueOf(users.size()));
        });
    }
}