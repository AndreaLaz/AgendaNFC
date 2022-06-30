package mobile.android.agendanfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AniadirContActivity extends AppCompatActivity {
    private FirebaseFirestore bd;
    Button masLlamada,masMensajeria;

    String numT,numM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_cont);

        bd = FirebaseFirestore.getInstance();//apuntamos a la bbdd
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("id_contacto");
        numT = null;
        numM = null;
        masLlamada = (Button) findViewById(R.id.bto_irGrabarLlamada);
        masMensajeria = (Button) findViewById(R.id.bto_irGrabarLMensajeria);
        Toast.makeText(getApplicationContext(),"ANTEES\n\nMENSAJE "+numM+"\n TELEFONO"+numT,Toast.LENGTH_LONG).show();

        getContacto(id);//valida
        masLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irGrabarLlamada(v,id);
            }
        });

        masMensajeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                irGrabarWhattsap(v,id);


            }
        });
    }
    public void irGrabarWhattsap(View view,String id){

        getContacto(id);

        Intent i = new Intent(this, GrabarWhattsapActivity.class);
        i.putExtra("numeroMensajeriaNFC",numM);
        startActivity(i);

    }
    public void irGrabarLlamada(View view,String id){
        getContacto(id);
        Intent i = new Intent(this,GrabarLlamadaActivity.class);
        i.putExtra("numeroTelefonoNFC",numT);
        startActivity(i);
    }
    public void irMenuPrincipal(View view){
        Intent i = new Intent(this,MenuPrincipalActivity.class);
        startActivity(i);
    }
    private void getContacto( String id ) {
        bd.collection("Contactos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreContacto =  documentSnapshot.getString("Nombre");
                String telefContacto =  documentSnapshot.getString("Telefono");
                String mensContacto =  documentSnapshot.getString("AppMensajeria");
                numM = mensContacto;
                numT = telefContacto;
                //Toast.makeText(getApplicationContext(),"nombre,telf,men  "+nombreContacto+"\n "+telefContacto+"\n  "+mensContacto,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"DESPUES\n\nMENSAJE "+numM+"\n TELEFONO"+numT,Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR al obtener el contacto!!!!",Toast.LENGTH_LONG).show();

            }
        });
    }
}
