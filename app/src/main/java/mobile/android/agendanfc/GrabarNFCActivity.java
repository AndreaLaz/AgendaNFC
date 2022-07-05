package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.nfc.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class GrabarNFCActivity extends AppCompatActivity {
    NFCManager mNFCManager = new  NFCManager(this);
    NdefMessage mNfcMessage;
    private RadioGroup radioMessageTypeGroup;
    private RadioButton radioMessageTypeButton;
    private Button btnEmpezar;
    private NfcAdapter nfcAdpt;
    private Tag mCurrentTag;
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_nfc);
        String numero_user = getIntent().getStringExtra("tipo_escr");
        Toast.makeText(this, numero_user, Toast.LENGTH_SHORT).show();
        btnEmpezar = (Button) findViewById(R.id.bto_aniadirLlamada);

        final LinearLayout container = (LinearLayout) findViewById(R.id.container);
        int numberOfControlsToGenerate = 0;

        try {
            numberOfControlsToGenerate = Integer.parseInt(numero_user);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        if (numberOfControlsToGenerate > 0) {

            if (container.getChildCount() > 0) {
                container.removeAllViews();
            }
            switch (numberOfControlsToGenerate) {
                case 1:
                    for (int counter = 0; counter < numberOfControlsToGenerate; counter++) {
                        int id_textVIew;
                        id_textVIew = addEditText(container,"link");
                        TextView contenido_id = (TextView) findViewById(id_textVIew);
                        String name = contenido_id.getText().toString();
                        mNfcMessage = mNFCManager.createUriMessage(name, "https://");
                    }
                    break;
                case 2:
                    final String numero;
                    final String[] mensaje = {""};
                    EditText contenido_mensaje;
                    TextView contenido_numero;
                    int id_textVIew;
                    id_textVIew = addEditText(container,"mensaje");
                    contenido_mensaje = (EditText) findViewById(id_textVIew);
                    numero = contenido_mensaje.getText().toString();
                    Toast.makeText(this, numero, Toast.LENGTH_SHORT).show();
                    contenido_numero = (TextView) findViewById(id_textVIew);
                    //numero = contenido_numero.getText().toString();
                    btnEmpezar.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                      //      mensaje[0] = contenido_mensaje.getText().toString();
                        //    numero = contenido_numero.getText().toString();
                            ProgressDialog mDialog = new ProgressDialog(GrabarNFCActivity.this);
                            mDialog.setMessage(numero);
                            mDialog.show();
//                            mNfcMessage = mNFCManager.createWhattsapMensaje(numero[0], mensaje[0]);
                            if (mNfcMessage != null) {
                                mDialog.setMessage("acerca la etiqueta NFC, por favor");
                                mDialog.show();
                            }

                        }
                    });
                    break;
                case 3:
                    String Nombre= "";
                    String Numero= "";
                    String NumeroWhattsap= "";
                    for (int counter = 0; counter < numberOfControlsToGenerate; counter++) {

                        if(counter == 0){
                            id_textVIew = addEditText(container,"nombre");
                            TextView contenido_id = (TextView) findViewById(id_textVIew);
                            Nombre = contenido_id.getText().toString();
                        }
                        if(counter == 1){
                            id_textVIew = addEditText(container,"numero");
                            TextView contenido_id = (TextView) findViewById(id_textVIew);
                            Numero = contenido_id.getText().toString();
                        }

                    }
                    NumeroWhattsap = Numero;
                    mNfcMessage = mNFCManager.grabaVcard(Nombre,Numero);
                    break;
            }

        }


    }
    public static final String TAG = MainActivity.class.getSimpleName();


    private Integer addEditText(LinearLayout container, String texto) {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText editTextToAdd = new EditText(this);
        editTextToAdd.setLayoutParams(params);
        editTextToAdd.setHint(texto);
        container.addView(editTextToAdd);
        int ID;
        return ID = (editTextToAdd.getId());
    }

    @Override
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
    //    @Override
//    public void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
//            Tag mCurrentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//        if (mNfcMessage != null) {
//            mNFCManager.escribirTag(mCurrentTag, mNfcMessage);
//            Dialog mDialog = new Dialog(this);
//            mDialog.dismiss();
//            Toast.makeText(this, "Etiqueta escrita", Toast.LENGTH_SHORT).show();
            //     }
   //     }
   // }
    @Override
    public void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        mCurrentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (mNfcMessage != null) {
            mNFCManager.escribirTag(mCurrentTag, mNfcMessage);
            String mNfcMessages = mNfcMessage.toString();
            Toast.makeText(this, "Etiqueta escrita", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        }
    }

    }



