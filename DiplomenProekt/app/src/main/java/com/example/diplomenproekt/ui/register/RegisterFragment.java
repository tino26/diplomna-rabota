package com.example.diplomenproekt.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diplomenproekt.R;
import com.example.diplomenproekt.authentication.EmailAuthActivity;
import com.example.diplomenproekt.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private EmailAuthActivity authActivity = new EmailAuthActivity();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RegisterViewModel registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button register_button= (Button)root.findViewById(R.id.btnRegister);
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText first_name = (EditText)root.findViewById(R.id.registrationFirstName);
                EditText last_name = (EditText)root.findViewById(R.id.registrationLastName);
                EditText email = (EditText)root.findViewById(R.id.registrationEmail);
                EditText password = (EditText)root.findViewById(R.id.registrationPassword);
                String display_name = first_name.getText().toString() + " " + last_name.getText().toString();
                authActivity.createAccount(display_name, email.getText().toString(), password.getText().toString(), inflater.getContext());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
