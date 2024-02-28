package com.example.diplomenproekt.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplomenproekt.R;
import com.example.diplomenproekt.databinding.FragmentLoginBinding;

import com.example.diplomenproekt.authentication.EmailAuthActivity;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
//    private EmailAuthActivity authActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button login_button= (Button)root.findViewById(R.id.btnLogin);
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText email = (EditText)root.findViewById(R.id.etEmail);
                EditText password = (EditText)root.findViewById(R.id.etPassword);
                Log.d("EMAIL", email.getText().toString());
                Log.d("PASS", password.getText().toString());
                ((EmailAuthActivity) getActivity()).signIn(email.getText().toString(), password.getText().toString());
            }
        });

//        final TextView textView = binding.textLogin;
//        loginViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
