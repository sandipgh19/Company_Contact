package com.example.sandip.company_contact;

import android.app.Activity;
import android.print.PrintAttributes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by sandip on 10/18/2016.
 */

public class CustomList extends ArrayAdapter<String> {
    private String[] names;
    private Activity context;

    public CustomList(Activity context, String[] names ){
        super(context,R.layout.custom_listview, names);
        this.context=context;
        this.names=names;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.custom_listview,null,true);
        TextView textViewname= (TextView) listViewItem.findViewById( R.id.company_name_view);
        textViewname.setText(names[position]);
        return listViewItem;
    }
}
