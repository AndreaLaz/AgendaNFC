package mobile.android.agendanfc;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class NFCManager {

    Context mContext;

    public NFCManager(Context context)
    {
        mContext=context;
    }
    public NdefMessage grabaLlamada(String numero){
        NdefMessage mNfcMessage = createUriMessage(numero, "tel:");
        return mNfcMessage;
    }
    public NdefMessage grabaWhatssapp(String whatssapp){
        NdefMessage mNfcMessage = createUriMessage(whatssapp, "https://wa.me/");
        return mNfcMessage;
    }
    public void grabaLink( String link ){
        NdefMessage mNfcMessage = createUriMessage(link, "https://");
    }
    public void elimminaNFC( ){

    }
    /*Creé una clase que es responsable de trabajar con NFC.
    Llamé a esta clase NFCManager y le agregué todos los métodos que puedes ver en MainActivity.*/

    //Este es un método para verificar si NFC está disponible en un dispositivo.
    public void verificarNFC(NfcAdapter nfcAdpt) throws NFCNotSupported, NFCNotEnabled {
         nfcAdpt = NfcAdapter.getDefaultAdapter(mContext);//aqui no se me esta rallando
        Toast.makeText(mContext, "llego a llamar a verificaNFC", Toast.LENGTH_SHORT).show();
        if (nfcAdpt == null)
            throw new NFCNotSupported();
        if (!nfcAdpt.isEnabled())
            throw  new  NFCNotEnabled();
    }
         //Este es un método para grabar datos en una etiqueta NFC.
        // Si es necesario, primero puede formatear la etiqueta y luego grabar sus datos en ella.
    public void escribirTag(Tag etiqueta, NdefMessage mensaje) {
        Toast.makeText(mContext, "llega a escribir", Toast.LENGTH_SHORT).show();
        if (etiqueta != null) {
                try {
                    Ndef ndefTag = Ndef.get(etiqueta);
                    if (ndefTag == null) {
                        NdefFormatable nForm = NdefFormatable.get(etiqueta);
                        if (nForm != null) {
                            nForm.connect();
                            nForm.format(mensaje);
                            nForm.close();
                        }
                    } else {
                        ndefTag.connect();
                        ndefTag.writeNdefMessage(mensaje);
                        ndefTag.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //Este es un método para crear un mensaje que contiene un enlace o un número de teléfono.
    public NdefMessage createUriMessage (String link, String tipo) {
        NdefRecord registro = android.nfc.NdefRecord.createUri (tipo + link);
        NdefMessage msg = new NdefMessage(new NdefRecord[]{registro});
        return msg;
        }
    //Aquí puede ver el método para un mensaje que contiene texto sin formato.
    public NdefMessage createTextMessage(String contenido) {
            try {
                byte[] lang = Locale.getDefault().getLanguage().getBytes(StandardCharsets.UTF_8);
                byte[] texto = contenido.getBytes(StandardCharsets.UTF_8); // Contenido en UTF-8

                int langSize = lang.length;
                int textLength = texto.length;

                ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + langSize + textLength);
                payload.write((byte) (langSize & 0x1F));
                payload.write(lang, 0, langSize);
                payload.write(texto, 0, texto.length);
                NdefRecord registro = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
                return new NdefMessage(new NdefRecord[]{registro});
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    //Este método le permitirá crear un mensaje con coordenadas de ubicación.
    public NdefMessage createGeoMessage() {
            String geoUri = "geo:" + 48.471066 + "," + 35.038664;
            NdefRecord geoUriRecord = NdefRecord.createUri(geoUri);
            return new NdefMessage(new  NdefRecord[]{geoUriRecord});
        }
        //En este punto, debe mencionar los errores no típicos en su código que el usuario verá si NFC no está disponible.
    public static class NFCNotSupported extends Exception {
            public NFCNotSupported() {
                super();
            }
        }
    public static class NFCNotEnabled extends Exception {
        public NFCNotEnabled() {
            super();
        }
    }

}

