package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);

        Button btn_App,btn_MensajeAut,btn_link,btn_Contacto;
        Button btn_App_info,btn_MensajeAut_info,btn_link_info,btn_Contacto_info;


        btn_App = (Button) findViewById(R.id.btn_app);
        btn_App_info = (Button) findViewById(R.id.btn_app_info);

        btn_Contacto = (Button) findViewById(R.id.btn_Contacto_Card);
        btn_Contacto_info = (Button) findViewById(R.id.btn_Contacto_Card_INFO);


        btn_link = (Button) findViewById(R.id.btn_Link);
        btn_link_info = (Button) findViewById(R.id.btn_Link_info);

        btn_MensajeAut = (Button) findViewById(R.id.btn_Mensaje_Auto);
        btn_MensajeAut_info = (Button) findViewById(R.id.btn_Mensaje_Auto_info);

        btn_App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irApp("1");
            }
        });
        btn_App_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irInfo("1");
            }
        });

        btn_Contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {irFormulario("3");
            }
        });
        btn_Contacto_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irInfo("3");
            }
        });


        btn_MensajeAut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { irFormulario("2");
            }
        });
        btn_MensajeAut_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irInfo("2");
            }
        });


        btn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irLink("1");
            }
        });
        btn_link_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irInfo("1");
            }
        });


    }

    private void irLink(String s) {
        Intent i = new Intent(this,LinkActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
    private void irApp(String s) {
        Intent i = new Intent(this,AppsActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }

    private void irFormulario(String s) {
        Intent i = new Intent(this,FormularioActivity.class);
        i.putExtra("tipo_form",s );
        startActivity(i);
    }

    public void irInfo(String dato){
        Intent i = new Intent(this,GrabarNFCActivity.class);//de donde estamos a donde queremos ir
        startActivity(i);//para ir de una ventana a otra
    }
    public void irGrabar(String dato){
        Intent i = new Intent(this,GrabarNFCActivity.class);//de donde estamos a donde queremos ir
        i.putExtra("tipo_escr",dato );
        startActivity(i);//para ir de una ventana a otra
    }

}