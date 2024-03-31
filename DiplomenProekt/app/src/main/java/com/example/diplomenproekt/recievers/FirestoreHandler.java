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

    // int user_id, int device_id, String device_mac_address, String device_name, boolean current_state, List<Integer> current_color
    public void addDevice(HashMap<String, Object> itemData) {
        db.collection("devices")
                .add(itemData)
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
    }

    public void updateDevice(String device_id, HashMap<String, Object> itemNewData) {
        // [START update_document]
        DocumentReference device_reference = db.collection("devices").document(device_id);

        // Set the "isCapital" field of the city 'DC'
        device_reference
                .update(itemNewData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("documen", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Err updating device", "Error updating document", e);
                    }
                });
        // [END update_document]
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
