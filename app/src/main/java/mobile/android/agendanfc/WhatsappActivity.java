package mobile.android.agendanfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


public class WhatsappActivity extends AppCompatActivity {
    Button btn;
    private PendingIntent pendingIntent;
    private IntentFilter[] readFilters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);

        Intent intent = new Intent(this,getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // btn = findViewBva a quedar inutilizado popr pruebas: yId(R.id.btn);
        pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_IMMUTABLE);
        IntentFilter whattsapfilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        whattsapfilter.addDataScheme("http");
        whattsapfilter.addDataAuthority("google ://api.whatsapp.com/send?phone=",null);
        readFilters = new IntentFilter[]{whattsapfilter};
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
                                // Read First Byte and then trim off the right length
                                byte[] textArray = Arrays.copyOfRange(payload, 1, payload.length);
                                // Convert to Text
                                String text = new String(textArray);
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("http://"+text));
                                startActivity(intent);
                            }
                    }
                }
            }
        }
    }

    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }
}