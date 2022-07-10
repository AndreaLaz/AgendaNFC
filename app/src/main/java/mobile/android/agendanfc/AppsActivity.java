package mobile.android.agendanfc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobile.android.agendanfc.adapter.AplicacionAdapter;

public class AppsActivity extends ListActivity {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private AplicacionAdapter listadaptor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        packageManager = getPackageManager();

        new LoadApplications().execute();

    }

    private void displayAboutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));

        builder.setPositiveButton("Saver más", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No gracias", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = applist.get(position);
        String nombrePckage = app.packageName;
        irGrabarNFCOtros(nombrePckage,"1");

    }

    private void irGrabarNFCOtros( String s1, String tipo_form) {
        Intent i = new Intent(this, GrabarNFC_OtrosActivity.class);
        i.putExtra("dato_graba_1",s1 );
        i.putExtra("tipo_form",tipo_form );

        this.startActivity(i);
    }


    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new AplicacionAdapter(AppsActivity.this,
                    R.layout.tarjeta_app, applist);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(AppsActivity.this, null,
                    "Cargando información de las aplicaciones");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}