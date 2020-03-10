package AppBD;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

import Model.Consulta;
import Model.Exame;
import Model.Hospital;
import Model.Usuario;

public class BD {

    private static DatabaseReference databaseReference;
    private static FirebaseDatabase firebaseDatabase;
    private static FirebaseAuth autenticacao;
    public static String email;
    public static Consulta consulta;
    public static Exame exame;
    public static Usuario usuario;
    public static int cont = 0;
    public static int cont2 = 0;
    public static ArrayList<Hospital> hospitalList = new ArrayList<>();
    public static ArrayList<String> cidadesList = new ArrayList<>();

    public static DatabaseReference getFirebase() {
        if (databaseReference == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            databaseReference = firebaseDatabase.getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getAutenticacao() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

}
