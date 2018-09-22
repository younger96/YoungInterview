package com.example.chenqiuyang.younginterview.act_frag.mvvm.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chenqiuyang.younginterview.R;
import com.example.chenqiuyang.younginterview.act_frag.mvvm.viewmodel.LoginViewModel;
import com.example.chenqiuyang.younginterview.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewModel = new LoginViewModel(this);
        binding.setViewmodel(viewModel);
    }
}
