package com.example.egar_admin.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;


import com.example.egar_admin.FirebaseManger.FirebaseAuthController;
import com.example.egar_admin.R;
import com.example.egar_admin.adapters.MyFragmentAdapter;

import com.example.egar_admin.databinding.ActivityRegisterBinding;
import com.example.egar_admin.interfaces.ProcessCallback;
import com.example.egar_admin.ui.MainActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityRegisterBinding binding;
    private MyFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        screenOperations();


    }

    private void screenOperations (){
        setOnClick();
        setTitle("REGISTER");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.active)));
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisterActivity.this,R.color.active));

    }
    private boolean isValidPalestinianPhoneNumber() {
        String phoneNumber = binding.etPhone.getText().toString().trim();
        if (phoneNumber.matches("^(059|056)\\d{7}$")) {
            return true;
        } else {
            Toast.makeText(this, "Enter A Valid Palestinian Number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isValidEmail() {
        String email = binding.etEmail.getText().toString().trim();
        boolean isValid = false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            isValid = false;
        } else {
            Matcher matcher = pattern.matcher(email);
            isValid = matcher.matches();
        }
        if (!isValid) {
            Snackbar.make(binding.getRoot(), "Invalid email address", Snackbar.LENGTH_LONG).setTextColor(ContextCompat.getColor(this,R.color.bronze)).show();

        }
        return isValid;
    }

    private boolean dataCheck (){
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPass.getText().toString().trim();

        if (name.isEmpty()) {
            binding.etName.setError("UserName field is Required");
            return false;
        } else if (email.isEmpty()  ) {
            binding.etEmail.setError("Email field is Required");
            return false;
        } else if (password.isEmpty()) {
            binding.etPhone.setError("Password field is Required");
            return false;
        }else if(!binding.checked.isChecked()) {
            Snackbar.make(binding.getRoot(),"You must agree to the terms and conditions",Snackbar.LENGTH_LONG).setTextColor(ContextCompat.getColor(this,R.color.bronze)).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setOnClick(){

        binding.bnRegister.setOnClickListener(this::onClick);
        binding.btnBack.setOnClickListener(this::onClick);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bn_register:
                if (dataCheck() && isValidPalestinianPhoneNumber() && isValidEmail()) {
                    register();
                    Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                    String email = binding.etEmail.getText().toString().trim();
                    String pass = binding.etPass.getText().toString().trim();
                    intent1.putExtra("email", email);
                    intent1.putExtra("password", pass);
                    startActivity(intent1);
                }
                break;
            case R.id.btn_back:
                Intent intent2 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent2);

                break;
        }
    }
    @Override
    public void onBackPressed() {
        // Create an exit dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setIcon(R.drawable.baseline_exit_to_app_24);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the application
                dialog.dismiss();
            }
        });
        // Create the dialog and show it
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Do nothing
            }
        });
        dialog.show();
    }
    public String getSelectedChipText() {

        int checkedChipId = binding.chipGroup.getCheckedChipId();

        if (checkedChipId != View.NO_ID) {
            Chip checkedChip = binding.chipGroup.findViewById(checkedChipId);
            return checkedChip.getText().toString();
        } else {
            Snackbar.make(binding.getRoot(),"Select The Store Type ",Snackbar.LENGTH_LONG).show();
            return null;
        }
    }


    private void register() {
        FirebaseAuthController.getInstance().createAccount(binding.etName.getText().toString(),
                binding.etEmail.getText().toString(),
                binding.etPass.getText().toString(),
                binding.etPhone.getText().toString().trim(),
                getSelectedChipText(),
                "North Gaza",
                "Gaza",
                "متجر إلكتروني شامل يوفر خدمات ومنتجات متنوعة لجميع احتياجاتك",

                new ProcessCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();
                    }
                });
    }

}