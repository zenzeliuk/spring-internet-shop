INSERT INTO `shop-spring`.items (id, count, price, name, image)
VALUES (13, 1, 5999, 'Планшет Lenovo Tab M10 FHD Plus (2nd Gen) Wi-Fi 64GB Platinum Grey (ZA5T0417UA)', 'https://content.rozetka.com.ua/goods/images/big/193988493.jpg'),
       (14, 1, 10499, 'Планшет Lenovo Tab P11 LTE 6/128GB Slate Grey (ZA7S0052UA)','https://content.rozetka.com.ua/goods/images/big/193663640.jpg'),
       (15, 1, 5199, 'Планшет Samsung Galaxy Tab A7 Lite LTE 32GB Grey (SM-T225NZAASEK)', 'https://content1.rozetka.com.ua/goods/images/big/186993722.jpg'),
       (16, 1, 3999, 'Планшет Pixus Blast 10.1" LTE 3/32GB Black (PXS Blast)', 'https://content.rozetka.com.ua/goods/images/big/72396117.jpg'),
       (17, 1, 16999, 'Планшет Samsung Galaxy Tab S7 FE LTE 64GB Pink (SM-T735NLIASEK)', 'https://content2.rozetka.com.ua/goods/images/big/194433591.jpg');

INSERT INTO `shop-spring`.brandes (id, name)
VALUES (5, 'Lenovo'),
       (6, 'Samsung'),
       (7, 'Pixus');

INSERT INTO `shop-spring`.categories (id, name, image)
VALUES (2, 'Планшети', 'https://video.rozetka.com.ua/img_superportal/kompyutery_i_noutbuki/2.1.png');

INSERT INTO `shop-spring`.colors (id, name)
VALUES (4, 'Рожевий');

INSERT INTO `shop-spring`.item_details (item_id, brand_id, category_id, color_id)
VALUES (13, 5, 2, 2),
       (14, 5, 2, 2),
       (15, 6, 2, 2),
       (16, 7, 2, 1),
       (17, 6, 2, 4);