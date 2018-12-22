package com.mrb.keepitgreen.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;
import com.mrb.keepitgreen.R;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_ACCOUNT_KIT = 1111;

    private int networkState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        networkState = getIntent().getIntExtra("networkState", 0);

        loginButton = findViewById(R.id.sign_in_button);
        loginButton.animate()
                .alpha(1.0f)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .start();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountKitLogin();
            }
        });
    }

    private void accountKitLogin() {

        Intent intent = new Intent(LoginActivity.this,
                AccountKitActivity.class);

        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);

        // constructor with a background image imageAccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder;
        UIManager uiManager;

        uiManager = new SkinManager(
                SkinManager.Skin.CLASSIC,
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? getResources().getColor(R.color.colorAccent,null):getResources().getColor(R.color.colorAccent))
        );

//                // Skin is CLASSIC, CONTEMPORARY, or TRANSLUCENT
//                // Tint is WHITE or BLACK
//                // TintIntensity is a value between 55-85%
//
//                uiManager = new SkinManager(
//                        SkinManager.Skin.CLASSIC,
//                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? getResources().getColor(R.color.colorPrimary,null):getResources().getColor(R.color.colorPrimary)),
//                        R.drawable.bg_alter,
//                        SkinManager.Tint.BLACK,
//                        0.55
//                );

//                UIManager themeManager = new ThemeUIManager(R.style.LoginTheme);

        configurationBuilder.setUIManager(uiManager);

        //Automatic SMS Receive

        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, REQUEST_ACCOUNT_KIT);
    }

    @Override
    protected void onActivityResult(

            final int requestCode,
            final int resultCode,
            final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ACCOUNT_KIT) { // confirm that this response matches your request
            final AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if (loginResult.getError() != null) {

                Log.d("LoginError", loginResult.getError().getErrorType().getMessage());

                Toast.makeText(LoginActivity.this,
                        getString(R.string.login_error),
                        Toast.LENGTH_LONG).show();

            } else if (loginResult.wasCancelled()) {

                Log.d("LoginError", getString(R.string.login_cancelled));

                Toast.makeText(LoginActivity.this,
                        getString(R.string.login_cancelled),
                        Toast.LENGTH_LONG).show();

            } else {

                if (loginResult.getAccessToken() != null) {

                    enterMainApp(loginResult.getAccessToken().getAccountId(),
                            String.format("Success for old user: %s",
                                    loginResult.getAccessToken().getAccountId()));

                } else {

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(Account account) {

                            enterMainApp(account.getId(),
                                    String.format("Success for new user: %s",
                                            loginResult.getAuthorizationCode()));
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {

                            Log.e("AccountKitError", accountKitError.getErrorType().getMessage());

                            Toast.makeText(LoginActivity.this,
                                    getString(R.string.login_error),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Send User Id to API...
            }
        }
    }

    private void enterMainApp(String accountId, String format) {

        Log.d("LoginResult", format);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("accountId", accountId);
        intent.putExtra("networkState", networkState);
        startActivity(intent);
        finish();
    }

    private ImageButton loginButton;
}
