package au.com.lumox.angeleyecontroller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class SettingsFragment extends Fragment {

    public static final String WiFiPreferences = "WifiPrefs";
    public static final String SSIDstring = "SSIDKey";
    public static final String PassString = "PassKey";
    SharedPreferences sharedpreferences;

    EditText ssidText,passText;
    Button saveBtn;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        ssidText = (EditText)rootView.findViewById(R.id.editSSIDText);
        passText = (EditText)rootView.findViewById(R.id.editPassText);
        saveBtn = (Button) rootView.findViewById(R.id.saveBtn);

        final Context context = getActivity();


        saveBtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                String ssid = ssidText.getText().toString();
                String pass = passText.getText().toString();

                SharedPreferences sharedPreferences = context.getSharedPreferences(WiFiPreferences, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(SSIDstring,ssid);
                editor.putString(PassString,pass);
                editor.commit();
                Toast.makeText(context,"WiFi Settings Saved",Toast.LENGTH_LONG).show();

            }
        });


        return rootView;
    }

}
