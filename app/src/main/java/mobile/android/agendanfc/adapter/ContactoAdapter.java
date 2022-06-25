package mobile.android.agendanfc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import mobile.android.agendanfc.R;
import mobile.android.agendanfc.model.Contacto;

public class ContactoAdapter  extends FirestoreRecyclerAdapter<Contacto,ContactoAdapter.ViewHolder> {

    public ContactoAdapter(@NonNull FirestoreRecyclerOptions<Contacto> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Contacto model) {
        holder.name.setText(model.getNombre());
        holder.phone.setText(model.getTelefono());
        holder.appt.setText(model.getAppMensajeria());

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.nombre);
            phone = itemView.findViewById(R.id.telefono);
            appt = itemView.findViewById(R.id.aplicacionM);
        }
    }
}
