package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    String category;
    CustomAdapter customAdapter;
    ArrayList<UserPlace> userPlaceList=new ArrayList<>();
    ListView listView;
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        firebaseFirestore= FirebaseFirestore.getInstance();
        listView=findViewById(R.id.listView);
        getDataList();
    }


    public void getDataList(){
        customAdapter= new CustomAdapter(this,userPlaceList);
        Intent intent=getIntent();
        category=intent.getStringExtra("category");


        CollectionReference collectionReference = firebaseFirestore.collection("users");
        collectionReference.whereEqualTo("category", category).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null){
                    for(DocumentSnapshot snapshot:value.getDocuments()){
                        Map<String,Object> user = snapshot.getData();
                        if(user.containsKey("workplace") && user.containsKey("phone") && user.containsKey("email") && user.containsKey("location") && user.containsKey("category")){
                            String workplaceName = (String) user.get("workplace");
                            String phoneNumber = (String) user.get("phone");
                            String uCategory= (String) user.get("category");
                            String uEmail= (String) user.get("email");
                            Map<String,Object> wLocation = (Map<String, Object>) user.get("location");
                            System.out.println(wLocation);
                            Double uLatitude = (Double) wLocation.get("latitude");
                            Double uLongitude = (Double) wLocation.get("longitude");

                            UserPlace userPlace= new UserPlace(workplaceName,uEmail,uCategory,phoneNumber,uLatitude,uLongitude);
                            System.out.println(userPlace.name);
                            System.out.println(userPlace.phone);
                            userPlaceList.add(userPlace);
                            customAdapter.notifyDataSetChanged();
                            listView.setAdapter(customAdapter);
                        }
                    }
                }
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ListActivity.this,WorkPlaceInfo.class);
                intent.putExtra("place",userPlaceList.get(i));
                startActivity(intent);
            }
        });



    }
    ArrayList<bina> binalar=new ArrayList<>();
    File f= new File("girdi.txt");
    Scanner dosya = new Scanner(f);
    while(dosya.hasNextLine())
        for(int i=0; i<=2; j++) {
            Bina bina = new bina();
            bina.baslagic = dosya.nextInt();
            bina.yukseklik= dosya.nextInt();
            bina.genislik = dosya.nextInt();
            binalar.add(bina);
        }
        dosya.nextLine();
    }
    public static void sortingWithSelection(int arr[]) {
        for (int i = 0; i < binalar.length; i++) {
            int min = binalar[i].baslangic;
            for (int j = i + 1; j < binalar.length; j++) {
                if (binalar[j].baslangic < min) {
                    min = binalar[j].baslangic;
                }
            }
            int temp = arr[i];
            binalar[i] = binalar[min];
            binalar[i].baslangic = temp;
        }
    }
    for(int i = 0; i<binalar.length; i++){
        int sonKoordinat = binalar[i].baslangic + binalar[i].genislik;
        System.out.println("("+binalar[i].baslangic+","+sonKoordinat+"),");
    }
}