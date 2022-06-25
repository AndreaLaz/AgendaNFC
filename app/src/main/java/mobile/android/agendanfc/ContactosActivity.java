package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import mobile.android.agendanfc.adapter.ContactoAdapter;
import mobile.android.agendanfc.model.Contacto;

public class ContactosActivity extends AppCompatActivity {

    RecyclerView miRecycler;
    ContactoAdapter miAdapter;
    FirebaseFirestore miFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        //MOSTRA DATOS
        miFirestore = FirebaseFirestore.getInstance();

        miRecycler = findViewById(R.id.recyclerViewSingle);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));

        Query query = miFirestore.collection("Contactos");

        FirestoreRecyclerOptions<Contacto> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Contacto>().setQuery(query,Contacto.class).build();

        miAdapter = new ContactoAdapter(firestoreRecyclerOptions);
        miAdapter.notifyDataSetChanged();//cada uno de los cambio

        miRecycler.setAdapter(miAdapter);
//end_MOSTRA DATOS
    }

    @Override
    protected void onStart() {
        super.onStart();
        miAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        miAdapter.stopListening();
    }

    public void irInicio(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
