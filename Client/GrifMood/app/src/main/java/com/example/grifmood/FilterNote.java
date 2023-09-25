package com.example.grifmood;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterNote extends Filter {

    ArrayList<ConditionResponse> filterList;

    AdapterNote adapterNote;

    public FilterNote(ArrayList<ConditionResponse> filterList, AdapterNote adapterNote) {
        this.filterList = filterList;
        this.adapterNote = adapterNote;
    }
    //поиск
    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        if(charSequence!= null || charSequence.length()>0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<ConditionResponse> filteredModels = new ArrayList<>();
            for(int i = 0;i<filterList.size();i++){
                if(filterList.get(i).getMyDate().toUpperCase().contains(charSequence)){
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;

        }
        else{
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapterNote.ArrayList = (ArrayList<ConditionResponse>)filterResults.values;

        adapterNote.notifyDataSetChanged();
    }
}
