package demo.forfirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

/**
 * Created by chetanpatil on 01/08/17.
 */

public class MobileLoginActivity extends AppCompatActivity {

    private static final String TAG = "MobileLoginActivity";

    private TextInputLayout mobileNoWrapper;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;
    private String mVerificationId = "";
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    private FirebaseAuth mFbAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_login);

        mobileNoWrapper = (TextInputLayout) findViewById(R.id.mobileNoWrapper);
        mFbAuth = FirebaseAuth.getInstance();
        verificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Log.v(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.v(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                e.printStackTrace();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mForceResendingToken = forceResendingToken;
            }
        };

    }

    public void onVerifyNoClick(View view){

        String mobileNo = mobileNoWrapper.getEditText().getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNo, 120, TimeUnit.SECONDS, this, verificationStateChangedCallbacks);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mFbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v(TAG, "task: "+task.toString());
                        Log.v(TAG, "task: "+task.isSuccessful());
                        Log.v(TAG, "task: "+task.getException());

                        if (task.isSuccessful()) {



                            Toast.makeText(MobileLoginActivity.this, "Mobile Register Successful", Toast.LENGTH_SHORT).show();

                            Log.v(TAG, "User: "+mFbAuth.getCurrentUser());

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                            Toast.makeText(MobileLoginActivity.this, "Mobile Register Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
