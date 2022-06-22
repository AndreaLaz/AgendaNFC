package mobile.android.agendanfc;

import androidx.appcompat.app.AlertDialog;
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

        if(auth.getCurrentUser()!=null){//ESTASCONTUUSUARIO
            irMenuPrincipal(this);
        }else{
            /*AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("Debes registrarte o iniciar sesion para poder usar la aplicacion");
            builder1.setCancelable(true);
            builder1.setPositiveButton("OK",(diaog , id)->{ dialog.cancel();});*/
            Toast.makeText(this,"Debes registrarte o iniciar sesion para poder usar la aplicacion",Toast.LENGTH_SHORT).show();
        }

    }
    public void irMenuPrincipal(MainActivity view){
        Intent i = new Intent(this,MenuPrincipalActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
    public void irInicioSesion(View view){
        Intent i = new Intent(this,IniciarSesionActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
    public void irRegistro(View view){
        Intent i = new Intent(this,RegistrarseActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }

}