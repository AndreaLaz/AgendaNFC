package mobile.android.agendanfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo, contrasenia,contraseniaRep,nombredeusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        mAuth = FirebaseAuth.getInstance();
        correo = findViewById(R.id.correoIS);
        contrasenia = findViewById(R.id.contraseniaIS);
        contraseniaRep = findViewById(R.id.contraseniaRep);
        nombredeusuario = findViewById(R.id.nombredeusuario);
    }
    public void irInicio(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {//Cuando inicialices tu actividad, verifica que el usuario haya accedido
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void registrarUsuario (View view){
        String nombreObl = nombredeusuario.getText().toString().trim();
        String correoObl = correo.getText().toString().trim();
        String contrObl = contrasenia.getText().toString().trim();
        String contraRepObl = contraseniaRep.getText().toString().trim();

        if(nombreObl.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Por favor, introduce tu nombre de usuario. \n Este compo es obligatorio (*)", Toast.LENGTH_SHORT).show();
        }
        if(correoObl.isEmpty())
            Toast.makeText(getApplicationContext(), "Por favor, introduce tu correo \n Este compo es obligatorio (*)", Toast.LENGTH_SHORT).show();

        if(contrObl.isEmpty())
            Toast.makeText(getApplicationContext(), "Por favor, introduce tu contraseña \n Este compo es obligatorio (*) ", Toast.LENGTH_SHORT).show();

        if(contraRepObl.isEmpty())
            Toast.makeText(getApplicationContext(), "Por favor, introduce de nuevo tu contraseña \n Este compo es obligatorio (*) ", Toast.LENGTH_SHORT).show();



        if(contrasenia.getText().toString().equals(contraseniaRep.getText().toString()) && !nombreObl.isEmpty()&& !contraRepObl.isEmpty() && !contrObl.isEmpty()&& !correoObl.isEmpty()){
            mAuth.createUserWithEmailAndPassword(correo.getText().toString().trim(), contrasenia.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(getApplicationContext(), "Bienveni@ "+nombreObl+" !!",Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "El Registro falló.",Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }else{
            Toast.makeText(this,"Las contraseñas no coinciden. inténtalo de nuevo",Toast.LENGTH_SHORT).show();
        }
    }

}