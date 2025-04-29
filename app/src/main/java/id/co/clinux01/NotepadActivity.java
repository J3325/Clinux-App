package id.co.clinux01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class NotepadActivity extends AppCompatActivity {

    private ImageButton backButton, plus;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private ArrayList<Note> noteList;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        // Inisialisasi komponen UI
        backButton = findViewById(R.id.back);  // Tombol kembali (backButton)
        plus = findViewById(R.id.plus);
        recyclerView = findViewById(R.id.recyclerView);

        // Menyiapkan RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteList = new ArrayList<>();
        adapter = new NoteAdapter(this, noteList);
        recyclerView.setAdapter(adapter);

        // Menghubungkan ke Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference("notes");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                noteList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Note note = data.getValue(Note.class);
                    if (note != null) {
                        noteList.add(note);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        // Menangani klik pada tombol plus untuk menambah catatan
        plus.setOnClickListener(v -> {
            startActivity(new Intent(NotepadActivity.this, NoteAdd.class));
        });

        // Menangani klik pada tombol back untuk kembali ke MainActivity
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(NotepadActivity.this, MainActivity.class)); // Kembali ke MainActivity
            finish(); // Menutup NotepadActivity
        });
    }
}
