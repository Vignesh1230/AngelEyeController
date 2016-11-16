package au.com.lumox.angeleyecontroller;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Vignesh Murugan on 10/12/15.
 */
public class MixerFragment extends Fragment {


    private SeekBar seekBarRedish;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;
    private SeekBar seekBarBrightness;
    private TextView textViewRed;
    private TextView textViewGreen;
    private TextView textViewBlue;
    private TextView textViewBrightness;
    private ImageView RGBColorView;

    public int serialVal = 8; //This changes how frequently the serial output is sent, instead of every 1 val change.
    public int redValue = 0;
    public int blueValue = 0;
    public int greenValue = 0;
    public int brightnessValue = 255;
    public int oldRedValue = serialVal;
    public int oldBlueValue = serialVal;
    public int oldGreenValue = serialVal;
    public int oldbrightnessValue = serialVal;

    public MixerFragment() {
    }

    ValuesChanged mCallback;

    // Container Activity must implement this interface
    public interface ValuesChanged {
        void SendMessage(String messageToSend);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ValuesChanged) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ValuesChanged");
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mixer, container, false);



        seekBarRedish = (SeekBar) rootView.findViewById(R.id.seekBarRed);
        seekBarGreen = (SeekBar) rootView.findViewById(R.id.seekBarBlue);
        seekBarBlue = (SeekBar) rootView.findViewById(R.id.seekBarGreen);
        seekBarBrightness = (SeekBar) rootView.findViewById(R.id.seekBarBrightness);
        textViewRed = (TextView) rootView.findViewById(R.id.textViewRed);
        textViewGreen = (TextView) rootView.findViewById(R.id.textViewGreen);
        textViewBlue = (TextView) rootView.findViewById(R.id.textViewBlue);
        textViewBrightness = (TextView) rootView.findViewById(R.id.textViewBrightness);

        RGBColorView = (ImageView)  rootView.findViewById(R.id.RGBColorView);

        seekBarRedish.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {





            @Override
            public void onProgressChanged(SeekBar seekBar, int progressR, boolean fromUser) {
                redValue = progressR;
                RGBColorView.setBackgroundColor(Color.argb(brightnessValue,redValue, greenValue, blueValue ));
                //brightnessValue = Math.round ((((float) brightnessValue) / 255) * 100);
                textViewRed.setText("Red: " + redValue);

                if (Math.abs(oldRedValue - redValue) >= serialVal ){
                    mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");
                    oldRedValue = redValue;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                //String hex = String.format("#%02x%02x%02x", redValue, 0, 0);
                //RGBColorView.setBackgroundColor(Color.parseColor(col));

                //textViewRedVal.setText(redValue);
                //Toast.makeText(getApplicationContext(), redValue, Toast.LENGTH_SHORT).show();
            }
        });

        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressB, boolean fromUser) {
                blueValue = progressB;
                RGBColorView.setBackgroundColor(Color.argb(brightnessValue,redValue, greenValue, blueValue ));
                //brightnessValue = Math.round ((((float) brightnessValue) / 255) * 100);
                textViewBlue.setText("Blue: " + blueValue);

                if (Math.abs(oldBlueValue - blueValue) >= serialVal ){
                    mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");
                    oldBlueValue = blueValue;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progressG, boolean fromUser) {
                greenValue = progressG;
                RGBColorView.setBackgroundColor(Color.argb(brightnessValue,redValue, greenValue, blueValue ));
                //brightnessValue = Math.round ((((float) brightnessValue) / 255) * 100);
                textViewGreen.setText("Green: " + greenValue);

                if (Math.abs(greenValue - oldGreenValue) >= serialVal ){
                    mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");
                    oldGreenValue = greenValue;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {



            }
        });

        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressBr, boolean fromUser) {
                brightnessValue = progressBr;
                RGBColorView.setBackgroundColor(Color.argb(brightnessValue,redValue, greenValue, blueValue ));

                brightnessValue = Math.round ((((float) brightnessValue) / 255) * 100);

                textViewBrightness.setText("Brightness: " + brightnessValue);



                if (Math.abs(brightnessValue - oldbrightnessValue) >= serialVal ){
                    mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");
                    oldbrightnessValue = brightnessValue;
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });




        return rootView;
    }

}
