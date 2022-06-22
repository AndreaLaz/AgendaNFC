package mobile.android.agendanfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import java.util.ArrayList;

import mobile.android.agendanfc.modelo.Contacto;

public class FormularioActivity extends AppCompatActivity {
    private EditText editText_NombreContacto,editText_Telefono,editText_Mensajeria;
    //private ArrayList<Contacto> contactos = new ArrayList();

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private Button guardar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        editText_NombreContacto = findViewById(R.id.editTextNombreContacto);
        editText_Telefono = findViewById(R.id.editTextTelefono);
        editText_Mensajeria = findViewById(R.id.editTextMensajeria);

        guardar = (Button) findViewById(R.id.buttonguardarFormulario);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Contactos");

        /*guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarFormulario(editText_NombreContacto.getText().toString(),Double.parseDouble( editText_Telefono.getText()+"" ),Double.parseDouble(editText_Mensajeria.getText()+"" ));
            }
        });*/

        guardar.setOnClickListener(v ->
                guardarFormulario(editText_NombreContacto.getText().toString(),Double.parseDouble( editText_Telefono.getText()+"" ),Double.parseDouble(editText_Mensajeria.getText()+"" ))
        );

    }

    public void guardarFormulario(String nombre,Double telf,Double mensj){

        Contacto agendaContactos = new Contacto(nombre,telf,mensj);
        if(mAuth.getCurrentUser()!=null){
            //guardarlo en bbdd
            //getUid()) es un id unico PK
            myRef.child(mAuth.getCurrentUser().getUid()).setValue(agendaContactos).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(FormularioActivity.this,"Tu contacto "+editText_NombreContacto+"se ha guardado correctamente",Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FormularioActivity.this,"OJO!!!!\n Tu contacto NO se ha guardado correctamente",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}