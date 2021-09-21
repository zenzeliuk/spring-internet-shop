package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.ItemDetails;

public interface ItemDetailsService {

    ItemDetails save(Item item, String category_id, String brand_id, String color_id);
}
