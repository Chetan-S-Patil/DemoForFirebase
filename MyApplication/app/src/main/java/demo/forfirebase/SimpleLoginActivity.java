package demo.forfirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by chetanpatil on 28/07/17.
 */

public class SimpleLoginActivity extends AppCompatActivity {

    private static final String TAG = "SimpleLoginActivity";
    private TextInputLayout emailWrapper, passwordWrapper;
    private FirebaseAuth mFbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);


        init();

    }

    private void init() {

        emailWrapper = (TextInputLayout) findViewById(R.id.textInputLayout1);
        passwordWrapper = (TextInputLayout) findViewById(R.id.textInputLayout);

        mFbAuth = FirebaseAuth.getInstance();


        if (mFbAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Loged in", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Non-Loged in", Toast.LENGTH_SHORT).show();
        }

    }

    public void onSimpleRegisterClick(View v) {

        Log.v(TAG, "onSimpleRegisterClick");

        String email = emailWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();

        Log.v(TAG, "onSimpleRegisterClick >> "+email+" >> "+password);

        mFbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.v(TAG, "task: "+task.toString());
                Log.v(TAG, "task: "+task.isSuccessful());
                Log.v(TAG, "task: "+task.getException());

                if (task.isSuccessful()) {



                    Toast.makeText(SimpleLoginActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                    Log.v(TAG, "User: "+mFbAuth.getCurrentUser());

                } else {
                    Toast.makeText(SimpleLoginActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void onSimpleLoginClick(View v) {

        Log.v(TAG, "onSimpleLoginClick");

        String email = emailWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();

        Log.v(TAG, "onSimpleRegisterClick >> "+email+" >> "+password);

        mFbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.v(TAG, "task: "+task.toString());
                Log.v(TAG, "task: "+task.isSuccessful());
                Log.v(TAG, "task: "+task.getException());

                if (task.isSuccessful()) {

                    Toast.makeText(SimpleLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Log.v(TAG, "User: "+mFbAuth.getCurrentUser());

                } else {
                    Toast.makeText(SimpleLoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
