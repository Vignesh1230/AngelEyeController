package au.com.lumox.angeleyecontroller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class ModesFragment extends Fragment {

    ImageButton mode1_1;

    public ModesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_modes, container, false);

        mode1_1 = (ImageButton) rootView.findViewById(R.id.modeCol1_1);

        //final ColorPickerView picker = new ColorPickerView(getContext());

        return rootView;
    }

}
