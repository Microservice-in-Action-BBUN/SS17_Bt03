package com.example.evict;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Cache-Aside Strategy: Cập nhật Database trước, sau đó xóa Cache.
     * Dùng @CacheEvict thay vì @CachePut để phòng tránh Race Condition.
     */
    @CacheEvict(value = "menuCache", key = "#menu.id")
    public Menu updateMenu(Menu menu) {
        // Cập nhật database trước
        return menuRepository.save(menu);
    }
}

// ================= Dummy Classes để Service compile không lỗi =================

class Menu {
    private Long id;
    private String dish_name;
    private int price;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}

interface MenuRepository {
    Menu save(Menu menu);
}
