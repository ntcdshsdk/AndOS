package com.osshare.andos.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.osshare.andos.MainActivity;
import com.osshare.andos.R;
import com.osshare.andos.base.abs.AbsActivity;
import com.osshare.andos.module.login.mvp.LoginPresenter;
import com.osshare.andos.module.login.mvp.LoginView;
import com.osshare.framework.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by apple on 16/10/6.
 */
public class LoginActivity extends AbsActivity implements LoginView {

    @Inject
    private LoginPresenter presenter;


    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvWrong;
    private EditText etAccount;
    private EditText etPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        immersiveHeaderContainer(R.id.layout_title_bar);

        btnLogin = (Button) findViewById(R.id.btn_login);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvWrong = (TextView) findViewById(R.id.tv_wrong);

        btnLogin.setOnClickListener(clickListener);
        tvRegister.setOnClickListener(clickListener);
        tvWrong.setOnClickListener(clickListener);


//        LoginModule.login(0, 15, new Observer<LoginTest>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.i(TAG, "onSubscribe" + d.isDisposed());
//            }
//
//            @Override
//            public void onNext(LoginTest value) {
//                Log.i(TAG, "onNext==>" + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//                Log.i(TAG, "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete");
//            }
//        });

    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    startActivity(new Intent(LoginActivity.this, IjkPlayerActivity.class));
                    break;
                case R.id.tv_register:
                    break;
                case R.id.tv_wrong:
                    break;
                default:
                    break;
            }
        }
    };


}
