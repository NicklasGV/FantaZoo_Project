package com.example.fantazoo_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.fantazoo_app.Fragments.AdminFragments.AdminFragment;
import com.example.fantazoo_app.Fragments.AnimalsFragment;
import com.example.fantazoo_app.Fragments.CagesFragment;
import com.example.fantazoo_app.Fragments.ZookeepersFragment;

public class MainActivity extends AppCompatActivity {
    public static RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rq = Volley.newRequestQueue(this);

        fragmentChanger(CagesFragment.class);

        initGui();
    }

    void initGui() {
        findViewById(R.id.btn_animals).setOnClickListener(v -> fragmentChanger(AnimalsFragment.class));
        findViewById(R.id.btn_cages).setOnClickListener(v -> fragmentChanger(CagesFragment.class));
        findViewById(R.id.btn_zookeepers).setOnClickListener(v -> fragmentChanger(ZookeepersFragment.class));
        findViewById(R.id.fab_admin).setOnClickListener(v -> fragmentChanger(AdminFragment.class));
    }

    private void fragmentChanger(Class c) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, c, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}