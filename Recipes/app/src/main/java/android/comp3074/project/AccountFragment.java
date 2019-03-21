package android.comp3074.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AccountFragment extends Fragment {
    private UserDatabaseHelper db;
    private EditText email, username, password, confirmPassword, phoneNo;
    private boolean validationCheck;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        email = (EditText)v.findViewById(R.id.txtMail);
        username = (EditText)v.findViewById(R.id.txtName);
        password = (EditText)v.findViewById(R.id.txtPw);
        confirmPassword = (EditText)v.findViewById(R.id.txtCpw);
        phoneNo = (EditText)v.findViewById(R.id.txtPhone);

        db = new UserDatabaseHelper(getActivity());
        validationCheck = true;

        // Get email from previous page
        String getMail = getActivity().getIntent().getExtras().getString("EMAIL");


        // Get user information from database
        Cursor cursor = db.getUser(getMail);

        if( cursor != null && cursor.moveToFirst() ){
            email.setText(cursor.getString(1));
            username.setText(cursor.getString(2));
            password.setText(cursor.getString(3));
            confirmPassword.setText(cursor.getString(3));
            phoneNo.setText(cursor.getString(4));
            cursor.close();
        }

        v.findViewById(R.id.btnEditAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String name = username.getText().toString();
                String pw = password.getText().toString();
                String confirmPw = confirmPassword.getText().toString();
                String phone = phoneNo.getText().toString();

                if(name.equals("") || pw.equals("") || confirmPw.equals("") || phone.equals("")){
                    validationCheck = false;
                    if(name.equals(""))
                        username.setError("Username is required");
                    if(pw.equals(""))
                        password.setError("Password is required");
                    if(confirmPw.equals(""))
                        confirmPassword.setError("Confirm Password is required");
                    if(phone.equals(""))
                        phoneNo.setError("Phone number is required");
                }
                else {
                    if (!pw.equals(confirmPw)) {
                        // Check password and confirm password are same
                        confirmPassword.setError("Password does not match the confirm password");
                    } else if (!phone.matches("^[0-9]+$")  || !(phone.length() == 10)) {
                        // Check phone number is valid
                        phoneNo.setError("Please enter a valid phone number");
                    } else {
                        if(db.editUser(mail, name, pw, phone))
                            // Edit the user information
                            Toast.makeText(getActivity().getApplicationContext(), "Edited successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return v;
    }
}
