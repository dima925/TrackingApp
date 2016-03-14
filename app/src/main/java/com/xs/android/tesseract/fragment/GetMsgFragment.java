package com.xs.android.tesseract.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xs.android.tesseract.Config;
import com.xs.android.tesseract.R;
import com.xs.android.tesseract.Session;
import com.xs.android.tesseract.helper.DividerItemDecoration;
import com.xs.android.tesseract.helper.GetMessageAdapter;
import com.xs.android.tesseract.helper.MessageItemData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetMsgFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetMsgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetMsgFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected RecyclerView mRecyclerView;
    protected ProgressBar mLoadingProgressBar;
    protected GetMessageAdapter mAdapter;
    private static final int DATASET_COUNT = 1;
    protected MessageItemData[] mDataset;

    public GetMsgFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment PopulationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetMsgFragment newInstance() {
        GetMsgFragment fragment = new GetMsgFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Session session = Session.getInstance(getContext());
        // remote server data load
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_msg, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        android.support.v7.widget.GridLayoutManager layoutManager = new android.support.v7.widget.GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingProgressBar = (ProgressBar) rootView.findViewById(R.id.load_progress);
        mLoadingProgressBar.setVisibility(View.VISIBLE);
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

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        // data load from server
        // .add("LoginForm[username]", username)
        RequestBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(Config.GETMESSAGE_ENDPOINT)
                .post(formBody)
                .build();


        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Read data on the worker thread
                final String response_data = response.body().string();
                Log.d("request_code", response_data.toString());
                try {
                    JSONObject js_data = new JSONObject(response_data);
                    if (js_data.has("status")) {
                        String statusMsg = js_data.getString("status");
                        if (statusMsg.equals("OK")) {
                            JSONArray messageData = js_data.getJSONArray("data");
                            mDataset = new MessageItemData[messageData.length()];
                            for (int i = 0; i < messageData.length(); i++) {
                                JSONObject js_item = messageData.getJSONObject(i);
                                String title = js_item.getString("title");
                                String content = js_item.getString("content");
                                String datetime = js_item.getString("datetime");
                                Config config = new Config();
                                datetime = config.convertDateTime(datetime);
                                Log.d("request_code",datetime);
                                mDataset[i] = new MessageItemData(title, content, datetime);
                            }
                            // mRecyclerView.setVisibility(View.VISIBLE);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter = new GetMessageAdapter(getActivity(),mDataset);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mLoadingProgressBar.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
