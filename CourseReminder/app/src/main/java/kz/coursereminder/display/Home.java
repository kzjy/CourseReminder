package kz.coursereminder.display;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kz.coursereminder.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private View view;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Drawable backgroundDrawable = ((MainActivity) getActivity()).getBackgroundDrawable();
        ((ImageView)view.findViewById(R.id.home_background)).setImageDrawable(backgroundDrawable);
        Drawable iconDrawable = ((MainActivity) getActivity()).getIconDrawable();
        ((ImageView)view.findViewById(R.id.home_icon)).setImageDrawable(iconDrawable);
        String user = "Welcome, " +  ((MainActivity) getActivity()).getUserName();
        ((TextView) view.findViewById(R.id.home_user)).setText(user);
        setAccentColor();
        return view;
    }

    private void setAccentColor() {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        view.findViewById(R.id.home_themed_accent).setBackgroundColor(color);
    }
}
