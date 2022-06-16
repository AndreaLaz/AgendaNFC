package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;

    private Button login,registrarse,cerrarSesion;
    private TextView opciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.buttonIDiniciarsesion);
        registrarse = findViewById(R.id.buttonIDregistrarse);
        cerrarSesion = findViewById(R.id.btoidcerrarsesion);
        opciones = findViewById(R.id.tvOr);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            login.setVisibility(View.GONE);
            registrarse.setVisibility(View.GONE);
            cerrarSesion.setVisibility(View.VISIBLE);
            opciones.setVisibility(View.GONE);
        }

    }
    public void irInicioSesion(View view){
        Intent i = new Intent(this,IniciarSesionActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
    public void irRegistro(View view){
        Intent i = new Intent(this,RegistrarseActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
    public void cerrarSesion(View view){
        auth.signOut();
        if(auth.getCurrentUser() == null){
            login.setVisibility(View.VISIBLE);
            registrarse.setVisibility(View.VISIBLE);
            cerrarSesion.setVisibility(View.GONE);
            Toast.makeText(this,"Sesion cerrada",Toast.LENGTH_SHORT).show();
        }
    }
}