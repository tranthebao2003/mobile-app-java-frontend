package com.bao.appgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bao.appgame.R;
import com.bao.appgame.adapter.NewestAdapter;
import com.bao.appgame.api.CategoryApi;
import com.bao.appgame.api.GameApi;
import com.bao.appgame.model.Category;
import com.bao.appgame.adapter.CategoryAdapter;
import com.bao.appgame.model.Game;
import com.bao.appgame.response.GamePageRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterCategory, adapterNewest;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private Button btnXemThem;
    private EditText searchInputHome;
    private int pageNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        bottomNavigation();
        callCategoryList();
        callGamePage(pageNo);
        searchGame();

        btnXemThem = findViewById(R.id.btnXemThem);
        btnXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNo += 1;
                Log.d("pageNo", "onClick: " + pageNo);
                callGamePage(pageNo);
            }
        });
    }

    // Dùng CategoryClickListener để chuyền sự kiện từ Adapter về homeActivity này.
    // rồi ở homeActivity xử lí sự kiện là call api game theo category. Chứ mình
    // ko thể call api bên trong recyclerView đc. Đó là lí do interface này tồn tại
    // ĐỊNH NGHĨA INTERFACE
    public interface CategoryClickListener {
        void onCategoryClick(Category category); // Callback khi user click vào một category
    }

    private Retrofit setupRetrofit(){
        // build retrofit và dùng GsonConverterFactory đế ảnh xạ json
        // vào entity và ngược lại
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/home/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private void callGameByCategoryId (Long categoryId){
        Retrofit retrofit = setupRetrofit();

        GameApi gameApi = retrofit.create(GameApi.class);

        Call<List<Game>> gameListByCategoryId = gameApi.getGameByCategoryId(categoryId);

        gameListByCategoryId.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    recyclerViewNewest(response.body());
                    Log.d("GameListByCategoryId", "tìm kiếm thành công" );
                } else {
                    Log.e("API Error", "Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.d("ErrorGameListByCategoryId", "onFailure: " + t);
            }
        });
    }

    private void searchGame(){
        searchInputHome = findViewById(R.id.searchInputHome);
        searchInputHome.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    String inputText = textView.getText().toString();
//                    Toast.makeText(HomeActivity.this, "searchInput " + inputText, Toast.LENGTH_SHORT).show();
                    callSearchGame(inputText);
                    return true;
                }
                return false;
            }
        });
    }

    private void callSearchGame(String searchInput){
        Retrofit retrofit = setupRetrofit();
        GameApi gameApi = retrofit.create(GameApi.class);

        Call<List<Game>> gameListBySearchInput = gameApi.getGameBySearchInput(searchInput);

        gameListBySearchInput.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    recyclerViewNewest(response.body());
                    Log.d("GameListBySearchInput", "tìm kiếm thành công" );
                } else {
                    Log.e("API Error", "Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.d("ErrorGameListBySearchInput", "onFailure: " + t);
            }
        });
    }

    private void callGamePage(int pageNo){
        // build retrofit và dùng GsonConverterFactory đế ảnh xạ json
        // vào entity và ngược lại
        Retrofit retrofit = setupRetrofit();

        // Create an implementation of the API endpoints defined by the service interface.
        GameApi gameApi = retrofit.create(GameApi.class);

        Call<GamePageRes> gamePageRes = gameApi.getGamePage(pageNo);
        // call api
        gamePageRes.enqueue(new Callback<GamePageRes>() {
            @Override
            public void onResponse(Call<GamePageRes> callGamePage, Response<GamePageRes> response) {
                // nhận json từ body của response gán nó cho jsonCategoryList
                GamePageRes gamePageRes1 = response.body();

                // truyền jsonCategoryList vào hàm recyclerViewCategory
                recyclerViewNewest(gamePageRes1.getGameList());
            }

            // nếu call api fail thì hàm này được gọi
            @Override
            public void onFailure(Call<GamePageRes> callCategoryList, Throwable t) {
                Log.d("ErrorGamePage", "onFailure: " + t);
            }
        });
    }

    private void callCategoryList(){
        // build retrofit và dùng GsonConverterFactory đế ảnh xạ json
        // vào entity và ngược lại
        Retrofit retrofit = setupRetrofit();

        // Create an implementation of the API endpoints defined by the service interface.
        CategoryApi categoryApi = retrofit.create(CategoryApi.class);

        Call<List<Category>> callCategoryList = categoryApi.getCategoryList();
        // call api
        callCategoryList.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> callCategoryList, Response<List<Category>> response) {
                // nhận json từ body của response gán nó cho jsonCategoryList
                List<Category> jsonCategoryList = response.body();

                // truyền jsonCategoryList vào hàm recyclerViewCategory
                recyclerViewCategory(jsonCategoryList);
            }

            // nếu call api fail thì hàm này được gọi
            @Override
            public void onFailure(Call<List<Category>> callCategoryList, Throwable t) {
                Log.d("ErrorCategoryList", "onFailure: " + t);
            }
        });
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

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
    }

    private void recyclerViewCategory(List<Category> categories) {
        /*
        RecyclerView hiển thị danh sách theo chiều ngang (cuộn ngang).
        Các mục trong danh sách sẽ được căn chỉnh theo thứ tự được cung cấp bởi Adapter của RecyclerView.
        */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerViewCategoryList = findViewById(R.id.recyclerViewCategory);

        // Gắn LinearLayoutManager vừa tạo vào RecyclerView để xác định cách hiển thị danh sách.
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        // truyền category list vào adapter để show lên screen
        // và IMPLEMENT interface CategoryClickListener()
        adapterCategory = new CategoryAdapter(categories, new HomeActivity.CategoryClickListener(){

            @Override
            public void onCategoryClick(Category category) {
                // gọi hàm callGameByCategoryId truyền id cho nó
                // để trả về danh sách game theo id
                callGameByCategoryId(category.getCategoryId());
            }
        });

        // set adapter cho recyclerView
        recyclerViewCategoryList.setAdapter(adapterCategory);
    }

    private void recyclerViewNewest(List<Game> gameList){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        recyclerViewPopularList = findViewById(R.id.recyclerViewNewest);
        recyclerViewPopularList.setLayoutManager(gridLayoutManager);

        adapterNewest = new NewestAdapter(gameList);
        recyclerViewPopularList.setAdapter(adapterNewest);
    }
}