package demo.forfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by chetanpatil on 26/07/17.
 */

public class LoginActiviy extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_login);

        mContext = LoginActiviy.this;

        FirebaseAuth.getInstance().signOut();

    }

    public void onSimpleLoginClick(View view) {

        startActivity(new Intent(mContext, SimpleLoginActivity.class));

    }

    public void onSocialLoginClick(View view) {

        startActivity(new Intent(mContext, SocialLoginActivity.class));

    }

    public void onMobileLoginClick(View view) {

        startActivity(new Intent(mContext, MobileLoginActivity.class));

    }
}
