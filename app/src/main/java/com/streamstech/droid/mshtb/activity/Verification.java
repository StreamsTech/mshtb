package com.streamstech.droid.mshtb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.streamstech.droid.mshtb.R;
import com.streamstech.droid.mshtb.data.State;
import com.streamstech.droid.mshtb.data.persistent.DBColumnKeys;
import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
import com.streamstech.droid.mshtb.data.persistent.Settings;
import com.streamstech.droid.mshtb.data.persistent.SettingsDao;
import com.streamstech.droid.mshtb.io.CommonResponse;
import com.streamstech.droid.mshtb.io.EndpointInterface;
import com.streamstech.droid.mshtb.io.Paths;
import com.streamstech.droid.mshtb.util.MSHTBApplication;
import com.streamstech.droid.mshtb.util.UIUtil;

import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Verification extends AppCompatActivity implements SweetAlertDialog.OnSweetClickListener  {

    @BindView(R.id.txtUserName)
    public EditText txtUserName;
    @BindView(R.id.txtPassword)
    public EditText txtPassword;
    @BindView(R.id.btnVerify)
    public Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        txtUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
                return false;
            }
        });

        MSHTBApplication.getInstance().hideKeyboard(this);
        String mobile = MSHTBApplication.getInstance().getSettings(DBColumnKeys.MOBILE_NO);
        txtUserName.setText(mobile);
//        List<Settings> lst = DatabaseManager.getInstance().getSession()
//                .getSettingsDao().queryBuilder()
//                .whereOr(SettingsDao.Properties.Key.eq(DBColumnKeys.USER_NAME),
//                        SettingsDao.Properties.Key.eq(DBColumnKeys.USER_PASSWORD),
//                        SettingsDao.Properties.Key.eq(DBColumnKeys.REMEMBER_ME))
//                .list();
    }

    @Override
    public void onClick(SweetAlertDialog sweetAlertDialog) {
        System.out.println("Sweet cancel clicked");
    }

    @OnClick(R.id.btnVerify)
    public void onVerifyClicked(){
        if (txtPassword.getText().toString().trim().length() < 4){
            UIUtil.showInfoDialog(this, SweetAlertDialog.WARNING_TYPE, "Information", "Invalid password");
            return;
        }

        if (!txtUserName.getText().toString().trim().equals("foysal") ||
                !txtPassword.getText().toString().trim().equals("1234")){
            UIUtil.showInfoDialog(this, SweetAlertDialog.ERROR_TYPE, "Error", "Invalid user name or password");
            return;
        }
        UIUtil.showSweetProgress(this, "Verifying...", false, this);
        MSHTBApplication.getInstance().hideKeyboard(this);

        Retrofit client = new Retrofit.Builder()
                .baseUrl(Paths.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EndpointInterface service = client.create(EndpointInterface.class);
        retrofit2.Call<CommonResponse> call = service.verify(txtUserName.getText().toString(), txtPassword.getText().toString());

        call.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {

                UIUtil.hideSweetProgress();
                startActivity(new Intent(Verification.this, NewMainActivity.class));
                finish();
//                if (response.isSuccessful() && response.code() != HttpURLConnection.HTTP_NO_CONTENT) {
//                    SettingsDao settingsDao = DatabaseManager.getInstance().getSession()
//                            .getSettingsDao();
//                    Settings settings = settingsDao.queryBuilder()
//                            .where(SettingsDao.Properties.Key.eq(DBColumnKeys.STATE))
//                            .unique();
//                    settings.setValue(State.VERIFIED.name());
//                    settingsDao.update(settings);
//
//                    settings = settingsDao.queryBuilder()
//                            .where(SettingsDao.Properties.Key.eq(DBColumnKeys.OTP))
//                            .unique();
//                    if (settings == null){
//                        settingsDao.save(new Settings(null, DBColumnKeys.OTP, txtPassword.getText().toString()));
//                    }else {
//                        settings.setValue(txtPassword.getText().toString());
//                        settingsDao.update(settings);
//                    }
//                    startActivity(new Intent(Verification.this, NewMainActivity.class));
//                    finish();
//                } else
//                    UIUtil.showInfoDialog(Verification.this, SweetAlertDialog.ERROR_TYPE, "Verification", response.body().getMessage());
            }
            @Override
            public void onFailure(retrofit2.Call<CommonResponse> call, Throwable t) {
                UIUtil.hideSweetProgress();
                System.out.println(t.getMessage());
                startActivity(new Intent(Verification.this, NewMainActivity.class));
                finish();
            }
        });
    }

//    private void loadData(final String token){
//        UIUtil.showSweetProgress(this, "Loading data...", false, this);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(90, TimeUnit.SECONDS)
//                .writeTimeout(90, TimeUnit.SECONDS)
//                .readTimeout(90, TimeUnit.SECONDS)
//                .build();
//        String jsonURL = Paths.SERVER_URL + Paths.API;
//        RequestBody formBody = new FormBody.Builder()
//                .add("token", token)
//                .add("action", "getAllData")
//                .build();
//        System.out.println("Load data Request: " + formBody.toString());
//
//        client.newCall(new Request.Builder().url(jsonURL).post(formBody).build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                UIUtil.hideSweetProgress();
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                UIUtil.hideSweetProgress();
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                        if (serverResponse.isSuccess()) {
//
//                        }
////                        Registration.this.startActivity(new Intent(Registration.this, NewMainActivity.class));
////                        Registration.this.finish();
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
}

