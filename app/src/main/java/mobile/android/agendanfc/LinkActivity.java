package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BTO_HOME_turquesa)));


        Button btn_escribir;
        btn_escribir = findViewById(R.id.btn_EscribirLink);

        EditText link;

        link = findViewById(R.id.linkTexView);

        btn_escribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link_mssg = link.getText().toString().trim();
                irGrabarNFC_Otros(link_mssg);
            }
        });

    }

    private void irGrabarNFC_Otros(String link_mssg) {
        Intent i = new Intent(this, GrabarNFC_OtrosActivity.class);
        i.putExtra("nombre_user","" );
        i.putExtra("telefono_user",link_mssg );
        i.putExtra("tipo_form","link" );

        this.startActivity(i);
    }
}