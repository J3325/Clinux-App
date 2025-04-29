package id.co.clinux01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private ImageButton back, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        back = findViewById(R.id.back);
        logout = findViewById(R.id.logout);

        // Tombol back kembali ke MainActivity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup MenuActivity agar tidak bisa dikembalikan
            }
        });

        // Tombol logout keluar dari aplikasi
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Menutup semua activity dan keluar dari aplikasi
                System.exit(0);   // (Opsional) Mengakhiri proses aplikasi
            }
        });
    }
}
