package de.bws.udrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
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

        // Den Variabeln Werte aus den Eingabefeldern übergeben
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbutton);

        // Button Klick
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Wenn Username und Passwort übereinstimmen mach ein Toast (unten kleiner Popup) der sagt was passiert ist
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("123")) {
                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent Home = new Intent(MainActivity.this, HomeActivity.class);
                    Home.putExtra("username", username.getText().toString());
                    MainActivity.this.startActivity(Home);
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}