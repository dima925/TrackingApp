package com.xs.android.tesseract.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.xs.android.tesseract.service.GetLocationService;
import com.xs.android.tesseract.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PopulationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PopulationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopulationFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private LinearLayout ll_populationBox;
    private ArrayList<BootstrapEditText> editTexts = new ArrayList<>();
    private BootstrapEditText et_curView;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    BootstrapButton btnSave;

    public PopulationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment PopulationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopulationFragment newInstance() {
        PopulationFragment fragment = new PopulationFragment();
       // fragment.setEnterTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_population, container, false);

        ll_populationBox = (LinearLayout) rootView.findViewById(R.id.population_box);

        for (int i=0; i< ll_populationBox.getChildCount(); i++){
            View view = ll_populationBox.getChildAt(i);
            if (view.getClass().getName().toString().contains("LinearLayout")) {

                BootstrapEditText tmpView = (BootstrapEditText) ((LinearLayout)view).getChildAt(1);
                editTexts.add(tmpView);
            }
        }
        btnSave = (BootstrapButton) rootView.findViewById(R.id.btnSave);

        init_views();

        return rootView;
    }

    private void init_views(){

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str = String.valueOf(monthOfYear + 1) + "/" +
                        String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
                et_curView.setText(str);
            }
        };

        final Calendar myCalendar = Calendar.getInstance();

        editTexts.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(9);
                new DatePickerDialog(getActivity(), dateSetListener,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTexts.get(10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(10);
                new DatePickerDialog(getActivity(), dateSetListener,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTexts.get(12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(12);
                String[] data = getResources().getStringArray(R.array.vote_type);
                new OptionBoxDialog(getActivity(), data).show();
            }
        });
        editTexts.get(13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(13);
                String[] data = getResources().getStringArray(R.array.mobile_app);
                new OptionBoxDialog(getActivity(), data).show();
            }
        });
        editTexts.get(20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(20);
                String[] data = getResources().getStringArray(R.array.civil_status);
                new OptionBoxDialog(getActivity(), data).show();
            }
        });
        editTexts.get(21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(21);
                String[] data = getResources().getStringArray(R.array.gender);
                new OptionBoxDialog(getActivity(), data).show();
            }
        });
        editTexts.get(37).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(37);
                String[] data = getResources().getStringArray(R.array.level_of_influence);
                new OptionBoxDialog(getActivity(), data).show();
            }
        });
        editTexts.get(38).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(38);
                String[] data = getResources().getStringArray(R.array.type_of_influence);
                new OptionBoxDialog(getActivity(), data).show();
            }
        });
        editTexts.get(39).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_curView = editTexts.get(39);
                String[] data = getResources().getStringArray(R.array.ambassador_network);
                new OptionBoxDialog(getActivity(), data).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Request_code", "save click!");
                Toast.makeText(getContext(),"button click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), GetLocationService.class);
                getContext().startService(intent);
            }
        });
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

    /*
        a class which show the option dialog to select a data
    */

    private class OptionBoxDialog extends Dialog {
        Context context;
        String[] options;
        BootstrapButton bt_ok, bt_cancel;

        public OptionBoxDialog(Context context, String[] options) {
            super(context);
            this.context = context;
            this.options = options;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_options);

            setTitle("Select an Option");
            final RadioGroup group = (RadioGroup) findViewById(R.id.rg_optionDlg);
            for (int i=0; i<options.length; i++){
                RadioButton option = new RadioButton(context);
                option.setText(options[i]);
                option.setId(View.generateViewId());
                group.addView(option);
            }

            bt_ok = (BootstrapButton) findViewById(R.id.bt_optionDlgOk);
            bt_cancel = (BootstrapButton) findViewById(R.id.bt_optionDlgCancel);
            bt_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int checkedId = group.getCheckedRadioButtonId();
                    if (checkedId == -1) return;

                    String data = ((RadioButton) findViewById(checkedId)).getText().toString();
                    et_curView.setText(data);

                    dismiss();
                }
            });
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
}
