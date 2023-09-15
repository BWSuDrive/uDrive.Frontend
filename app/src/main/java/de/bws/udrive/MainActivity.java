package de.bws.udrive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Lokale Variabeln
    private EditText username;
    private EditText password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Die Elemente per ID initialisieren
        this.initElements();

        // Button Klicks registrieren
        this.handleListeners();
    }

    /**
     * Initialisiert die Elemente auf der UI per ID
     */
    private void initElements() {
        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        this.loginButton = findViewById(R.id.loginbutton);
    }

    /**
     * Setzt die Listener für einzelne Objekte
     */
    private void handleListeners() {
        loginButton.setOnClickListener(loginButtonListener);
    }

    private View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Wenn Username und Passwort übereinstimmen mach ein Toast (unten kleiner Popup) der sagt was passiert ist
            if (username.getText().toString().equals("admin") && password.getText().toString().equals("123")) {
                Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                homeIntent.putExtra("username", username.getText().toString());
                MainActivity.this.startActivity(homeIntent);
            } else {
                showErrorDialog();
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void showErrorDialog() {
        ConstraintLayout errorLayout = findViewById(R.id.errorConstraintLayout);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.error_dialog, errorLayout);
        Button errorClose = view.findViewById(R.id.errorClose);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        errorClose.findViewById(R.id.errorClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(MainActivity.this, "Close", Toast.LENGTH_SHORT).show();
            }
        });
        if (alertDialog.getWindow() !=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}