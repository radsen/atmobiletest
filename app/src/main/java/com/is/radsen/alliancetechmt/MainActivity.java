package com.is.radsen.alliancetechmt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ContactAdapter adapter;
    List<Contact> result = new ArrayList<Contact>();
    private ListView initialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ContactAdapter(result, getApplicationContext());
        PlaceholderFragment listFragment = new PlaceholderFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, listFragment)
                    .commit();
        }

        AllianceTechAPICall call = new AllianceTechAPICall();
        call.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setInitialAdapter(ListView initialAdapter) {
        this.initialAdapter = initialAdapter;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{

        private ListView lv;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            lv = (ListView) getView().findViewById(R.id.lvRegistrants);
            ((MainActivity) getActivity()).setInitialAdapter(lv);
        }
    }

    private class AllianceTechAPICall extends AsyncTask<String, Void, List<Contact>> implements AdapterView.OnItemClickListener{
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading contacts...");
            dialog.show();
        }


        @Override
        protected List<Contact> doInBackground(String... params) {

            try {
                URL url = new URL("http://dev4.ieventstest.com/restapi/registration");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String Authorization = "Authorization";
                String userpwd = "mobiletest@alliancetech.com:mtAlliancetech1*";
                String AuthType = "Basic " + Base64.encodeToString(userpwd.getBytes(), Base64.NO_WRAP);
                Log.d("Authorization", AuthType);
                connection.setRequestMethod("GET");
                connection.setRequestProperty(Authorization, AuthType);
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();

                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                int length = -1;
                while ((length = is.read(buffer)) != -1){
                    baos.write(buffer, 0, length);
                }

                String JSONResp = new String(baos.toByteArray());
                JSONObject jsonObject = new JSONObject(JSONResp);
                Log.d("WCR", JSONResp);
                JSONArray arr = jsonObject.getJSONArray("registrantList");

                for(int index = 0; index <= arr.length(); index++){
                    result.add(new Contact(arr.getJSONObject(index)));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<Contact> result){
            dialog.dismiss();
            initialAdapter.setAdapter(adapter);
            initialAdapter.setOnItemClickListener(this);
            adapter.setContactList(result);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("contact.detail", result.get(position).getJsonObject().toString());
            startActivity(intent);
        }
    }
}
