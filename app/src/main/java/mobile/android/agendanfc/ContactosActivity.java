package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import mobile.android.agendanfc.adapter.ContactoAdapter;
import mobile.android.agendanfc.model.Contacto;

public class ContactosActivity extends AppCompatActivity {

    RecyclerView miRecycler;//MOSTRA DATOS
    ContactoAdapter miAdapter;
    FirebaseFirestore miFirestore;
    Query query;
    SearchView search_View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        search_View = findViewById(R.id.search);

        //MOSTRA DATOS
        miFirestore = FirebaseFirestore.getInstance();

        miRecycler = findViewById(R.id.recyclerViewSingle);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));

        query = miFirestore.collection("Contactos");

        FirestoreRecyclerOptions<Contacto> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Contacto>().setQuery(query,Contacto.class).build();

        miAdapter = new ContactoAdapter(firestoreRecyclerOptions,this, getSupportFragmentManager());
        miAdapter.notifyDataSetChanged();//cada uno de los cambio
        miRecycler.setAdapter(miAdapter);
        //end_MOSTRA DATOS
        search_view();
    }

    //region Metodos para el SearchView
    @SuppressLint("NotifyDataSetChanged")
    private void setUpRecyclerView(){
        miRecycler = findViewById(R.id.recyclerViewSingle);
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        query = miFirestore.collection("Contactos");

        FirestoreRecyclerOptions<Contacto> firestoreRecyclerOptions =
                new  FirestoreRecyclerOptions.Builder<Contacto>().setQuery(query,Contacto.class).build();

        miAdapter = new ContactoAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        miAdapter.notifyDataSetChanged();
        miRecycler.setAdapter(miAdapter);
    }
    private void search_view(){
        search_View.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });
    }
    public void textSearch(String s){
        FirestoreRecyclerOptions<Contacto> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Contacto>()
                .setQuery(query.orderBy("Nombre")
                        .startAt(s).endAt(s+"~"),Contacto.class).build();

        miAdapter = new ContactoAdapter(firestoreRecyclerOptions,this,getSupportFragmentManager());
        miAdapter.startListening();
        miRecycler.setAdapter(miAdapter);
    }
    //endregion

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
