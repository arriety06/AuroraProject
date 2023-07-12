/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.mob;

import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Béo
 */
public class ArrietyDrop {

    public static final int[] list_thuc_an = new int[]{663, 664, 665, 666, 667};
    public static final int[] list_manh_thien_xu = new int[]{1066, 1067, 1068, 1069, 1070};

    public static final int[][] list_do_huy_diet = {
        {650, 651, 657, 658, 656},//td
        {652, 653, 659, 660, 656},//nm
        {654, 655, 661, 662, 656}//xd
    };

    public static final int[][] list_item_SKH = {
        {0, 6, 21, 27, 12},//td
        {1, 7, 22, 28, 12},//nm
        {2, 8, 23, 29, 12}//xd
    };

    public static void DropItemReWard(Player player, int idItem, int soluong, int x, int y) {
        ItemMap item = new ItemMap(player.zone, idItem, soluong, Util.nextInt((x - 50), (x + 50)), y, player.id);
        item.options.add(new Item.ItemOption(30, 0));
        Service.getInstance().dropItemMap(player.zone, item);
    }

    public static void DropItemReWardDoHuyDietKichHoat(Player player, int soluong, int x, int y) {
        Item itemHuyDiet = ArrietyDrop.randomCS_DHD(ArrietyDrop.list_do_huy_diet[player.gender][Util.nextInt(0, 4)], player.gender);
        ItemMap item = new ItemMap(player.zone, itemHuyDiet.template.id, soluong, Util.nextInt((x - 50), (x + 50)), y, player.id);
        item.options = itemHuyDiet.itemOptions;

        Service.getInstance().dropItemMap(player.zone, item);
    }

    public static boolean IsItemKhongChoGiaoDich(int id) {
        return (id >= 663 && id <= 667);
    }

    public static Item randomCS_DHD(int itemId, int gender) {
        Item it = ItemService.gI().createItemSetKichHoat(itemId, 1);
        List<Integer> ao = Arrays.asList(650, 652, 654);
        List<Integer> quan = Arrays.asList(651, 653, 655);
        List<Integer> gang = Arrays.asList(657, 659, 661);
        List<Integer> giay = Arrays.asList(658, 660, 662);
        int nhd = 656;

//        int idOptionTD[] = new int[] {};
        if (ao.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(47, Util.highlightsItem(gender == 2, new Random().nextInt(1001) + 1800))); // áo từ 1800-2800 giáp
        }
        if (quan.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(22, Util.highlightsItem(gender == 0, new Random().nextInt(16) + 85))); // hp 85-100k
        }
        if (gang.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(0, Util.highlightsItem(gender == 2, new Random().nextInt(150) + 8500))); // 8500-10000
        }
        if (giay.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(23, Util.highlightsItem(gender == 1, new Random().nextInt(11) + 80))); // ki 80-90k
        }
        if (nhd == itemId) {
            it.itemOptions.add(new Item.ItemOption(14, new Random().nextInt(3) + 17)); //chí mạng 17-19%
        }
        it.itemOptions.add(new Item.ItemOption(21, 80));// yêu cầu sm 80 tỉ
        it.itemOptions.add(new Item.ItemOption(30, 1));// ko the gd
        return it;
    }

    public static Item randomCS_DKH(int itemId, byte type, byte gender) {
        Item it = ItemService.gI().createItemSetKichHoat(itemId, 1);
        List<Integer> ao = Arrays.asList(0, 1, 2);
        List<Integer> quan = Arrays.asList(6, 7, 8);
        List<Integer> gang = Arrays.asList(21, 22, 23);
        List<Integer> giay = Arrays.asList(27, 28, 29);
        int rada = 12;

        if (ao.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(47, new Random().nextInt(3) + 2)); // áo từ 2-5 giáp
        }
        if (quan.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(6, new Random().nextInt(80) + 20)); // hp 20-100
        }
        if (gang.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(0, new Random().nextInt(6) + 4)); // sd 4-6
        }
        if (giay.contains(itemId)) {
            it.itemOptions.add(new Item.ItemOption(7, new Random().nextInt(80) + 20)); // ki 20-100
        }
        if (rada == itemId) {
            it.itemOptions.add(new Item.ItemOption(14, 1)); //chí mạng 1%
        }
        if (type == 0) { //Đồ quái rơi (50% chỉ số)
            if (Util.isTrue(20, 100)) {
                int IDSet = Util.nextInt(0, 6);
                switch (gender) {
                    case 0:
                        if (IDSet <= 2) {
                            it.itemOptions.add(new Item.ItemOption(210, 0));
                            it.itemOptions.add(new Item.ItemOption(222, 0));
                        } else if (IDSet <= 4) {
                            it.itemOptions.add(new Item.ItemOption(211, 0));
                            it.itemOptions.add(new Item.ItemOption(223, 0));
                        } else {
                            it.itemOptions.add(new Item.ItemOption(212, 0));
                            it.itemOptions.add(new Item.ItemOption(224, 0));
                        }
                        break;
                    case 1:
                        if (IDSet <= 2) {
                            it.itemOptions.add(new Item.ItemOption(213, 0));
                            it.itemOptions.add(new Item.ItemOption(225, 0));
                        } else if (IDSet <= 4) {
                            it.itemOptions.add(new Item.ItemOption(214, 0));
                            it.itemOptions.add(new Item.ItemOption(226, 0));
                        } else {
                            it.itemOptions.add(new Item.ItemOption(215, 0));
                            it.itemOptions.add(new Item.ItemOption(227, 0));
                        }
                        break;
                    case 2:
                        if (IDSet <= 2) {
                            it.itemOptions.add(new Item.ItemOption(216, 0));
                            it.itemOptions.add(new Item.ItemOption(219, 0));
                        } else if (IDSet <= 4) {
                            it.itemOptions.add(new Item.ItemOption(217, 0));
                            it.itemOptions.add(new Item.ItemOption(220, 0));
                        } else {
                            it.itemOptions.add(new Item.ItemOption(218, 0));
                            it.itemOptions.add(new Item.ItemOption(221, 0));
                        }
                        break;
                }
                it.itemOptions.add(new Item.ItemOption(30, 0));// ko the gd
            }
            if (Util.isTrue(20, 100)) {
                it.itemOptions.add(new Item.ItemOption(107, Util.nextInt(1, 3)));
            }
        } else if (type == 1) { // Đồ nâng cấp 100% chỉ số
            int IDSet = Util.nextInt(0, 6);
            switch (gender) {
                case 0:
                    if (IDSet <= 2) {
                        it.itemOptions.add(new Item.ItemOption(127, 0));
                        it.itemOptions.add(new Item.ItemOption(139, 0));
                    } else if (IDSet <= 4) {
                        it.itemOptions.add(new Item.ItemOption(128, 0));
                        it.itemOptions.add(new Item.ItemOption(140, 0));
                    } else {
                        it.itemOptions.add(new Item.ItemOption(129, 0));
                        it.itemOptions.add(new Item.ItemOption(141, 0));
                    }
                    break;
                case 1:
                    if (IDSet <= 2) {
                        it.itemOptions.add(new Item.ItemOption(130, 0));
                        it.itemOptions.add(new Item.ItemOption(142, 0));
                    } else if (IDSet <= 4) {
                        it.itemOptions.add(new Item.ItemOption(131, 0));
                        it.itemOptions.add(new Item.ItemOption(143, 0));
                    } else {
                        it.itemOptions.add(new Item.ItemOption(132, 0));
                        it.itemOptions.add(new Item.ItemOption(144, 0));
                    }
                    break;
                case 2:
                    if (IDSet <= 2) {
                        it.itemOptions.add(new Item.ItemOption(133, 0));
                        it.itemOptions.add(new Item.ItemOption(136, 0));
                    } else if (IDSet <= 4) {
                        it.itemOptions.add(new Item.ItemOption(134, 0));
                        it.itemOptions.add(new Item.ItemOption(137, 0));
                    } else {
                        it.itemOptions.add(new Item.ItemOption(135, 0));
                        it.itemOptions.add(new Item.ItemOption(138, 0));
                    }
                    break;
            }
            it.itemOptions.add(new Item.ItemOption(30, 0));// ko the gd
        }
        return it;
    }

    public static ItemMap DropItemSetKichHoat(Player player, int soluong, int x, int y) {
        Item itemkichhoat = ArrietyDrop.randomCS_DKH(ArrietyDrop.list_item_SKH[player.gender][Util.nextInt(0, 4)], (byte) 0, player.gender);
        ItemMap item = new ItemMap(player.zone, itemkichhoat.template.id, soluong, Util.nextInt((x - 50), (x + 50)), y, player.id);
        item.options = itemkichhoat.itemOptions;
        return item;
    }

    /*
     *
     */
}
