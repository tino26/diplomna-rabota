package com.example.diplomenproekt.recievers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.PersistentCacheSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreHandler {
    private final FirebaseFirestore db;
    public FirestoreHandler(FirebaseFirestore db) {
        this.db = db;
    }
    public void setup() {
        // [START get_firestore_instance]
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings =
                new FirebaseFirestoreSettings.Builder(db.getFirestoreSettings())
                        // Use memory-only cache
                        .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                        // Use persistent disk cache (default)
                        .setLocalCacheSettings(PersistentCacheSettings.newBuilder()
                                .build())
                        .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }

    public void setupCacheSize() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [START fs_setup_cache]
        FirebaseFirestoreSettings settings =
                new FirebaseFirestoreSettings.Builder(db.getFirestoreSettings())
                        .setLocalCacheSettings(PersistentCacheSettings.newBuilder()
                                // Set size to 100 MB
                                .setSizeBytes(1024 * 1024 * 100)
                                .build())
                        .build();
        db.setFirestoreSettings(settings);
        // [END fs_setup_cache]
    }

    public void addDevice(int user_id, int device_id, String device_mac_address, String device_name, boolean current_state, List<Integer> current_color) {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        Map<String, Object> item = new HashMap<>();
        item.put("user_id", user_id);
        item.put("device_mac_address", device_mac_address);
        item.put("device_name", device_name);
        item.put("current_state", current_state);
        item.put("current_state", current_state);
        item.put("current_state", current_state);
        item.put("current_state", current_state);
        item.put("current_state", current_state);
        item.put("current_color", current_color);

        // Add a new document with a generated ID
        db.collection("devices")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("added device", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error adding device", "Error adding document", e);
                    }
                });
        // [END add_ada_lovelace]
    }

    public void getDevice(String device_id) {
        // [START get_document]
        DocumentReference docRef = db.collection("devices").document(device_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("device data", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("no such device", "No such document");
                    }
                } else {
                    Log.d("failed get document", "get failed with ", task.getException());
                }
            }
        });
        // [END get_document]
    }

}
