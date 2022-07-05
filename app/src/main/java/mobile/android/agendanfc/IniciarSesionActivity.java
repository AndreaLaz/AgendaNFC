package mobile.android.agendanfc;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo, contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BTO_HOME_turquesa)));


        mAuth = FirebaseAuth.getInstance();
        correo = findViewById(R.id.correoIS);
        contrasenia = findViewById(R.id.contraseniaIS);


    }
    public void irInicio(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void iniciarSesion (View view){

        /*mAuth.createUserWithEmailAndPassword(correo.getText().toString().trim(), contrasenia.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Usuario iniciado con exito!!.",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent i = new Intent(getApplicationContext(),MenuPrincipalActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Error , vuelva a intentarlo o \n registrese en caso de no tener cuenta.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });*/
        mAuth.signInWithEmailAndPassword(correo.getText().toString().trim(), contrasenia.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(IniciarSesionActivity.this,"Usuario iniciado con exito!!." ,
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),MenuPrincipalActivity.class);
                            startActivity(i);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(IniciarSesionActivity.this, "Error , vuelva a intentarlo o \n registrese en caso de no tener cuenta.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
    /*public void irMenuPrincipal(View view){
        Intent i = new Intent(this,GrabarNFCActivity.class);
        startActivity(i);
    }*/
}