package mobile.android.agendanfc;
import android.net.Uri;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class NFCManager {

    Context mContext;

    public NFCManager(Context context) {
        mContext = context;
    }
    public NdefMessage grabaApp(String app_data){
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{NdefRecord
                .createApplicationRecord(app_data)});
        return ndefMessage;
    }
    public NdefRecord createVcardRecord(String name, String tel){

        String payloadStr = "BEGIN:VCARD" + "\n" +
                "VERSION:2.1" + "\n" +
                "N:;" + name + "\n" +
                "TEL:" + tel + "\n" + "END:VCARD";
        byte[] uriField = payloadStr.getBytes(Charset.forName("US-ASCII"));
        byte[] payload = new byte[uriField.length + 1];
        System.arraycopy(uriField, 0, payload, 1, uriField.length);
        NdefRecord nfcRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                "text/vcard".getBytes(),
                new byte[0],
                payload);

        return nfcRecord;
    }
    public NdefMessage grabaVcard(String nombre,String nummero){
        NdefRecord[] records = new NdefRecord[1];
        records[0] = createVcardRecord(nombre, nummero);
        NdefMessage message = new NdefMessage(records);
        return message;
    }
    public NdefMessage grabaLlamada(String numero){
        NdefMessage mNfcMessage = createUriMessage(numero, "tel:");
        return mNfcMessage;
    }
    public NdefMessage grabaWhatssapp(String whatssapp){
        NdefMessage mNfcMessage = createUriMessage(whatssapp, "http://api.whatsapp.com/send?phone=");
        return mNfcMessage;
    }
    public NdefMessage grabaWhatsappMensaje(String numero, String mensaje){
        String link="api.whatsapp.com/send?phone="+numero+"&text="+mensaje;
        NdefMessage mNfcMessage = createUriMessage(link, "http://");
        return mNfcMessage;
    }
    public NdefMessage grabaLink( String link ){
        NdefRecord registro = android.nfc.NdefRecord.createUri ( link );
        NdefMessage msg = new NdefMessage(new NdefRecord[]{registro});
        return msg;
    }
    //Este es un m??todo para verificar si NFC est?? disponible en un dispositivo.
    public void verificarNFC(NfcAdapter nfcAdpt) throws NFCNotSupported, NFCNotEnabled {
         nfcAdpt = NfcAdapter.getDefaultAdapter(mContext);//aqui no se me esta rallando
        if (nfcAdpt == null)
            throw new NFCNotSupported();
        if (!nfcAdpt.isEnabled())
            throw  new  NFCNotEnabled();
    }
         //Este es un m??todo para grabar datos en una etiqueta NFC.
        // Si es necesario, primero puede formatear la etiqueta y luego grabar sus datos en ella.
    public void escribirTag(Tag etiqueta, NdefMessage mensaje) {
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
        //Este es un m??todo para crear un mensaje que contiene un enlace o un n??mero de tel??fono.
    public NdefMessage createUriMessage (String link, String tipo) {
        NdefRecord registro = android.nfc.NdefRecord.createUri (tipo + link );
        NdefMessage msg = new NdefMessage(new NdefRecord[]{registro});
        return msg;
        }
    //Aqu?? puede ver el m??todo para un mensaje que contiene texto sin formato.
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
    //Este m??todo le permitir?? crear un mensaje con coordenadas de ubicaci??n.
    public NdefMessage createGeoMessage(String contenido) {
            String geoUri = "geo:" + 48.471066 + "," + 35.038664;
        String uri = new String("http://api.whatsapp.com/send?phone="+contenido);
        Uri laUri = Uri.parse(uri);
        NdefRecord geoUriRecord = NdefRecord.createUri(geoUri);
            return new NdefMessage(new  NdefRecord[]{geoUriRecord});
        }
        //En este punto, debe mencionar los errores no t??picos en su c??digo que el usuario ver?? si NFC no est?? disponible.
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

