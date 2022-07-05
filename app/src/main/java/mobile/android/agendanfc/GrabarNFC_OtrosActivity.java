package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GrabarNFC_OtrosActivity extends AppCompatActivity {
    NFCManager mNFCManager = new  NFCManager(this);
    NdefMessage mNfcMessage;
    private NfcAdapter nfcAdpt;
    private Tag mCurrentTag;

    private Button grabaLlamada,home;
    private TextView instrucciones;
    private AlertDialog.Builder builder;
    private AlertDialog mDialogoAlerta;

    String Ndef_type = "";
    String Ndef_message;
    String tipo_form;
    private Button btn_grabar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_nfc_otros);

        String numero_user = getIntent().getStringExtra("telefono_user");
        String nombre_user = getIntent().getStringExtra("nombre_user");
        String tipo_form = getIntent().getStringExtra("tipo_form");

        instrucciones = findViewById(R.id.textInstruccionesw);
        btn_grabar = (Button) findViewById(R.id.buttonGrabar);
        switch(tipo_form)
        {
            // declaración case
            // crear app
            case "1" :
                    instrucciones.setText("Crear app");
                    this.setTitle("ABRIR APP AUTOMATICO");
                    btn_grabar = findViewById(R.id.buttonGrabar);
                    btn_grabar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tamanioNumero = 0;

                            builder = new AlertDialog.Builder(GrabarNFC_OtrosActivity.this);
                            builder.setMessage(Html.fromHtml("Porfavor acerque el movil a la pagina <font color='#0075F1'> ABRIR APP AUTOMATICO</font> de sus contacto en la agenda"))
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
                // Declaraciones
                break;
            //crear mensaje auto
            case ("2") :
                instrucciones.setText("Crear Mensaje Automatico");
                btn_grabar = findViewById(R.id.buttonGrabar);
                // Declaraciones
                    this.setTitle("CREAR WHATSAPP AUTOMÁTICO");
                    btn_grabar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tamanioNumero = 0;

                            builder = new AlertDialog.Builder(GrabarNFC_OtrosActivity.this);
                            mNfcMessage = mNFCManager.createWhattsapMensaje(numero_user,nombre_user);
                            builder.setMessage(Html.fromHtml("Porfavor acerque el movil a la pagina <font color='#0075F1'>MENSAJE WHATSAPP</font> de sus contacto en la agenda"))
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
                // Declaraciones
                break;

            // crear vCard
            case "3":
                btn_grabar = findViewById(R.id.buttonGrabar);
                instrucciones.setText("Crear tajeta conatacto");
                // Declaraciones
                if(numero_user==null || numero_user==""){
                    this.setTitle("CREAR TARJETA DE CONTACTO");
                    btn_grabar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tamanioNumero = 0;

                            builder = new AlertDialog.Builder(GrabarNFC_OtrosActivity.this);
                            mNfcMessage = mNFCManager.grabaVcard(numero_user,nombre_user);
                            builder.setMessage(Html.fromHtml("Porfavor acerque el movil a la pagina <font color='#0075F1'> TARJETA DE CONTACTO</font> de sus contacto en la agenda"))
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
                // Declaraciones
                break;
            // Declaraciones
            default :
                btn_grabar = findViewById(R.id.buttonGrabar);
                instrucciones.setText("Crear link");
                // Declaraciones
                    this.setTitle("CREAR LINK");
                    btn_grabar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder = new AlertDialog.Builder(GrabarNFC_OtrosActivity.this);
                            mNfcMessage = mNFCManager.grabaLink(numero_user);
                            builder.setMessage(Html.fromHtml("Porfavor acerque el movil a la pagina <font color='#0075F1'>CREAR LINK</font> de sus contacto en la agenda"))
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
                // Declaraciones
        }


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
            AlertDialog.Builder escrito = new AlertDialog.Builder(GrabarNFC_OtrosActivity.this);
            escrito.setMessage("Escritura Terminada")
                    .setCancelable(false)
                    .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            irMenuPrincipal(instrucciones);
                        }
                    });

            escrito.show();
            TextView textView = (TextView) mDialogoAlerta.findViewById(android.R.id.message);
            textView.setTextSize(25);
        }
    }
    public void irMenuPrincipal(View view){
        Intent i = new Intent(this,MenuPrincipalActivity.class);
        startActivity(i);
    }
    public void irGrabarWhattsap(String id, String segundo_paso){

        Intent i = new Intent(this, GrabaWhattsapActivity.class);
        i.putExtra("numeroCont",id);
        i.putExtra("NFC2",segundo_paso);
        startActivity(i);

    }
}