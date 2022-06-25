package mobile.android.agendanfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

//import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class FormularioActivity extends AppCompatActivity {

    EditText nombre,telefono;

    private FirebaseFirestore bd;

    private CountryCodePicker ccp;
    private TextView textnumeroTelefono;//textViewNumTelefono
    Button guardar,sendCcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        this.setTitle("Añadir Contacto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bd = FirebaseFirestore.getInstance();//apuntamos a la bbdd

        nombre = findViewById(R.id.editTextNombreContacto);
        telefono = findViewById(R.id.editTextTelefono);
        //mensajeria = findViewById(R.id.editTextMensajeria);
        guardar = (Button) findViewById(R.id.saveButton);
        inicializarView();


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tamanioNumero = 0;

                String nombreContacto = nombre.getText().toString().trim();
                String numeroTelefono = telefono.getText().toString().trim();
                String numeroMensajeria = telefono.getText().toString().trim();

                String codigo = ccp.getSelectedCountryCode();
                //String pais = ccp.getSelectedCountryEnglishName();


                tamanioNumero = contarCaracteres(numeroTelefono, tamanioNumero);

                if(nombreContacto.isEmpty()&&numeroTelefono.isEmpty()&&numeroMensajeria.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Error: Ingresa los datos!!!!",Toast.LENGTH_LONG).show();
                }else if(tamanioNumero<9){
                    Toast.makeText(getApplicationContext(),"Error: Números muy corto ",Toast.LENGTH_LONG).show();
                }else
                    guardarContacto(nombreContacto, codigo,numeroTelefono,numeroMensajeria);
            }
        });

    }//FINonCreate

    private void guardarContacto(String nombreContacto, String codigo,String numeroTelefono, String numeroMensajeria) {
        String mas = "+";
        String espacio = " ";

        Map<String,Object> map = new  HashMap<>();
        map.put("AppMensajeria",mas+codigo+espacio+numeroMensajeria);
        map.put("Telefono",mas+codigo+espacio+numeroTelefono);
        map.put("Nombre",nombreContacto);


        bd.collection("Contactos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Contacto CREADO!!!!",Toast.LENGTH_SHORT).show();
                //finish();
                //continuar(nombreContacto, numeroTelefono,  numeroMensajeria);
                startActivity(new Intent(FormularioActivity.this,AniadirContActivity.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR!!!!",Toast.LENGTH_LONG).show();

            }
        });

    }

    public int contarCaracteres(String cadena, int tamanioNumero) {

        String numeros ="";
        for (int i =0;i<cadena.length();i++){
            if (Character.isDigit(cadena.charAt(i))){
                tamanioNumero++;
                numeros+=cadena.charAt(i);
            }
        }
        return tamanioNumero;
    }

    private void inicializarView(){
        ccp = (CountryCodePicker) findViewById(R.id.countryCodePicker);
        textnumeroTelefono=(EditText) findViewById(R.id.editTextTelefono);
        sendCcp=(Button) findViewById(R.id.saveButton);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}//FINclass