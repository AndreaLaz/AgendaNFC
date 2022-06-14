package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void irInicioSesion(View view){
        String nombre = "andrea";
        Intent i = new Intent(this,IniciarSesionActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
        Toast.makeText(getApplicationContext(), "Bienveni@ "+nombre+" !!",Toast.LENGTH_SHORT).show();
    }
    public void irRegistro(View view){
        Intent i = new Intent(this,RegistrarseActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
}