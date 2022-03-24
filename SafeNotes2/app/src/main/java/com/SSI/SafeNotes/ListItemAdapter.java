package com.SSI.SafeNotes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListItemAdapter extends ArrayAdapter<Note> {
    Context mContext;
    int layoutResourceId;
    Context context;
    LayoutInflater inflater;
    List<Note> data = null;
    List<Note> data2 = null;


    private SparseBooleanArray mSelectedItemsIds;

    public ListItemAdapter(Context context, int layoutResourceId, List<Note>  data) {

        super(context, layoutResourceId, data);
        this.context = context;
        mSelectedItemsIds = new SparseBooleanArray();
        inflater = LayoutInflater.from(context);
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.data2 = data;
    }



    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Note> tempList=new ArrayList<Note>();
            //constraint is the result from text you want to filter against.
            //objects is your data set you will filter from
            if(constraint != null && data!=null) {
                int length=data2.size();
                int i=0;
                while(i<length){

                    Note item=data2.get(i);
                    if(item.getTitlu().toLowerCase().contains(constraint.toString().toLowerCase())){


                        tempList.add(item);}

                    i++;
                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {

            if(contraint.length()==0){
                data= data2;
            }else {
                data = (ArrayList<Note>) results.values;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private class ViewHolder {
        TextView titlu;
        TextView size;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        notifyDataSetChanged();
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item_adapter, null);
            // Locate the TextViews in listview_item.xml
            // aici punem elementele ....
            holder.titlu = (TextView) view.findViewById(R.id.titlu);
            holder.size = (TextView) view.findViewById(R.id.size);

            // Locate the ImageView in listview_item.xml
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
        if(data.get(position).getTitlu().length()>10){
        holder.titlu.setText(data.get(position).getTitlu().substring(0,10)+"...");}
        else{holder.titlu.setText(data.get(position).getTitlu());}
        holder.titlu.setTextColor(Color.BLACK);




        StringBuilder c2 = new StringBuilder( new SimpleDateFormat("MMM dd").format( new Date().getTime()) );
        StringBuilder d2 = new StringBuilder( new SimpleDateFormat("MMM dd").format( new Date((Integer.parseInt(data.get(position).getModified()))*1000L) ) );
        if(c2.toString().equals(d2.toString())){

            StringBuilder h2 = new StringBuilder( new SimpleDateFormat("HH:mm").format( new Date((Integer.parseInt(data.get(position).getModified()))*1000L) ) );
            holder.size.setText(h2 + " ");
        }
        else {
            holder.size.setText(d2 + " ");
        }
        holder.size.setTextColor(Color.BLACK);



        if(data.get(position).getSize().equals("c1"))
        {holder.titlu.setTextSize(14);
            holder.size.setTextSize(14);}
        else if(data.get(position).getSize().equals("c2"))
        {holder.titlu.setTextSize(20);
            holder.size.setTextSize(20);}
        else if(data.get(position).getSize().equals("c3"))
        {holder.titlu.setTextSize(25);
            holder.size.setTextSize(25);}
        else if(data.get(position).getSize().equals("c4"))
        {holder.titlu.setTextSize(30);
            holder.size.setTextSize(30);}




        if(data.get(position).getType().equals("c0"))
        {holder.titlu.setTypeface(Typeface.DEFAULT);
            holder.size.setTypeface(Typeface.DEFAULT);}
        else if(data.get(position).getType().equals("c1"))
        {holder.titlu.setTypeface(Typeface.SERIF);
            holder.size.setTypeface(Typeface.SERIF);}
        else if(data.get(position).getType().equals("c2"))
        {Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "font/Garamond.ttf");
            holder.titlu.setTypeface(custom_font);
            holder.size.setTypeface(custom_font);}
        else if(data.get(position).getType().equals("c3"))
        {  Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "font/Calibri.ttf");
            holder.titlu.setTypeface(custom_font);
            holder.size.setTypeface(custom_font);}
        else if(data.get(position).getType().equals("c4"))
        {Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "font/Georgia.ttf");
            holder.titlu.setTypeface(custom_font);
            holder.size.setTypeface(custom_font);}


        // Capture position and set to the ImageView
        notifyDataSetChanged();
        return view;

    }



    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
