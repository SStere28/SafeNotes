package com.SSI.SafeNotes;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {


    private EditText pass, rpass;
    private Button save, ftype, fsize;
    private CheckBox showp;
    private CheckBox c0, c1, c2, c3, c4, c5, c6, c7, c8;
    private TextView type;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    String c = "", cc = "";
    int s = 0;
    private static final String PASSWORD = "password";
    private static final String PREFS_NAME = "SafeNotes";
    private int note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            note = extras.getInt("set");
        }

        pass = (EditText) findViewById(R.id.editText1);
        rpass = (EditText) findViewById(R.id.editText2);
        save = (Button) findViewById(R.id.save);
        showp = (CheckBox) findViewById(R.id.checkBox);
        ftype = (Button) findViewById(R.id.button2);
        fsize = (Button) findViewById(R.id.button);
        type = (TextView) findViewById(R.id.textView3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);


        showp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s == 0) {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                    rpass.setInputType(InputType.TYPE_CLASS_TEXT);
                    pass.setSelection(pass.getText().length());
                    rpass.setSelection(rpass.getText().length());
                    s = 1;
                } else {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    rpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setSelection(pass.getText().length());
                    rpass.setSelection(rpass.getText().length());
                    s = 0;
                }
            }
        });

        if (sharedPreferences.getString("Text", "c1").equals("c1")) {
            type.setTextSize(14);
        } else if (sharedPreferences.getString("Text", "c1").equals("c2")) {
            type.setTextSize(20);
        } else if (sharedPreferences.getString("Text", "c1").equals("c3")) {
            type.setTextSize(25);
        } else if (sharedPreferences.getString("Text", "c1").equals("c4")) {
            type.setTextSize(30);
        }


        if (sharedPreferences.getString("Type", "c1").equals("c1")) {
            type.setTypeface(Typeface.SERIF);
        } else if (sharedPreferences.getString("Type", "c1").equals("c2")) {
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Garamond.ttf");
            type.setTypeface(custom_font);
        } else if (sharedPreferences.getString("Type", "c1").equals("c3")) {
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Calibri.ttf");
            type.setTypeface(custom_font);
        } else if (sharedPreferences.getString("Type", "c1").equals("c4")) {
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Georgia.ttf");

            type.setTypeface(custom_font);
        } else if (sharedPreferences.getString("Type", "c1").equals("c0")) {
            type.setTypeface(Typeface.DEFAULT);
        }


        ftype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(Settings.this);
                View promptsView = li.inflate(R.layout.type, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Settings.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                TextView titlet = new TextView(getApplicationContext());
                // You Can Customise your Title here
                titlet.setText("Font type");
                titlet.setTextColor(Color.BLACK);
                titlet.setTextSize(20);
                titlet.setPadding(10, 10, 10, 10);
                titlet.setGravity(Gravity.CENTER);

                alertDialogBuilder.setCustomTitle(titlet);
                c0 = (CheckBox) promptsView.findViewById(R.id.c0);
                c1 = (CheckBox) promptsView.findViewById(R.id.c1);
                c2 = (CheckBox) promptsView.findViewById(R.id.c2);
                c3 = (CheckBox) promptsView.findViewById(R.id.c3);
                c4 = (CheckBox) promptsView.findViewById(R.id.c4);

                if (sharedPreferences.getString("Type", "c1").equals("c1")) {
                    c1.setChecked(true);
                    c = "c1";
                } else if (sharedPreferences.getString("Type", "c1").equals("c2")) {
                    c2.setChecked(true);
                    c = "c2";
                } else if (sharedPreferences.getString("Type", "c1").equals("c3")) {
                    c3.setChecked(true);
                    c = "c3";
                } else if (sharedPreferences.getString("Type", "c1").equals("c4")) {
                    c4.setChecked(true);
                    c = "c4";
                } else if (sharedPreferences.getString("Type", "c1").equals("c0")) {
                    c0.setChecked(true);
                    c = "c0";
                }


                LinearLayout a0 = (LinearLayout) promptsView.findViewById(R.id.a0);
                a0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        c0.setChecked(true);
                        c1.setChecked(false);
                        c2.setChecked(false);
                        c3.setChecked(false);
                        c4.setChecked(false);
                        c = "c0";
                    }
                });

                LinearLayout a1 = (LinearLayout) promptsView.findViewById(R.id.a1);
                a1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c0.setChecked(false);
                        c1.setChecked(true);
                        c2.setChecked(false);
                        c3.setChecked(false);
                        c4.setChecked(false);
                        c = "c1";
                    }
                });

                LinearLayout a2 = (LinearLayout) promptsView.findViewById(R.id.a2);
                a2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c0.setChecked(false);
                        c1.setChecked(false);
                        c2.setChecked(true);
                        c3.setChecked(false);
                        c4.setChecked(false);
                        c = "c2";
                    }
                });

                LinearLayout a3 = (LinearLayout) promptsView.findViewById(R.id.a3);
                a3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c0.setChecked(false);
                        c1.setChecked(false);
                        c2.setChecked(false);
                        c3.setChecked(true);
                        c4.setChecked(false);
                        c = "c3";
                    }
                });

                LinearLayout a4 = (LinearLayout) promptsView.findViewById(R.id.a4);
                a4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c0.setChecked(false);
                        c1.setChecked(false);
                        c2.setChecked(false);
                        c3.setChecked(false);
                        c4.setChecked(true);
                        c = "c4";
                    }
                });
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                        if (c.equals("c0")) {
                                            type.setTypeface(Typeface.DEFAULT);
                                        } else if (c.equals("c1")) {
                                            type.setTypeface(Typeface.SERIF);
                                        } else if (c.equals("c2")) {
                                            Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Garamond.ttf");
                                            type.setTypeface(custom_font);
                                        } else if (c.equals("c3")) {
                                            Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Calibri.ttf");
                                            type.setTypeface(custom_font);
                                        } else if (c.equals("c4")) {
                                            Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Georgia.ttf");
                                            type.setTypeface(custom_font);
                                        }
                                        editor.putString("Type", c);
                                        editor.commit();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        fsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(Settings.this);
                View promptsView = li.inflate(R.layout.size, null);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Settings.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                TextView titlet = new TextView(getApplicationContext());
                // You Can Customise your Title here
                titlet.setText("Font size");
                titlet.setTextColor(Color.BLACK);
                titlet.setTextSize(20);
                titlet.setPadding(10, 10, 10, 10);
                titlet.setGravity(Gravity.CENTER);

                alertDialogBuilder.setCustomTitle(titlet);

                c5 = (CheckBox) promptsView.findViewById(R.id.c1);
                c6 = (CheckBox) promptsView.findViewById(R.id.c2);
                c7 = (CheckBox) promptsView.findViewById(R.id.c3);
                c8 = (CheckBox) promptsView.findViewById(R.id.c4);

                if (sharedPreferences.getString("Text", "c1").equals("c1")) {
                    c5.setChecked(true);
                    cc = "c1";
                } else if (sharedPreferences.getString("Text", "c1").equals("c2")) {
                    c6.setChecked(true);
                    cc = "c2";
                } else if (sharedPreferences.getString("Text", "c1").equals("c3")) {
                    c7.setChecked(true);
                    cc = "c3";
                } else if (sharedPreferences.getString("Text", "c1").equals("c4")) {
                    c8.setChecked(true);
                    cc = "c4";
                }

                LinearLayout a1 = (LinearLayout) promptsView.findViewById(R.id.a1);
                a1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c5.setChecked(true);
                        c6.setChecked(false);
                        c7.setChecked(false);
                        c8.setChecked(false);
                        cc = "c1";
                    }
                });

                LinearLayout a2 = (LinearLayout) promptsView.findViewById(R.id.a2);
                a2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c5.setChecked(false);
                        c6.setChecked(true);
                        c7.setChecked(false);
                        c8.setChecked(false);
                        cc = "c2";
                    }
                });

                LinearLayout a3 = (LinearLayout) promptsView.findViewById(R.id.a3);
                a3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c5.setChecked(false);
                        c6.setChecked(false);
                        c7.setChecked(true);
                        c8.setChecked(false);
                        cc = "c3";
                    }
                });

                LinearLayout a4 = (LinearLayout) promptsView.findViewById(R.id.a4);
                a4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c5.setChecked(false);
                        c6.setChecked(false);
                        c7.setChecked(false);
                        c8.setChecked(true);
                        cc = "c4";
                    }
                });
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                        if (cc.equals("c1")) {
                                            type.setTextSize(14);
                                        } else if (cc.equals("c2")) {
                                            type.setTextSize(20);
                                        } else if (cc.equals("c3")) {
                                            type.setTextSize(25);
                                        } else if (cc.equals("c4")) {
                                            type.setTextSize(30);
                                        }
                                        editor.putString("Text", cc);
                                        editor.commit();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pass.getText().toString().isEmpty()) {
                    if (pass.getText().toString().equals(rpass.getText().toString())) {
                        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PASSWORD, pass.getText().toString());
                        editor.commit();

                        pass.setText("");
                        rpass.setText("");

                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords doesn't match !", Toast.LENGTH_LONG).show();
                    }
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
        if (note == 1) {

            Intent intent = new Intent(this, Meniu.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            return;

        } else if (note == 2) {
            Intent intent = new Intent(this, ViewNotes.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            return;
        }
    }

}
