package com.example.covid_19indiatracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DistrictAdapter extends ArrayAdapter<DistrictModel> {
    public DistrictAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    private Context context;
    private List<DistrictModel> districtModelsList;
    private List<DistrictModel> districtModelsListFiltered;

    public DistrictAdapter(Context context, List<DistrictModel> districtModelsList) {
        super(context, R.layout.district_list_items,districtModelsList);

        this.context = context;
        this.districtModelsList = districtModelsList;
        this.districtModelsListFiltered = districtModelsList;
    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView txtState, txtCase, t_txtcase, txtActive, txtRecover, t_txtRecover,
                txtDeath, t_txtDeaths;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );


        if (convertView == null) {
            // Set the border of View (ListView Item)

            convertView = inflater.inflate(R.layout.district_list_items, null);
            viewHolder = new ViewHolder();


            viewHolder.txtState = convertView.findViewById(R.id.tvStateName);
            viewHolder.txtCase = convertView.findViewById(R.id.tvTotalCases);
            viewHolder.txtActive = convertView.findViewById(R.id.tvTotalActive);
            viewHolder.txtRecover = convertView.findViewById(R.id.tvTotalRecovered);
            viewHolder.txtDeath = convertView.findViewById(R.id.tvDeath);
            viewHolder.t_txtcase = convertView.findViewById(R.id.tvTodayCase);
            viewHolder.t_txtRecover = convertView.findViewById(R.id.tvTodayRecovered);
            viewHolder.t_txtDeaths = convertView.findViewById(R.id.tvTodayDeath);


            convertView.setTag(viewHolder);
            convertView.setBackground(getContext().getDrawable(R.drawable.list_view_border));
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.WHITE);
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtState.setText(districtModelsListFiltered.get(position).getDistrict());
        viewHolder.txtCase.setText(districtModelsListFiltered.get(position).getCases());
        viewHolder.txtActive.setText(districtModelsListFiltered.get(position).getActive());
        viewHolder.txtRecover.setText(districtModelsListFiltered.get(position).getRecovered());
        viewHolder.txtDeath.setText(districtModelsListFiltered.get(position).getDeaths());

        String s1 = districtModelsListFiltered.get(position).gettCase();
        String s2 = districtModelsListFiltered.get(position).gettRecover();
        String s3 = districtModelsListFiltered.get(position).gettDeath();
        if(s1.equals("0")) {
            viewHolder.t_txtcase.setText("");
        } else {
            viewHolder.t_txtcase.setText("+"+ districtModelsListFiltered.get(position).gettCase());
        }
        if(s2.equals("0")) {
            viewHolder.t_txtRecover.setText("");
        } else {
            viewHolder.t_txtRecover.setText("+"+ districtModelsListFiltered.get(position).gettRecover());
        }
        if(s3.equals("0")) {
            viewHolder.t_txtDeaths.setText("");
        } else {
            viewHolder.t_txtDeaths.setText("+"+ districtModelsListFiltered.get(position).gettDeath());
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return districtModelsListFiltered.size();
    }

    @Nullable
    @Override
    public DistrictModel getItem(int position) {
        return districtModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = districtModelsList.size();
                    filterResults.values = districtModelsList;

                }else{
                    List<DistrictModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(DistrictModel itemsModel: districtModelsList){
                        if(itemsModel.getDistrict().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                districtModelsListFiltered = (List<DistrictModel>) results.values;
                DistrictActivity.districtModelsList = (List<DistrictModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}

