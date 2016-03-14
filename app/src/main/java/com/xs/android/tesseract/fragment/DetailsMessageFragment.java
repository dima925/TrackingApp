package com.xs.android.tesseract.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xs.android.tesseract.Config;
import com.xs.android.tesseract.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsMessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsMessageFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvDatetime;


    public DetailsMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment PopulationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsMessageFragment newInstance() {
        DetailsMessageFragment fragment = new DetailsMessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details_message, container, false);
        tvTitle = (TextView)rootView.findViewById(R.id.tv_messageTitle);
        tvContent = (TextView)rootView.findViewById(R.id.tv_messageContent);

        tvTitle.setText(Config.selectedMessageTitle);
        tvContent.setText(Config.selectedMessageContent);
        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
