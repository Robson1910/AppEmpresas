package com.example.appempresas.appempresas;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by ROBSON on 13/08/2018.
 */

public class ConfiguracaoFirebase {
    private static DatabaseReference refenciaFirebase;

    public static DatabaseReference getFirebase() {
        if (refenciaFirebase == null) {
            refenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return refenciaFirebase;
    }
}
