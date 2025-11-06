package com.example.travelapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.models.Destination;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TourDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TOUR = "extra_tour";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        db = FirebaseFirestore.getInstance();
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        ImageView imgTour = findViewById(R.id.imgTour);
        TextView tvName = findViewById(R.id.tvTourName);
        TextView tvLocation = findViewById(R.id.tvTourLocation);
        TextView tvDescription = findViewById(R.id.tvTourDescription);
        TextView tvPrice = findViewById(R.id.tvTourPrice);
        Button btnBook = findViewById(R.id.btnBookTour);

        Destination tour = (Destination) getIntent().getSerializableExtra(EXTRA_TOUR);

        if (tour != null) {
            tvName.setText(tour.getName());
            tvLocation.setText(tour.getLocation());
            tvDescription.setText(tour.getDescription());
            tvPrice.setText("Giá: " + tour.getPrice() + " VND");

            if (!tour.getImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(tour.getImageUrl())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(imgTour);
            } else if (tour.getImageResId() != 0) {
                imgTour.setImageResource(tour.getImageResId());
            }
        }

        // Khi ấn nút “Đặt tour”
        btnBook.setOnClickListener(v -> {
            showBookingDialog(tour);
        });
    }

    private void showBookingDialog(Destination tour) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_booking, null);
        builder.setView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.edtCustomerName);
        EditText edtPhone = dialogView.findViewById(R.id.edtPhoneNumber);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirmBooking);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> booking = new HashMap<>();
            booking.put("tourName", tour.getName());
            booking.put("tourPrice", tour.getPrice());
            booking.put("customerName", name);
            booking.put("phoneNumber", phone);
            booking.put("email", email);
            booking.put("bookingDate", Timestamp.now());

            db.collection("bookings")
                    .add(booking)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Đặt tour thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi đặt tour: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
