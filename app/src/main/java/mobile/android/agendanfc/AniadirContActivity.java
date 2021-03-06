package mobile.android.agendanfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AniadirContActivity extends AppCompatActivity {

    Button masLlamada,masMensajeria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_cont);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BTO_HOME_turquesa)));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("nombre_user");

        masLlamada = (Button) findViewById(R.id.bto_irGrabarLlamada);
        masMensajeria = (Button) findViewById(R.id.bto_irGrabarLMensajeria);


        masLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                irGrabarLlamada(id);
            }
        });

        masMensajeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                irGrabarWhattsap(id);

            }
        });
    }
    public void irGrabarWhattsap(String id){
        Intent i = new Intent(this, GrabaWhattsapActivity.class);
        i.putExtra("numeroCont",id);
        startActivity(i);

    }
    public void irGrabarLlamada(String id){
        Intent i = new Intent(this,GrabarLlamadaActivity.class);
        i.putExtra("numeroCont",id);
        startActivity(i);
    }
    public void irMenuPrincipal(View view){
        Intent i = new Intent(this,MenuPrincipalActivity.class);
        startActivity(i);
    }
}
