package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GrabaWhattsapActivity extends AppCompatActivity {

    Button bto_grabaWhattsapp;
    private TextView instrucciones;
    private AlertDialog.Builder builder;
    private NdefMessage mNfcMessage;
    private NFCManager mNFCManager = new NFCManager(this);
    private AlertDialog mDialogoAlerta;
    private NfcAdapter nfcAdpt;
    private Tag mCurrentTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graba_whattsap);
        bto_grabaWhattsapp = findViewById(R.id.buttonGrabarWhattsap);
        instrucciones = findViewById(R.id.textInstruccionesw);
        instrucciones.setText("Acerque el telefono a la agenda telefonica");

        String numeroCont = getIntent().getStringExtra("numeroCont");

        bto_grabaWhattsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(GrabaWhattsapActivity.this);
                mNfcMessage = mNFCManager.grabaLlamada(numeroCont);
                builder.setMessage("Acerque la pagina")
                        .setCancelable(true)
                        .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDialogoAlerta.dismiss();
                            }
                        });
                builder.setTitle("graba");
                mDialogoAlerta= builder.show();

            }
        });
    }
    protected void onResume() {
        super.onResume();
        try {
            mNFCManager.verificarNFC(nfcAdpt);
            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,nfcIntent,0);
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
            escrito.setMessage("Escritura Terminada")
                    .setCancelable(false)
                    .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDialogoAlerta.dismiss();
                            dialogInterface.cancel();
                        }
                    });
            escrito.setTitle("graba?");
            escrito.show();
        }
        Toast.makeText(this, "Etiqueta Whattsap", Toast.LENGTH_SHORT).show();

    }
}