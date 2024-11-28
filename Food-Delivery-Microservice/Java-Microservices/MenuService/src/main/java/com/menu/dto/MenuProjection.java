package com.menu.dto;

import java.math.BigDecimal;

public interface MenuProjection {
    Long getMenuId();
    String getItemName();
    String getItemDescription();
    BigDecimal getPrice();
    Boolean getAvailable();
    BigDecimal getRating();
}
