package android.comp3074.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private UserDatabaseHelper db;
    private EditText username, email, password, confirmPassword, phoneNo;
    private String mail, name, pw, confirmPw, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new UserDatabaseHelper(this);
        email = (EditText)findViewById(R.id.txtEmail);
        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText)findViewById(R.id.txtPassword);
        confirmPassword = (EditText)findViewById(R.id.txtConfirmPassword);
        phoneNo = (EditText)findViewById(R.id.txtPhoneNo);

        findViewById(R.id.btnSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = email.getText().toString();
                name = username.getText().toString();
                pw = password.getText().toString();
                confirmPw = confirmPassword.getText().toString();
                phone = phoneNo.getText().toString();

                if(name.equals("") || mail.equals("") || pw.equals("") || confirmPw.equals("") || phone.equals("")){
                    // Check empty fields
                    if(name.equals(""))
                        username.setError("Username is required");
                    if(mail.equals(""))
                        email.setError("Email is required");
                    if(pw.equals(""))
                        password.setError("Password is required");
                    if(confirmPw.equals(""))
                        confirmPassword.setError("Confirm Password is required");
                    if(phone.equals(""))
                        phoneNo.setError("Phone number is required");
                } else {
                    if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                        // Check email is valid
                        email.setError("Please enter a valid email address");
                    } else if(!db.checkEmail(mail)){
                        // Check email exists
                        email.setError("Email already exists");
                    } else if (!pw.equals(confirmPw)) {
                        // Check password and confirm password are same
                        confirmPassword.setError("Password does not match the confirm password");
                    } else if (!phone.matches("^[0-9]+$") || !(phone.length() == 10)) {
                        // Check phone number is valid
                        phoneNo.setError("Please enter a valid phone number");

                    } else {
                        // Registration
                        if (db.insertData(mail, name, pw, phone))
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            Toast.makeText(getApplicationContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }


}
