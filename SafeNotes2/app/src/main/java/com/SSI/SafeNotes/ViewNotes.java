package com.SSI.SafeNotes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class ViewNotes extends AppCompatActivity {

    private EditText text;
    private DatabaseHandler db;
    private int id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        text = (EditText) findViewById(R.id.edit_story);
        db = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            text.setText(extras.getString("Text"));
            id = extras.getInt("ID");

            if (extras.getString("Size").equals("c1")) {
                text.setTextSize(14);
            } else if (extras.getString("Size").equals("c2")) {
                text.setTextSize(20);
            } else if (extras.getString("Size").equals("c3")) {
                text.setTextSize(25);
            } else if (extras.getString("Size").equals("c4")) {
                text.setTextSize(30);
            }


            if (extras.getString("Type").equals("c1")) {
                text.setTypeface(Typeface.SERIF);
            } else if (extras.getString("Type").equals("c2")) {
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Garamond.ttf");
                text.setTypeface(custom_font);
            } else if (extras.getString("Type").equals("c3")) {
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Calibri.ttf");
                text.setTypeface(custom_font);
            } else if (extras.getString("Type").equals("c4")) {
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Georgia.ttf");

                text.setTypeface(custom_font);
            } else if (extras.getString("Type").equals("c0")) {
                text.setTypeface(Typeface.DEFAULT);
            }

        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Note");
        setSupportActionBar(toolbar);



        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String Text = text.getText().toString();
                if (Text.length() != 0) {
                    int seconds = (int) (new Date().getTime() / 1000);

                    String Titlu = "";

                    if (Text.contains("\n")) {
                        Titlu = Text.substring(0, Text.indexOf("\n"));
                    } else {
                        Titlu = Text;
                    }
                    if (id != -1) {
                        Toast.makeText(getBaseContext(), "Note updated", Toast.LENGTH_LONG).show();
                        db.updateNote(new Note(id, Titlu, Text, seconds + ""));
                        onBackPressed();
                    } else {
                        Toast.makeText(getBaseContext(), "Note saved", Toast.LENGTH_LONG).show();
                        db.addNote(new Note(Titlu, Text, seconds + ""));
                       onBackPressed();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Insert text", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            item.setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        Intent intent = new Intent(this, Meniu.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return;

    }


}
