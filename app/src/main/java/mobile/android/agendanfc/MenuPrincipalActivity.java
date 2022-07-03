package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import mobile.android.agendanfc.adapter.ContactoAdapter;
import mobile.android.agendanfc.model.Contacto;

public class MenuPrincipalActivity extends AppCompatActivity {
    FirebaseAuth auth;

    private Button cerrarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        cerrarSesion = findViewById(R.id.btoidcerrarsesion);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            cerrarSesion.setVisibility(View.VISIBLE);
        }
    }

    public void irGrabarMenj(View view){
        Intent i = new Intent(this,GrabaWhattsapActivity.class);
        startActivity(i);
    }
    public void irFormulario(View view){
        Intent i = new Intent(this,FormularioActivity.class);
        startActivity(i);
    }
    public void irContactos(View view){
        Intent i = new Intent(this,ContactosActivity.class);
        startActivity(i);
    }

    public void irInicio(MenuPrincipalActivity view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void cerrarSesion(View view){
        auth.signOut();
        if(auth.getCurrentUser() == null){
            Toast.makeText(this,"Sesion cerrada",Toast.LENGTH_SHORT).show();
            irInicio(this);
        }
    }

}