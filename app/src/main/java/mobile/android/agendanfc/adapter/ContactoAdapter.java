package mobile.android.agendanfc.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import mobile.android.agendanfc.ContactosActivity;
import mobile.android.agendanfc.FormularioActivity;
import mobile.android.agendanfc.R;
import mobile.android.agendanfc.model.Contacto;

public class ContactoAdapter  extends FirestoreRecyclerAdapter<Contacto,ContactoAdapter.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    public ContactoAdapter(@NonNull FirestoreRecyclerOptions<Contacto> options,Activity activity) {

        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Contacto model) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.name.setText(model.getNombre());
        holder.phone.setText(model.getTelefono());
        holder.appt.setText(model.getAppMensajeria());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, FormularioActivity.class);
                i.putExtra("id_contacto",id);
                activity.startActivity(i);;
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContacto(id);
            }
        });
    }
    private void deleteContacto(String id){
        mFirestore.collection("Contactos").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity,"Contacto eliminado correctamente ",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,"Error al eliminar ",Toast.LENGTH_SHORT).show();

            }
        });

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //conectar el Adaptador con el view_contacto_single

        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contacto_single,parent,false);
        return new ViewHolder(vi);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {//instancia

        TextView name , phone , appt;
        ImageView delete,edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.nombre);
            phone = itemView.findViewById(R.id.telefono);
            appt = itemView.findViewById(R.id.aplicacionM);
            delete = itemView.findViewById(R.id.bto_eliminar);
            edit = itemView.findViewById(R.id.bto_editar);
        }
    }
}
