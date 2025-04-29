package id.co.clinux01;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoteDetail extends AppCompatActivity {

    private TextView titleText, contentText;
    private ImageButton backButton, editButton;
    private Note note;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail);

        titleText = findViewById(R.id.titleText);
        contentText = findViewById(R.id.contentText);
        backButton = findViewById(R.id.back);
        editButton = findViewById(R.id.edit);

        note = (Note) getIntent().getSerializableExtra("note");

        if (note != null) {
            titleText.setText(note.getTitle());
            contentText.setText(note.getContent());
        }

        databaseRef = FirebaseDatabase.getInstance().getReference("notes");

        backButton.setOnClickListener(v -> finish());

        editButton.setOnClickListener(v -> showEditDialog());
    }

    private void showEditDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_note, null);
        EditText editTitle = dialogView.findViewById(R.id.editTitle);
        EditText editContent = dialogView.findViewById(R.id.editContent);

        editTitle.setText(note.getTitle());
        editContent.setText(note.getContent());

        new AlertDialog.Builder(this)
                .setTitle("Edit Catatan")
                .setView(dialogView)
                .setPositiveButton("Simpan", (dialog, which) -> {
                    String newTitle = editTitle.getText().toString();
                    String newContent = editContent.getText().toString();

                    if (!newTitle.isEmpty() && !newContent.isEmpty()) {
                        note.setTitle(newTitle);
                        note.setContent(newContent);

                        databaseRef.child(note.getId()).setValue(note)
                                .addOnSuccessListener(aVoid -> {
                                    titleText.setText(newTitle);
                                    contentText.setText(newContent);
                                    Toast.makeText(this, "Catatan diperbarui", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Gagal memperbarui catatan", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(this, "Judul dan isi tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
