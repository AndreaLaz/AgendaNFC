package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.Arrays;


public class TratadoDeDatosActivity extends AppCompatActivity {
    private PendingIntent pendingIntent;
    private IntentFilter[] readFilters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tratado_de_datos);

        Intent intent = new Intent(this,getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_IMMUTABLE);
        processNFC(getIntent());
    }

    private void  enableRead(){
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this,pendingIntent, readFilters,null);
    }
    private void  disableRead(){
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        enableRead();
    }
    @Override
    protected void onPause() {
        super.onPause();
        disableRead();
    }
    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        processNFC(intent);
    }

    private void processNFC(Intent intent) {
        Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if(messages != null){
            for (Parcelable message : messages){
                NdefMessage ndefMessage = (NdefMessage) message;
                for (NdefRecord record : ndefMessage.getRecords()){
                    switch (record.getTnf()){
                        case NdefRecord.TNF_WELL_KNOWN:
                            if (Arrays.equals(record.getType(),NdefRecord.RTD_URI)){
                                String whattsap= new String(record.getPayload());
                                byte[] payload = ndefMessage.getRecords()[0].getPayload();
                                byte[] textArray = Arrays.copyOfRange(payload,1 , payload.length);
                                // convertir a texto
                                String text = new String(textArray);
                                String type = text.substring(0,1);
                                String type_phone = "+";
                                if (type.equals(type_phone)) {
                                    if (ContextCompat.checkSelfPermission(
                                            this, Manifest.permission.CALL_PHONE) ==
                                            PackageManager.PERMISSION_GRANTED) {
                                        // You can use the API that requires the permission.
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:"+text));//change the number
                                        startActivity(callIntent);
                                    } else {
                                        // You can directly ask for the permission.
                                        // The registered ActivityResultCallback gets the result of this request.
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(
                                                    new String[] { Manifest.permission.CALL_PHONE },
                                                    1);
                                        }
                                    }

                                }else {
                                    intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("http://"+text));
                                    startActivity(intent);
                                }
                            }
                    }
                }
            }
        }
    }

}