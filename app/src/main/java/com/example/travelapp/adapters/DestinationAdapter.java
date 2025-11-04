package com.example.travelapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.R;
import com.example.travelapp.models.Destination;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    private List<Destination> destinationList;
    private OnItemClickListener listener;
    private SharedPreferences prefs;
    private Set<String> favoriteSet;

    // Interface listener để truyền sự kiện click ra Fragment/Activity
    public interface OnItemClickListener {
        void onItemClick(Destination destination);
    }

    // Constructor
    public DestinationAdapter(Context context, List<Destination> destinationList) {
        this.destinationList = destinationList;
        prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        favoriteSet = new HashSet<>(prefs.getStringSet("favorite_names", new HashSet<>()));
    }

    // Setter để ExploreFragment có thể gán listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateList(List<Destination> newList) {
        destinationList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DestinationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_destination, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationAdapter.ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.tvName.setText(destination.getName());
        holder.tvLocation.setText(destination.getLocation());

        if (destination.getImageResId() != 0) {
            holder.imgDestination.setImageResource(destination.getImageResId());
        } else {
            holder.imgDestination.setImageResource(R.drawable.ic_launcher_foreground);
        }

        // ✅ Cập nhật trạng thái icon yêu thích
        boolean isFavorite = favoriteSet.contains(destination.getName());
        holder.imgFavorite.setImageResource(isFavorite ?
                R.drawable.ic_favorite_filled :
                R.drawable.ic_favorite_border);

        // 🔹 Click item → mở chi tiết
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(destination);
        });

        // 🔹 Click ❤️ → lưu / xóa khỏi danh sách yêu thích
        holder.imgFavorite.setOnClickListener(v -> {
            boolean currentFav = favoriteSet.contains(destination.getName());
            if (currentFav) {
                favoriteSet.remove(destination.getName());
                holder.imgFavorite.setImageResource(R.drawable.ic_favorite_border);
            } else {
                favoriteSet.add(destination.getName());
                holder.imgFavorite.setImageResource(R.drawable.ic_favorite_filled);
                showHeartAnimation(holder.imgFavorite); // 💗 thêm hiệu ứng bay lên
            }

            // Lưu lại vào SharedPreferences
            prefs.edit().putStringSet("favorite_names", favoriteSet).apply();
        });
    }

    @Override
    public int getItemCount() {
        return destinationList == null ? 0 : destinationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDestination, imgFavorite;
        TextView tvName, tvLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDestination = itemView.findViewById(R.id.imgDestination);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
        }
    }

    // 💗 Hiệu ứng trái tim bay lên
    private void showHeartAnimation(View view) {
        ImageView heart = new ImageView(view.getContext());
        heart.setImageResource(R.drawable.ic_favorite_filled);
        heart.setLayoutParams(new ViewGroup.LayoutParams(80, 80));

        // Lấy root view (decor view)
        ViewGroup rootView = (ViewGroup) ((ViewGroup) view.getRootView()).getChildAt(0);
        rootView.addView(heart);

        // Lấy vị trí ban đầu
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        heart.setX(location[0]);
        heart.setY(location[1] - 50);

        // Tạo animation bay lên + mờ dần
        heart.animate()
                .translationYBy(-250f)
                .alpha(0f)
                .setDuration(1000)
                .withEndAction(() -> rootView.removeView(heart))
                .start();
    }
}
