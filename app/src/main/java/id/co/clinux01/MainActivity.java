package id.co.clinux01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton settings;
    private ImageButton notepad;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private CheatSheetAdapter adapter;
    private List<CheatSheetItem> cheatSheetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen
        settings = findViewById(R.id.settings);
        notepad = findViewById(R.id.notepad);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        // Ubah warna teks dan hint di SearchView menjadi putih
        int searchTextId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = searchView.findViewById(searchTextId);
        if (searchText != null) {
            searchText.setTextColor(getResources().getColor(android.R.color.white)); // Teks putih
            searchText.setHintTextColor(getResources().getColor(android.R.color.white)); // Hint putih
        }

        // Navigasi ke MenuActivity
        settings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        // Navigasi ke NotepadActivity
        notepad.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotepadActivity.class);
            startActivity(intent);
        });

        // Siapkan data dan tampilkan di RecyclerView
        cheatSheetList = getCheatSheetData();
        adapter = new CheatSheetAdapter(this, cheatSheetList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Fungsi pencarian
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    // Filter berdasarkan input pencarian
    private void filter(String text) {
        List<CheatSheetItem> filteredList = new ArrayList<>();
        for (CheatSheetItem item : cheatSheetList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    // Data Linux Cheat Sheet
    private List<CheatSheetItem> getCheatSheetData() {
        List<CheatSheetItem> list = new ArrayList<>();

        list.add(new CheatSheetItem("Files",
                "# Tampilkan direktori saat ini\npwd\n" +
                        "# Daftar semua file\nls -la\n" +
                        "# Pindah direktori\ncd <dir>\n" +
                        "# Buat direktori\nmkdir <dir>\n" +
                        "# Hapus file/folder\nrm <file/dir>\n" +
                        "# Salin file/folder\ncp <src> <dst>\n" +
                        "# Pindah/ubah nama file\nmv <src> <dst>\n" +
                        "# Cari file\nfind <path> -name <name>"));

        list.add(new CheatSheetItem("Users",
                "# Tampilkan user aktif\nwhoami\n" +
                        "# Info user & grup\nid\n" +
                        "# Jalankan sebagai root\nsudo <cmd>\n" +
                        "# Ubah permission\nchmod <perm> <file>\n" +
                        "# Ubah pemilik\nchown <user>:<group> <file>\n" +
                        "# Ubah password\npasswd\n" +
                        "# Tambah user ke grup\nusermod -aG <group> <user>"));

        list.add(new CheatSheetItem("Processes",
                "# Lihat proses\nps aux\n" +
                        "# Monitor proses\ntop / htop\n" +
                        "# Hentikan via PID\nkill <PID>\n" +
                        "# Hentikan via nama\npkill <name>\n" +
                        "# Waktu nyala sistem\nuptime\n" +
                        "# Matikan sistem\nshutdown now\n" +
                        "# Reboot sistem\nreboot"));

        list.add(new CheatSheetItem("Network",
                "# Interface jaringan\nip a\n" +
                        "# Cek koneksi\nping <host>\n" +
                        "# Port terbuka\nnetstat -tuln\n" +
                        "# Header HTTP\ncurl -I <url>\n" +
                        "# Unduh file\nwget <url>\n" +
                        "# Salin via SSH\nscp <src> <user@host>:<dst>"));

        list.add(new CheatSheetItem("Storage",
                "# Lihat disk\nlsblk\n" +
                        "# Penggunaan disk\ndf -h\n" +
                        "# Ukuran folder\ndu -sh <dir>\n" +
                        "# Mount disk\nmount <dev> <mnt>\n" +
                        "# Unmount disk\numount <mnt>"));

        list.add(new CheatSheetItem("Logs",
                "# Log kernel\ndmesg\n" +
                        "# Log systemd\njournalctl\n" +
                        "# Log (Debian)\ncat /var/log/syslog\n" +
                        "# Log (RHEL)\ncat /var/log/messages\n" +
                        "# Log autentikasi\ntail -f /var/log/auth.log"));

        list.add(new CheatSheetItem("Packages",
                "# APT (Ubuntu/Debian)\n" +
                        "sudo apt update\n" +
                        "sudo apt upgrade\n" +
                        "sudo apt install <pkg>\n" +
                        "sudo apt remove <pkg>\n\n" +
                        "# DNF (Fedora)\n" +
                        "sudo dnf install <pkg>\n" +
                        "sudo dnf remove <pkg>\n\n" +
                        "# Pacman (Arch)\n" +
                        "sudo pacman -Syu\n" +
                        "sudo pacman -S <pkg>\n" +
                        "sudo pacman -R <pkg>"));

        list.add(new CheatSheetItem("Services",
                "# Systemd\n" +
                        "systemctl start <svc>\n" +
                        "systemctl stop <svc>\n" +
                        "systemctl status <svc>\n" +
                        "systemctl enable <svc>\n" +
                        "systemctl disable <svc>\n\n" +
                        "# SysVinit\n" +
                        "service <svc> start\n" +
                        "service <svc> stop\n\n" +
                        "# OpenRC\n" +
                        "rc-service <svc> start\n" +
                        "rc-service <svc> stop\n" +
                        "rc-update add <svc> default"));

        list.add(new CheatSheetItem("Firewall",
                "# UFW\n" +
                        "sudo ufw enable\n" +
                        "sudo ufw allow 22\n" +
                        "sudo ufw deny 80\n" +
                        "sudo ufw status\n\n" +
                        "# Firewalld\n" +
                        "firewall-cmd --add-port=80/tcp --permanent\n" +
                        "firewall-cmd --reload\n\n" +
                        "# iptables\n" +
                        "iptables -A INPUT -p tcp --dport 22 -j ACCEPT\n" +
                        "iptables -A INPUT -p tcp --dport 80 -j DROP"));

        return list;
    }

}
