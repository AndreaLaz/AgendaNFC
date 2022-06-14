package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IniciarSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

    }
    public void irInicio(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    /*public void irMenuPrincipal(View view){
        Intent i = new Intent(this,GrabarNFCActivity.class);
        startActivity(i);
    }*/
}