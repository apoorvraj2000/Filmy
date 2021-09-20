package com.example.filmy.dao;


import com.example.filmy.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDao {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference usersCollection=db.collection("users");

    public  void addUser(User user){
        if(user!=null){
            //Adding data in firebase fire store
            usersCollection.document(user.displayName).set(user);

        }
    }
}
