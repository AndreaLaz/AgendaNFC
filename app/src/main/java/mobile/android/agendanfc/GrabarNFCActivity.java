package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.nfc.*;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
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

            radioMessageTypeGroup = (RadioGroup) findViewById(R.id.radioTypeMessage);
            btnEmpezar = (Button) findViewById(R.id.button);
        TextView editaelmensaje = (TextView) findViewById(R.id.editaelmensaje);

        btnEmpezar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // get selected radio button from radioGroup
                    int selectedId = radioMessageTypeGroup.getCheckedRadioButtonId();
                    String contenidotxt = editaelmensaje.getText().toString();
                    // find the radiobutton by returned id
                    radioMessageTypeButton = (RadioButton) findViewById(selectedId);
                    switch (radioMessageTypeGroup.getCheckedRadioButtonId()) {
                        case R.id.radioTypeMessage:
                            mNfcMessage = mNFCManager.grabaWhatssapp(contenidotxt);
                            break;
                        case R.id.radioUri:
                            mNfcMessage = mNFCManager.createUriMessage(contenidotxt, "https://");
                            break;
                        case R.id.radioPhone:
                            mNfcMessage = mNFCManager.grabaLlamada(contenidotxt);
                            break;
                        case R.id.radioLocation:
                            //mNfcMessage = mNFCManager.createGeoMessage(contenidotxt);
                            break;
                    }if (mNfcMessage != null) {
                        ProgressDialog mDialog = new ProgressDialog(GrabarNFCActivity.this);
                        mDialog.setMessage("acerca la etiqueta NFC, por favor");
                        mDialog.show();
                    }

                }

            });
    }

    @Override
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



