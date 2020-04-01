package com.manyong.smarttrashcan_final;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by manyong on 2017-06-09.
 */

public class NfcWriteActivity extends Activity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    Intent intent;
    TextView textView;
    EditText editText;
    boolean mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcwrite);

        editText = (EditText) findViewById(R.id.tag1);
        textView = (TextView) findViewById(R.id.tag2);

        nfcAdapter = nfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String str = editText.getText().toString();
        NdefMessage ndefMessage = getNdefMessage(str);
        write(ndefMessage, tagFromIntent);
    }

    private NdefMessage getNdefMessage(String text) {
        byte[] textBytes = text.getBytes();
        NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {}, textBytes);
        NdefMessage ndefMessage = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ndefMessage = new NdefMessage(textRecord);
        }
        return ndefMessage;
    }

    private boolean write (NdefMessage ndefMessage, Tag tagFromIntent) {
        try {
            Ndef ndef = Ndef.get(tagFromIntent);
            if(ndef != null) {
                ndef.connect();
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                Toast.makeText(this, "태그 기록 성공", Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        } catch (Exception e) {
            Toast.makeText(this, "태그 기록 실패", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void onResume() {
        super.onResume();
        if(nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
}
