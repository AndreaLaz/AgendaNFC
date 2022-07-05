package mobile.android.agendanfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

//import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class FormularioActivity extends AppCompatActivity {

    EditText nombre,telefono,mensajeria;


    private CountryCodePicker ccp;
    private TextView textnumeroTelefono, primer_dato;//textViewNumTelefono
    Button guardar,sendCcp;

    public String nombreContactoF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BTO_HOME_turquesa)));


        String id_contacto = getIntent().getStringExtra("id_contacto");
        String nombre_user = getIntent().getStringExtra("nombre_user");
        String numero_user = getIntent().getStringExtra("telefono_user");

        String tipo_form = getIntent().getStringExtra("tipo_form");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombre = findViewById(R.id.editTextNombreContacto);
        telefono = findViewById(R.id.editTextTelefono);
        mensajeria = findViewById(R.id.editTextTelefono);
        guardar = (Button) findViewById(R.id.saveButton);

        primer_dato = findViewById(R.id.textViewNombreContacto);
        inicializarVistaCCP();

        switch(tipo_form)
        {
            // declaración case
            // crear nuevo contacto
            case "1" :
                if(id_contacto==null || id_contacto==""){
                    this.setTitle("Añadir Contacto");
                    guardar.setText("GUARDAR");
                    guardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tamanioNumero = 0;

                            String nombreContacto = nombre.getText().toString().trim();
                            String numeroTelefono = telefono.getText().toString().trim();
                            String numeroMensajeria = telefono.getText().toString().trim();
                            String codigo = "+"+ccp.getSelectedCountryCode();

                            tamanioNumero = contarCaracteres(numeroTelefono, tamanioNumero);

                            if(nombreContacto.isEmpty()&&numeroTelefono.isEmpty()&&numeroMensajeria.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Error: Ingresa los datos!!!!",Toast.LENGTH_LONG).show();
                            }else if(tamanioNumero<9){
                                Toast.makeText(getApplicationContext(),"Error: Números muy corto ",Toast.LENGTH_LONG).show();
                            }else
                                guardarContacto(nombreContacto,codigo+numeroTelefono,codigo+numeroMensajeria);

                        }
                    });
                }
                // Declaraciones
                break; // break es opcional
            //crear mensaje auto
            case ("2") :
                // Declaraciones
                if(id_contacto==null || id_contacto==""){
                    this.setTitle("CREAR WHATSAPP AUTOMÁTICO");
                    primer_dato.setText("Mensaje");
                    guardar.setText("GRABAR");
                    guardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tamanioNumero = 0;

                            String nombreContacto = nombre.getText().toString().trim();
                            String numeroTelefono = telefono.getText().toString().trim();
                            String numeroMensajeria = telefono.getText().toString().trim();
                            String codigo = "+"+ccp.getSelectedCountryCode();

                            tamanioNumero = contarCaracteres(numeroTelefono, tamanioNumero);

                            if(nombreContacto.isEmpty()&&numeroTelefono.isEmpty()&&numeroMensajeria.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Error: Ingresa los datos!!!!",Toast.LENGTH_LONG).show();
                            }else if(tamanioNumero<9){
                                Toast.makeText(getApplicationContext(),"Error: Números muy corto ",Toast.LENGTH_LONG).show();
                            }else
                                irGrabarNFCOtros(nombreContacto,codigo+numeroTelefono,tipo_form);

                        }
                    });
                }
                // Declaraciones
                break;

            // crear vCard
            case "3":
                if(id_contacto==null || id_contacto==""){
                    this.setTitle("CREAR TARJETA CONTACTO");
                    guardar.setText("GRABAR");
                    guardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tamanioNumero = 0;

                            String nombreContacto = nombre.getText().toString().trim();
                            String numeroTelefono = telefono.getText().toString().trim();
                            String numeroMensajeria = telefono.getText().toString().trim();
                            String codigo = "+"+ccp.getSelectedCountryCode();

                            tamanioNumero = contarCaracteres(numeroTelefono, tamanioNumero);

                            if(nombreContacto.isEmpty()&&numeroTelefono.isEmpty()&&numeroMensajeria.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Error: Ingresa los datos!!!!",Toast.LENGTH_LONG).show();
                            }else if(tamanioNumero<9){
                                Toast.makeText(getApplicationContext(),"Error: Números muy corto ",Toast.LENGTH_LONG).show();
                            }else
                                irGrabarNFCOtros(nombreContacto,codigo+numeroTelefono,tipo_form);

                        }
                    });
                }
                // Declaraciones
                break;
            default :
            {
                this.setTitle("Crear tajeta contacto");
                guardar.setText("Grabar");
                //getContacto(id);
                actalizarVista(nombre_user, numero_user);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tamanioNumero = 0;

                        nombreContactoF = nombre.getText().toString().trim();
                        String numeroTelefono = telefono.getText().toString().trim();
                        String numeroMensajeria = telefono.getText().toString().trim();
                        String codigo = "+"+ccp.getSelectedCountryCode();
                        tamanioNumero = contarCaracteres(numeroTelefono, tamanioNumero);


                        if(nombreContactoF.isEmpty()&&numeroTelefono.isEmpty()&&numeroMensajeria.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Error: Ingresa los datos!!!!",Toast.LENGTH_LONG).show();
                        }else if(tamanioNumero<9){
                            Toast.makeText(getApplicationContext(),"Error: Números muy corto ",Toast.LENGTH_LONG).show();
                        }else
                            actualizarContacto(nombreContactoF,codigo+numeroTelefono,codigo+numeroMensajeria,id_contacto);
                    }
                });
            }
        }
    }//FINonCreate

    private void irGrabarNFCOtros(String s, String s1, String tipo_form) {
        Intent i = new Intent(this, GrabarNFC_OtrosActivity.class);
        i.putExtra("nombre_user",s );
        i.putExtra("telefono_user",s1 );
        i.putExtra("tipo_form",tipo_form );

        this.startActivity(i);
    }

    private void actalizarVista(String nombre_user, String numero_user) {
        nombre.setText(nombre_user);
        telefono.setText(numero_user.substring(3,numero_user.length()));
        String codpais =numero_user.substring(0,3);
        ccp.setDefaultCountryUsingPhoneCode(Integer.parseInt(codpais));
        ccp.resetToDefaultCountry();
    }

    private void actualizarContacto(String nombreContacto, String numeroTelefono, String numeroMensajeria, String id_contacto) {

        Map<String,Object> map = new  HashMap<>();
        map.put("AppMensajeria",numeroMensajeria);
        map.put("Telefono",numeroTelefono);
        map.put("Nombre",nombreContacto);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        FirebaseDatabase.getInstance().getReference().child("Contactos").child(firebaseUser.getUid()).child(id_contacto).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                DialogoContacto("Atualizado", numeroTelefono);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR al actualizar!!!!",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void guardarContacto(String nombreContacto,String numeroTelefono, String numeroMensajeria) {

        String numeroTelefono_User = numeroTelefono;
        String numeroWhattsap_User = numeroMensajeria;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Map<String,Object> map = new  HashMap<>();

        map.put("AppMensajeria",numeroWhattsap_User);
        map.put("Telefono",numeroTelefono_User);
        map.put("Nombre",nombreContacto);

        reference.child("Contactos").child(firebaseUser.getUid()).push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                DialogoContacto("Creado",numeroWhattsap_User);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR al actualizar!!!!",Toast.LENGTH_LONG).show();
            }
        });

    }

   public void DialogoContacto (String actOrNuv,String numeroMensajeria){
       AlertDialog.Builder builder = new AlertDialog.Builder(FormularioActivity.this);
       builder.setTitle("Contacto "+actOrNuv+"!!!!");
       builder.setMessage(" Procederemos a escribirlo en nuestra agenda !");

       builder.setPositiveButton("Vale", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               irAniadirContactos(numeroMensajeria);

           }
       });

       builder.setNegativeButton("Mas Tarde", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               Toast.makeText(FormularioActivity.this,"No olvides grabar la agenda más tarde",Toast.LENGTH_SHORT).show();
               irContactos();
           }
       });

       builder.show();
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

    private void inicializarVistaCCP(){
        ccp = (CountryCodePicker) findViewById(R.id.countryCodePicker);
        ccp.setDefaultCountryUsingNameCode("ES");
        textnumeroTelefono=(EditText) findViewById(R.id.editTextTelefono);
        sendCcp=(Button) findViewById(R.id.saveButton);

    }

    private  void irContactos(){
        Intent i = new Intent(this, ContactosActivity.class);
        this.startActivity(i);
    }
    private  void irAniadirContactos(String num){
        Intent i = new Intent(this, AniadirContActivity.class);
        i.putExtra("nombre_user",num );
        this.startActivity(i);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}//FINclass