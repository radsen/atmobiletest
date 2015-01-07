package com.is.radsen.alliancetechmt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Radsen on 1/6/15.
 */
public class ContactAdapter extends ArrayAdapter<Contact>{

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    private List<Contact> contactList;
    private Context ctx;
    private LayoutInflater inflater = null;

    public ContactAdapter(List<Contact> contactList, Context ctx){
        super(ctx, R.layout.contact_item, contactList);
        this.contactList = contactList;
        this.ctx = ctx;

        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        if(contactList!=null){
            return contactList.size();
        }

        return 0;
    }

    public Contact getItem(int position){
       if(contactList != null){
           return contactList.get(position);
       }

        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;
        Contact contact = getItem(position);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.contact_item, null);
            holder = new ViewHolder();

            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(contact.getName());

        return convertView;
    }

    static class ViewHolder{
        TextView tvName;
    }
}


