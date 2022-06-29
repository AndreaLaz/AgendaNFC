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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

//import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class FormularioActivity extends AppCompatActivity {

    EditText nombre,telefono,mensajeria;

    private FirebaseFirestore bd;

    private CountryCodePicker ccp;
    private TextView textnumeroTelefono;//textViewNumTelefono
    Button guardar,sendCcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        String id = getIntent().getStringExtra("id_contacto");

        this.setTitle("Añadir Contacto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bd = FirebaseFirestore.getInstance();//apuntamos a la bbdd

        nombre = findViewById(R.id.editTextNombreContacto);
        telefono = findViewById(R.id.editTextTelefono);
        mensajeria = findViewById(R.id.editTextTelefono);
        guardar = (Button) findViewById(R.id.saveButton);
        inicializarView();
        String codigo = ccp.getSelectedCountryCode();

        if(id==null || id ==""){

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tamanioNumero = 0;

                    String nombreContacto = nombre.getText().toString().trim();
                    String numeroTelefono = telefono.getText().toString().trim();
                    String numeroMensajeria = telefono.getText().toString().trim();


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

        }else {
            guardar.setText("ACTUALIZAR");
            getContacto(id);

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tamanioNumero = 0;
                    String nombreContacto = nombre.getText().toString().trim();
                    String numeroTelefono = telefono.getText().toString().trim();
                    String numeroMensajeria = telefono.getText().toString().trim();
                    String codigo = ccp.getSelectedCountryCode();
                    tamanioNumero = contarCaracteres(numeroTelefono, tamanioNumero);


                    if(nombreContacto.isEmpty()&&numeroTelefono.isEmpty()&&numeroMensajeria.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Error: Ingresa los datos!!!!",Toast.LENGTH_LONG).show();
                    }else if(tamanioNumero<9){
                        Toast.makeText(getApplicationContext(),"Error: Números muy corto ",Toast.LENGTH_LONG).show();
                    }else
                        actualizarContacto(nombreContacto, codigo,numeroTelefono,numeroMensajeria,id);
                }
            });
        }
    }//FINonCreate

    private void actualizarContacto(String nombreContacto, String codigo, String numeroTelefono, String numeroMensajeria, String id) {
        String mas = "+";
        String espacio = " ";

        Map<String,Object> map = new  HashMap<>();
        map.put("AppMensajeria",mas+codigo+espacio+numeroMensajeria);
        map.put("Telefono",mas+codigo+espacio+numeroTelefono);
        map.put("Nombre",nombreContacto);

        bd.collection("Contactos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Contacto ACTUALIZADO!!!!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FormularioActivity.this,MenuPrincipalActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR al actualizar!!!!",Toast.LENGTH_LONG).show();

            }
        });
    }

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
                startActivity(new Intent(FormularioActivity.this,AniadirContActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR al crear el contacto!!!!",Toast.LENGTH_LONG).show();

            }
        });

    }

    private void getContacto( String id ) {
        bd.collection("Contactos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreContacto =  documentSnapshot.getString("Nombre");
                String telefContacto =  documentSnapshot.getString("Telefono");
                String mensContacto =  documentSnapshot.getString("AppMensajeria");

                nombre.setText(nombreContacto);
                telefono.setText(telefContacto);
                mensajeria.setText(mensContacto);
                //AQUISACA BIEN LOS DATOS//Toast.makeText(getApplicationContext(),"nombre,telf,men  "+nombreContacto+"\n "+telefContacto+"\n  "+mensContacto,Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR al obtener el contacto!!!!",Toast.LENGTH_LONG).show();

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