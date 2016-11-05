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
        public void SendMessage(int red,int green,int blue,int brightness);
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
                    + " must implement ValuesChanged");
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


        touchControl.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                }

                ImageView touchControl = ((ImageView)v);

                Bitmap bitmap;
                bitmap = ((BitmapDrawable)touchControl.getDrawable()).getBitmap();


                if (y< bitmap.getHeight() && y>=0) {
                    if ( x >= 0 && x < bitmap.getWidth()) {
                        int pixel = bitmap.getPixel(x, y);
                        int redValue = Color.red(pixel);
                        int blueValue = Color.blue(pixel);
                        int greenValue = Color.green(pixel);
                        if (redValue!=0 && blueValue !=0 && greenValue!=0) {
                            RGBColorView2.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));
                            rgbVals.setText("Red: "+ redValue + " Green: " + greenValue + " Blue: " + blueValue);

                            mCallback.SendMessage(redValue,greenValue,blueValue,100);


                        }
                    }
                }
                return true;
            }
        });


        return rootView;
    }

}
