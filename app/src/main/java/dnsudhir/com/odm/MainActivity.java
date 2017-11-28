package dnsudhir.com.odm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference myRef = firebaseDatabase.getReference("message");

    myRef.setValue("Hello World!");


    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {

        String value = dataSnapshot.getValue(String.class);
        Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
      }

      @Override public void onCancelled(DatabaseError databaseError) {


      }
    });

  }
}
