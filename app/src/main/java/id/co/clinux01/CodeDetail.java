package id.co.clinux01;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CodeDetail extends AppCompatActivity {

    private ImageButton back;
    private TextView titleTextView;
    private TextView commandTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_detail);

        back = findViewById(R.id.back);
        titleTextView = findViewById(R.id.titleTextView);
        commandTextView = findViewById(R.id.commandTextView);

        // Ambil data dari intent
        String title = getIntent().getStringExtra("title");
        String commands = getIntent().getStringExtra("content");

        // Tampilkan judul
        titleTextView.setText(title);

        // Buat teks bisa highlight komentar (#) dengan warna berbeda
        SpannableString spannable = new SpannableString(commands);
        String[] lines = commands.split("\n");

        int start = 0;
        for (String line : lines) {
            if (line.trim().startsWith("#")) {
                spannable.setSpan(
                        new ForegroundColorSpan(Color.parseColor("#077A7D")),
                        start,
                        start + line.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
            start += line.length() + 1; // +1 untuk newline
        }

        // Tampilkan teks dengan format berwarna
        commandTextView.setText(spannable);

        // Tombol back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // kembali ke MainActivity
            }
        });
    }
}
