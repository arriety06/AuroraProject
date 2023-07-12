package com.girlkun.models.npc;

import AururaFactory.Setting;
import AururaFactory.AlexDConfig;
import com.Arriety.furry.FurryManager;
import com.Arriety.furry.FurryService;
import com.Arriety.games.LuckyNumberGameManager;
import com.Arriety.kygui.ItemKyGui;
import com.Arriety.kygui.ShopKyGuiService;
import com.girlkun.models.DragonBallNamec.NgocRongNamecService;
import com.girlkun.consts.ConstMap;
import com.girlkun.services.*;
import com.girlkun.consts.ConstNpc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.consts.ConstTask;
import com.girlkun.database.GirlkunDB;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.list_boss.ConRua;
import com.girlkun.models.boss.list_boss.DuongTank;
import com.girlkun.models.boss.list_boss.MewMew;
import com.girlkun.models.boss.list_boss.NhanBan;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.clan.ClanMember;
import java.util.HashMap;
import java.util.List;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.services.func.SummonDragon;
import static com.girlkun.services.func.SummonDragon.SHENRON_1_STAR_WISHES_1;
import static com.girlkun.services.func.SummonDragon.SHENRON_1_STAR_WISHES_2;
import static com.girlkun.services.func.SummonDragon.SHENRON_SAY;
import com.girlkun.models.player.Player;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.blackball.BlackBallWar;
import com.girlkun.models.map.MapMaBu.MapMaBu;
import com.girlkun.models.map.bdkb.BanDoKhoBau;
import com.girlkun.models.map.bdkb.BanDoKhoBauService;
import com.girlkun.models.map.dhvt23.MartialCongressService;
import com.girlkun.models.map.doanhtrai.DoanhTrai;
import com.girlkun.models.map.doanhtrai.DoanhTraiService;
import com.girlkun.models.map.mappvp.mapPvp;
import com.girlkun.models.player.NPoint;
import com.girlkun.models.matches.PVPService;
import com.girlkun.models.matches.TOP;
import com.girlkun.models.matches.pvp.DaiHoiVoThuat;
import com.girlkun.models.matches.pvp.DaiHoiVoThuatService;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.shop.ShopServiceNew;
import com.girlkun.models.skill.Skill;
import com.girlkun.server.Client;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import com.girlkun.services.func.CombineServiceNew;
import com.girlkun.services.func.Input;
import com.girlkun.services.func.LuckyRound;
import com.girlkun.utils.Logger;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import com.girlkun.services.func.ChonAiDay;
import java.sql.Connection;
import java.util.logging.Level;

public class NpcFactory {

    private static final int COST_HD = 50000000;

    private static boolean nhanVang = false;
    private static boolean nhanDeTu = false;

    //playerid - object
    public static final java.util.Map<Long, Object> PLAYERID_OBJECT = new HashMap<Long, Object>();

    private NpcFactory() {

    }

    public static Npc createNPC(int mapId, int status, int cx, int cy, int tempId) {
        int avatar = Manager.NPC_TEMPLATES.get(tempId).avatar;
        try {
            switch (tempId) {
                case ConstNpc.DOC_NHAN:
                    return docNhan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUNG_LINH_THU:
                    return trungLinhThu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.POTAGE:
                    return poTaGe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUY_LAO_KAME:
                    return quyLaoKame(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUONG_LAO_GURU:
                    return truongLaoGuru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.FURRY:
                    return furry(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VUA_VEGETA:
                    return vuaVegeta(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ONG_GOHAN:
                case ConstNpc.ONG_MOORI:
                case ConstNpc.ONG_PARAGUS:
                    return ongGohan_ongMoori_ongParagus(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA:
                    return bulmaQK(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NOEL:
                    return Noel(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TORI:
                    return tori(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DENDE:
                    return dende(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.APPULE:
                    return appule(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DR_DRIEF:
                    return drDrief(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CARGO:
                    return cargo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUI:
                    return cui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SANTA:
                    return santa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.URON:
                    return uron(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.HUNG_VUONG:
                    return hungvuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BA_HAT_MIT:
                    return baHatMit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RUONG_DO:
                    return ruongDo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DAU_THAN:
                    return dauThan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CALICK:
                    return calick(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.JACO:
                    return jaco(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUA_HANG_KY_GUI:
                    return kyGui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THUONG_DE:
                    return thuongDe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_MEO_KARIN:
                    return karin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GIUMA_DAU_BO:
                    return mavuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KHI_DAU_MOI:
                    return khidaumoi(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ADMIN:
                    return Admin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_VU_TRU:
                    return thanVuTru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KIBIT:
                    return kibit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.OSIN:
                    return osin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LY_TIEU_NUONG:
                    return npclytieunuong54(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LUCKY_NUMER:
                    return luckyNumber(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DUONG_TANG:
                    return duongtank(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LINH_CANH:
                    return linhCanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GHI_DANH:
                    return GhiDanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUA_TRUNG:
                    return quaTrung(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUOC_VUONG:
                    return quocVuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA_TL:
                    return bulmaTL(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_OMEGA:
                    return rongOmega(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_1S:
                case ConstNpc.RONG_2S:
                case ConstNpc.RONG_3S:
                case ConstNpc.RONG_4S:
                case ConstNpc.RONG_5S:
                case ConstNpc.RONG_6S:
                case ConstNpc.RONG_7S:
                    return rong1_to_7s(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRONG_TAI:
                    return TrongTai(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TO_SU_KAIO:
                    return tosukaio(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NPC_64:
                    return npcThienSu64(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BILL:
                    return bill(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BO_MONG:
                    return boMong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ:
                    return gokuSSJ_1(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ_:
                    return gokuSSJ_2(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.WHIS:
                    return whist(mapId, status, cx, cy, tempId, avatar);
                default:
                    return new Npc(mapId, status, cx, cy, tempId, avatar) {
                        @Override
                        public void openBaseMenu(Player player) {
                            if (canOpenNpc(player)) {
                                super.openBaseMenu(player);
                            }
                        }

                        @Override
                        public void confirmMenu(Player player, int select) {
                            if (canOpenNpc(player)) {
//                                ShopService.gI().openShopNormal(player, this, ConstNpc.SHOP_BUNMA_TL_0, 0, player.gender);
                            }
                        }
                    };
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException(NpcFactory.class, e, "Lỗi load npc");
            return null;
        }
    }

    private static Npc whist(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Hi :3",
                                "Từ chối", "Đóng");
                    }
                    if (this.mapId == 154) {
                        Item BK = InventoryServiceNew.gI().findItemBag(player, 1119);// Pí Kiếp
                        int bikip = (BK == null) ? 0 : BK.quantity;
                        String BiKipString = (bikip >= 9999) ? "|2|Bí kíp tuyệt kỹ " + bikip + "/9999" : "|7|Bí kíp tuyệt kỹ " + bikip + "/9999";
                        this.createOtherMenu(player, ConstNpc.OPEN, "|1|Qua sẽ dậy ngươi tuyệt kỹ %1\n"
                                .replaceAll("%1", player.gender == ConstPlayer.TRAI_DAT ? "Super Kamejoko 1"
                                        : player.gender == ConstPlayer.NAMEC ? "Ma Phong Ba 1" : "Cađíc liên hoàn chưởng") + "|8|Giá vàng: 1.000.000.000\n|3|Giá ngọc hồng: 200\n" + BiKipString,
                                "Học\n tuyệt kỹ", "Nói chuyện", "Đóng");
                    } else {
                        this.createOtherMenu(player, ConstNpc.CAN_OPEN, "Chưa đến lúc học Skill", "Nói chuyện", "Đóng");
                    }

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        switch (select) {
                            case 0:
                                this.npcChat("Ngủ đi iem");
                                break;
                            case 1:
                                break;
                        }
                    }
                    if (this.mapId == 154) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_OPEN:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ giúp ngươi chế tạo trang bị Thiên Sứ!", "OK", "Đóng");
                                }
                                break;
                            case ConstNpc.OPEN:
                                switch (select) {
                                    case 0: {
                                        Item bikip = null;
                                        try {
                                            bikip = InventoryServiceNew.gI().findItemBag(player, 1119);
                                        } catch (Exception e) {
                                            Service.gI().sendThongBao(player, "Không tìm thấy !" + e);
                                        }
                                        if (bikip == null || bikip.quantity < 9999) {
                                            this.npcChat(player, "Bạn không đủ 9999 Bí kíp tuyệt kỹ");
                                        } else if (player.inventory.gold < 1) {
                                            this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
                                        } else if (player.inventory.ruby < 200) {
                                            this.npcChat(player, "Bạn không đủ 200 hồng ngọc");
                                        } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                            this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                        } else {
                                            player.inventory.gold -= 1;
                                            player.inventory.ruby -= 200;
                                            Service.getInstance().sendMoney(player);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, bikip, 9999);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            if (player.gender == ConstPlayer.TRAI_DAT) {
                                                SkillService.gI().learSkillSpecial(player, Skill.SUPER_KAME);
                                            } else if (player.gender == ConstPlayer.NAMEC) {
                                                SkillService.gI().learSkillSpecial(player, Skill.MA_PHONG_BA);
                                            } else if (player.gender == ConstPlayer.XAYDA) {
                                                SkillService.gI().learSkillSpecial(player, Skill.LIEN_HOAN_CHUONG);
                                            }
                                            this.npcChat(player, "Bạn đã học được tuyệt kỹ :3");
                                        }
                                        break;
                                    }
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ giúp ngươi chế tạo trang bị Thiên Sứ!", "OK", "Đóng");
                                        break;
                                }
                                break;
                            case ConstNpc.BASE_MENU:
                                if (select == 0) {
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_DO_TS);
                                }
                                break;
                            case ConstNpc.MENU_NANG_CAP_DO_TS:
                                if (select == 0) {
                                    CombineServiceNew.gI().startCombine(player);
                                }
                                break;
                        }
                    }

                }
            }
        };
    }

    public static Npc npc(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (mapId == 0) {
                        mapPvp.gI().setTimeJoinmappvp();
                        long now = System.currentTimeMillis();
                        if (now > mapPvp.TIME_OPEN_PVP && now < mapPvp.TIME_CLOSE_PVP) {
                            this.createOtherMenu(player, 0, "TEXT 1", "Éo", "OK");
                        } else {
                            this.createOtherMenu(player, 0, "TEXT 2 Chưa đến Time!", "Éo");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                    if (mapId == 0) {
                        switch (select) {
                            case 0:
                                break;
                            case 1:
                                if (player.nPoint.power < 80000000000L) {// 80 ty mới được qua
                                    Service.getInstance().sendThongBao(player, "Sức mạnh bạn Đéo đủ để qua map!");
                                    return;
                                } else if (player.inventory.ruby < 500) {// mỗi 1 lần vô bay 500 ngọc  hồng
                                    Service.getInstance().sendThongBao(player, "Phí vào là 500 hồng ngọc một lần bạn ey!\nBạn đéo đủ!");
                                    return;
                                } else {
                                    player.inventory.ruby -= 500;
                                    PlayerService.gI().sendInfoHpMpMoney(player);
                                    ChangeMapService.gI().changeMapInYard(player, 123, -1, -1);// id map tọa độ
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    private static Npc duongtank(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (Setting.EVENT_HALLOWEEN) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xia Xia thua phùa", "Từ chối", "Ok");
                    } else {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Hôm nay không có sự kiện nào cả", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select
            ) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 0) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    break;
                                case 1: {
                                    Boss oldDuongTank = BossManager.gI().getBossById(Util.createIdDuongTank((int) player.id));
                                    if (oldDuongTank != null) {
                                        this.npcChat(player, "Nhà ngươi hãy tiêu diệt Boss lúc trước gọi ra đã, con boss đó đang ở khu " + oldDuongTank.zone.zoneId);
                                    } else if (player.inventory.gold < 200_000_000) {
                                        this.npcChat(player, "Nhà ngươi không đủ 200 Triệu vàng ");
                                    } else {
                                        BossData bossDataClone = new BossData(
                                                "Đường Tank",
                                                (byte) 0,
                                                new short[]{467, 468, 469, -1, -1, -1},
                                                0,
                                                new int[]{player.nPoint.hpMax / 2},
                                                new int[]{140},
                                                new int[][]{},
                                                new String[]{}, //text chat 1
                                                new String[]{}, //text chat 2
                                                new String[]{}, //text chat 3
                                                60
                                        );

                                        try {
                                            DuongTank dt = new DuongTank(Util.createIdDuongTank((int) player.id), bossDataClone, player.zone, player.location.x - 20, player.location.y);
                                            dt.playerTarger = player;
                                            int[] map = {5, 6, 13, 19, 20, 29, 30, 33, 34, 37, 38, 68, 69, 70, 71, 72, 64, 65, 63, 66, 67, 73, 74, 75, 76, 77, 81, 82, 83, 79, 80, 92, 93, 94, 96, 97, 98, 99, 100};
                                            dt.mapCongDuc = map[Util.nextInt(map.length)];
                                            player.haveDuongTang = true;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //trừ vàng khi gọi boss
                                        player.inventory.gold -= 200_000_000;
                                        Service.getInstance().sendMoney(player);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }
//    public static Npc duongtank(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        if (Setting.EVENT_NGU_HANH_SON) {
//            return new Npc(mapId, status, cx, cy, tempId, avartar) {
//                @Override
//                public void openBaseMenu(Player player) {
//                    if (canOpenNpc(player)) {
//                        if (mapId == 0) {
//                            nguhs.gI().setTimeJoinnguhs();
//                            long now = System.currentTimeMillis();
//                            if (Setting.EVENT_NGU_HANH_SON && now > nguhs.TIME_OPEN_NHS && now < nguhs.TIME_CLOSE_NHS) {
//                                this.createOtherMenu(player, 0, "Ngũ Hàng Sơn x10 Tnsm\nHỗ trợ cho Ae trên 80 Tỷ SM?\nThời gian từ 13h - 18h, 250 hồng ngọc 1 lần vào", "Éo", "OK");
//                            } else {
//                                this.createOtherMenu(player, 0, "Ngũ Hàng Sơn x10 Tnsm\nHỗ trợ cho Ae trên 80 Tỷ SM?\nThời gian từ 13h - 18h, 250 hồng ngọc 1 lần vào", "Éo");
//                            }
//                        }
//                        if (mapId == 123) {
//                            this.createOtherMenu(player, 0, "Bạn Muốn Quay Trở Lại Làng Ảru?", "OK", "Từ chối");
//
//                        }
//                        if (mapId == 122) {
//                            this.createOtherMenu(player, 0, "Xia xia thua phùa\b|7|Thí chủ đang có: " + player.pointPvp + " điểm ngũ hành sơn\b|1|Thí chủ muốn đổi cải trang x4 chưởng ko?", "Âu kê", "Top Ngu Hanh Son", "No");
//                        }
//                    }
//                }
//
//                @Override
//                public void confirmMenu(Player player, int select) {
//                    if (canOpenNpc(player)) {
//
//                        if (mapId == 0) {
//                            switch (select) {
//                                case 0:
//                                    break;
//                                case 1:
//                                    if (player.nPoint.power < 80000000000L) {
//                                        Service.getInstance().sendThongBao(player, "Sức mạnh bạn Đéo đủ để qua map!");
//                                        return;
//                                    } else if (player.inventory.ruby < 500) {
//                                        Service.getInstance().sendThongBao(player, "Phí vào là 500 hồng ngọc một lần bạn ey!\nBạn đéo đủ!");
//                                        return;
//                                    } else {
//                                        player.inventory.ruby -= 500;
//                                        PlayerService.gI().sendInfoHpMpMoney(player);
//                                        ChangeMapService.gI().changeMapInYard(player, 123, -1, -1);
//                                    }
//                                    break;
//                            }
//                        }
//                        if (mapId == 123) {
//                            if (select == 0) {
//                                ChangeMapService.gI().changeMapInYard(player, 0, -1, 469);
//                            }
//                        }
//                        if (mapId == 122) {
//                            if (select == 0) {
//                                if (player.pointPvp >= 500) {
//                                    player.pointPvp -= 500;
//                                    Item item = ItemService.gI().createNewItem((short) (711));
//                                    item.itemOptions.add(new Item.ItemOption(49, 80));
//                                    item.itemOptions.add(new Item.ItemOption(77, 80));
//                                    item.itemOptions.add(new Item.ItemOption(103, 50));
//                                    item.itemOptions.add(new Item.ItemOption(207, 0));
//                                    item.itemOptions.add(new Item.ItemOption(33, 0));
////                                      
//                                    InventoryServiceNew.gI().addItemBag(player, item);
//                                    Service.gI().sendThongBao(player, "Chúc Mừng Bạn Đổi Vật Phẩm Thành Công !");
//                                } else {
//                                    Service.gI().sendThongBao(player, "Không đủ điểm, bạn còn " + (500 - player.pointPvp) + " điểm nữa");
//                                }
//
//                            }
//                            if (select == 1) {
//                                Service.gI().showListTop(player, Manager.topNHS);
//
//                            }
//                        }
//
//                    }
//                }
//            };
//        }
//        return null;
//    }

    public static Npc bill(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 154) {
                        createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Hi chào cậu nha :33",
                                "Từ chối", "Đóng");
                    }
                    if (this.mapId == 48) {
                        if (player.setClothes.setThanLinh != 5) {
                            this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Ngươi hãy mang 5 món thần linh\nvà x99 thức ăn đến đây...\nrồi ta nói tiếp", "Từ chối");
                        } else {
                            createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Ngươi muốn gì nào?",
                                    "Mua đồ\nhủy diệt", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 48:
                            switch (player.iDMark.getIndexMenu()) {
                                case ConstNpc.BASE_MENU:
                                    if (select == 0) {
                                        ShopServiceNew.gI().opendShop(player, "BILL", true);
                                        break;
                                    }
                                    break;
                            }
                            break;
                        case 154:
                            this.npcChat(player, "Hi :3");
                            break;
                    }
                }
            }
        };
    }

    private static Npc TrongTai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 113) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đại hội võ thuật Siêu Hạng\ndiễn ra 24/7 kể cả ngày lễ và chủ nhật\nHãy thi đấu để khẳng định đẳng cấp của mình nhé", "Top 100\nCao thủ", "Hướng\ndẫn\nthêm", "Đấu ngay", "Về\nĐại Hội\nVõ Thuật");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 113) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    try (Connection con = GirlkunDB.getConnection()) {
                                        Manager.topSieuHang = Manager.realTopSieuHang(con);
                                    } catch (Exception ignored) {
                                        Logger.error("Lỗi đọc top: " + ignored);
                                    }
                                    Service.gI().showListTop(player, Manager.topSieuHang, (byte) 1);
                                    break;
                                case 1:
                                    Service.gI().sendPopUpMultiLine(player, tempId, avartar, "");
                                    break;
                                case 2:
                                    List<TOP> tops = new ArrayList<>();
                                    tops.addAll(Manager.realTopSieuHang(player));
                                    Service.gI().showListTop(player, tops, (byte) 1);
                                    tops.clear();
                                    break;
                                case 3:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, -1, 432);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc GhiDanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            String[] menuselect = new String[]{};

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 52 || this.mapId == 14 || this.mapId == 7 || this.mapId == 0) {
                        createOtherMenu(pl, 0, DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).Giai(pl), "Thông tin\nChi tiết", DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).CanReg(pl) ? "Đăng ký" : "OK", "Đại Hội\nVõ Thuật\nLần thứ\n23", "Giải\nSiêu hạng");
                    } else if (this.mapId == 129) {
                        int goldchallenge = pl.goldChallenge;
                        if (pl.levelWoodChest == 0) {
                            menuselect = new String[]{"Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Về\nĐại Hội\nVõ Thuật"};
                        } else {
                            menuselect = new String[]{"Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Nhận thưởng\nRương cấp\n" + pl.levelWoodChest, "Về\nĐại Hội\nVõ Thuật"};
                        }
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU, "Đại hội võ thuật lần thứ 23\nDiễn ra bất kể ngày đêm,ngày nghỉ ngày lễ\nPhần thưởng vô cùng quý giá\nNhanh chóng tham gia nào", menuselect, "Từ chối");

                    } else {
                        super.openBaseMenu(pl);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 52 || this.mapId == 14 || this.mapId == 7 || this.mapId == 0) {
                        switch (select) {
                            case 0:
                                Service.gI().sendPopUpMultiLine(player, tempId, avartar, DaiHoiVoThuat.gI().Info());
                                break;
                            case 1:
                                if (DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).CanReg(player)) {
                                    DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).Reg(player);
                                }
                                break;
                            case 2:
//                                ChangeMapService.gI().changeMapNonSpaceship(player, 129, player.location.x, 360);
                                npcChat(player, "Bảo trì");
                                break;
                            case 3:
                                ChangeMapService.gI().changeMapNonSpaceship(player, 113, player.location.x, 360);
                                break;
                        }
                    } else if (this.mapId == 129) {
                        int goldchallenge = player.goldChallenge;
                        if (player.levelWoodChest == 0) {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressService.gI().startChallenge(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                    break;
                            }
                        } else {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressService.gI().startChallenge(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    if (!player.receivedWoodChest) {
                                        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                            Item it = ItemService.gI().createNewItem((short) 570);
                                            it.itemOptions.add(new Item.ItemOption(72, player.levelWoodChest));
                                            it.itemOptions.add(new Item.ItemOption(30, 0));
                                            it.createTime = System.currentTimeMillis();
                                            InventoryServiceNew.gI().addItemBag(player, it);
                                            InventoryServiceNew.gI().sendItemBags(player);

                                            player.receivedWoodChest = true;
                                            player.levelWoodChest = 0;
                                            Service.getInstance().sendThongBao(player, "Bạn nhận được rương gỗ");
                                        } else {
                                            this.npcChat(player, "Hành trang đã đầy");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Mỗi ngày chỉ có thể nhận rương báu 1 lần");
                                    }
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

//    private static Npc karin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                if (Setting.KARIN) {
//                    Item VoOc = InventoryServiceNew.gI().findItemBag(player, 695);// vỏ ốc
//                    int slVoOc = (VoOc == null) ? 0 : VoOc.quantity;
//
//                    Item Cua = InventoryServiceNew.gI().findItemBag(player, 697);// item cua
//                    int slCua = (Cua == null) ? 0 : Cua.quantity;
//
//                    Item tom = InventoryServiceNew.gI().findItemBag(player, 696);//Sò
//                    int sltom = (tom == null) ? 0 : tom.quantity;
//
//                    Item ca = InventoryServiceNew.gI().findItemBag(player, 1003);// item cá
//                    int slCa = (ca == null) ? 0 : ca.quantity;
//
//                    String VoOcString = (slVoOc >= 99) ? "\b|2|Bạn đang có " + slVoOc + "/99 Vỏ Ốc(-99 Khi ấn đổi)" : "\b|7|Bạn đang có " + slVoOc + "/99 Vỏ Ốc(-99 Khi ấn đổi)";
//                    String CuaString = (slCua >= 99) ? "\b|2|Bạn đang có " + slCua + "/99  Cua Đỏ(-99 Khi ấn đổi)" : "\b|7|Bạn đang có " + slCua + "/99  Cua Đỏ(-99 Khi ấn đổi)";
//                    String CaString = (slCa >= 10) ? "\b|2|Bạn đang có " + slCa + "/10 Cá Bảy Màu(-10 Khi ấn đổi)" : "\b|7|Bạn đang có " + slCa + "/10 Cá Bảy Màu(-10 Khi ấn đổi)";
//                    String TomString = (sltom >= 10) ? "\b|2|Bạn đang có " + sltom + "/10 Sò(-10 Khi ấn đổi)" : "\b|7|Bạn đang có " + sltom + "/10 Sò(-10 Khi ấn đổi)";
//                    if (canOpenNpc(player)) {
//                        if (this.mapId == 46) {
//                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                    "|1|Chào bạn đến với Lễ Hội của chúng tôi,\nnếu bạn Tham Gia hãy thực hiện thử thách của chúng tôi đưa ra"
//                                    + VoOcString
//                                    + CuaString
//                                    + TomString
//                                    + CaString,
//                                    "Từ chối",
//                                    "Đổi\nVỏ Ốc",// cai trang
//                                    "Đổi\nCua rang me",//hop qua deo lung
//                                    "Đổi\nCá Bảy Màu",
//                                    "Đổi\nSò");
//                        }
//                    }
//                } else {
//                    this.createOtherMenu(player, 0, "Sự kiện đã kết thúc!", "Ok");
//                }
//
//            }
//
//            @Override
//            public void confirmMenu(Player player, int select
//            ) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 46) {
//                        if (player.iDMark.isBaseMenu()) {
//                            switch (select) {
//                                case 0:
//                                    npcChat("Thật Sảd");
//                                    break;
//                                case 1: {
//                                    Item VoOc = null;
//                                    try {
//                                        VoOc = InventoryServiceNew.gI().findItemBag(player, 695);
//                                    } catch (Exception e) {
//                                    }
//                                    if (VoOc == null || VoOc.quantity < 99) {
//                                        this.npcChat(player, "Bạn không đủ 99 Vỏ Ốc, bạn còn thiếu");
//                                    } else if (player.inventory.gold < 100_000_000) {
//                                        this.npcChat(player, "Bạn không đủ 100tr vàng");
//                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
//                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
//                                    } else {
//                                        player.inventory.gold -= 100_000_000;
//                                        InventoryServiceNew.gI().subQuantityItemsBag(player, VoOc, 99);
//                                        Service.getInstance().sendMoney(player);
//                                        Item VoOcDepZai = ItemService.gI().createNewItem((short) 1115);// id item
//                                        InventoryServiceNew.gI().addItemBag(player, VoOcDepZai);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        this.npcChat(player, "Bạn nhận được Hộp Quà Cải Trang");
//                                    }
//                                    break;
//
//                                }
//                                case 2: {
//                                    Item CuaBeo = null;
//                                    try {
//                                        CuaBeo = InventoryServiceNew.gI().findItemBag(player, 697);
//                                    } catch (Exception e) {
//                                    }
//                                    if (CuaBeo == null || CuaBeo.quantity < 99) {
//                                        this.npcChat(player, "Bạn không đủ 99 Cua, bạn còn thiếu");
//                                    } else if (player.inventory.gold < 100_000_000) {
//                                        this.npcChat(player, "Bạn không đủ 100tr vàng");
//                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
//                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
//                                    } else {
//                                        player.inventory.gold -= 100_000_000;
//                                        InventoryServiceNew.gI().subQuantityItemsBag(player, CuaBeo, 99);
//                                        Service.getInstance().sendMoney(player);
//                                        Item VoOcDepZai = ItemService.gI().createNewItem((short) 1116);// id item
//                                        InventoryServiceNew.gI().addItemBag(player, VoOcDepZai);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        this.npcChat(player, "Bạn nhận được Hộp Quà Item Đeo Lưng");
//                                    }
//                                    break;
//                                }
//
//                                case 3: {
//                                    Item Ca = null;
//                                    try {
//                                        Ca = InventoryServiceNew.gI().findItemBag(player, 1003);
//                                    } catch (Exception e) {
//                                    }
//                                    if (Ca == null || Ca.quantity < 10) {
//                                        this.npcChat(player, "Bạn không đủ 99 Cá, bạn còn thiếu");
//                                    } else if (player.inventory.gold < 10_000_000) {
//                                        this.npcChat(player, "Bạn không đủ 10tr vàng");
//                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
//                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
//                                    } else {
//                                        player.inventory.gold -= 100_000_000;
//                                        InventoryServiceNew.gI().subQuantityItemsBag(player, Ca, 99);
//                                        Service.getInstance().sendMoney(player);
//                                        Item TomDepZai = ItemService.gI().createNewItem((short) 1114);// id item
//                                        InventoryServiceNew.gI().addItemBag(player, TomDepZai);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        this.npcChat(player, "Bạn nhận được Hộp Quà Đồ Vụn");
//                                    }
//                                    break;
//                                }
//                                case 4: {
//                                    Item Tom = null;
//                                    try {
//                                        Tom = InventoryServiceNew.gI().findItemBag(player, 696);
//                                    } catch (Exception e) {
//                                    }
//                                    if (Tom == null || Tom.quantity < 99) {
//                                        this.npcChat(player, "Bạn không đủ 99 Sò, bạn còn thiếu");
//                                    } else if (player.inventory.gold < 100_000_000) {
//                                        this.npcChat(player, "Bạn không đủ 100tr vàng");
//                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
//                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
//                                    } else {
//                                        player.inventory.gold -= 100_000_000;
//                                        InventoryServiceNew.gI().subQuantityItemsBag(player, Tom, 99);
//                                        Service.getInstance().sendMoney(player);
//                                        Item TomDepZai = ItemService.gI().createNewItem((short) 1114);// id item
//                                        InventoryServiceNew.gI().addItemBag(player, TomDepZai);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        this.npcChat(player, "Bạn nhận được Hộp Quà Đồ Vụn");
//                                    }
//                                    break;
//                                }
//
//                            }
//                        }
//                    }
//                }
//            }
//        };
//    }
    private static Npc karin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 46) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Con hãy bay theo cây Gậy Như Ý trên đỉnh tháp để đến Thần Điện gặp Thượng đế"
                                + "\n Con xứng đáng đượng làm đệ tử ông ấy!", "Tập luyện\nvới\nThần mèo", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select
            ) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 46) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    BossData DataConRua = new BossData(
                                            "Karin", //name
                                            ConstPlayer.TRAI_DAT, //gender
                                            new short[]{89, 90, 91, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
                                            200000, //dame
                                            new int[]{200000}, //hp
                                            new int[]{46}, //map join
                                            new int[][]{
                                                {Skill.DEMON, 7, 1000},},//skill
                                            new String[]{"|-1|Đang ngủ gọi coan coặt",
                                                "|-1|Ta sẽ cho ngươi thấy sức mạnh Mew Mew"
                                            }, //text chat 1
                                            new String[]{"|-1|Méo mèo méo méo mèo?",
                                                "|-1|Mẻo Méo Mèo Mẹo Mẹo Mèo Méo Mèo",
                                                "|-1|Lew lew!!"
                                            }, //text chat 2
                                            new String[]{"|-1|Oẳng Oẳng"}, //text chat 3
                                            5 //second rest
                                    );

                                    try {
                                        MewMew ruacon = new MewMew(Util.createIdBossClone((int) player.id), DataConRua, player.zone, player.location.x - 20, player.location.y, tempId);
                                        ruacon.playerTarger = player;
                                        Service.gI().sendEffectHideNPC(player, (byte) tempId, (byte) 0);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 1:
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc trungLinhThu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        if (Setting.KY_GUI) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đổi Trứng Linh thú cần:\b|7|X99 Hồn Linh Thú + 1 Tỷ vàng", "Đổi Trứng\nLinh thú", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Hôm nay không có sự kiện nào cả", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 2029);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ 99 Hồn Linh thú");
                                    } else if (player.inventory.gold < 1_000_000_000) {
                                        this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.gold -= 1_000_000_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        Service.getInstance().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 2028);
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Trứng Linh thú");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc kyGui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (Setting.KY_GUI) {
                        createOtherMenu(player, 0, "Cửa hàng chúng tôi chuyên mua bán hàng hiệu, hàng độc, cảm ơn bạn đã ghé thăm.", "Hướng\ndẫn\nthêm", "Mua bán\nKý gửi", "Từ chối");
                    } else {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Shop ký gửi chúng tôi\ntạm thời đóng lại\nhẹn bạn vào buổi khác", "Hướng\ndẫn\nthêm");
                    }
                }
            }

            @Override
            public void confirmMenu(Player pl, int select
            ) {
                if (canOpenNpc(pl)) {
                    switch (select) {
                        case 0:
                            Service.gI().sendPopUpMultiLine(pl, tempId, avartar, "Cửa hàng chuyên nhận ký gửi mua bán vật phẩm\bChỉ với 5 hồng ngọc\bGiá trị ký gửi 10k-200Tr vàng hoặc 2-2k ngọc\bMột người bán, vạn người mua, mại dô, mại dô");
                            break;
                        case 1:
                            ShopKyGuiService.gI().openShopKyGui(pl);
                            break;

                    }
                }
            }
        };
    }

    private static Npc poTaGe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đa vũ trụ song song \b|7|Con muốn gọi con trong đa vũ trụ \b|1|Với giá 200tr vàng không?", "Gọi Boss\nNhân bản", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Boss oldBossClone = BossManager.gI().getBossById(Util.createIdBossClone((int) player.id));
                                    if (oldBossClone != null) {
                                        this.npcChat(player, "Nhà ngươi hãy tiêu diệt Boss lúc trước gọi ra đã, con boss đó đang ở khu " + oldBossClone.zone.zoneId);
                                    } else if (player.inventory.gold < 200_000_000) {
                                        this.npcChat(player, "Nhà ngươi không đủ 200 Triệu vàng ");
                                    } else {
                                        List<Skill> skillList = new ArrayList<>();
                                        for (byte i = 0; i < player.playerSkill.skills.size(); i++) {
                                            Skill skill = player.playerSkill.skills.get(i);
                                            if (skill.point > 0) {
                                                skillList.add(skill);
                                            }
                                        }
                                        int[][] skillTemp = new int[skillList.size()][3];
                                        for (byte i = 0; i < skillList.size(); i++) {
                                            Skill skill = skillList.get(i);
                                            if (skill.point > 0) {
                                                skillTemp[i][0] = skill.template.id;
                                                skillTemp[i][1] = skill.point;
                                                skillTemp[i][2] = skill.coolDown;
                                            }
                                        }
                                        BossData bossDataClone = new BossData(
                                                "Nhân Bản" + player.name,
                                                player.gender,
                                                new short[]{player.getHead(), player.getBody(), player.getLeg(), player.getFlagBag(), player.getAura(), player.getEffFront()},
                                                player.nPoint.dame,
                                                new int[]{player.nPoint.hpMax},
                                                new int[]{140},
                                                skillTemp,
                                                new String[]{"|-2|Boss nhân bản đã xuất hiện rồi"}, //text chat 1
                                                new String[]{"|-1|Ta sẽ chiếm lấy thân xác của ngươi hahaha!"}, //text chat 2
                                                new String[]{"|-1|Lần khác ta sẽ xử đẹp ngươi"}, //text chat 3
                                                60
                                        );

                                        try {
                                            new NhanBan(Util.createIdBossClone((int) player.id), bossDataClone, player.zone);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //trừ vàng khi gọi boss
                                        player.inventory.gold -= 200_000_000;
                                        Service.getInstance().sendMoney(player);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc mavuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, tôi có thể giúp gì cho cậu?", "Tây thánh địa", "Từ chối");
                    } else if (this.mapId == 156) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                //đến tay thanh dia
                                ChangeMapService.gI().changeMapBySpaceShip(player, 156, -1, 360);
                            }
                        }
                    } else if (this.mapId == 156) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về lanh dia bang hoi
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc quyLaoKame(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp gì nào?", "Giải tán bang hội", "Lãnh địa Bang Hội", "Kho báu dưới biển");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU:
                            switch (select) {
                                case 0:
                                    Clan clan = player.clan;
                                    if (clan != null) {
                                        ClanMember cm = clan.getClanMember((int) player.id);
                                        if (cm != null) {
                                            if (clan.members.size() > 1) {
                                                Service.gI().sendThongBao(player, "Bang phải còn một người");
                                                break;
                                            }
                                            if (!clan.isLeader(player)) {
                                                Service.gI().sendThongBao(player, "Phải là bảng chủ");
                                                break;
                                            }
                                            NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DISSOLUTION_CLAN, -1, "Con có chắc chắn muốn giải tán bang hội không? Ta cho con 2 lựa chọn...",
                                                    "Yes you do!", "Từ chối!");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Có bang hội đâu ba!!!");
                                    break;
                                case 1:
                                    if (player.nPoint.power >= 80000000000L) {

                                        ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                    } else {
                                        this.npcChat(player, "Bạn chưa đủ 80 tỷ sức mạnh để vào");
                                    }
                                    break; // qua lanh dia
                                case 2:
                                    if (player.clan == null) {
                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Vào bang hội trước", "Đóng");
                                        break;
                                    }
                                    if (player.clan.getMembers().size() < BanDoKhoBau.N_PLAYER_CLAN) {
                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Bang hội phải có ít nhất 5 thành viên mới có thể mở", "Đóng");
                                        break;
                                    }
//                                    if (player.clanMember.getNumDateFromJoinTimeToToday() < 1) {
//                                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
//                                                "Bản đồ kho báu chỉ cho phép những người ở trong bang trên 1 ngày. Hẹn ngươi quay lại vào lúc khác",
//                                                "OK");
//                                        break;
//                                    }
                                    if (player.bdkb_countPerDay >= 3) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Con đã đạt giới hạn lượt đi trong ngày",
                                                "OK");
                                        break;
                                    }

                                    if (player.clan.banDoKhoBau != null && TimeUtil.getSecondLeft(player.clan.banDoKhoBau.getLastTimeOpen(), BanDoKhoBau.TIME_BAN_DO_KHO_BAU / 1000) > 0) {
                                        createOtherMenu(player, ConstNpc.MENU_OPENED_DBKB,
                                                "Bang hội của con đang tham gia Bản đồ kho báu cấp độ " + player.clan.banDoKhoBau.level + "\n"
                                                + "Thời gian còn lại là "
                                                + TimeUtil.getSecondLeft(player.clan.banDoKhoBau.getLastTimeOpen(), BanDoKhoBau.TIME_BAN_DO_KHO_BAU / 1000)
                                                + " giây. Con có muốn tham gia không?",
                                                "Tham gia", "Không");
                                        break;
                                    }
//                                    player.clan.banDoKhoBau_haveGone = !(System.currentTimeMillis() - player.clan.banDoKhoBau_lastTimeOpen > 300000);
                                    if (player.clan.banDoKhoBau_haveGone) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Bang hội của con đã đi Bản Đồ lúc " + TimeUtil.formatTime(player.clan.banDoKhoBau_lastTimeOpen, "HH:mm:ss") + " hôm nay. Người mở\n"
                                                + "(" + player.clan.banDoKhoBau_playerOpen + "). Hẹn con sau 5 phút nữa", "OK");
                                        break;
                                    }
                                    Input.gI().createFormChooseLevelBDKB(player);
                                    break;
                            }
                            break;
                        case ConstNpc.MENU_OPENED_DBKB:
                            if (select == 0) {
                                BanDoKhoBauService.gI().joinBDKB(player);
                            }
                            break;
                        case ConstNpc.MENU_ACCEPT_GO_TO_BDKB:
                            if (select == 0) {
                                Object level = PLAYERID_OBJECT.get(player.id);
                                BanDoKhoBauService.gI().openBDKB(player, (int) level);
                            }
                            break;

                    }
                }
            }
        };
    }

    public static Npc truongLaoGuru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc vuaVegeta(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc ongGohan_ongMoori_ongParagus(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Con cố gắng theo %1 học thành tài, đừng lo lắng cho ta."
                                        .replaceAll("%1", player.gender == ConstPlayer.TRAI_DAT ? "Quy lão Kamê"
                                                : player.gender == ConstPlayer.NAMEC ? "Trưởng lão Guru" : "Vua Vegeta"),
                                "Đổi mật khẩu", "Nhận ngọc xanh", "Nhận hồng ngọc", "Nhận vàng", "Nhận đệ tử", "Cách kiếm vàng", "Kích hoạt\n Tài khoản", "GiftCode", "Hỗ trợ\nbỏ qua\nNhiệm vụ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                Input.gI().createFormChangePassword(player);
                                break;
                            case 1:
                                if (player.inventory.gem == 200000) {
                                    this.npcChat(player, "Bú ít thôi con");
                                    break;
                                }
                                player.inventory.gem = 200000;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "Bạn vừa nhận được 200K ngọc xanh");
                                break;
                            case 2:
                                if (player.inventory.ruby == 200000) {
                                    this.npcChat(player, "Bú ít thôi con");
                                    break;
                                }
                                player.inventory.ruby = 200000;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "Bạn vừa nhận được 200K ngọc hồng");
                                break;
                            case 3:
                                if (!(player.inventory.gold == Inventory.LIMIT_GOLD)) {
                                    player.inventory.gold = Inventory.LIMIT_GOLD;
                                    Service.gI().sendMoney(player);
                                    Service.gI().sendThongBao(player, "Bạn vừa nhận được 2 tỉ vàng");
                                } else {
                                    this.npcChat(player, "Bú ít thôi con");
                                }
                                break;
                            case 4:
                                this.npcChat(player, "San Supper ddeeee!");
                                break;
                            case 5:
                                this.npcChat(player, "Hiện tại đang update!");
                                break;
                            case 6:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_ACTIVE,
                                        -1,
                                        "Mở thành viên nhó?", "Đồng ý", "Từ chối");
                                break;
                            case 7:
                                Input.gI().createFormGiftCode(player);
                                break;
                            case 8: {
                                if (player.playerTask.taskMain.id == 11) {
                                    if (player.playerTask.taskMain.index == 0) {
                                        TaskService.gI().DoneTask(player, ConstTask.TASK_11_0);
                                    } else if (player.playerTask.taskMain.index == 1) {
                                        TaskService.gI().DoneTask(player, ConstTask.TASK_11_1);
                                    } else if (player.playerTask.taskMain.index == 2) {
                                        TaskService.gI().DoneTask(player, ConstTask.TASK_11_2);
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Ta đã giúp con hoàn thành nhiệm vụ rồi mau đi trả nhiệm vụ");
                                    }
                                } else if (player.playerTask.taskMain.id == 13) {
                                    if (player.playerTask.taskMain.index == 0) {
                                        TaskService.gI().DoneTask(player, ConstTask.TASK_13_0);
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Ta đã giúp con hoàn thành nhiệm vụ rồi mau đi trả nhiệm vụ");
                                    }
                                } else if (player.playerTask.taskMain.id == 14) {
                                    if (player.playerTask.taskMain.index == 0) {
                                        for (int i = player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count; i < 25; i++) {
                                            TaskService.gI().DoneTask(player, ConstTask.TASK_14_0);
                                        }
                                    } else if (player.playerTask.taskMain.index == 1) {
                                        for (int i = player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count; i < 25; i++) {
                                            TaskService.gI().DoneTask(player, ConstTask.TASK_14_1);
                                        }
                                    } else if (player.playerTask.taskMain.index == 2) {
                                        for (int i = player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count; i < 25; i++) {
                                            TaskService.gI().DoneTask(player, ConstTask.TASK_14_2);
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Ta đã giúp con hoàn thành nhiệm vụ rồi mau đi trả nhiệm vụ");
                                    }
                                } else if (player.playerTask.taskMain.id == 15) {
                                    if (player.playerTask.taskMain.index == 0) {
                                        for (int i = player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count; i < 50; i++) {
                                            TaskService.gI().DoneTask(player, ConstTask.TASK_15_0);
                                        }
                                    } else if (player.playerTask.taskMain.index == 1) {
                                        for (int i = player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count; i < 50; i++) {
                                            TaskService.gI().DoneTask(player, ConstTask.TASK_15_1);
                                        }
                                    } else if (player.playerTask.taskMain.index == 2) {
                                        for (int i = player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).count; i < 50; i++) {
                                            TaskService.gI().DoneTask(player, ConstTask.TASK_15_2);
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Ta đã giúp con hoàn thành nhiệm vụ rồi mau đi trả nhiệm vụ");
                                    }
                                } else {
                                    Service.getInstance().sendThongBao(player, "Nhiệm vụ hiện tại không thuộc diện hỗ trợ");
                                }
                                break;
                            }
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.QUA_TAN_THU) {
                        switch (select) {
                            case 2:
                                if (nhanDeTu) {
                                    if (player.pet == null) {
                                        PetService.gI().createNormalPet(player);
                                        Service.gI().sendThongBao(player, "Bạn vừa nhận được đệ tử");
                                    } else {
                                        this.npcChat("Con đã nhận đệ tử rồi");
                                    }
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_THUONG) {
                        switch (select) {

                        }
                    }
                }

            }

        };
    }

    public static Npc bulmaQK(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Cậu cần trang bị gì cứ đến chỗ tôi nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                ShopServiceNew.gI().opendShop(player, "BUNMA", true);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc tori(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Specializing in rare treasures that can t be found anywhereee elsee\n dcm bắn tiếng ank đnăgr sờ cắp vl", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                ShopServiceNew.gI().opendShop(player, "TORI", true);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc Noel(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Hô Hô Hô....", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                ShopServiceNew.gI().opendShop(player, "NOEL", true);
                                break;
                        }
                    }
                }
            }
        };
    }

    private static Npc furry(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, 0, "Cửa hàng của chúng tôi chuyên bán hàng hiệu, hàng độc\nCảm ơn bạn đã ghé thăm.", "Hướng\ndẫn\nthêm", "Mua bán\nký gửi\nSự kiện", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player pl, int select
            ) {
                if (canOpenNpc(pl)) {
                    switch (select) {
                        case 0:
                            Service.gI().sendPopUpMultiLine(pl, tempId, avartar, "Ohh");
                            break;
//                        case 1:
//                            FurryService.gI().openShopKyGui(pl);
//                            break;

                    }
                }
            }
        };
    }

    public static Npc dende(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.idNRNM != -1) {
                            if (player.zone.map.mapId == 7) {
                                this.createOtherMenu(player, 1, "Ồ, ngọc rồng namếc, bạn thật là may mắn\nnếu tìm đủ 7 viên sẽ được Rồng Thiêng Namếc ban cho điều ước", "Hướng\ndẫn\nGọi Rồng", "Gọi rồng", "Từ chối");
                            }
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Anh cần trang bị gì cứ đến chỗ em nhé", "Cửa\nhàng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                ShopServiceNew.gI().opendShop(player, "DENDE", true);
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 1) {
                        if (player.zone.map.mapId == 7 && player.idNRNM != -1) {
                            if (player.idNRNM == 353) {
                                if (NgocRongNamecService.gI().canCallDragonNamec(player)) {
                                    int ThờiGianGọiRồng = TimeUtil.getSecondLeft(NgocRongNamecService.gI().timedu7v, (int) (NgocRongNamecService.gI().timechouoc / 1000));
                                    if (ThờiGianGọiRồng <= 0) {
                                        NgocRongNamecService.gI().firstNrNamec = true;
                                        NgocRongNamecService.gI().doneDragonNamec();
                                        NgocRongNamecService.gI().initNgocRongNamec((byte) 1);
                                        NgocRongNamecService.gI().reInitNrNamec((long) 86399000);
                                        SummonDragon.gI().summonNamec(player);
                                    } else {
                                        Service.gI().sendThongBao(player, "Cố gắng lên còn " + ThờiGianGọiRồng + " giây nữa mày sẽ được ước :)");
                                    }
                                } else {
                                    Service.gI().sendThongBao(player, "Hãy nhanh chóng tập hợp đủ 7 viên!!");
                                }
                            } else {
                                Service.gI().sendThongBao(player, "Anh phải có viên ngọc rồng Namếc 1 sao");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc appule(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi cần trang bị gì cứ đến chỗ ta nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                ShopServiceNew.gI().opendShop(player, "APPULE", true);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc drDrief(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 84) {
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                pl.gender == ConstPlayer.TRAI_DAT ? "Đến\nTrái Đất" : pl.gender == ConstPlayer.NAMEC ? "Đến\nNamếc" : "Đến\nXayda");
                    } else if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nNamếc", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 84) {
                        ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 24, -1, -1);
                    } else if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cargo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nTrái Đất", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_FIND_BOSS = 50000000;

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            if (this.mapId == 19) {

                                int taskId = TaskService.gI().getIdTask(pl);
                                switch (taskId) {
                                    case ConstTask.TASK_19_0:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_KUKU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nKuku\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    case ConstTask.TASK_19_1:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_MAP_DAU_DINH,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nMập đầu đinh\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    case ConstTask.TASK_19_2:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_RAMBO,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nRambo\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    default:
                                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến Cold", "Đến\nNappa", "Từ chối");

                                        break;
                                }
                            } else if (this.mapId == 68) {
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Ngươi muốn về Thành Phố Vegeta", "Đồng ý", "Từ chối");
                            } else {
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Tàu vũ trụ Xayda sử dụng công nghệ mới nhất, "
                                        + "có thể đưa ngươi đi bất kỳ đâu, chỉ cần trả tiền là được.",
                                        "Đến\nTrái Đất", "Đến\nNamếc", "Siêu thị");
                            }
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 19) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_KUKU) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.KUKU);
                                    if (boss == null || boss.isDie()) {
                                        Service.getInstance().sendThongBao(player, "Chết rồi ba...");
                                        break;
                                    } else {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
//                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
//                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                            player.inventory.gold -= COST_FIND_BOSS;
                                            ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                            Service.getInstance().sendMoney(player);
//                                            } else {
//                                                Service.getInstance().sendThongBao(player, "Khu vực đang full.");
//                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
//                                    Service.getInstance().sendThongBao(player, "Chết rồi ba...");
//                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_MAP_DAU_DINH) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.MAP_DAU_DINH);
                                    if (boss == null || boss.isDie()) {
                                        Service.getInstance().sendThongBao(player, "Chết rồi ba...");
                                        break;
                                    } else {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
//                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
//                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                            player.inventory.gold -= COST_FIND_BOSS;
                                            ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                            Service.getInstance().sendMoney(player);
//                                            } else {
//                                                Service.getInstance().sendThongBao(player, "Khu vực đang full.");
//                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_RAMBO) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.RAMBO);
                                    if (boss == null || boss.isDie()) {
                                        Service.getInstance().sendThongBao(player, "Chết rồi ba...");
                                        break;
                                    } else {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
//                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
//                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                            player.inventory.gold -= COST_FIND_BOSS;
                                            ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                            Service.getInstance().sendMoney(player);
//                                            } else {
//                                                Service.getInstance().sendThongBao(player, "Khu vực đang full.");
//                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }

                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 68) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 19, -1, 1100);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc santa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?",
                            "Cửa hàng", "Tiệm\nhồng ngọc");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop
                                    ShopServiceNew.gI().opendShop(player, "SANTA", false);
                                    break;
                                case 1: //tiệm hồng ngọc
                                    ShopServiceNew.gI().opendShop(player, "SANTA_RUBY", false);
                                    break;
                                case 2:
                                    ShopServiceNew.gI().opendShop(player, "SANTA_EVENT", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc uron(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    ShopServiceNew.gI().opendShop(pl, "URON", false);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc antrom(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "|1|Chào bạn bạn muốn gì", "Cửa hàng\nThỏi vàng", "Cửa hàng\nNgũ sắc", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (select) {
                        case 0:
                            ShopServiceNew.gI().opendShop(player, "AN_TROM", true);
                            break;
                        case 1:
                            ShopServiceNew.gI().opendShop(player, "DA_NGU_SAC", true);
                            break;
                    }
                }
            }
        };
    }

    public static Npc hungvuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 0 || mapId == 14 || mapId == 7) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "|1|Chào bạn bạn muốn gì", "Hưỡng Dẫn", "Cửa hàng\nDư hấu", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (select) {
                        case 0:
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.SU_KIEN_HUNG_VUONG);
                            break;
                        case 1:
                            ShopServiceNew.gI().opendShop(player, "DUA_HAU", true);
                            break;

                    }
                }
            }
        };
    }

    public static Npc baHatMit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Ép sao\ntrang bị", "Pha lê\nhóa\ntrang bị", "Set up\nactivate", "Upgrade vip\nactivation set");
                    } else if (this.mapId == 121) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Về đảo\nrùa");

                    } else {

                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Cửa hàng\nBùa", "Nâng cấp\nVật phẩm",
                                "Nâng cấp\nBông tai\nPorata", "Mở chỉ số\nBông tai\nPorata",
                                "Nhập\nNgọc Rồng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
//                                                CombineService.gI().openTabCombine(player, CombineService.EP_SAO_TRANG_BI);
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_SAO_TRANG_BI);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_TRANG_BI);
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_DO_KICH_HOAT);
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_SKH_VIP);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.EP_SAO_TRANG_BI:
                                case CombineServiceNew.PHA_LE_HOA_TRANG_BI:
                                case CombineServiceNew.CHUYEN_HOA_TRANG_BI:
                                case CombineServiceNew.NANG_CAP_DO_KICH_HOAT:
                                case CombineServiceNew.NANG_CAP_SKH_VIP:
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DAP_DO_KICH_HOAT) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_DOI_SKH_VIP) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        }
                    } else if (this.mapId == 112) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
                                    break;
                            }
                        }
                    } else if (this.mapId == 42 || this.mapId == 43 || this.mapId == 44 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
                                            "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
                                            + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
                                            "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng", "Đóng");
                                    break;
                                case 1:

                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
                                    break;
                                case 2: //nâng cấp bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI);
                                    break;
                                case 3: //làm phép nhập đá
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
                                    break;

                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_SHOP_BUA) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1H", true);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "BUA_8H", true);
                                    break;
                                case 2:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1M", true);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_VAT_PHAM:
                                case CombineServiceNew.NANG_CAP_BONG_TAI:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI:
                                case CombineServiceNew.NHAP_NGOC_RONG:
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_RA_DO_THAN_LINH) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_DO_TS) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        }
                    }
                }
            }
        };
    }

//    public static Npc baHatMit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 5) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "Ngươi tìm ta có việc gì?",
//                                "Ép sao\ntrang bị", "Pha lê\nhóa\ntrang bị", "Pha lê\nhóa\nCải trang", "Pha lê\nhóa\nPET", "Pha lê\nhóa\nLinh Thú", "nâng cấp");
//                    } else if (this.mapId == 121) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "Ngươi tìm ta có việc gì?",
//                                "Về đảo\nrùa");
//
//                    } else {
//
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "Ngươi tìm ta có việc gì?",
//                                "Cửa hàng\nBùa", "Nâng cấp\nVật phẩm",
//                                "Nâng cấp\nBông tai\nPorata", "Mở chỉ số\nBông tai\nPorata",
//                                "Nhập\nNgọc Rồng", "Phân Rã\nĐồ Thần Linh", "Nâng Cấp \nĐồ Thiên Sứ");
//                    }
//                }
//            }
//
//            @Override
//            public void confirmMenu(Player player, int select) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 5) {
//                        if (player.iDMark.isBaseMenu()) {
//                            switch (select) {
//                                case 0:
////                                                CombineService.gI().openTabCombine(player, CombineService.EP_SAO_TRANG_BI);
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_SAO_TRANG_BI);
//                                    break;
//                                case 1:
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_TRANG_BI);
//                                    break;
//                                case 2:
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_CAI_TRANG);
//                                    break;
//                                case 3:
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_PET);
//                                    break;
//                                case 4:
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_LINH_THU);
//                                    break;
//                                case 5:
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_DO_KICH_HOAT);
//                                    break;
//                            }
//                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
//                            switch (player.combineNew.typeCombine) {
//                                case CombineServiceNew.EP_SAO_TRANG_BI:
//                                case CombineServiceNew.PHA_LE_HOA_TRANG_BI:
//                                case CombineServiceNew.CHUYEN_HOA_TRANG_BI:
//                                case CombineServiceNew.PHA_LE_HOA_CAI_TRANG:
//                                case CombineServiceNew.PHA_LE_HOA_PET:
//                                case CombineServiceNew.PHA_LE_HOA_LINH_THU:
//                                case CombineServiceNew.NANG_CAP_DO_KICH_HOAT:
//
//                                    if (select == 0) {
//                                        CombineServiceNew.gI().startCombine(player);
//                                    }
//                                    break;
//                            }
//                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DAP_DO_KICH_HOAT) {
//                            if (select == 0) {
//                                CombineServiceNew.gI().startCombine(player);
//                            }
//                        }
//                    } else if (this.mapId == 112) {
//                        if (player.iDMark.isBaseMenu()) {
//                            switch (select) {
//                                case 0:
//                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
//                                    break;
//                            }
//                        }
//                    } else if (this.mapId == 42 || this.mapId == 43 || this.mapId == 44 || this.mapId == 84) {
//                        if (player.iDMark.isBaseMenu()) {
//                            switch (select) {
//                                case 0: //shop bùa
//                                    createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
//                                            "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
//                                            + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
//                                            "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng", "Đóng");
//                                    break;
//                                case 1:
////                                                CombineService.gI().openTabCombine(player, CombineService.NANG_CAP_TRANG_BI);
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
//                                    break;
//                                case 2: //nâng cấp bông tai
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI);
//                                    break;
//                                case 3: //làm phép nhập đá
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI);
//                                    break;
//                                case 4:
////                                                CombineService.gI().openTabCombine(player, CombineService.NHAP_NGOC_RONG);
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
//                                    break;
//                                case 5: //phân rã đồ thần linh
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHAN_RA_DO_THAN_LINH);
//                                    break;
//                                case 6:
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_DO_TS);
//                                    break;
//                            }
//                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_SHOP_BUA) {
//                            switch (select) {
//                                case 0:
//                                    ShopServiceNew.gI().opendShop(player, "BUA_1H", true);
//                                    break;
//                                case 1:
//                                    ShopServiceNew.gI().opendShop(player, "BUA_8H", true);
//                                    break;
//                                case 2:
//                                    ShopServiceNew.gI().opendShop(player, "BUA_1M", true);
//                                    break;
//                            }
//                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
//                            switch (player.combineNew.typeCombine) {
//                                case CombineServiceNew.NANG_CAP_VAT_PHAM:
//                                case CombineServiceNew.NANG_CAP_BONG_TAI:
//                                case CombineServiceNew.MO_CHI_SO_BONG_TAI:
//                                case CombineServiceNew.LAM_PHEP_NHAP_DA:
//                                case CombineServiceNew.NHAP_NGOC_RONG:
//                                case CombineServiceNew.PHAN_RA_DO_THAN_LINH:
//                                case CombineServiceNew.NANG_CAP_DO_TS:
//                                case CombineServiceNew.NANG_CAP_SKH_VIP:
//                                    if (select == 0) {
//                                        CombineServiceNew.gI().startCombine(player);
//                                    }
//                                    break;
//                            }
//                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_RA_DO_THAN_LINH) {
//                            if (select == 0) {
//                                CombineServiceNew.gI().startCombine(player);
//                            }
//                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_DO_TS) {
//                            if (select == 0) {
//                                CombineServiceNew.gI().startCombine(player);
//                            }
//                        }
//                    }
//                }
//            }
//        };
//    }
    public static Npc ruongDo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    InventoryServiceNew.gI().sendItemBox(player);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc dauThan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    player.magicTree.openMenuTree();
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    TaskService.gI().checkDoneTaskConfirmMenuNpc(player, this, (byte) select);
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_LEFT_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                if (player.magicTree.level == 10) {
                                    player.magicTree.fastRespawnPea();
                                } else {
                                    player.magicTree.showConfirmUpgradeMagicTree();
                                }
                            } else if (select == 2) {
                                player.magicTree.fastRespawnPea();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_FULL_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUpgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UPGRADE:
                            if (select == 0) {
                                player.magicTree.upgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_UPGRADE:
                            if (select == 0) {
                                player.magicTree.fastUpgradeMagicTree();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUnuppgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UNUPGRADE:
                            if (select == 0) {
                                player.magicTree.unupgradeMagicTree();
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc calick(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                player.iDMark.setIndexMenu(ConstNpc.BASE_MENU);
                if (TaskService.gI().getIdTask(player) < ConstTask.TASK_20_0) {
                    Service.getInstance().hideWaitDialog(player);
                    Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    return;
                }
                if (this.mapId == 102) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?",
                            "Kể\nChuyện", "Quay về\nQuá khứ");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?", "Kể\nChuyện", "Đi đến\nTương lai", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (this.mapId == 102) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) {
                            //kể chuyện
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                        } else if (select == 1) {
                            //về quá khứ
                            ChangeMapService.gI().goToQuaKhu(player);
                        }
                    }
                } else if (player.iDMark.isBaseMenu()) {
                    if (select == 0) {
                        //kể chuyện
                        NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                    } else if (select == 1) {
                        //đến tương lai
//                                    changeMap();
                        if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_20_0) {
                            ChangeMapService.gI().goToTuongLai(player);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                }
            }
        };
    }

    public static Npc jaco(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Gô Tên, Calich và Monaka đang gặp chuyện ở hành tinh Potaufeu \n Hãy đến đó ngay", "Đến \nPotaufeu");
                    } else if (this.mapId == 139) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                //đến potaufeu
                                ChangeMapService.gI().goToPotaufeu(player);
                            }
                        }
                    } else if (this.mapId == 139) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về trạm vũ trụ
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24 + player.gender, -1, -1);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

//public static Npc Potage(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 149) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "tét", "Gọi nhân bản");
//                    }
//                }
//            }
//            @Override
//            public void confirmMenu(Player player, int select) {
//                if (canOpenNpc(player)) {
//                   if (select == 0){
//                        BossManager.gI().createBoss(-214);
//                   }
//                }
//            }
//        };
//    }
    public static Npc Admin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "|2|Ta Vừa Hắc Mắp Xêm Được Tóp Của Toàn Server\b|7|Người Muốn Xem Tóp Gì?",
                            "Tóp Sức Mạnh", "Top Nhiệm Vụ", "Top Pvp", "Top Nạp", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 5:
                            switch (player.iDMark.getIndexMenu()) {
                                case ConstNpc.BASE_MENU:
                                    if (select == 0) {
                                        Service.gI().showListTop(player, Manager.topSM);
                                        break;
                                    }
                                    if (select == 1) {
                                        Service.gI().showListTop(player, Manager.topNV);
                                        break;
                                    }
                                    if (select == 2) {
                                        Service.gI().showListTop(player, Manager.topPVP);
                                        break;
                                    }
                                    if (select == 4) {
                                        Service.gI().showListTop(player, Manager.topNap);
                                        break;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc npclytieunuong54(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0, "Trò chơi Chọn ai đây đang được diễn ra, nếu bạn tin tưởng mình đang tràn đầy may mắn thì có thể tham gia thử", "Thể lệ", "Chọn\nThỏi vàng");
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (pl.nPoint.power < 17000000000L) {
                    Service.gI().sendThongBao(pl, "Yêu cầu sức mạnh lớn hơn 17 tỷ");
                    canOpenNpc(pl);
                    return;
                }
                if (canOpenNpc(pl)) {
                    String time = ((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
                    if (pl.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            createOtherMenu(pl, ConstNpc.IGNORE_MENU, "Thời gian giữa các giải là 5 phút\nKhi hết giờ, hệ thống sẽ ngẫu nhiên chọn ra 1 người may mắn.\nLưu ý: Số thỏi vàng nhận được sẽ bị nhà cái lụm đi 5%!Trong quá trình diễn ra khi đặt cược nếu thoát game mọi phần đặt đều sẽ bị hủy", "Ok");
                        } else if (select == 1) {
                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                        }
                    } else if (pl.iDMark.getIndexMenu() == 1) {
                        if (((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                    break;
                                case 1: {
                                    try {
                                        if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 20) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 20);
                                            InventoryServiceNew.gI().sendItemBags(pl);
                                            pl.goldNormar += 20;
                                            ChonAiDay.gI().goldNormar += 20;
                                            ChonAiDay.gI().addPlayerNormar(pl);
                                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                        } else {
                                            Service.getInstance().sendThongBao(pl, "Bạn không đủ thỏi vàng");
                                        }
                                    } catch (Exception ex) {
                                        java.util.logging.Logger.getLogger(NpcFactory.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;

                                case 2: {
                                    try {
                                        if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 200) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 200);
                                            InventoryServiceNew.gI().sendItemBags(pl);
                                            pl.goldVIP += 200;
                                            ChonAiDay.gI().goldVip += 200;
                                            ChonAiDay.gI().addPlayerVIP(pl);
                                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                        } else {
                                            Service.getInstance().sendThongBao(pl, "Bạn không đủ thỏi vàng");
                                        }
                                    } catch (Exception ex) {
//                                            java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;

                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc luckyNumber(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0, "Trò chơi LUCKY NUMBER đang được diễn ra, hãy tham gia ngay để thử vận may nào", "Thể lệ", "Chọn ");
            }

            @Override
            public void confirmMenu(Player pl, int select) {
//                if(pl.nPoint.power < 17000000000L) {
//                    Service.gI().sendThongBao(pl, "Yêu cầu sức mạnh lớn hơn 17 tỷ");
//                    canOpenNpc(pl);
//                    return;
//                }
                if (canOpenNpc(pl)) {
                    if (pl.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            String ncpSay = String.format("Thời gian giữa các lần quay số là %d giây\nKhi hết giờ, hệ thống sẽ thực hiện quay số và chọn ra một con số may mắn.\nLưu ý: Trong quá trình quay số vui lòng online để nhận giải.", AlexDConfig.LUCKY_GAME_TOTAL_TIME);
                            createOtherMenu(pl, ConstNpc.IGNORE_MENU, ncpSay, "OK");
                        } else if (select == 1) {
                            int luckyNumber = LuckyNumberGameManager.getInstance().getPreviousLuckyNumber();
                            String luckyNumberStr = luckyNumber == -1 ? "Chưa quay số" : ("" + luckyNumber);
                            String ncpSay = String.format("Tổng giải thưởng: %d\nThời gian còn lại: %d giây\n Số may mắn vòng quay trước: %s",
                                    LuckyNumberGameManager.getInstance().getTotalReward(),
                                    LuckyNumberGameManager.getInstance().getCurrentCountDown(),
                                    luckyNumberStr
                            );
                            createOtherMenu(pl, 1, ncpSay, "Cập nhập", "Chọn số may mắn", "Đóng");
                        }
                    } else if (pl.iDMark.getIndexMenu() == 1) {
                        switch (select) {
                            case 0:
                                int luckyNumber = LuckyNumberGameManager.getInstance().getPreviousLuckyNumber();
                                String luckyNumberStr = luckyNumber == -1 ? "Chưa quay số" : ("" + luckyNumber);

                                String ncpSay = String.format("Tổng giải thưởng: %d ngọc\nThời gian còn lại: %d giây\nDanh sách số may mắn đã chọn: %s\nSố người đang tham gian vòng quay này: %d người\nSố may mắn vòng quay trước: %s",
                                        LuckyNumberGameManager.getInstance().getTotalReward(),
                                        LuckyNumberGameManager.getInstance().getCurrentCountDown(),
                                        LuckyNumberGameManager.getInstance().getBookNumber((int) pl.id),
                                        LuckyNumberGameManager.getInstance().getTotalPerson(),
                                        luckyNumberStr
                                );
                                createOtherMenu(pl, 1, ncpSay, "Cập nhập", "Chọn số may mắn", "Đóng");
                                break;
                            case 1:
                                Input.gI().createLuckyNumber(pl);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc thuongDe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 45) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Con muốn làm gì nào", "Đến Kaio", "Quay số\nmay mắn");
                    }
                    if (this.mapId == 0) {
                        this.createOtherMenu(player, 0,
                                "Con muốn gì nào?\nCon đang còn : " + player.pointPvp + " điểm PvP Point", "Đến DHVT", "Đổi Cải trang sự kiên", "Top PVP");
                    }
                    if (this.mapId == 129) {
                        this.createOtherMenu(player, 0,
                                "Con muốn gì nào?", "Quay ve");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 129) {
                        switch (select) {
                            case 0: // quay ve
                                ChangeMapService.gI().changeMapBySpaceShip(player, 0, -1, 354);
                                break;
                        }
                    }
                    if (this.mapId == 45) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 48, -1, 354);
                                    break;
                                case 1:
                                    this.createOtherMenu(player, ConstNpc.MENU_CHOOSE_LUCKY_ROUND,
                                            "Con muốn làm gì nào?", "Quay bằng\nvàng",
                                            "Rương phụ\n("
                                            + (player.inventory.itemsBoxCrackBall.size()
                                            - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                            + " món)",
                                            "Xóa hết\ntrong rương", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_CHOOSE_LUCKY_ROUND) {
                            switch (select) {
                                case 0:
                                    if ((player.inventory.itemsBoxCrackBall.size() - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall) < 56)) {
                                        LuckyRound.gI().openCrackBallUI(player, LuckyRound.USING_GOLD);
                                    } else {
                                        Service.gI().sendThongBao(player, "Rương phụ đã đầy!");
                                    }
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "ITEMS_LUCKY_ROUND", true);
                                    break;
                                case 2:
                                    NpcService.gI().createMenuConMeo(player,
                                            ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND, this.avartar,
                                            "Con có chắc muốn xóa hết vật phẩm trong rương phụ? Sau khi xóa "
                                            + "sẽ không thể khôi phục!",
                                            "Đồng ý", "Hủy bỏ");
                                    break;
                            }
                        }
                    }

                }
            }
        };
    }

    public static Npc thanVuTru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Con muốn làm gì nào", "Di chuyển");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, ConstNpc.MENU_DI_CHUYEN,
                                            "Con muốn đi đâu?", "Về\nthần điện", "Thánh địa\nKaio", "Con\nđường\nrắn độc", "Từ chối");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DI_CHUYEN) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 45, -1, 354);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 2:
                                    //con đường rắn độc
                                    break;
                            }
                        }
                    }
                }
            }

        };
    }

    public static Npc kibit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Từ chối");
                    }
                    if (this.mapId == 114) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc osin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Đến\nhành tinh\nBill", "Từ chối");
                    } else if (this.mapId == 154) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Về thánh địa", "Đến\nhành tinh\nngục tù", "Từ chối");
                    } else if (this.mapId == 155) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else if (this.mapId == 52) {
                        try {
                            MapMaBu.gI().setTimeJoinMapMaBu();
                            if (this.mapId == 52) {
                                long now = System.currentTimeMillis();
                                if (now > MapMaBu.TIME_OPEN_MABU && now < MapMaBu.TIME_CLOSE_MABU) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB, "Đại chiến Ma Bư đã mở, "
                                            + "ngươi có muốn tham gia không?",
                                            "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_MMB,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }

                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu osin");
                        }

                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.fightMabu.pointMabu >= player.fightMabu.POINT_MAX) {
                            this.createOtherMenu(player, ConstNpc.GO_UPSTAIRS_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Lên Tầng!", "Quay về", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Quay về", "Từ chối");
                        }
                    } else if (this.mapId == 120) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                                    break;
                            }
                        }
                    } else if (this.mapId == 154) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 155, -1, 111, 792);
                                    break;
                            }
                        }
                    } else if (this.mapId == 155) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                            }
                        }
                    } else if (this.mapId == 52) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.MENU_REWARD_MMB:
                                break;
                            case ConstNpc.MENU_OPEN_MMB:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                } else if (select == 1) {
                                    ChangeMapService.gI().changeMap(player, 114, -1, 318, 336);
                                }
                                break;
                            case ConstNpc.MENU_NOT_OPEN_BDW:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                }
                                break;
                        }
                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.GO_UPSTAIRS_MENU) {
                            if (select == 0) {
                                player.fightMabu.clear();
                                ChangeMapService.gI().changeMap(player, this.map.mapIdNextMabu((short) this.mapId), -1, this.cx, this.cy);
                            } else if (select == 1) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        } else {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    } else if (this.mapId == 120) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc docNhan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
                        return;
                    }
                    if (player.clan.doanhTrai_haveGone) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai", "OK");
                        return;
                    }

                    boolean flag = true;
                    for (Mob mob : player.zone.mobs) {
                        if (!mob.isDie()) {
                            flag = false;
                        }
                    }
                    for (Player boss : player.zone.getBosses()) {
                        if (!boss.isDie()) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        player.clan.doanhTrai_haveGone = true;
                        player.clan.doanhTrai.setLastTimeOpen(System.currentTimeMillis() + 290_000);
                        player.clan.doanhTrai.DropNgocRong();
                        for (Player pl : player.clan.membersInGame) {
                            ItemTimeService.gI().sendTextTime(pl, (byte) 0, "Doanh trại độc nhãn sắp kết thúc : ", 300);
                        }
                        player.clan.doanhTrai.timePickDragonBall = true;
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai", "OK");
                    } else {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Hãy tiêu diệt hết quái và boss trong map", "OK");
                    }

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                DoanhTraiService.gI().joinDoanhTrai(player);
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc linhCanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
                        return;
                    }
                    if (player.clan.getMembers().size() < DoanhTrai.N_PLAYER_CLAN) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội phải có ít nhất 5 thành viên mới có thể mở", "Đóng");
                        return;
                    }
                    if (player.clan.doanhTrai != null) {
                        createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                                "Bang hội của ngươi đang đánh trại độc nhãn\n"
                                + "Thời gian còn lại là "
                                + TimeUtil.getSecondLeft(player.clan.doanhTrai.getLastTimeOpen(), DoanhTrai.TIME_DOANH_TRAI / 1000)
                                + ". Ngươi có muốn tham gia không?",
                                "Tham gia", "Không", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    int nPlSameClan = 0;
                    for (Player pl : player.zone.getPlayers()) {
                        if (!pl.equals(player) && pl.clan != null
                                && pl.clan.equals(player.clan) && pl.location.x >= 1285
                                && pl.location.x <= 1645) {
                            nPlSameClan++;
                        }
                    }
                    if (nPlSameClan < DoanhTrai.N_PLAYER_MAP) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ngươi phải có ít nhất " + DoanhTrai.N_PLAYER_MAP + " đồng đội cùng bang đứng gần mới có thể\nvào\n"
                                + "tuy nhiên ta khuyên ngươi nên đi cùng với 3-4 người để khỏi chết.\n"
                                + "Hahaha.", "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    if (player.clanMember.getNumDateFromJoinTimeToToday() < 1) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Doanh trại chỉ cho phép những người ở trong bang trên 1 ngày. Hẹn ngươi quay lại vào lúc khác",
                                "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }

                    if (!player.clan.doanhTrai_haveGone) {
                        player.clan.doanhTrai_haveGone = (new java.sql.Date(player.clan.doanhTrai_lastTimeOpen)).getDay() == (new java.sql.Date(System.currentTimeMillis())).getDay();
                    }

                    if (player.clan.doanhTrai_haveGone) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội của ngươi đã đi trại lúc " + TimeUtil.formatTime(player.clan.doanhTrai_lastTimeOpen, "HH:mm:ss") + " hôm nay. Người mở\n"
                                + "(" + player.clan.doanhTrai_playerOpen + "). Hẹn ngươi quay lại vào ngày mai", "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                            "Hôm nay bang hội của ngươi chưa vào trại lần nào. Ngươi có muốn vào\n"
                            + "không?\nĐể vào, ta khuyên ngươi nên có 3-4 người cùng bang đi cùng",
                            "Vào\n(miễn phí)", "Không", "Hướng\ndẫn\nthêm");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                DoanhTraiService.gI().joinDoanhTrai(player);
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

//    private static Npc popo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 0) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "Thượng Đế vừa phát 1 loại khí đang âm thầm\n hủy diệt mọi mầm sống trên Trái Đất\n nó được gọi là Destron Gas.\n Ta sẽ đưa cậu đến đó, cậu đã sẵn sàng chưa?,\n", "Thông Tin\nChi Tiết", "Top\nKhí Gas", "Thành tích", "Sẵn sàng", "Từ chối");
//                    }
//                }
//            }
//
//            @Override
//            public void confirmMenu(Player player, int select) {
//                if (canOpenNpc(player)) {
//                    switch (player.iDMark.getIndexMenu()) {
//                        case ConstNpc.BASE_MENU:
//                            switch (select) {
//                                case 0:
//                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.KHIGAS);
//                                    break;
//                                case 1:
//                                    Service.gI().showListTop(player, Manager.topKhiGas);
//                                    break;
//                                case 2:
//                                    Service.gI().sendThongBaoOK(player, "Điểm Khí Gas Của Bạn: " + player.khigas + "Quá Gke :>>");
//                                    break;
//                                case 3:
//                                    if (player.clan == null) {
//                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
//                                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
//                                        return;
//                                    }
//                        
//                            }
//                            break;
//                        case ConstNpc.MENU_OPENED_DBKB:
//                            if (select == 0) {
//                                BanDoKhoBauService.gI().joinBDKB(player);
//                            }
//                            break;
//                        case ConstNpc.MENU_ACCEPT_GO_TO_BDKB:
//                            if (select == 0) {
//                                Object level = PLAYERID_OBJECT.get(player.id);
//                                BanDoKhoBauService.gI().openBDKB(player, (int) level);
//                            }
//                            break;
//
//                    }
//                }
//            }
//        };
//    }
    public static Npc quaTrung(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_AP_TRUNG_NHANH = 1000000000;

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        player.mabuEgg.sendMabuEgg();
                        if (player.mabuEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Burk Burk...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.numberToMoney(COST_AP_TRUNG_NHANH) + " vàng", "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Burk Burk...", "Nở", "Hủy bỏ\ntrứng", "Đóng");
                        }
                    }
                    if (this.mapId == 200) {
                        player.egglinhthu.sendEggLinhThu();
                        if (player.egglinhthu.getSecondDone() > 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Burk Burk...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n", "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Burk Burk...", "Nở", "Hủy bỏ\ntrứng", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_EGG:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                } else if (select == 1) {
                                    if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                        player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                        player.mabuEgg.timeDone = 0;
                                        Service.gI().sendMoney(player);
                                        player.mabuEgg.sendMabuEgg();
                                    } else {
                                        Service.gI().sendThongBao(player,
                                                "Bạn không đủ vàng để thực hiện, còn thiếu "
                                                + Util.numberToMoney((COST_AP_TRUNG_NHANH - player.inventory.gold)) + " vàng");
                                    }
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_EGG,
                                                "Bạn có chắc chắn cho trứng nở?\n"
                                                + "Đệ tử của bạn sẽ được thay thế bằng đệ Mabư",
                                                "Đệ mabư\nTrái Đất", "Đệ mabư\nNamếc", "Đệ mabư\nXayda", "Từ chối");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                                "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        player.mabuEgg.openEgg(ConstPlayer.TRAI_DAT);
                                        break;
                                    case 1:
                                        player.mabuEgg.openEgg(ConstPlayer.NAMEC);
                                        break;
                                    case 2:
                                        player.mabuEgg.openEgg(ConstPlayer.XAYDA);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_EGG:
                                if (select == 0) {
                                    player.mabuEgg.destroyEgg();
                                }
                                break;
                        }
                    }
                    if (this.mapId == 200) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_EGG:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Linh Thú?", "Đồng ý", "Từ chối");
                                } else if (select == 1) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_SPEED_EGG,
                                            "Lựa chọn:\n 1) 1 Hồn Linh Thú + 1 Đá Ngũ Sắc + 50tr Vàng(Giảm 1h)\n "
                                            + "2) 5 Hồn Linh Thú + 5 Đá Ngũ Sắc + 250tr Vàng(Giảm 5h)\n "
                                            + "3) 10 Hồn Linh Thú + 10 Đá Ngũ Sắc + 500tr Vàng(Giảm 10h)", "Lựa chọn 1", "Lựa chọn 2", "Lựa chọn 3", "Từ chối");
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_EGG,
                                                "Bạn có chắc chắn cho trứng nở?\n",
                                                "Đồng ý", "Từ chối");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                                "Bạn có chắc chắn muốn hủy bỏ trứng Bill?", "Đồng ý", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        player.egglinhthu.openEgg();
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_SPEED_EGG:
                                byte time = 0;
                                switch (select) {
                                    case 0:
                                        time = 1;
                                        break;
                                    case 1:
                                        time = 5;
                                        break;
                                    case 2:
                                        time = 10;
                                        break;
                                    default:
                                        break;
                                }
                                Item dns = null;
                                Item honlinhthu = null;
                                for (Item i : player.inventory.itemsBag) {
                                    if (i.template != null) {
                                        if (i.template.id == 674 && i.quantity >= time) {
                                            dns = i;
                                        } else if (i.template.id == 2029 && i.quantity >= time) {
                                            honlinhthu = i;
                                        }
                                    }
                                }
                                if (player.inventory.gold < 50_000_000 * time) {
                                    Service.gI().sendThongBao(player, "Thiếu vàng!");
                                } else {
                                    if (dns != null) {
                                        if (honlinhthu != null) {
                                            player.egglinhthu.subTimeDone(0, time, 0, 0);
                                            player.inventory.gold -= 50_000_000 * time;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, time);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, honlinhthu, time);
                                            Service.gI().sendMoney(player);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                        } else {
                                            Service.gI().sendThongBao(player, "Thiếu hồn linh thú!");
                                        }
                                    } else {
                                        Service.gI().sendThongBao(player, "Thiếu đá ngũ sắc!");
                                    }
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_EGG:
                                if (select == 0) {
                                    player.egglinhthu.destroyEgg();
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc test(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                        "Con muốn nâng giới hạn sức mạnh cho bản thân hay đệ tử?",
                        "Bản thân", "Từ chối");
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
                                            "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của bản thân lên "
                                            + Util.numberToMoney(player.nPoint.getPowerNextLimit()),
                                            "Nâng\ngiới hạn\nsức mạnh",
                                            "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Sức mạnh của con đã đạt tới giới hạn",
                                            "Đóng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT) {
                        switch (select) {
                            case 0:
                                OpenPowerService.gI().openPowerBasic(player);
                                break;
                            case 1:
                                if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                    if (OpenPowerService.gI().openPowerSpeed(player)) {
                                        player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                        Service.getInstance().sendMoney(player);
                                    }
                                } else {
                                    Service.getInstance().sendThongBao(player,
                                            "Bạn không đủ vàng để mở, còn thiếu "
                                            + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc quocVuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 43) {
                        if (player.setClothes.setThanLinh != 5) {
                            this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Ngươi hãy mang 5 món thần linh đến đây...\nrồi ta nói tiếp", "Từ chối");
                        } else {
                            createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Con muốn nâng giới hạn sức mạnh cho bản thân hay đệ tử?",
                                    "Bản thân", "Từ chối");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU:
                            if (player.iDMark.isBaseMenu()) {
                                switch (select) {
                                    case 0:
                                        if (player.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                            this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
                                                    "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của bản thân lên "
                                                    + Util.numberToMoney(player.nPoint.getPowerNextLimit()),
                                                    "Nâng\ngiới hạn\nsức mạnh",
                                                    "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                        } else {
                                            this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                    "Sức mạnh của con đã đạt tới giới hạn",
                                                    "Đóng");
                                        }
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT) {
                                switch (select) {
                                    case 0:
                                        OpenPowerService.gI().openPowerBasic(player);
                                        break;
                                    case 1:
                                        if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                            if (OpenPowerService.gI().openPowerSpeed(player)) {
                                                player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                                Service.getInstance().sendMoney(player);
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player,
                                                    "Bạn không đủ vàng để mở, còn thiếu "
                                                    + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                                        }
                                        break;
                                }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc tosukaio(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                        "Con muốn nâng giới hạn sức mạnh cho bản thân hay đệ tử?",
                        "Đệ tử", "Từ chối");
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.pet != null) {
                                    if (player.pet.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                        this.createOtherMenu(player, ConstNpc.OPEN_POWER_PET,
                                                "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của đệ tử lên "
                                                + Util.numberToMoney(player.pet.nPoint.getPowerNextLimit()),
                                                "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Sức mạnh của đệ con đã đạt tới giới hạn",
                                                "Đóng");
                                    }
                                } else {
                                    Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                                }
                                //giới hạn đệ tử
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_PET) {
                        switch (select) {
                            case 0:
                                if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                    if (OpenPowerService.gI().openPowerSpeed(player.pet)) {
                                        player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                        Service.getInstance().sendMoney(player);
                                    }
                                } else {
                                    Service.getInstance().sendThongBao(player,
                                            "Bạn không đủ vàng để mở, còn thiếu "
                                            + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc bulmaTL(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu bé muốn mua gì nào?", "Cửa hàng", "Đóng");
                        }
                    } else if (this.mapId == 200) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Kính chào Ngài Linh thú sư!", "Cửa hàng", "Ấp trứng linh thú", "Nâng cấp linh thú", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_FUTURE", true);
                            }
                        }
                    } else if (this.mapId == 200) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "BUNMA_LINHTHU", true);
                                    break;
                                case 1:
                                    if (player.egglinhthu == null) {
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.AP_TRUNG_LINH_THU);
                                        break;
                                    } else {
                                        Service.gI().sendThongBao(player, "Hãy nở trứng trước!");
                                        break;
                                    }
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_LINH_THU);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_AP_TRUNG) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_LINH_THU) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc rongOmega(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    BlackBallWar.gI().setTime();
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        try {
                            long now = System.currentTimeMillis();
                            if (now > BlackBallWar.TIME_OPEN && now < BlackBallWar.TIME_CLOSE) {
                                this.createOtherMenu(player, ConstNpc.MENU_OPEN_BDW, "Đường đến với ngọc rồng sao đen đã mở, "
                                        + "ngươi có muốn tham gia không?",
                                        "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                            } else {
                                String[] optionRewards = new String[7];
                                int index = 0;
                                for (int i = 0; i < 7; i++) {
                                    if (player.rewardBlackBall.timeOutOfDateReward[i] > System.currentTimeMillis()) {
                                        optionRewards[index] = "Nhận thưởng\n" + (i + 1) + " sao";
                                        index++;
                                    }
                                }
                                if (index != 0) {
                                    String[] options = new String[index + 1];
                                    for (int i = 0; i < index; i++) {
                                        options[i] = optionRewards[i];
                                    }
                                    options[options.length - 1] = "Từ chối";
                                    this.createOtherMenu(player, ConstNpc.MENU_REWARD_BDW, "Ngươi có một vài phần thưởng ngọc "
                                            + "rồng sao đen đây!",
                                            options);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_BDW,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }
                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu rồng Omega");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_REWARD_BDW:
                            player.rewardBlackBall.getRewardSelect((byte) select);
                            break;
                        case ConstNpc.MENU_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            } else if (select == 1) {
                                player.iDMark.setTypeChangeMap(ConstMap.CHANGE_BLACK_BALL);
                                ChangeMapService.gI().openChangeMapTab(player);
                            }
                            break;
                        case ConstNpc.MENU_NOT_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc rong1_to_7s(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isHoldBlackBall()) {
                        this.createOtherMenu(player, ConstNpc.MENU_PHU_HP, "Ta có thể giúp gì cho ngươi?", "Phù hộ", "Từ chối");
                    } else {
                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?", "Về nhà", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHU_HP) {
                        if (select == 0) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_PHU_HP,
                                    "Ta sẽ giúp ngươi tăng HP lên mức kinh hoàng, ngươi chọn đi",
                                    "x3 HP\n" + Util.numberToMoney(BlackBallWar.COST_X3) + " vàng",
                                    "x5 HP\n" + Util.numberToMoney(BlackBallWar.COST_X5) + " vàng",
                                    "x7 HP\n" + Util.numberToMoney(BlackBallWar.COST_X7) + " vàng",
                                    "Từ chối"
                            );
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_GO_HOME) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PHU_HP) {
                        if (player.effectSkin.xHPKI > 1) {
                            Service.getInstance().sendThongBao(player, "Bạn đã được phù hộ rồi!");
                            return;
                        }
                        switch (select) {
                            case 0:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X3);
                                break;
                            case 1:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X5);
                                break;
                            case 2:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X7);
                                break;
                            case 3:
                                this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc npcThienSu64(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 14) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 7) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 0) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 146) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 147) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 148) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 5) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!", "Hướng Dẫn",
                            "Đổi SKH VIP", "Từ Chối");
                }
            }

            //if (player.inventory.gold < 500000000) {
//                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
//                return;
//            }
            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu() && this.mapId == 7) {
                        if (select == 0) {
                            if (player.getSession().getPlayer().nPoint.power >= 80000000000L && player.inventory.gold > COST_HD) {
                                player.inventory.gold -= COST_HD;
                                Service.getInstance().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 146, -1, 168);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 14) {
                        if (select == 0) {
                            if (player.getSession().getPlayer().nPoint.power >= 80000000000L && player.inventory.gold > COST_HD) {
                                player.inventory.gold -= COST_HD;
                                Service.getInstance().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 148, -1, 168);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 0) {
                        if (select == 0) {
                            if (player.getSession().getPlayer().nPoint.power >= 80000000000L && player.inventory.gold > COST_HD) {
                                player.inventory.gold -= COST_HD;
                                Service.getInstance().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 147, -1, 168);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 147) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 0, -1, 450);
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 148) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 14, -1, 450);
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 146) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 7, -1, 450);
                        }
                        if (select == 1) {
                        }

                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 5) {
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOI_SKH_VIP);
                        }
                        if (select == 1) {
                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_SKH_VIP);
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_DOI_SKH_VIP) {
                        if (select == 0) {
                            CombineServiceNew.gI().startCombine(player);
                        }
                    }
                }
            }

        };
    }

    public static Npc boMong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, cậu muốn tôi giúp gì?", "Nhiệm vụ\nhàng ngày", "Nhận Ngọc\nmiễn phí", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.playerTask.sideTask.template != null) {
                                        String npcSay = "Nhiệm vụ hiện tại: " + player.playerTask.sideTask.getName() + " ("
                                                + player.playerTask.sideTask.getLevel() + ")"
                                                + "\nHiện tại đã hoàn thành: " + player.playerTask.sideTask.count + "/"
                                                + player.playerTask.sideTask.maxCount + " ("
                                                + player.playerTask.sideTask.getPercentProcess() + "%)\nSố nhiệm vụ còn lại trong ngày: "
                                                + player.playerTask.sideTask.leftTask + "/" + ConstTask.MAX_SIDE_TASK;
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_PAY_SIDE_TASK,
                                                npcSay, "Trả nhiệm\nvụ", "Hủy nhiệm\nvụ");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK,
                                                "Tôi có vài nhiệm vụ theo cấp bậc, "
                                                + "sức cậu có thể làm được cái nào?",
                                                "Dễ", "Bình thường", "Khó", "Siêu khó", "Địa ngục", "Từ chối");
                                    }
                                    break;
                                case 1:
                                    player.achievement.Show();
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    TaskService.gI().changeSideTask(player, (byte) select);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PAY_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                    TaskService.gI().paySideTask(player);
                                    break;
                                case 1:
                                    TaskService.gI().removeSideTask(player);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_1(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 80) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào, tôi có thể giúp gì cho cậu?", "Tới hành tinh\nYardart", "Từ chối");
                    } else if (this.mapId == 131) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào, tôi có thể giúp gì cho cậu?", "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU:
                            if (this.mapId == 131) {
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 80, -1, 870);
                                }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc khidaumoi(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 14) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Bạn muốn nâng cấp khỉ ư?", "Nâng cấp\nkhỉ", "Shop của Khỉ", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 14) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, 1,
                                            "|7|Cần Khỉ Lv1,2,3,4,5,6,7 để nâng cấp lên ct khỉ cấp cao hơn\b|2|Mỗi lần nâng cấp tiếp thì mỗi cấp cần thêm 10 đá ngũ sắc",
                                            "Nâng cấp",
                                            "Từ chối");
                                    break;
                                case 1: //shop
                                    ShopServiceNew.gI().opendShop(player, "KHI", false);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 1) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_KHI);
                                    break;
                                case 1:
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_KHI) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_KHI:
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_2(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    try {
                        Item biKiep = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 590);
                        if (biKiep != null) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Bạn đang có " + biKiep.quantity + " bí kiếp.\n"
                                    + "Hãy kiếm đủ 10000 bí kiếp tôi sẽ dạy bạn cách dịch chuyển tức thời của người Yardart", "Học dịch\nchuyển", "Đóng");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    try {
                        Item biKiep = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 590);
                        if (biKiep != null) {
                            if (biKiep.quantity >= 10000 && InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                Item yardart = ItemService.gI().createNewItem((short) (player.gender + 592));
                                yardart.itemOptions.add(new Item.ItemOption(47, 400));
                                yardart.itemOptions.add(new Item.ItemOption(108, 10));
                                InventoryServiceNew.gI().addItemBag(player, yardart);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, biKiep, 10000);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.getInstance().sendThongBao(player, "Bạn vừa nhận được trang phục tộc Yardart");
                            }
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        };
    }

    //girlkun75-mark
    public static void createNpcRongThieng() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.RONG_THIENG, -1) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.SHENRON_CONFIRM:
                        if (select == 0) {
                            SummonDragon.gI().confirmWish();
                        } else if (select == 1) {
                            SummonDragon.gI().reOpenShenronWishes(player);
                        }
                        break;
                    case ConstNpc.SHENRON_1_1:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_1 && select == SHENRON_1_STAR_WISHES_1.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_2, SHENRON_SAY, SHENRON_1_STAR_WISHES_2);
                            break;
                        }
                    case ConstNpc.SHENRON_1_2:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_2 && select == SHENRON_1_STAR_WISHES_2.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_1, SHENRON_SAY, SHENRON_1_STAR_WISHES_1);
                            break;
                        }
                    default:
                        SummonDragon.gI().showConfirmShenron(player, player.iDMark.getIndexMenu(), (byte) select);
                        break;
                }
            }
        };
    }

    public static void createNpcConMeo() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.CON_MEO, 351) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.MAKE_MATCH_PVP: {
                        if (!player.getSession().getIsActive()) {
                            Service.getInstance().sendThongBaoFromAdmin(player,
                                    "|5|Vui Lòng Kích Hoạt Tài Khoản Để Use Chức Năng Này!");
                            return;
                        }
                        if (Maintenance.isRuning) {
                            break;
                        }
                        PVPService.gI().sendInvitePVP(player, (byte) select);
                        break;
                    }
                    case ConstNpc.MAKE_FRIEND:
                        if (select == 0) {
                            Object playerId = PLAYERID_OBJECT.get(player.id);
                            if (playerId != null) {
                                FriendAndEnemyService.gI().acceptMakeFriend(player,
                                        Integer.parseInt(String.valueOf(playerId)));
                            }
                        }
                        break;
                    case ConstNpc.REVENGE:
                        if (select == 0) {
                            PVPService.gI().acceptRevenge(player);
                        }
                        break;
                    case ConstNpc.TUTORIAL_SUMMON_DRAGON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        }
                        break;
                    case ConstNpc.SUMMON_SHENRON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        } else if (select == 1) {
                            SummonDragon.gI().summonShenron(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM1105:
                        if (select == 0) {
                            IntrinsicService.gI().sattd(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().satnm(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().setxd(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2000:
                    case ConstNpc.MENU_OPTION_USE_ITEM2001:
                    case ConstNpc.MENU_OPTION_USE_ITEM2002:
                        try {
                            ItemService.gI().OpenSKH(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2003:
                    case ConstNpc.MENU_OPTION_USE_ITEM2004:
                    case ConstNpc.MENU_OPTION_USE_ITEM2005:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM736:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().showAllIntrinsic(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().showConfirmOpen(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().showConfirmOpenVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().open(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC_VIP:
                        if (select == 0) {
                            IntrinsicService.gI().openVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_LEAVE_CLAN:
                        if (select == 0) {
                            if (player.clan.doanhTrai != null) {
                                Service.gI().sendThongBao(player, "Đang đi doanh trại bạn ơi!");
                            } else if (player.clan.banDoKhoBau != null) {
                                Service.gI().sendThongBao(player, "Đang đi BĐKB bạn ơi!");
                            } else {
                                ClanService.gI().leaveClan(player);
                            }
                        }
                        break;
                    case ConstNpc.CONFIRM_NHUONG_PC:
                        if (select == 0) {
                            ClanService.gI().phongPc(player, (int) PLAYERID_OBJECT.get(player.id));
                        }
                        break;
                    case ConstNpc.BAN_PLAYER:
                        if (select == 0) {
                            PlayerService.gI().banPlayer((Player) PLAYERID_OBJECT.get(player.id));
                            Service.getInstance().sendThongBao(player, "Ban người chơi " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                        }
                        break;

                    case ConstNpc.BUFF_PET:
                        if (select == 0) {
                            Player pl = (Player) PLAYERID_OBJECT.get(player.id);
                            if (pl.pet == null) {
                                PetService.gI().createNormalPet(pl);
                                Service.getInstance().sendThongBao(player, "Phát đệ tử cho " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                            }
                        }
                        break;
                    case ConstNpc.UP_TOP_ITEM:
                        if (select == 0) {
                            if (player.inventory.gem >= 50 && player.iDMark.getIdItemUpTop() != -1) {
                                ItemKyGui it = ShopKyGuiService.gI().getItemBuy(player.iDMark.getIdItemUpTop());
                                if (it == null || it.isBuy) {
                                    Service.gI().sendThongBao(player, "Vật phẩm không tồn tại hoặc đã được bán");
                                    return;
                                }
                                if (it.player_sell != player.id) {
                                    Service.gI().sendThongBao(player, "Vật phẩm không thuộc quyền sở hữu");
                                    ShopKyGuiService.gI().openShopKyGui(player);
                                    return;
                                }
                                player.inventory.gem -= 50;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "Thành công");
                                it.isUpTop += 1;
                                ShopKyGuiService.gI().openShopKyGui(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ hồng ngọc");
                                player.iDMark.setIdItemUpTop(-1);
                            }
                        }
                        break;
                    case ConstNpc.MENU_ADMIN:
                        switch (select) {
                            case 0:
                                for (int i = 14; i <= 20; i++) {
                                    Item item = ItemService.gI().createNewItem((short) i);
                                    InventoryServiceNew.gI().addItemBag(player, item);
                                }
                                InventoryServiceNew.gI().sendItemBags(player);
                                break;
                            case 1:
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                } else {
                                    if (player.pet.typePet == 1) {
                                        PetService.gI().changeNormalPet(player);
                                    } else if (player.pet.typePet == 2) {
                                        PetService.gI().changeBerusPet(player);
                                    } else if (player.pet.typePet == 3) {
                                        PetService.gI().changePicPet(player);
                                    }
                                    PetService.gI().changeMabuPet(player);
                                }
                                break;
                            case 2:
                                if (player.isAdmin()) {
                                    System.out.println(player.name);
                                    Maintenance.gI().start(15);
                                    System.out.println(player.name);
                                }
                                break;
                            case 3:
                                Input.gI().createFormFindPlayer(player);
                                break;
                            case 4:
                                BossManager.gI().showListBoss(player);
                                break;
                            case 5:
                                this.createOtherMenu(player, ConstNpc.CALL_BOSS,
                                        "Chọn Boss?", "Full Cụm\nANDROID", "BLACK", "BROLY", "Cụm\nCell",
                                        "Cụm\nDoanh trại", "DOREMON", "FIDE", "FIDE\nBlack", "Cụm\nGINYU", "Cụm\nNAPPA", "NGỤC\nTÙ");
                                break;
                        }
                        break;
                    case ConstNpc.CALL_BOSS:
                        switch (select) {
                            case 0:
                                BossManager.gI().createBoss(BossID.ANDROID_13);
                                BossManager.gI().createBoss(BossID.ANDROID_14);
                                BossManager.gI().createBoss(BossID.ANDROID_15);
                                BossManager.gI().createBoss(BossID.ANDROID_19);
                                BossManager.gI().createBoss(BossID.DR_KORE);
                                BossManager.gI().createBoss(BossID.KING_KONG);
                                BossManager.gI().createBoss(BossID.PIC);
                                BossManager.gI().createBoss(BossID.POC);
                                break;
                            case 1:
                                BossManager.gI().createBoss(BossID.BLACK);
                                break;
                            case 2:
                                BossManager.gI().createBoss(BossID.BROLY_THUONG);
                                break;
                            case 3:
                                BossManager.gI().createBoss(BossID.SIEU_BO_HUNG);
                                BossManager.gI().createBoss(BossID.XEN_BO_HUNG);
                                break;
                            case 4:
                                Service.getInstance().sendThongBao(player, "Không có boss");
                                break;
                            case 5:
                                BossManager.gI().createBoss(BossID.CHAIEN);
                                BossManager.gI().createBoss(BossID.XEKO);
                                BossManager.gI().createBoss(BossID.XUKA);
                                BossManager.gI().createBoss(BossID.NOBITA);
                                BossManager.gI().createBoss(BossID.DORAEMON);
                                break;
                            case 6:
                                BossManager.gI().createBoss(BossID.FIDE);
                                break;
                            case 7:
                                BossManager.gI().createBoss(BossID.FIDE_ROBOT);
                                BossManager.gI().createBoss(BossID.VUA_COLD);
                                break;
                            case 8:
                                BossManager.gI().createBoss(BossID.SO_1);
                                BossManager.gI().createBoss(BossID.SO_2);
                                BossManager.gI().createBoss(BossID.SO_3);
                                BossManager.gI().createBoss(BossID.SO_4);
                                BossManager.gI().createBoss(BossID.TIEU_DOI_TRUONG);
                                break;
                            case 9:
                                BossManager.gI().createBoss(BossID.KUKU);
                                BossManager.gI().createBoss(BossID.MAP_DAU_DINH);
                                BossManager.gI().createBoss(BossID.RAMBO);
                                break;
                            case 10:
                                BossManager.gI().createBoss(BossID.COOLER_GOLD);
                                BossManager.gI().createBoss(BossID.CUMBER);
                                BossManager.gI().createBoss(BossID.SONGOKU_TA_AC);
                                break;
                        }
                        break;
                    case ConstNpc.menutd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().settaiyoken(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgenki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setkamejoko(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.menunm:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgoddam(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setsummon(player);
                                } catch (Exception e) {

                                }
                                break;
                        }
                        break;

                    case ConstNpc.menuxd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodgalick(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setmonkey(player);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setgodhp(player);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        break;

                    case ConstNpc.CONFIRM_DISSOLUTION_CLAN:
                        switch (select) {
                            case 0:
                                Clan clan = player.clan;
                                clan.deleteDB(clan.id);
                                clan.updatePlClan(clan.id);
                                Manager.CLANS.remove(clan);
                                player.clan = null;
                                player.clanMember = null;
                                ClanService.gI().sendMyClan(player);
                                ClanService.gI().sendClanId(player);
                                Service.getInstance().sendThongBao(player, "Đã giải tán bang hội.");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_ACTIVE:
                        switch (select) {
                            case 0:
                                if (player.getSession().getVnd() >= 10000) {
                                    if (PlayerDAO.subVnd(player, 10000)) {
                                        Service.getInstance().sendThongBao(player, "Đã mở thành viên thành công!");
                                    } else {
                                        this.npcChat(player, "Lỗi vui lòng báo admin...");
                                    }
                                } else {
                                    Service.getInstance().sendThongBao(player, "Số dư vnd không đủ vui lòng nạp thêm tại: \n Nroaurora.online");
                                }
                        }
                        break;
                    case ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND:
                        if (select == 0) {
                            for (int i = 0; i < player.inventory.itemsBoxCrackBall.size(); i++) {
                                player.inventory.itemsBoxCrackBall.set(i, ItemService.gI().createItemNull());
                            }
                            player.inventory.itemsBoxCrackBall.clear();
                            Service.getInstance().sendThongBao(player, "Đã xóa hết vật phẩm trong rương");
                        }
                        break;
                    case ConstNpc.MENU_FIND_PLAYER:
                        Player p = (Player) PLAYERID_OBJECT.get(player.id);
                        if (p != null) {
                            switch (select) {
                                case 0:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMapYardrat(player, p.zone, p.location.x, p.location.y);
                                    }
                                    break;
                                case 1:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMap(p, player.zone, player.location.x, player.location.y);
                                    }
                                    break;
                                case 2:
                                    Input.gI().createFormChangeName(player, p);
                                    break;
                                case 3:
                                    String[] selects = new String[]{"Đồng ý", "Hủy"};
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.BAN_PLAYER, -1,
                                            "Bạn có chắc chắn muốn ban " + p.name, selects, p);
                                    break;
                                case 4:
                                    Service.getInstance().sendThongBao(player, "Kik người chơi " + p.name + " thành công");
                                    Client.gI().getPlayers().remove(p);
                                    Client.gI().kickSession(p.getSession());
                                    break;
                            }
                        }
                        break;
//                    case ConstNpc.MENU_EVENT:
//                        switch (select) {
//                            case 0:
//                                Service.getInstance().sendThongBaoOK(player, "Điểm sự kiện: " + player.inventory.event + " ngon ngon...");
//                                break;
//                            case 1:
//                                Util.showListTop(player, (byte) 2);
//                                break;
//                            case 2:
//                                Service.getInstance().sendThongBao(player, "Sự kiện đã kết thúc...");
////                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_GIAO_BONG, -1, "Người muốn giao bao nhiêu bông...",
////                                        "100 bông", "1000 bông", "10000 bông");
//                                break;
//                            case 3:
//                                Service.getInstance().sendThongBao(player, "Sự kiện đã kết thúc...");
////                                NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN, -1, "Con có thực sự muốn đổi thưởng?\nPhải giao cho ta 3000 điểm sự kiện đấy... ",
////                                        "Đồng ý", "Từ chối");
//                                break;
//
//                        }
//                        break;
                    case ConstNpc.MENU_GIAO_BONG:
                        ItemService.gI().giaobong(player, (int) Util.tinhLuyThua(10, select + 2));
                        break;
                    case ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN:
                        if (select == 0) {
                            ItemService.gI().openBoxVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_TELE_NAMEC:
                        if (select == 0) {
                            NgocRongNamecService.gI().teleportToNrNamec(player);
                            player.inventory.subGemAndRuby(50);
                            Service.getInstance().sendMoney(player);
                        }
                        break;
                }
            }
        };
    }

}
