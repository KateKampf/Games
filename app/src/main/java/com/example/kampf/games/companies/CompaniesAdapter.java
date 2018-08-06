package com.example.kampf.games.companies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kampf.games.R;
import com.example.kampf.games.network.GbObjectResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.ViewHolder> {

    private List<GbObjectResponse> companies = new ArrayList<>();
    private final Callback callback;

    public CompaniesAdapter(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_company, parent, false);
        final ViewHolder holder = new ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GbObjectResponse company = companies.get(holder.getAdapterPosition());
                callback.onCompanyClick(company);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GbObjectResponse company = companies.get(position);
        Picasso.get().load(company.getImage().getSmallUrl()).into(holder.ivPicture);
        holder.tvName.setText(company.getName());
        holder.tvDeck.setText(company.getDeck());
        holder.tvCountry.setText(company.getLocationCountry());
        holder.tvCity.setText(company.getLocationCity());

    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public void addAll(List<GbObjectResponse> companiesToAdd) {

        companies.addAll(companiesToAdd);
        notifyDataSetChanged();

    }

    public void replaceAll(List<GbObjectResponse> companiesToReplace) {

        companies.clear();
        companies.addAll(companiesToReplace);
        notifyDataSetChanged();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPicture;
        TextView tvName;
        TextView tvDeck;
        TextView tvCountry;
        TextView tvCity;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivCompanyPicture);
            tvName = itemView.findViewById(R.id.tvCompanyName);
            tvDeck = itemView.findViewById(R.id.tvCompanyDeck);
            tvCountry = itemView.findViewById(R.id.tvCompanyCountry);
            tvCity = itemView.findViewById(R.id.tvCompanyCity);
        }
    }

    public interface Callback {
        void onCompanyClick(GbObjectResponse company);
    }
}
