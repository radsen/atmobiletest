package com.is.radsen.alliancetechmt;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        TextView firstname;
        TextView lastname;
        TextView email;
        TextView address;
        TextView phone;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            firstname = (TextView)rootView.findViewById(R.id.firstnameval);
            lastname = (TextView)rootView.findViewById(R.id.lastnameval);
            email = (TextView)rootView.findViewById(R.id.emailval);
            address = (TextView)rootView.findViewById(R.id.addressval);
            phone = (TextView)rootView.findViewById(R.id.phoneval);

            return rootView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            try {
                JSONObject detail = new JSONObject(getActivity().getIntent().getStringExtra("contact.detail"));
                firstname.setText(detail.getString("first"));
                lastname.setText(detail.getString("last"));
                email.setText(detail.getString("email"));
                address.setText(detail.getString("address1"));
                phone.setText(detail.getString("phone"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
