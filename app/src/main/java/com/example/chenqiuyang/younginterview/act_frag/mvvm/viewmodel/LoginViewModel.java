package com.example.chenqiuyang.younginterview.act_frag.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.chenqiuyang.younginterview.act_frag.mvvm.model.UserBean;
import com.example.chenqiuyang.younginterview.act_frag.mvvm.view.MainActivity;

public class LoginViewModel implements ViewModel{
    private Context context;
    public ObservableField<String> loginMessage;
    public ObservableInt loginMessageVisibility;

    private String editTextUsernameValue = "";
    private String editTextPasswordValue = "";


    public LoginViewModel(Context context) {
        this.context = context;
        this.loginMessage = new ObservableField<>("");
        this.loginMessageVisibility = new ObservableInt(View.INVISIBLE);
    }


    public TextWatcher getUsernameUpdate(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextUsernameValue = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    //登陆，实际上这个方法是在layout文件中调用的
    public void loginAuthentication(View view) {
        if ( (editTextUsernameValue.equals("admin")) && (editTextPasswordValue.equals("123456"))) {
            loginMessage.set("");
            loginMessageVisibility.set(View.INVISIBLE);
            UserBean user = new UserBean(editTextUsernameValue, editTextPasswordValue);
            context.startActivity(MainActivity.newIntent(context, user));

        }else if ( (editTextUsernameValue.equals("")) || (editTextPasswordValue.equals(""))) {
            loginMessage.set("Username or Password can't be empty!");
            loginMessageVisibility.set(View.VISIBLE);

        } else {
            loginMessage.set("Username = admin \n Password = 123456");
            loginMessageVisibility.set(View.VISIBLE);
        }
    }

    public TextWatcher getPasswordUpdate(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextPasswordValue= s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    @Override
    public void destroy() {

    }
}
