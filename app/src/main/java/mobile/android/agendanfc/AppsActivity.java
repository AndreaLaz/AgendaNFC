package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AppsActivity extends AppCompatActivity {
    private TextView textViewEstado;
    private ListView lisataDeApps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BTO_HOME_turquesa)));


        lisataDeApps = findViewById(R.id.ListViewApps);
        mostrarLista();
        lisataDeApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object listItem = lisataDeApps.getItemAtPosition(i);
                String item = listItem.toString();
                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();

            }

        });
    }

    public void mostrarLista (){
        List<ApplicationInfo> listaAppsInfo = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        String[] StringList = new String[listaAppsInfo.size()];
        int i = 0;

        for(ApplicationInfo applicationInfo : listaAppsInfo){
            StringList[i]= applicationInfo.packageName;
            i++;
        }
        lisataDeApps.setAdapter(new ArrayAdapter<String>(AppsActivity.this, android.R.layout.simple_list_item_1,StringList));
    }
}