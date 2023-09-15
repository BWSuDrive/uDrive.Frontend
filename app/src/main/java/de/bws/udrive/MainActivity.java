package de.bws.udrive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.Tag;
import de.bws.udrive.utilities.model.uDrive;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /* Lokale Variabeln */
    private EditText username;
    private EditText password;
    private Button loginButton;

    /* AlertDialog für falschen Login */
    private AlertDialog alertDialog;

    /* "Konstruktor"-Methode */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Die Elemente per ID initialisieren
        this.initElements();

        // Button Klicks registrieren
        this.handleListeners();
    }

    /*
        Initialisiert die Elemente auf der UI per ID
        Entwickelt von: Fabian & Lucas
     */
    private void initElements()
    {
        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        this.loginButton = findViewById(R.id.loginbutton);
    }

    /*
        Setzt die Listener für einzelne Objekte
        Entwickelt von: Fabian & Lucas
     */
    private void handleListeners()
    {
        loginButton.setOnClickListener(loginButtonListener);
    }

    /* =================================================================================== */


    /*
        Methode um die HomeActivity zu öffnen
        Entwickelt von: Fabian, Lucas
        Angepasst durch: Lucas
    */
    private void openHomeActivity()
    {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        homeIntent.putExtra("signedInUser", username.getText().toString());
        MainActivity.this.startActivity(homeIntent);
    }

    /*
        Error-Dialog Box die bei ungültigem Login angezeigt wird
        Entwickelt von: Fabian
        Angepasst durch: Lucas
    */
    private void showErrorDialog(String errorText) {
        ConstraintLayout errorLayout = findViewById(R.id.errorConstraintLayout);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.error_dialog, errorLayout);
        Button errorClose = view.findViewById(R.id.errorClose);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        this.alertDialog = builder.create();

        errorClose.setOnClickListener(errorDialogListener);
        if (this.alertDialog.getWindow() != null)
        {
            this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        this.alertDialog.show();

        if(errorText != null)
            ((TextView) view.findViewById(R.id.errorDesc)).setText(errorText);
    }

    /* =================================================================================== */

    /*
        OnClickListener für "Login"-Button
        Entwickelt von: Fabian, Lucas
    */
    private View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /* User-Input in Variablen speichern */
            String inputUsername = MainActivity.this.username.getText().toString();
            String inputPassword = MainActivity.this.password.getText().toString();

            /* Login-Objekt erstellen --> Für API Call */
            uDrive.Login login = new uDrive.Login(null, null, null, 0, null);

            /* API Call vorbereiten und Login-Objekt übergeben */
            Call<ResponseBody> loginRequest = APIClient.getAPI().create(APIInterface.class).sendLoginRequest(login);

            /* Überprüfung, ob Name & Password Felder eine bestimmte Länge haben */
            boolean inputValid = (inputUsername.length() > 3 && inputPassword.length() > 5);

            boolean loginSuccesful = false;

            loginRequest.enqueue(MainActivity.this.loginResponse);

            if(loginSuccesful)
                MainActivity.this.openHomeActivity();
        }
    };

    private View.OnClickListener errorDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity.this.alertDialog.dismiss();
            Toast.makeText(MainActivity.this, "Close", Toast.LENGTH_SHORT).show();
        }
    };

    private Callback<ResponseBody> loginResponse = new Callback<ResponseBody>() {
        private String errorText = null;
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            /* Antwort OK von API */
            if(response.code() == 200)
            {
                MainActivity.this.openHomeActivity();
            }
            else /* Antwort nicht OK */
            {
                errorText = "Der Login war nicht möglich!\n";
                errorText += "Fehler-Code: " + response.code() + "\n";
                errorText += "Fehler: " + response.message();
                errorText += response.headers().toString();

                showErrorDialog(errorText);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            String errorText = "Die Kommunikation mit der API war nicht möglich!\n";
            errorText += t.getMessage();
            Log.wtf(Tag.FAILURE, errorText);

            showErrorDialog(errorText);
        }
    };

    /* =================================================================================== */

}