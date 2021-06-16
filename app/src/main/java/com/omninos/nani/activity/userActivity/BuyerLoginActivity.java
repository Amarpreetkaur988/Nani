package com.omninos.nani.activity.userActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.omninos.nani.R;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.myViewModel.LoginRegisterViewModel;
import com.omninos.nani.myViewModel.SocialLoginViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ConstantData;

import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class BuyerLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout buyerRegisterButton;
    private Button sign_in_button_login;

    private GoogleSignInClient mgoogleSignInClient;
    private EditText emailEditText, passwordEditText;
    private String email, passowrd;

    private LoginRegisterViewModel viewModel;
    private RelativeLayout googlelayout, facebooklayout;
    private int RC_SIGN_IN = 100;

    private String fbEmail, fbLastName, fbFirstName, fbId, userName, fbSocialUserserName, userStringEmail, socialId, loginType, userImage;
    private URL fbProfilePicture;
    private SocialLoginViewModel socialLoginViewModel;
    CallbackManager callbackManager;

    private TextView forgot_password_text_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login);

        CommonUtils.CheckService(BuyerLoginActivity.this);

        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);

        socialLoginViewModel = ViewModelProviders.of(this).get(SocialLoginViewModel.class);

        initView();
        SetUp();

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.omninos.nani", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }


    private void initView() {
        buyerRegisterButton = findViewById(R.id.buyerRegisterButton);
        sign_in_button_login = findViewById(R.id.sign_in_button_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        googlelayout = findViewById(R.id.googlelayout);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgoogleSignInClient = GoogleSignIn.getClient(this, gso);


        facebooklayout = findViewById(R.id.facebooklayout);
        FacebookSdk.sdkInitialize(getApplication());
        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();

        forgot_password_text_login=findViewById(R.id.forgot_password_text_login);


    }

    private void SetUp() {
        buyerRegisterButton.setOnClickListener(this);
        sign_in_button_login.setOnClickListener(this);
        googlelayout.setOnClickListener(this);
        facebooklayout.setOnClickListener(this);
        forgot_password_text_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buyerRegisterButton:
                startActivity(new Intent(BuyerLoginActivity.this, BuyerRegisterActivity.class));
                break;
            case R.id.sign_in_button_login:
                Login();
                break;
            case R.id.googlelayout:
                googlelogin();
                App.getSingleton().setLogintype("google");
                break;

            case R.id.facebooklayout:
                FBLogin();
                App.getSingleton().setLogintype("facebook");
                break;

            case R.id.forgot_password_text_login:
                startActivity(new Intent(BuyerLoginActivity.this,BuyerForgotActivity.class));
                break;

        }
    }

    private void FBLogin() {
        if (CommonUtils.isNetworkConnected(BuyerLoginActivity.this)) {
            LoginManager.getInstance().logInWithReadPermissions(BuyerLoginActivity.this, Arrays.asList("public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d("onSuccess: ", loginResult.getAccessToken().getToken());
                    getFacebookData(loginResult);
                }

                @Override
                public void onCancel() {
                    Toast.makeText(BuyerLoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(BuyerLoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(BuyerLoginActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void getFacebookData(LoginResult loginResult) {
        CommonUtils.showProgress(BuyerLoginActivity.this);
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                CommonUtils.dismissProgress();
                try {

                    if (object.has("id")) {
                        fbId = object.getString("id");
                        Log.e("LoginActivity", "id" + fbId);

                    }
                    //check permission first userName
                    if (object.has("first_name")) {
                        fbFirstName = object.getString("first_name");
                        Log.e("LoginActivity", "first_name" + fbFirstName);

                    }
                    //check permisson last userName
                    if (object.has("last_name")) {
                        fbLastName = object.getString("last_name");
                        Log.e("LoginActivity", "last_name" + fbLastName);
                    }
                    //check permisson email
                    if (object.has("email")) {
                        fbEmail = object.getString("email");
                        Log.e("LoginActivity", "email" + fbEmail);
                    }

                    fbSocialUserserName = fbFirstName + " " + fbLastName;

                    JSONObject jsonObject = new JSONObject(object.getString("picture"));
                    if (jsonObject != null) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        Log.e("Loginactivity", "json oject get picture" + dataObject);
                        fbProfilePicture = new URL("https://graph.facebook.com/" + fbId + "/picture?width=500&height=500");
                        Log.e("LoginActivity", "json object=>" + object.toString());
                    }
                    userName = fbSocialUserserName;
                    userStringEmail = fbEmail;
                    socialId = fbId;
                    loginType = "facebook";
                    App.getAppPreference().SaveString(ConstantData.USERNAME, userName);
                    if (fbProfilePicture != null) {
                        userImage = String.valueOf(fbProfilePicture);
                        App.getAppPreference().SaveString(ConstantData.USERIMAGE, userImage);
                    } else {
                        userImage = "";
                    }

                    App.getAppPreference().SaveString(ConstantData.LOGIN_TYPE, "Facebook");
                    CheckSocialId();
                } catch (Exception e) {

                }
            }
        });

        Bundle bundle = new Bundle();
        Log.e("LoginActivity", "bundle set");
        bundle.putString("fields", "id, first_name, last_name,email,picture,gender,location");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    private void googlelogin() {
        googleLogOut();
        Intent signInIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void Login() {
        email = emailEditText.getText().toString();
        passowrd = passwordEditText.getText().toString();
        if (email.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_in_button_login, "Enter email");
        } else if (passowrd.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_in_button_login, "Enter password");
        } else {
            viewModel.Login(BuyerLoginActivity.this, email, passowrd, "android", FirebaseInstanceId.getInstance().getToken(), "2").observe(BuyerLoginActivity.this, new Observer<LoginRegisterModelClass>() {
                @Override
                public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                    if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                        App.getAppPreference().SaveString(ConstantData.TOKEN, "1");
                        App.getAppPreference().SaveString(ConstantData.USER_TYPE,"2");
                        App.getAppPreference().saveUserDetails(loginRegisterModelClass);
                        startActivity(new Intent(BuyerLoginActivity.this, BuyerHomeActivity.class).putExtra("Type","0"));
                        finishAffinity();
                    } else {
                        CommonUtils.showSnackbarAlert(sign_in_button_login, loginRegisterModelClass.getMessage());
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult task = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            // Signed in successfolly, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("Account: ", acct.getDisplayName());
            Log.d("Account: ", acct.getId());
            Log.d("Account: ", acct.getEmail());
            socialId = acct.getId();
            userName = acct.getDisplayName();
            userStringEmail = acct.getEmail();
            if (acct.getPhotoUrl() != null) {
                userImage = String.valueOf(acct.getPhotoUrl());
            } else {
                userImage = "";
            }
            //  App.getAppPreference().SaveString(activity, ConstantData.LOGIN_TYPE, "Google");
            CheckSocialId();
        } else {
        }
    }

    private void CheckSocialId() {
        socialLoginViewModel.socialCheck(BuyerLoginActivity.this, socialId).observe(BuyerLoginActivity.this, new Observer<LoginRegisterModelClass>() {
            @Override
            public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                    App.getAppPreference().saveUserDetails(loginRegisterModelClass);
                    App.getAppPreference().SaveString(ConstantData.TOKEN, "1");
                    App.getAppPreference().SaveString(ConstantData.USER_TYPE,"2");
                    startActivity(new Intent(BuyerLoginActivity.this, BuyerHomeActivity.class).putExtra("Type","0"));
                } else {
                    App.getSingleton().setSocialId(socialId);
                    Intent intent = new Intent(BuyerLoginActivity.this, BuyerRegisterActivity.class);
                    intent.putExtra("name", userName);
                    intent.putExtra("SocailId", socialId);
                    intent.putExtra("email", userStringEmail);
                    intent.putExtra("image", userImage);
                    startActivity(intent);
                    Toast.makeText(BuyerLoginActivity.this, "Make Account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void googleLogOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mgoogleSignInClient.signOut();
    }


}
