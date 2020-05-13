//package com.streamstech.droid.mshtb.activity;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.streamstech.droid.mshtb.R;
//import com.streamstech.droid.mshtb.data.State;
//import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
//import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
//import com.streamstech.droid.mshtb.data.persistent.Settings;
//import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
//import com.streamstech.droid.mshtb.io.CommonResponse;
//import com.streamstech.droid.mshtb.io.EndpointInterface;
//import com.streamstech.droid.mshtb.io.Paths;
//import com.streamstech.droid.mshtb.util.MSHTBApplication;
//import com.streamstech.droid.mshtb.util.UIUtil;
//
//import java.net.HttpURLConnection;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class Registration extends AppCompatActivity implements SweetAlertDialog.OnSweetClickListener {
//
//    @BindView(R.id.txtFirstName)
//    public EditText txtFirstName;
//    @BindView(R.id.txtLastName)
//    public EditText txtLastName;
//    @BindView(R.id.txtPhone)
//    public EditText txtPhone;
//    @BindView(R.id.btnRegister)
//    public Button btnRegister;
////    @BindView(R.id.btnLogin)
////    public Button btnLogin;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//        ButterKnife.bind(this);
//        txtPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.register || id == EditorInfo.IME_NULL) {
//                    attemptRegister();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        MSHTBApplication.getInstance().hideKeyboard(this);
//        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
//                .getSettingsDao();
//        Settings settings = settingsDao.queryBuilder()
//                .where(SettingsDao.Properties.Key.eq(DBColumnKeys.STATE))
//                .unique();
//        if (settings == null || State.UNDEFINED.name().equalsIgnoreCase(settings.getValue())){
//            return;
//        }else if (State.REGISTERED.name().equalsIgnoreCase(settings.getValue())){
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }else if (State.VERIFIED.name().equalsIgnoreCase(settings.getValue())){
//            startActivity(new Intent(this, NewMainActivity.class));
//            finish();
//        }
//    }
//
//    @OnClick(R.id.btnRegister)
//    public void onRegisterClicked() {
////        startActivity(new Intent(Registration.this, NewMainActivity.class));
////        startActivity(new Intent(Registration.this, ImageCaptureActivity.class));
////        finish();
//        attemptRegister();
//    }
//
////    @OnClick(R.id.btnLogin)
////    public void onLoginClicked() {
////        startActivity(new Intent(this, LoginActivity.class));
////    }
//
//    @Override
//    public void onClick(SweetAlertDialog sweetAlertDialog) {
//        System.out.println("Sweet cancel clicked");
//    }
//
//    /**
//     * Attempts to sign in or register the account specified by the login form.
//     * If there are form errors (invalid email, missing fields, etc.), the
//     * errors are presented and no actual login attempt is made.
//     */
//    private void attemptRegister() {
//
//        txtFirstName.setError(null);
//        txtLastName.setError(null);
//        txtPhone.setError(null);
//
//        final String fname = txtFirstName.getText().toString();
//        final String lname = txtLastName.getText().toString();
//        final String phone = txtPhone.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        if (TextUtils.isEmpty(phone) || isMobilenoValid(phone)) {
//            txtPhone.setError(getString(R.string.error_invalid_phone));
//            focusView = txtPhone;
//            cancel = true;
//        }
//
//        if (TextUtils.isEmpty(fname)) {
//            txtFirstName.setError(getString(R.string.error_field_required));
//            focusView = txtFirstName;
//            cancel = true;
//        }
//        if (TextUtils.isEmpty(lname)) {
//            txtLastName.setError(getString(R.string.error_field_required));
//            focusView = txtLastName;
//            cancel = true;
//        }
//
//        if (cancel) {
//            focusView.requestFocus();
//        } else {
//            UIUtil.showSweetProgress(this, "Registering...", false, this);
//            MSHTBApplication.getInstance().hideKeyboard(this);
//
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(Paths.SERVER_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            EndpointInterface service = client.create(EndpointInterface.class);
//            retrofit2.Call<CommonResponse> call = service.register(fname, lname, phone);
//
//            call.enqueue(new retrofit2.Callback<CommonResponse>() {
//                @Override
//                public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
//
//                    UIUtil.hideSweetProgress();
//                    if (response.isSuccessful() && response.code() != HttpURLConnection.HTTP_NO_CONTENT) {
//                        SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
//                                .getSettingsDao();
//                        settingsDao.save(new Settings(null, DBColumnKeys.FIRST_NAME, fname));
//                        settingsDao.save(new Settings(null, DBColumnKeys.LAST_NAME, lname));
//                        settingsDao.save(new Settings(null, DBColumnKeys.MOBILE_NO, phone));
//                        settingsDao.save(new Settings(null, DBColumnKeys.STATE, State.REGISTERED.name()));
//                        startActivity(new Intent(Registration.this, LoginActivity.class));
//                        finish();
//                        System.out.println("Registration successful");
//                    } else
//                        System.out.println("Cell id location not found");
//                }
//                @Override
//                public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
//                    UIUtil.hideSweetProgress();
//                    System.out.println(t.getMessage());
//                }
//            });
//        }
//
//    }
//
//    private boolean isMobilenoValid(String phone) {
//        return phone.length() < 11;
//    }
//
////    private void loadData(final String token) {
////        UIUtil.showSweetProgress(this, "Loading data...", false, this);
////        OkHttpClient client = new OkHttpClient.Builder()
////                .connectTimeout(90, TimeUnit.SECONDS)
////                .writeTimeout(90, TimeUnit.SECONDS)
////                .readTimeout(90, TimeUnit.SECONDS)
////                .build();
////        String jsonURL = Paths.SERVER_URL + Paths.API;
////        RequestBody formBody = new FormBody.Builder()
////                .add("token", token)
////                .add("action", "getAllData")
////                .build();
////        System.out.println("Load data Request: " + formBody.toString());
////
////        client.newCall(new Request.Builder().url(jsonURL).post(formBody).build()).enqueue(new Callback() {
////            @Override
////            public void onFailure(Call call, IOException e) {
////                UIUtil.hideSweetProgress();
////                e.printStackTrace();
////            }
////
////            @Override
////            public void onResponse(Call call, okhttp3.Response response) throws IOException {
////                UIUtil.hideSweetProgress();
////                if (response.isSuccessful()) {
////                    String strResponse = response.body().string();
////                    try {
////                        ServerResponse serverResponse = (ServerResponse) new Gson()
////                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
////                        if (serverResponse.isSuccess()) {
////
////                        }
//////                        Registration.this.startActivity(new Intent(Registration.this, NewMainActivity.class));
//////                        Registration.this.finish();
////                    } catch (Exception exp) {
////                        exp.printStackTrace();
////                    }
////                }
////            }
////        });
////    }
//}
//
