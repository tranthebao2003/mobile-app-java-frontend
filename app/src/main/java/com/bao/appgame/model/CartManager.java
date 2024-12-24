package com.bao.appgame.model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
        // static này là để nó có thể sử dụng trong getInstance
        private static CartManager instance; // Singleton instance
        private List<Game> cartItems = new ArrayList<>(); // Danh sách game trong giỏ hàng

        private CartManager() {
        }

        // chính là để lấy 1 instance của CartManager
        // synchronized đảm bảo chỉ tạo 1 instance tại 1 thời điểm
        // nếu có nhiều luồng gọi method này
        public static synchronized CartManager getInstance() {
            if (instance == null) {// Kiểm tra nếu chưa có đối tượng
                // Tạo đối tượng đầu tiên và duy nhất
                instance = new CartManager();
            }
            // Trả về đối tượng duy nhất của cartManager
            return instance;
        }

        // thêm game vào cart
        public void addItem(Game game) {
            cartItems.add(game);
        }

        // thêm game vào cart
        public void removeItem(Game game) {
            cartItems.remove(game);
        }

        // danh sách game trong cart
        public List<Game> getCartItems() {
            return cartItems;
        }

        // tổng game trong cart
        public int getTotalItems() {
            return cartItems.size();
        }

        // tổng giá trong cart của all các game
        public Double getTotalPrice() {
            double total = 0;
            for (Game game : cartItems) {
                total += game.getGamePrice();
            }
            return total;
        }
    public void clear() {
        cartItems.clear(); // Xóa hết các phần tử trong danh sách cartItems
    }


}
