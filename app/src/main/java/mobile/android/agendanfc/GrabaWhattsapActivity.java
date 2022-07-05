package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GrabaWhattsapActivity extends AppCompatActivity {

    private NdefMessage mNfcMessage;
    private NFCManager mNFCManager = new NFCManager(this);

    private NfcAdapter nfcAdpt;
    private Tag mCurrentTag;

    private Button bto_grabaWhattsapp;
    private TextView instrucciones;
    private AlertDialog.Builder builder;
    private AlertDialog mDialogoAlerta;

    String numeroCont;
    String detectar_segunfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graba_whattsap);

        bto_grabaWhattsapp = findViewById(R.id.buttonGrabarWhattsap);
        instrucciones = findViewById(R.id.textInstruccionesw);
        instrucciones.setText(Html.fromHtml("<font color='#040504'>Cuando tenga lista la pagina</font> Whattsap <font color='#040504'> de la agenda presione en </font> GRABAR CONTACTO"));

        numeroCont = getIntent().getStringExtra("numeroCont");
        detectar_segunfo = getIntent().getStringExtra("NFC2");

        bto_grabaWhattsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(GrabaWhattsapActivity.this);
                mNfcMessage = mNFCManager.grabaWhatssapp("+"+numeroCont);
                builder.setMessage(Html.fromHtml("Porfavor acerque el movil a la pagina <font color='#75D113'>Whattsap</font> de sus contacto en la agenda"))
                        .setCancelable(true)
                        .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDialogoAlerta.dismiss();
                            }
                        });
                mDialogoAlerta= builder.show();
                TextView textView = (TextView) mDialogoAlerta.findViewById(android.R.id.message);
                textView.setTextSize(25);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        try {
            mNFCManager.verificarNFC(nfcAdpt);
            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,nfcIntent,PendingIntent.FLAG_MUTABLE);
            IntentFilter[] intentFiltersArray = new IntentFilter[]{};
            String[][] techList = new String[][]{{android.nfc.tech.Ndef.class.getName()}, {android.nfc.tech.NdefFormatable.class.getName()}};
            nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this,pendingIntent, intentFiltersArray, techList);
        } catch (NFCManager.NFCNotSupported nfcnsup) {
            Toast.makeText(this, "NFC no compatible", Toast.LENGTH_SHORT).show();
        } catch (NFCManager.NFCNotEnabled nfcnEn) {
            Toast.makeText(this, "NFC no habilitado", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        nfcAdpt.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        mCurrentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (mNfcMessage != null) {
            mNFCManager.escribirTag(mCurrentTag, mNfcMessage);
            AlertDialog.Builder escrito = new AlertDialog.Builder(GrabaWhattsapActivity.this);
            if (detectar_segunfo==null || detectar_segunfo==""){
                escrito.setMessage(Html.fromHtml("¿Quieres agregar tambien la <font color='#0075F1'>Llamada</font> de su contacto"))
                        .setCancelable(false)
                        .setNegativeButton("Más tarde", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                irMenuPrincipal(instrucciones);
                            }
                        })
                        .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                irGrabarLlamada(numeroCont,"es_segundo");
                            }
                        });
            }else {
                escrito.setMessage("¡Ya has finaliazado de guardar el contacto en la agenda!")
                        .setCancelable(false)
                        .setPositiveButton("Vale", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                irMenuPrincipal(instrucciones);
                            }
                        });
            }
                escrito.setTitle(Html.fromHtml("<big>¡Contacto Añadido a la agenda correctamente!</big>"));
                mDialogoAlerta= escrito.show();
                TextView textView = (TextView) mDialogoAlerta.findViewById(android.R.id.message);
                textView.setTextSize(40);

        }
        Toast.makeText(this, "Etiqueta Whattsap", Toast.LENGTH_SHORT).show();

    }

    public void irGrabarLlamada(String numeroCont,String segundo_paso){
        Intent i = new Intent(this,GrabarLlamadaActivity.class);
        i.putExtra("numeroCont",numeroCont);
        i.putExtra("NFC2",segundo_paso);
        finish();
        startActivity(i);
    }

    public void irMenuPrincipal(View view){
        Intent i = new Intent(this,MenuPrincipalActivity.class);
        finish();
        startActivity(i);
    }
}