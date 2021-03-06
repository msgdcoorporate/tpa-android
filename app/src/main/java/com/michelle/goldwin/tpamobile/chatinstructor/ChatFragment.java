package com.michelle.goldwin.tpamobile.chatinstructor;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michelle.goldwin.tpamobile.R;
import com.michelle.goldwin.tpamobile.activity.ChatRoomActivity;
import com.michelle.goldwin.tpamobile.activity.HomeActivity;
import com.michelle.goldwin.tpamobile.global.LoggedUserInformation;
import com.michelle.goldwin.tpamobile.object.ChatMessage;
import com.michelle.goldwin.tpamobile.object.User;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

  private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference databaseReference;
  private FirebaseListAdapter<User> userFirebaseListAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }

    public void displayUserList(View view){

        final ListView instructorListView = (ListView) view.findViewById(R.id.listViewChat);

        userFirebaseListAdapter = new FirebaseListAdapter<User>(getActivity(),User.class,R.layout.single_instructor,FirebaseDatabase.getInstance().getReference().child("users")) {
            @Override
            protected void populateView(View v, User model, int position) {

                TextView instructorName     = (TextView) v.findViewById(R.id.lblInstructorName);
                ImageView instructorImage   = (ImageView) v.findViewById(R.id.imgProfile);

                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals("9DOHdZE6tvelBdyBWtwZuMzKPuo1") || FirebaseAuth.getInstance().getCurrentUser().getUid().equals("rL9F6eUljrWbTfGZoStgV05n1BH3")) {

                    if (!model.fullname.equals(LoggedUserInformation.getInstance().getFullname())) {
                        instructorImage.setVisibility(v.VISIBLE);
                        instructorName.setVisibility(v.VISIBLE);

                        instructorName.setText(model.fullname);
                        instructorImage.setImageResource(R.drawable.chat);
                    } else {
                        instructorImage.setVisibility(v.GONE);
                        instructorName.setVisibility(v.GONE);
                    }
                }
                else {
                    if (model.fullname.equals("Goldwin Japar") || model.fullname.equals("Michelle Neysa")) {
                        instructorImage.setVisibility(v.VISIBLE);
                        instructorName.setVisibility(v.VISIBLE);

                        instructorName.setText(model.fullname);
                        instructorImage.setImageResource(R.drawable.chat);
                    } else {
                        instructorImage.setVisibility(v.GONE);
                        instructorName.setVisibility(v.GONE);
                    }
                }

                }

        };

        instructorListView.setAdapter(userFirebaseListAdapter);

        instructorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView user = (TextView) view.findViewById(R.id.lblInstructorName);

                startActivity(new Intent(getContext(), ChatRoomActivity.class).putExtra("key",FirebaseAuth.getInstance().getCurrentUser().getUid()).putExtra("name",user.getText().toString()));

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_instructor, container, false);
        displayUserList(view);

        return view;
    }

}
