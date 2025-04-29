package id.co.clinux01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheatSheetAdapter extends RecyclerView.Adapter<CheatSheetAdapter.ViewHolder> {
    private List<CheatSheetItem> cheatList;
    private Context context;

    public CheatSheetAdapter(Context context, List<CheatSheetItem> cheatList) {
        this.context = context;
        this.cheatList = cheatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cheat_sheet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheatSheetItem item = cheatList.get(position);
        holder.title.setText(item.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CodeDetail.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("content", item.getContent());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cheatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cheatTitle);
        }
    }

    public void filterList(List<CheatSheetItem> filteredList) {
        cheatList = filteredList;
        notifyDataSetChanged();
    }
}
