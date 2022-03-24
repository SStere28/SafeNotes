package com.SSI.SafeNotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String PREFS_NAME = "SafeNotes";
    private static final String PASSWORD = "password";
    private static final String FIRST_TIME_ACCOUNT = "first_time_account";
    private Button login;
    private EditText password, reEnterPassword;
    int k=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        login = (Button) findViewById(R.id.Login);
        password = (EditText) findViewById(R.id.passwordValueID);
        reEnterPassword = (EditText) findViewById(R.id.reEnterPasswordValueID);
        login.setOnClickListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        if (sharedPreferences.getBoolean(FIRST_TIME_ACCOUNT, true)) {
            reEnterPassword.setVisibility(View.VISIBLE);
            login.setText("  Sign Up  ");
            k=1;
        }

        SharedPreferences prefs = getSharedPreferences("SafeN", MODE_PRIVATE);
        if (!prefs.getBoolean("isFirstTime", false)) {
            addShortcut();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstTime", true);
            editor.commit();
        }

    }

    private void addShortcut() {

        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, this.getResources().getString(R.string.app_name));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                R.mipmap.ic_launcher));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }



    private void firstTimeAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!sharedPreferences.getBoolean(FIRST_TIME_ACCOUNT, false)) {
            if (!password.getText().toString().isEmpty()) {
                editor.putString(PASSWORD, password.getText().toString());
            }
            editor.putBoolean(FIRST_TIME_ACCOUNT, false);
            editor.commit();
            Toast.makeText(this, "Account created succesfully!", Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.Login) {
            if(k==0){
                SharedPreferences preferences = getSharedPreferences("Account", MODE_PRIVATE);
                String pass = preferences.getString(PASSWORD, "");

                if (((!password.getText().toString().isEmpty() )
                        && pass.equals(password.getText().toString())) ) {

                    Intent n = new Intent(getBaseContext(), Meniu.class);
                    startActivity(n);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_LONG).show();
                }
            }else{
                if ((!password.getText().toString().isEmpty() )
                        && (!reEnterPassword.getText().toString().isEmpty())) {
                    if(password.getText().toString().length()<5) {
                        String firstPass = password.getText().toString();
                        String reEnterPass = reEnterPassword.getText().toString();
                        if (firstPass.equals(reEnterPass)) {

                            firstTimeAccount();
                            Intent n = new Intent(getBaseContext(), Meniu.class);
                            startActivity(n);
                        } else {
                            Toast.makeText(this, "Passwords doesn't match !", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(this, "Password too short !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Insert Password !", Toast.LENGTH_LONG).show();
                }
            }


        }
    }

}
