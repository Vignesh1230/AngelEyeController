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
 * Created by anupamchugh on 10/12/15.
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

    public int redValue = 0;
    public int blueValue = 0;
    public int greenValue = 0;
    public int brightnessValue = 0;

    public MixerFragment() {
    }

    ValuesChanged mCallback;

    // Container Activity must implement this interface
    public interface ValuesChanged {
        public void SendMessage(String messageToSend);
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
                brightnessValue = Math.round ((((float) brightnessValue) / 255) * 100);
                textViewRed.setText("Red Val: " + redValue);

                mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");


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
                brightnessValue = Math.round ((((float) brightnessValue) / 255) * 100);
                textViewBlue.setText("Blue Val: " + blueValue);

                mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");


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
                brightnessValue = Math.round ((((float) brightnessValue) / 255) * 100);
                textViewGreen.setText("Green Val: " + greenValue);

                mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");


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

                mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + brightnessValue + "\n");




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
