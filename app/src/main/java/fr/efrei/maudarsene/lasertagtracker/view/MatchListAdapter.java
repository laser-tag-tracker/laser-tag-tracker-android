package fr.efrei.maudarsene.lasertagtracker.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.MatchItemBinding;
import fr.efrei.maudarsene.lasertagtracker.model.Match;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MatchViewHolder> {

    private List<Match> matches;

    public MatchListAdapter(List<Match> matches) {
        this.matches = matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.match_item, parent, false);
        return new MatchViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        holder.binding.setMatch(this.matches.get(position));
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder {

        public MatchItemBinding binding;

        public MatchViewHolder(@NonNull MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
