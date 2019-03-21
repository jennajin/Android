package android.comp3074.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private EditText email, password;
    private CheckBox checkBox;
    private UserDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("Login","onCreate");

        db = new UserDatabaseHelper(this);
        email = (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        checkBox = (CheckBox)findViewById(R.id.ckRemember);

        email.setError(null);
        password.setError(null);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        checkSharedPreferences();

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the checkbox preferences
                if(checkBox.isChecked()){
                    //set a checkbox when the application starts
                    editor.putString(getString(R.string.ckRemember), "True");
                    editor.commit();

                    //save the name
                    String mail = email.getText().toString();
                    editor.putString(getString(R.string.email), mail);
                    editor.commit();

                    //save password
                    String pw = password.getText().toString();
                    editor.putString(getString(R.string.etPassword), pw);
                    editor.commit();
                } else {
                    //set a checkbox to false
                    editor.putString(getString(R.string.ckRemember), "False");
                    editor.commit();

                    //save the name as empty
                    editor.putString(getString(R.string.email), "");
                    editor.commit();

                    //save the password as empty
                    editor.putString(getString(R.string.etPassword), "");
                    editor.commit();

                }

                // Validate whether username and password are correct
                validate(email.getText().toString(), password.getText().toString());
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    // Check validation
    private void validate(String mail, String pw){
        if(mail.equals("") || pw.equals("")){
            if(mail.equals(""))
                email.setError("Email is required");
            if(pw.equals(""))
                password.setError("Password is required");
        } else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Please enter a valid email address");
        } else {
            if(db.checkUser(mail, pw)){
                email.setError(null);
                password.setError(null);
                openDashboard(mail);
            } else {
                email.setError("Email or password is incorrect");
                password.setError("Email or password is incorrect");
            }
        }
    }

    // Go to main page if email and password are correct
    public void openDashboard(String mail){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("EMAIL", mail);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Login","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Login","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Login","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Login","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Login","onDestroy");
    }

    // Check the shared preferences and set them
    protected void checkSharedPreferences(){
        String rememberMe = preferences.getString(getString(R.string.ckRemember), "False");
        String mail = preferences.getString(getString(R.string.email), "");
        String pw = preferences.getString(getString(R.string.etPassword), "");

        email.setText(mail);
        password.setText(pw);

        if(rememberMe.equals("True")){
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }
}
