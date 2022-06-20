package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AniadirContActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_cont);
    }
    public void irGrabarWhattsap(View view){
        Intent i = new Intent(this, GrabarWhattsapActivity.class);
        startActivity(i);
    }
    public void irGrabarLlamada(View view){
        Intent i = new Intent(this,GrabarLlamadaActivity.class);
        startActivity(i);
    }
}
