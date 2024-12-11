package com.bao.appgame.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bao.appgame.R;
import com.bao.appgame.adapter.NewestAdapter;
import com.bao.appgame.model.Category;
import com.bao.appgame.adapter.CategoryAdapter;
import com.bao.appgame.model.Game;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterCategory, adapterNewest;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        recyclerViewCategory();
        recyclerViewNewest();
    }

    private void recyclerViewCategory() {
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

        ArrayList<Category> category = new ArrayList<>();
        category.add(new Category(1L, "Hành động", "img1"));
        category.add(new Category(2L, "Phiêu lưu", "img2"));
        category.add(new Category(3L, "Nhập vai", "img3"));
        category.add(new Category(4L, "RGB", "img3"));

        adapterCategory = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(adapterCategory);
    }

    private void recyclerViewNewest(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        recyclerViewPopularList = findViewById(R.id.recyclerViewNewest);
        recyclerViewPopularList.setLayoutManager(gridLayoutManager);

        ArrayList<Game> gameList = new ArrayList<>();
        gameList.add(new Game(
                1L,
                "God of war",
                "category1",
                "game phiêu lưu, hành động, nhập vai", 2333));
        gameList.add(new Game(
                2L,
                "God of 3333wardfsdfsdfd",
                "category1",
                "game phiêu lưu, hành động, nhập vai, thế giới mở", 2333));
        gameList.add(new Game(
                3L,
                "God of wareee",
                "category1",
                "game phiêu lưu, hành động, nhập vai", 2333));


        adapterNewest = new NewestAdapter(gameList);
        recyclerViewPopularList.setAdapter(adapterNewest);
    }
}