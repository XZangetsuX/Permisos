package mx.maxa.dev.t01permisos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class App extends AppCompatActivity {
    private static final int PICK_CONTACT = 5;
    private Button btnSMS;
    private Button btnCall;
    private Button btnContacts;
    private TextView txtContact = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);
        btnSMS = findViewById(R.id.btnSms);
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    sendSMS("6144477068", "El fin de los tiempos se acerca, orad y arrepentirse ante el gran monstruo de espagueti volador");
                } else {
                    int permiso = checkSelfPermission(Manifest.permission.SEND_SMS);
                    if (permiso != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 2);
                    } else
                        sendSMS("6144477068", "El fin de los tiempos se acerca, orad y arrepentirse ante el gran monstruo de espagueti volador");

                }
                // sendSMS("6142696889","El fin de los tiempos se acerca, orad y arrepentirse ante el gran monstruo de espagueti volador");

            }
        });
        btnCall = findViewById(R.id.btnLlamada);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    makeCall("6144477068");
                } else {
                    int permiso = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (permiso != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 3);
                    } else
                        makeCall("6144477068");

                }
            }
        });
        btnContacts = findViewById(R.id.btnContacto);
        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    browseContacts();
                } else {
                    int permiso = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (permiso != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 4);
                    } else
                        browseContacts();

                }
            }
        });
    }

    private void browseContacts() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
            Toast.makeText(this, "ira ira ira si jala la mugre esta!", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(this, "A la chistorra! algo pasó :(", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    private void makeCall(String phoneNum) {
        try {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNum, null)));
        } catch (Exception ex) {
            Toast.makeText(this, "Ave Maria Purisima! " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSMS(String phoneNum, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviadirijillo! ;)",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, "La sangre de cristo nos cubra! " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        txtContact = findViewById(R.id.txtContactoSel);
                        txtContact.setText(name);
                    }
                    c.close();
                }
                break;
        }
    }
    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    sendSMS("6142696889", "si jala");
                } else {
                    // permission denied
                }
                return;
            }
        }
    }*/

}
