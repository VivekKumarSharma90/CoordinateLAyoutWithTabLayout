package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.android2.coordinatelayoutwithtablayout.MainActivity;
import com.vivek.android2.coordinatelayoutwithtablayout.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Model.Model;

/**
 * Created by android2 on 2/8/17.
 */

public class Second extends Fragment {
    String TAG = "Second Class";
    SwipeRefreshLayout srl;
    RecyclerView recyclerView;
    ArrayList<Model> arrModel = new ArrayList<>();
    Model model = new Model();
    private String feedUrl = "http://stacktips.com/api/get_category_posts/?dev=1&slug=android";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //This line set progress bar color
        srl.setColorSchemeColors(Color.parseColor("#ff0000"));

        //This line also set progress bar color
        srl.setColorSchemeResources(R.color.colorPrimaryDark);

        new DownloadFilesTask().execute(feedUrl);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownloadFilesTask().execute(feedUrl);
            }
        });
        return view;
    }

    private class DownloadFilesTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            if (null != arrModel) {
                updateList();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            // getting JSON string from URL
            JSONObject json = getJSONFromUrl(params[0]);

            //parsing json data
            parseJson(json);
            return null;
        }
    }

    public JSONObject getJSONFromUrl(String url) {
        InputStream is = null;
        JSONObject jObj = null;
        String json = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

            jObj = new JSONObject(json);

        } catch (Exception e) {
            Log.e("Error", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public void parseJson(JSONObject json) {
        try {
            if (json.getString("status").equalsIgnoreCase("ok")) {
                JSONArray posts = json.getJSONArray("posts");

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = (JSONObject) posts.getJSONObject(i);
                    model.setName(post.getString("title"));
                    arrModel.add(model);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateList() {
        recyclerView.setAdapter(new MyAdapter(arrModel));

        if (srl.isRefreshing()) {
            srl.setRefreshing(false);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        ArrayList<Model> arrModel = new ArrayList<>();

        public MyAdapter(ArrayList<Model> arrModel) {
            this.arrModel = arrModel;
            Toast.makeText(getActivity(), "" + this.arrModel.size() + "", Toast.LENGTH_SHORT).show();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text_view);
            }
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_second, parent, false);
            return new MyAdapter.MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(arrModel.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return arrModel.size();
        }
    }
}
