package com.girlkun.jdbc.daos;

import com.girlkun.models.item.Item;
import com.girlkun.models.shop.ItemShop;
import com.girlkun.models.shop.Shop;
import com.girlkun.models.shop.TabShop;
import com.girlkun.services.ItemService;
import com.girlkun.utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopDAO {

    public static List<Shop> getShops(Connection con) {
        List<Shop> shops = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM shop ORDER BY npc_id ASC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Shop shop = createShop(rs);
                loadShopTab(con, shop);
                shops.add(shop);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logException(ShopDAO.class, e);
        }
        return shops;
    }

    public static void updateShop(Connection con, Shop shop) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE shop SET npc_id = ?, tag_name = ?, type_shop = ? WHERE id = ?");
            ps.setByte(1, shop.npcId);
            ps.setString(2, shop.tagName);
            ps.setByte(3, shop.typeShop);
            ps.setInt(4, shop.id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logException(ShopDAO.class, e);
        }
    }

    private static Shop createShop(ResultSet rs) throws SQLException {
        Shop shop = new Shop();
        shop.id = rs.getInt("id");
        shop.npcId = rs.getByte("npc_id");
        shop.tagName = rs.getString("tag_name");
        shop.typeShop = rs.getByte("type_shop");
        return shop;
    }

    private static void loadShopTab(Connection con, Shop shop) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tab_shop WHERE shop_id = ? ORDER BY id");
            ps.setInt(1, shop.id);
            ResultSet rs = ps.executeQuery();
            List<TabShop> tabShops = new ArrayList<>();
            while (rs.next()) {
                TabShop tab = createTabShop(rs, shop);
                loadItemShop(con, tab);
                tabShops.add(tab);
            }
            rs.close();
            ps.close();
            shop.tabShops = tabShops;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logException(ShopDAO.class, e);
        }
    }

    private static TabShop createTabShop(ResultSet rs, Shop shop) throws SQLException {
        TabShop tab = new TabShop();
        tab.shop = shop;
        tab.id = rs.getInt("id");
        tab.name = rs.getString("name").replaceAll("<>", "\n");
        return tab;
    }

    private static void loadItemShop(Connection con, TabShop tabShop) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM item_shop WHERE is_sell = 1 AND tab_id = ? ORDER BY create_time DESC");
            ps.setInt(1, tabShop.id);
            ResultSet rs = ps.executeQuery();
            List<ItemShop> itemShops = new ArrayList<>();
            while (rs.next()) {
                ItemShop itemShop = createItemShop(rs, tabShop);
                loadItemShopOption(con, itemShop);
                itemShops.add(itemShop);
            }
            rs.close();
            ps.close();
            tabShop.itemShops = itemShops;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logException(ShopDAO.class, e);
        }
    }

    private static ItemShop createItemShop(ResultSet rs, TabShop tabShop) throws SQLException {
        ItemShop itemShop = new ItemShop();
        itemShop.tabShop = tabShop;
        itemShop.id = rs.getInt("id");
        itemShop.temp = ItemService.gI().getTemplate(rs.getShort("temp_id"));
        itemShop.isNew = rs.getBoolean("is_new");
        itemShop.cost = rs.getInt("cost");
        itemShop.iconSpec = rs.getInt("icon_spec");
        itemShop.typeSell = rs.getByte("type_sell");
        return itemShop;
    }

    private static void loadItemShopOption(Connection con, ItemShop itemShop) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM item_shop_option WHERE item_shop_id = ?");
            ps.setInt(1, itemShop.id);
            ResultSet rs = ps.executeQuery();
            List<Item.ItemOption> options = new ArrayList<>();
            while (rs.next()) {
                Item.ItemOption option = new Item.ItemOption(rs.getInt("option_id"), rs.getInt("param"));
                options.add(option);
            }
            rs.close();
            ps.close();
            itemShop.options = options;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logException(ShopDAO.class, e);
        }
    }
}
