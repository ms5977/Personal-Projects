package com.menu.services;

import com.menu.dto.MenuRequest;
import com.menu.dto.MenuResponse;
import com.menu.dto.MenuReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface MenuService {
    MenuResponse createMenu(MenuRequest menuRequest, Long dishTypeId, Integer restaurantId);
    Page<MenuResponse> getAllMenu(Pageable pageable);
    void deleteMenu(Long menuId);
//    MenuDto updateMenu(Long menuId,MenuDto menuDto,Integer restaurantId,Long dishTypeId);
    MenuResponse getMenuById(Long menuId);
    MenuResponse updateMenuOnly(Long menuId, MenuRequest menuRequest);
    Page<MenuResponse>getMenuByItemName(String ItemName,Pageable pageable);
    Page<MenuResponse>findByRating(BigDecimal rating, Pageable pageable);
    Page<MenuResponse>getAllMenusSortedByPrice(Pageable pageable);
    Page<MenuResponse>findByItemNameAndRating(String itemName, BigDecimal rating , Pageable pageable);
    Page<MenuResponse>searchMenus(String itemName, BigDecimal rating, Pageable pageable);
    List<MenuResponse> getMenuByRestaurantId(Integer restaurantId);
    List<MenuResponse> deleteMenuByRestaurantId(Integer restaurantId);
    MenuReviewResponse findReviewByMenuId(Long menuId);
    MenuReviewResponse updateMenuReviewById(Long menuId, BigDecimal rating);
}
