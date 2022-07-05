package mobile.android.agendanfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import mobile.android.agendanfc.adapter.ContactoAdapter;
import mobile.android.agendanfc.model.Contacto;

public class ContactosActivity extends AppCompatActivity {

    ContactoAdapter miAdapter;

    RecyclerView recyclerView;
    ContactoAdapter adapter;
    private SearchView search_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BTO_HOME_turquesa)));


        search_View = findViewById(R.id.search);

        recyclerView = findViewById(R.id.recyclerViewSingle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseRecyclerOptions<Contacto> options =
                new FirebaseRecyclerOptions.Builder<Contacto>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Contactos").child(firebaseUser.getUid()), Contacto.class)
                .build();
        adapter = new ContactoAdapter(options,ContactosActivity.this);
        recyclerView.setAdapter(adapter);
        search_view();

    }

    @Override
    protected void onStart() {

        super.onStart();

        adapter.startListening();
    }
    @Override
    protected void onStop() {

        super.onStop();
        adapter.stopListening();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setUpRecyclerView(){
        recyclerView = findViewById(R.id.recyclerViewSingle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseRecyclerOptions<Contacto> options =
                new FirebaseRecyclerOptions.Builder<Contacto>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Contactos").child(firebaseUser.getUid()).orderByChild("Nombre"), Contacto.class)
                        .build();
        miAdapter = new ContactoAdapter(options,ContactosActivity.this);
        miAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(miAdapter);
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
    public void textSearch(String str) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseRecyclerOptions<Contacto> options =
                new FirebaseRecyclerOptions.Builder<Contacto>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Contactos").child(firebaseUser.getUid()).orderByChild("Nombre").startAt(str).endAt(str + "~"), Contacto.class)
                        .build();
        miAdapter = new ContactoAdapter(options,ContactosActivity.this);
        miAdapter.startListening();
        recyclerView.setAdapter(miAdapter);
    }


}
