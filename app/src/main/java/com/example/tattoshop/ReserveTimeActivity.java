package com.example.tattoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveTimeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ReservationDAO {
    private final static String LOG_TAG = ReserveTimeActivity.class.getName();
    private final static String CHANNEL_ID = "1";
    Spinner spinner;
    FirebaseFirestore mStore;
    List<String> artists;
    ReservedTimeAdapter mTimeAdapter;
    RecyclerView mrecycler;
    EditText appointmentDate;
    String selectedArtist;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_time);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        spinner = findViewById(R.id.artistSpinner);
        mStore = FirebaseFirestore.getInstance();
        artists = new ArrayList<>();
        appointmentDate = findViewById(R.id.getAppointment);

        mStore.collection("Tetovalok").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot doc : task.getResult()){
                    artists.add(doc.getId());
                    Log.d(LOG_TAG, doc.getId());
                }
                //Akik tetoválnak azok közüll lehessen választani.
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ReserveTimeActivity.this, android.R.layout.simple_spinner_item, artists);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(this);
            }else{
                Log.w(LOG_TAG, "Valami nem jó LMAO" + task.getException());
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedArtist = adapterView.getItemAtPosition(i).toString();
        Log.d(LOG_TAG, selectedArtist);
        getReservedTime(selectedArtist);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Foglalás")
                .setContentText("Gyorsan foglalj időpontot!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, builder.build());
        //TODO lekért tetovalonak a foglalt idopontjait kiirni.
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //TODO
    }

    @Override
    public void insert() {
    }

    @Override
    public void delete() {

    }

    @Override
    public void getReservedTime(String artist) {
        DocumentReference df = mStore.collection("Tetovalok").document(artist);
        df.get().addOnCompleteListener(task -> {
            DocumentSnapshot ds = task.getResult();
            if(task.isSuccessful()){
                //RecycleView létrehozása
                mrecycler = findViewById(R.id.reservTimeRV);
                mrecycler.setLayoutManager(new GridLayoutManager(this, 1));
                mTimeAdapter = new ReservedTimeAdapter(this, ds.getData().values());
                mrecycler.setAdapter(mTimeAdapter);

            }else{
                Log.d(LOG_TAG, "WTF, csak olyant kérdezhet le ami létezik (What have you done?)");
            }
        });
    }

    public void reserveAppointment(View view) {
        // insert();
    }
}