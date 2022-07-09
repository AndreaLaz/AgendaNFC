package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BTO_HOME_turquesa)));


        Button btn_App,btn_MensajeAut,btn_link,btn_Contacto;



        btn_App = (Button) findViewById(R.id.btn_app);


        btn_Contacto = (Button) findViewById(R.id.btn_Contacto_Card);



        btn_link = (Button) findViewById(R.id.btn_Link);

        btn_MensajeAut = (Button) findViewById(R.id.btn_Mensaje_Auto);


        btn_App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irApp();
            }
        });
        btn_Contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {irFormulario("3");
            }
        });
        btn_MensajeAut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { irFormulario("2");
            }
        });
        btn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irLink("1");
            }
        });



    }

    private void irLink(String s) {
        Intent i = new Intent(this,LinkActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
    private void irApp() {
        Intent i = new Intent(this,AppsActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }

    private void irFormulario(String s) {
        Intent i = new Intent(this,FormularioActivity.class);
        i.putExtra("tipo_form",s );
        startActivity(i);
    }
    public void irMenuPrincipal(View view){
        Intent i = new Intent(this,MenuPrincipalActivity.class);
        startActivity(i);
    }

}