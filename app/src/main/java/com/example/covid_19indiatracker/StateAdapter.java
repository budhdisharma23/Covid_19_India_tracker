package com.example.covid_19indiatracker;

import android.app.Activity;
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

public class StateAdapter extends ArrayAdapter<StateModel> {
    public StateAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    private Context context;
    private List<StateModel> stateModelsList;
    private List<StateModel> stateModelsListFiltered;

    public StateAdapter(Context context, List<StateModel> countryModelsList) {
        super(context, R.layout.layout_list_items,countryModelsList);

        this.context = context;
        this.stateModelsList = countryModelsList;
        this.stateModelsListFiltered = countryModelsList;
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

            convertView = inflater.inflate(R.layout.layout_list_items, null);
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

        viewHolder.txtState.setText(stateModelsListFiltered.get(position).getState());
        viewHolder.txtCase.setText(stateModelsListFiltered.get(position).getCases());
        viewHolder.txtActive.setText(stateModelsListFiltered.get(position).getActive());
        viewHolder.txtRecover.setText(stateModelsListFiltered.get(position).getRecovered());
        viewHolder.txtDeath.setText(stateModelsListFiltered.get(position).getDeaths());

        String s1 = stateModelsListFiltered.get(position).gettCase();
        String s2 = stateModelsListFiltered.get(position).gettRecover();
        String s3 = stateModelsListFiltered.get(position).gettDeath();
        if(s1.equals("0")) {
            viewHolder.t_txtcase.setText("");
        } else {
            viewHolder.t_txtcase.setText("+"+ stateModelsListFiltered.get(position).gettCase());
        }
        if(s2.equals("0")) {
            viewHolder.t_txtRecover.setText("");
        } else {
            viewHolder.t_txtRecover.setText("+"+stateModelsListFiltered.get(position).gettRecover());
        }
        if(s3.equals("0")) {
            viewHolder.t_txtDeaths.setText("");
        } else {
            viewHolder.t_txtDeaths.setText("+"+stateModelsListFiltered.get(position).gettDeath());
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return stateModelsListFiltered.size();
    }

    @Nullable
    @Override
    public StateModel getItem(int position) {
        return stateModelsListFiltered.get(position);
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
                    filterResults.count = stateModelsList.size();
                    filterResults.values = stateModelsList;

                }else{
                    List<StateModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(StateModel itemsModel: stateModelsList){
                        if(itemsModel.getState().toLowerCase().contains(searchStr)){
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
                stateModelsListFiltered = (List<StateModel>) results.values;
                AffectedStates.stateModelsList = (List<StateModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
