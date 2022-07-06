package mobile.android.agendanfc.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mobile.android.agendanfc.AniadirContActivity;
import mobile.android.agendanfc.FormularioActivity;
import mobile.android.agendanfc.R;
import mobile.android.agendanfc.model.Contacto;

public class ContactoAdapter  extends FirebaseRecyclerAdapter<Contacto,ContactoAdapter.myViewHolder> {


    private  Activity activity;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ContactoAdapter(@NonNull FirebaseRecyclerOptions<Contacto> options,Activity activity) {
        super(options);
        this.activity = activity;
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Contacto model) {
        holder.name.setText(model.getNombre());
        holder.telefono.setText(model.getTelefono());
        holder.appMensajeria.setText(model.getAppMensajeria());
        int q = position;

        holder.Tagbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AniadirContActivity.class);
                String telefono = model.getTelefono();
                i.putExtra("nombre_user", telefono);
                activity.startActivity(i);
            }
        });

        holder.Deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Estas seguro de eliminar este contacto");
                builder.setMessage("No podras deshacer esta acción");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseDatabase.getInstance().getReference().child("Contactos")
                                .child(firebaseUser.getUid()).child(getRef(q).getKey()).removeValue();
                        Toast.makeText(holder.name.getContext(),"Contacto eliminado correctamente ",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                builder.show();

            }
        });

        holder.Editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Intent i = new Intent(activity, FormularioActivity.class);
                DatabaseReference qs = FirebaseDatabase.getInstance().getReference().child("Contactos")
                        .child(firebaseUser.getUid()).child(getRef(q).getKey());
                String referencia_cont = String.valueOf(qs);
                String[] parts = referencia_cont.split("/");
                String part1 = parts[5];
                String name = model.getNombre();
                String telefono = model.getTelefono();
                i.putExtra("nombre_user", name);
                i.putExtra("telefono_user", telefono);
                i.putExtra("id_contacto", part1);
                i.putExtra("tipo_form",part1);
                activity.startActivity(i);
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contacto_single,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView name,telefono,appMensajeria;

        ImageView Tagbtn,Deletebtn,Editbtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.nombre);
            telefono = (TextView) itemView.findViewById(R.id.telefono);
            appMensajeria = (TextView) itemView.findViewById(R.id.aplicacionM);

            Tagbtn = itemView.findViewById(R.id.bto_TAG);
            Deletebtn =itemView.findViewById(R.id.bto_eliminar);
            Editbtn =itemView.findViewById(R.id.bto_editar);
        }
    }

}
