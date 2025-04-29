package id.co.clinux01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class NoteAdd extends AppCompatActivity {

    private EditText inputTitle, inputContent;
    private Button btnSave;
    private ImageButton backBtn;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_add);

        // Inisialisasi komponen UI
        inputTitle = findViewById(R.id.inputTitle);
        inputContent = findViewById(R.id.inputContent);
        btnSave = findViewById(R.id.btnSave);
        backBtn = findViewById(R.id.back);
        dbRef = FirebaseDatabase.getInstance().getReference("notes");

        // Tombol Simpan
        btnSave.setOnClickListener(v -> {
            String id = UUID.randomUUID().toString();
            String title = inputTitle.getText().toString().trim();
            String content = inputContent.getText().toString().trim();

            if (!title.isEmpty() || !content.isEmpty()) {
                Note note = new Note(id, title, content);
                dbRef.child(id).setValue(note);
            }
            finish(); // Kembali ke activity sebelumnya
        });

        // Tombol Back
        backBtn.setOnClickListener(v -> {
            finish(); // Tutup activity saat tombol back diklik
        });
    }
}
