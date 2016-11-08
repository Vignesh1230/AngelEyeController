package au.com.lumox.angeleyecontroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class TouchFragment extends Fragment {
    public int x= 0;
    public int y= 0;
    public int redValue = 0;
    public int blueValue = 0;
    public int greenValue = 0;
    public int brightnessVal = 100;
    private ImageView touchControl;
    private ImageView RGBColorView2;
    private TextView rgbVals;



    TouchValuesChanged mCallback;

    // Container Activity must implement this interface
    public interface TouchValuesChanged {
        public void SendMessage(String messageToSend);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TouchValuesChanged) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TouchValuesChanged");
        }
    }




    public TouchFragment() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_touch, container, false);


        touchControl = (ImageView) rootView.findViewById(R.id.touchControl);
        RGBColorView2 = (ImageView)  rootView.findViewById(R.id.RGBColorView2);
        rgbVals = (TextView) rootView.findViewById(R.id.labelConnection);


        final Bitmap bitmap;
        bitmap = ((BitmapDrawable)touchControl.getDrawable()).getBitmap();




        touchControl.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {

                final int x = (int)event.getX();
                final int y = (int)event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                }



                new Thread(new Runnable() {
                    public void run() {

                        if (y< bitmap.getHeight() && y>=0) {
                            if ( x >= 0 && x < bitmap.getWidth()) {
                                int pixel = bitmap.getPixel(x, y);
                                final int redValue = Color.red(pixel);
                                final int blueValue = Color.blue(pixel);
                                final int greenValue = Color.green(pixel);
                                if (redValue!=0 && blueValue !=0 && greenValue!=0) {
                                        mCallback.SendMessage("1," + redValue + "," + greenValue + "," + blueValue + "," + 100 + "\n");
                                }

                                //Changes UI in a background thread within worker thread.
                                RGBColorView2.post(new Runnable() {
                                    public void run() {
                                        RGBColorView2.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));
                                    }
                                });

                                rgbVals.post(new Runnable() {
                                    public void run() {
                                        rgbVals.setText("Red: "+ redValue + " Green: " + greenValue + " Blue: " + blueValue);
                                    }
                                });




                            }
                        }

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();

                return true;
            }
        });


        return rootView;
    }

}
