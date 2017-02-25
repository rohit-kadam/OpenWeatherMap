package com.openweathermap.android.weather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.openweathermap.R;
import com.openweathermap.android.utils.App;
import com.openweathermap.core.models.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 25-02-2017.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> implements Filterable {

    private List<City> cityList;
    private List<City> searchCityList;

    public class CityViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RelativeLayout relativeLayout;

        public CityViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_root);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public CitiesAdapter(List<City> cityList) {
        this.cityList = cityList;
        this.searchCityList = cityList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_row, parent, false);

        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        City city = cityList.get(position);
        holder.title.setText(App.firstLetterCapital(city.getName()));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cityList = (List<City>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence charText) {
                FilterResults filterResults = new FilterResults();
                List<City> filterList = new ArrayList<City>();
                if (charText != null && charText.toString().trim().length() > 0) {
                    for (City mBy : searchCityList) {
                        if (mBy.getName().toLowerCase().contains(charText.toString().toLowerCase())) {
                            filterList.add(mBy);
                        }
                    }
                    filterResults.count = filterList.size();
                    filterResults.values = filterList;
                } else {
                    filterResults.count = searchCityList.size();
                    filterResults.values = searchCityList;
                }
                return filterResults;
            }
        };
    }

    public City getClickedItem(int position) {
        return cityList.get(position);
    }
}
