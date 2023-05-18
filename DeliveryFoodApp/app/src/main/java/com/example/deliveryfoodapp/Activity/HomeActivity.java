package com.example.deliveryfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.view.ViewTreeObserver;
import android.widget.Button;

import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.deliveryfoodapp.Adapter.CategoryAdapter;

import com.example.deliveryfoodapp.Adapter.GridVideoAdapter;
import com.example.deliveryfoodapp.Adapter.PopularAdapter;
import com.example.deliveryfoodapp.Domain.CategoryDomain;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.Domain.VideoDomain;
import com.example.deliveryfoodapp.R;

import com.example.deliveryfoodapp.Domain.User;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private Button btnLogout;

    private ImageView imgUserAvatar, btn_search;

    private EditText edt_search;

    private GridView gridView2;
    private GridVideoAdapter adapter1;

    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = findViewById(R.id.txt_greetingUser);
        imgUserAvatar = findViewById(R.id.imgUserAvatar);
        btn_search = findViewById(R.id.btn_search);
        edt_search = findViewById(R.id.edt_search);

        FloatingActionButton floatingAction_btn  = findViewById(R.id.cartBtn);

//        floatingAction_btn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                BadgeDrawable badgeDrawable = BadgeDrawable.create(HomeActivity.this);
//                badgeDrawable.setNumber(2);
//                //Important to change the position of the Badge
//                badgeDrawable.setHorizontalOffset(30);
//                badgeDrawable.setVerticalOffset(20);
//                badgeDrawable.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.red));
////                badgeDrawable.setBadgeTextColor(Color.WHITE);
//
//                BadgeUtils.attachBadgeDrawable(badgeDrawable, floatingAction_btn, null);
//
//                floatingAction_btn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });



//        final TextView fullNameTextView = findViewById(R.id.txt_fullName);
//        final TextView emailTextView = findViewById(R.id.txt_emailAddress);
//        final TextView ageTextView = findViewById(R.id.txt_age);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullname = userProfile.fullname;
                    String email = userProfile.email;
                    String phone = userProfile.phone;
                    String imageUrl = userProfile.avatar;

                    greetingTextView.setText("Welcome, "+ fullname + "!");
//                    fullNameTextView.setText(fullname);
//                    emailTextView.setText(email);
//                    ageTextView.setText(age);

                    Glide.with(HomeActivity.this)
                            .load(imageUrl)
                            .transform(new RoundedCorners(20))
                            .into(imgUserAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Something wrong happend!", Toast.LENGTH_LONG).show();
            }
        });



        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        image_slider();
        searchFood();
        gridViewVideo();
    }

    private void  bottomNavigation() {
        FloatingActionButton floatingActionButton  = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout supportBtn = findViewById(R.id.supportBtn);
        LinearLayout shopBtn = findViewById(R.id.shopBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SupportActivity.class));
            }
        });

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ShopActivity.class));
            }
        });

    }


    private void searchFood() {

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = edt_search.getText().toString().trim();
                if (TextUtils.isEmpty(searchText)) {
                    Toast.makeText(HomeActivity.this, "Please enter the text to search", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean found = false;
                String[] arr = {"pizza", "pizza vegetable", "pizza seafood", "pizza sausage", "pizza mix", "pizza cheese", "pizza ham",  "burger", "hot dog", "potato", "bento", "chocolate cake", "strawberry cake", "gato cake", "cake", "waffle", "drink", "milk tea", "orange", "coffee", "smoothies", "ice cream", "donut"};

                for (String str : arr) {
                    if (str.equals(searchText) || str.equalsIgnoreCase(searchText)) {
                        found = true;
                        edt_search.setText("");
                        Toast.makeText(HomeActivity.this, "Found the results", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                        intent.putExtra("searchText", searchText);
                        startActivity(intent);
                        break;
                    }
                }

                if (!found) {
                    edt_search.setText("");
                    Toast.makeText(HomeActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Pizza", "cat_1"));
        category.add(new CategoryDomain("Burger", "cat_2"));
        category.add(new CategoryDomain("HotDog", "cat_3"));
        category.add(new CategoryDomain("Drink", "cat_4"));
        category.add(new CategoryDomain("Donut", "cat_5"));

        adapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }
    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni Pizza","pop_1", "Pepperoni is an American version of salami, made from a mixture of marinated pork and beef.", 9.0 ));
        foodList.add(new FoodDomain("Cheese Burger","pop_2", "Traditionally, the cheese is placed on top of the meat.", 8.0 ));
        foodList.add(new FoodDomain("Vegetable pizza","pop_3", " Arrange broccoli, radish, onion, bell pepper, carrot, and celery on top of the cream cheese mixture.", 8.8 ));

        adapter2 = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    private void image_slider(){
        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.slider1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider5, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider6, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
    }

    private void gridViewVideo(){
        gridView2 = findViewById(R.id.gridView2);

        ArrayList<VideoDomain> videoDomains = new ArrayList<>();
        videoDomains.add(new VideoDomain("Pizza", "video_1"));
        videoDomains.add(new VideoDomain("Bento", "video_2"));
        videoDomains.add(new VideoDomain("Cake", "video_3"));
        videoDomains.add(new VideoDomain("Drink", "video_4"));

        adapter1 = new GridVideoAdapter(getBaseContext(),videoDomains);
        gridView2.setAdapter(adapter1);

    }
}