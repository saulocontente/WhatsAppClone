package com.aulaudemy.whatsappcloneandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aulaudemy.whatsappcloneandroid.R;
import com.aulaudemy.whatsappcloneandroid.helper.Permissions;
import com.aulaudemy.whatsappcloneandroid.helper.Preferences;
import com.aulaudemy.whatsappcloneandroid.model.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private EditText editName;
    private EditText editDDI;
    private EditText editPhone;
    private Button registerButton;
    private String[] neededPermissions = new String[] {
            Manifest.permission.SEND_SMS
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Permissions.validatePermissions(this, neededPermissions, 1);

        editName    =   findViewById(R.id.editTextName);
        editName.setText("Saulo Gonçalves Contente");

        editPhone   =    findViewById(R.id.editPhone);
        SimpleMaskFormatter simpleMaskFormatterPhone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher maskTextWatcherPhone = new MaskTextWatcher(editPhone,simpleMaskFormatterPhone);
        editPhone.addTextChangedListener(maskTextWatcherPhone);

        editDDI     =    findViewById(R.id.editDDI);
        SimpleMaskFormatter simpleMaskFormatterDDI = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskTextWatcherDDI = new MaskTextWatcher(editDDI,simpleMaskFormatterDDI);
        editDDI.addTextChangedListener(maskTextWatcherDDI);
        editDDI.setText("55");

        registerButton =  findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                registerUser(user);
                user.setToken( makeToken() );
                Log.i("TOKEN", "T: "+user.getToken() );
                String sendMessage = "WhatsApp validation code: ";

                //Salvar dados para validação
                Preferences preferences = new Preferences(LoginActivity.this);
                preferences.saveUsersPreferences(user);

                //Enviar SMS
                boolean smsSent = sendSMS( "5554", sendMessage + user.getToken() );

                //Exibir os dados salvos nas preferencias
                HashMap<String, String> userPreferences = preferences.getUserData();
                Log.i("TOKEN","Name: "+userPreferences.get("name")+" Phone: "+userPreferences.get("phone")+" Token: "+userPreferences.get("token"));


            }
        });

    }

    private void registerUser(User user) {

        String completePhone = editDDI.getText().toString()+editPhone.getText().toString();
        String unformattedPhone = completePhone.replace("+", "");
        unformattedPhone = unformattedPhone.replace("(", "");
        unformattedPhone = unformattedPhone.replace(")", "");
        unformattedPhone = unformattedPhone.replace("-", "");
        user.setPhone(unformattedPhone);
        user.setName(editName.getText().toString());

        reference.child("users").setValue(user);

    }

    private String makeToken() {
        Random random = new Random();
        int randomNumber = random.nextInt(8999) + 1000;
        return  String.valueOf(randomNumber);
    }

    private boolean sendSMS( String phone, String message ) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+" + phone, null, message, null, null );
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}