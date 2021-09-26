INSERT INTO `shop-spring`.items (id, count, price, name, image)
VALUES (1, 1, 25499, 'Ноутбук Acer Aspire 7 A715-75G-54HY', 'https://content.rozetka.com.ua/goods/images/big/153238962.jpg'),
       (2, 1, 24999, 'Ноутбук Asus TUF Gaming F15 FX506LI-HN012', 'https://content.rozetka.com.ua/goods/images/big/179861938.jpg'),
       (3, 1, 33999, 'Ноутбук Apple MacBook Air 13" M1 256GB 2020', 'https://content1.rozetka.com.ua/goods/images/big/144249716.jpg'),
       (4, 1, 36999, 'Ноутбук Asus TUF Gaming F17 FX706HCB-HX113', 'https://content.rozetka.com.ua/goods/images/big/205322611.jpg'),
       (5, 1, 27999, 'Ноутбук Asus ZenBook OLED 13 UX325JA-KG284', 'https://content1.rozetka.com.ua/goods/images/big/219561701.jpg'),
       (6, 1, 26999, 'Ноутбук Asus ZenBook Flip UX363JA-EM120T', 'https://content1.rozetka.com.ua/goods/images/big/180142757.jpg'),
       (7, 1, 39999, 'Ноутбук Asus ZenBook 15 UX534FAC-A8148T', 'https://content1.rozetka.com.ua/goods/images/big/196847865.jpg'),
       (8, 1, 19999, 'Ноутбук MSI Bravo 15 (A4DCR-093XUA)', 'https://content2.rozetka.com.ua/goods/images/big/143569616.jpg'),
       (9, 1, 31999, 'Ноутбук Asus ZenBook OLED 13 UX325JA-KG250T (90NB0QY1-M05950)', 'https://content1.rozetka.com.ua/goods/images/big/217933282.jpg'),
       (10, 1, 23999, 'Ноутбук Acer Nitro 5 AN515-44-R8BU (NH.Q9HEU.00H)', 'https://content1.rozetka.com.ua/goods/images/big/37886074.jpg'),
       (11, 1, 62999, 'Ноутбук Asus ROG Zephyrus G14 GA401QM-K2263T (90NR05S6-M06970)', 'https://content2.rozetka.com.ua/goods/images/big/203020150.jpg'),
       (12, 1, 25999, 'Ноутбук Asus AsusPRO P3540FB-BQ0434R (90NX0251-M06180)', 'https://content.rozetka.com.ua/goods/images/big/184585053.jpg');
INSERT INTO `shop-spring`.brandes (id, name)
VALUES (1, 'Acer'),
       (2, 'Asus'),
       (3, 'Apple'),
       (4, 'MSI');

INSERT INTO `shop-spring`.categories (id, name, image)
VALUES (1, 'Ноутбуки', 'https://video.rozetka.com.ua/img_superportal/kompyutery_i_noutbuki/1.1.png');

INSERT INTO `shop-spring`.colors (id, name)
VALUES (1, 'Чорний'),
       (2, 'Сірий'),
       (3, 'Синій');

INSERT INTO `shop-spring`.item_details (item_id, brand_id, category_id, color_id)
VALUES (1, 1, 1, 1),
       (2, 2, 1, 1),
       (3, 3, 1, 2),
       (4, 2, 1, 2),
       (5, 2, 1, 2),
       (6, 2, 1, 2),
       (7, 2, 1, 3),
       (8, 4, 1, 1),
       (9, 2, 1, 2),
       (10, 1, 1, 1),
       (11, 2, 1, 2),
       (12, 2, 1, 2);