package com.girlkun.services.func;

import com.girlkun.consts.ConstNpc;
import com.girlkun.models.item.Item;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.mob.ArrietyDrop;
import com.girlkun.models.npc.Npc;
import com.girlkun.models.npc.NpcManager;
import com.girlkun.models.npc.specialnpc.EggLinhThu;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerNotify;
import com.girlkun.network.io.Message;
import com.girlkun.services.*;
import com.girlkun.utils.Util;

import java.util.*;
import java.util.stream.Collectors;

public class CombineServiceNew {

    private static final int COST_DOI_VE_DOI_DO_HUY_DIET = 500000000;
    private static final int COST_DAP_DO_KICH_HOAT = 500000000;
    private static final int COST_DOI_MANH_KICH_HOAT = 500000000;

    private static final int COST_NANG_DO_TS = 1_000_000_000;
    private static final int COST = 500_000_000;

    private static final byte MAX_STAR_ITEM = 8;
    private static final byte MAX_STAR_CAI_TRANG_HOA = 12;
    private static final byte MAX_STAR_PET = 12;
    private static final byte MAX_START_LINHTHU = 12;
    private static final byte MAX_LEVEL_ITEM = 8;

    private static final byte OPEN_TAB_COMBINE = 0;
    private static final byte REOPEN_TAB_COMBINE = 1;
    private static final byte COMBINE_SUCCESS = 2;
    private static final byte COMBINE_FAIL = 3;
    private static final byte COMBINE_DRAGON_BALL = 5;
    public static final byte OPEN_ITEM = 6;

    public static final int EP_SAO_TRANG_BI = 500;
    public static final int PHA_LE_HOA_TRANG_BI = 501;
    public static final int CHUYEN_HOA_TRANG_BI = 502;
    public static final int PHA_LE_HOA_CAI_TRANG = 509;
    public static final int PHA_LE_HOA_PET = 517;
    public static final int PHA_LE_HOA_LINH_THU = 518;
//    public static final int DOI_VE_HUY_DIET = 503;
//    public static final int DAP_SET_KICH_HOAT = 504;
//    public static final int DOI_MANH_KICH_HOAT = 505;
//    public static final int DOI_CHUOI_KIEM = 506;
//    public static final int DOI_LUOI_KIEM = 507;
//    public static final int DOI_KIEM_THAN = 508;
    private static final int GOLD_MOCS_BONG_TAI = 500_000_000;
    private static final int Gem_MOCS_BONG_TAI = 500;
    private static final int GOLD_BONG_TAI2 = 500_000_000;
    private static final int GEM_BONG_TAI2 = 1_000;

    private static final int GOLD_NANG_KHI = 500_000_000;
    private static final int RUBY_NANG_KHI = 1000;

    public static final int NANG_CAP_KHI = 600;

    public static final int NANG_CAP_VAT_PHAM = 510;
    public static final int NANG_CAP_BONG_TAI = 511;
    public static final int MO_CHI_SO_BONG_TAI = 519;
    public static final int LAM_PHEP_NHAP_DA = 512;
    public static final int NHAP_NGOC_RONG = 513;
    public static final int PHAN_RA_DO_THAN_LINH = 514;
    public static final int NANG_CAP_DO_TS = 515;
    public static final int NANG_CAP_SKH_VIP = 516;

    public static final int NANG_CAP_DO_KICH_HOAT = 550;
    public static final int AP_TRUNG_LINH_THU = 551;
    public static final int NANG_CAP_LINH_THU = 552;

    private static final int GOLD_BONG_TAI = 500_000_000;
    private static final int GEM_BONG_TAI = 5_000;
    private static final int RATIO_BONG_TAI = 15;
    private static final int RATIO_NANG_CAP = 22;

    private final Npc baHatMit;
    private final Npc npsthiensu64;
    private final Npc khidaumoi;
    private final Npc whis;
    private final Npc bulmatl;

    private static CombineServiceNew i;

    public CombineServiceNew() {
        this.baHatMit = NpcManager.getNpc(ConstNpc.BA_HAT_MIT);
        this.npsthiensu64 = NpcManager.getNpc(ConstNpc.NPC_64);
        this.khidaumoi = NpcManager.getNpc(ConstNpc.KHI_DAU_MOI);
        this.whis = NpcManager.getNpc(ConstNpc.WHIS);
        this.bulmatl = NpcManager.getNpc(ConstNpc.BUNMA_TL);
    }

    public static CombineServiceNew gI() {
        if (i == null) {
            i = new CombineServiceNew();
        }
        return i;
    }

    /**
     * Mở tab đập đồ
     *
     * @param player
     * @param type kiểu đập đồ
     */
    public void openTabCombine(Player player, int type) {
        player.combineNew.setTypeCombine(type);
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_TAB_COMBINE);
            msg.writer().writeUTF(getTextInfoTabCombine(type));
            msg.writer().writeUTF(getTextTopTabCombine(type));
            if (player.iDMark.getNpcChose() != null) {
                msg.writer().writeShort(player.iDMark.getNpcChose().tempId);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông tin đập đồ
     *
     * @param player
     */
    public void showInfoCombine(Player player, int[] index) {
        player.combineNew.clearItemCombine();
        if (index.length > 0) {
            for (int i = 0; i < index.length; i++) {
                player.combineNew.itemsCombine.add(player.inventory.itemsBag.get(index[i]));
            }
        }
        switch (player.combineNew.typeCombine) {
            case NANG_CAP_KHI:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item ctkhi = null;
                    Item dns = null;
                    Item dgl = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (checkctkhi(item)) {
                            ctkhi = item;
                        } else if (item.template.id == 674) {
                            dns = item;
                        } else if (item.template.id == 579) {
                            dgl = item;
                        }
                    }

                    if (ctkhi != null && dns != null && dgl != null) {
                        int level = 0;
                        for (Item.ItemOption io : ctkhi.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < MAX_LEVEL_ITEM) {
                            int lvkhi = lvkhi(ctkhi);
                            int countdns = getcountdnsnangkhi(lvkhi);
                            player.combineNew.goldCombine = getGoldnangkhi(lvkhi);
                            player.combineNew.rubyCombine = getRubydnangkhi(lvkhi);
                            player.combineNew.ratioCombine = getRatioNangkhi(lvkhi);

                            String npcSay = "Cải trang khỉ Cấp: " + lvkhi + " \n|2|";
                            for (Item.ItemOption io : ctkhi.itemOptions) {
                                npcSay += io.getOptionString() + "\n";
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (dgl.quantity >= 20) {
                                if (dns.quantity >= countdns) {
                                    if (player.combineNew.goldCombine <= player.inventory.gold) {
                                        if (player.combineNew.rubyCombine <= player.inventory.ruby) {
                                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                            khidaumoi.createOtherMenu(player, ConstNpc.MENU_NANG_KHI, npcSay,
                                                    "Nâng cấp\ncần " + player.combineNew.rubyCombine + " hồng ngọc");
                                        } else {
                                            npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.rubyCombine - player.inventory.ruby) + " hồng ngọc";
                                            khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                        }
                                    } else {
                                        npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                        khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                    }
                                } else {
                                    npcSay += "Còn thiếu " + Util.numberToMoney(countdns - dns.quantity) + " Đá Ngũ Sắc";
                                    khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                }
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(20 - dgl.quantity) + " Duoi khi";
                                khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            this.khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Đã đạt cấp tối đa! Nâng con cặc :)))", "Đóng");
                        }
                    } else {
                        this.khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Cải trang khỉ Cấp 1-7 và 10 + 10*lvkhi Đá Ngũ Sắc\n x20 Duoi khi", "Đóng");
                    }
                } else {
                    this.khidaumoi.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Cải trang khỉ Cấp 1-7 và 10 + 10*lvkhi Đá Ngũ Sắc\n x20 Duoi khi", "Đóng");
                }
                break;
            case NANG_CAP_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item bongtai = null;
                    Item manhvobt = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (checkbongtai(item)) {
                            bongtai = item;
                        } else if (item.template.id == 933) {
                            manhvobt = item;
                        }
                    }

                    if (bongtai != null && manhvobt != null) {
                        int level = 0;
                        for (Item.ItemOption io : bongtai.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < 2) {
                            int lvbt = lvbt(bongtai);
                            int countmvbt = getcountmvbtnangbt(lvbt);
                            player.combineNew.goldCombine = getGoldnangbt(lvbt);
                            player.combineNew.gemCombine = getgemdnangbt(lvbt);
                            player.combineNew.ratioCombine = getRationangbt(lvbt);

                            String npcSay = "Bông tai Porata Cấp: " + lvbt + " \n|2|";
                            for (Item.ItemOption io : bongtai.itemOptions) {
                                npcSay += io.getOptionString() + "\n";
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (manhvobt.quantity >= countmvbt) {
                                if (player.combineNew.goldCombine <= player.inventory.gold) {
                                    if (player.combineNew.gemCombine <= player.inventory.gem) {
                                        npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                        baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                                "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                                    } else {
                                        npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.gemCombine - player.inventory.gem) + " ngọc";
                                        baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                    }
                                } else {
                                    npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                    baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                }
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(countmvbt - manhvobt.quantity) + " Mảnh vỡ bông tai";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Đã đạt cấp tối đa! Nâng con cặc :)))", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 1 hoặc 2 và Mảnh vỡ bông tai", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 1 hoặc 2 và Mảnh vỡ bông tai", "Đóng");
                }
                break;
            case MO_CHI_SO_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item bongTai = null;
                    Item manhHon = null;
                    Item daXanhLam = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 921 || item.template.id == 1128) {
                            bongTai = item;
                        } else if (item.template.id == 934) {
                            manhHon = item;
                        } else if (item.template.id == 935) {
                            daXanhLam = item;
                        }
                    }
                    if (bongTai != null && manhHon != null && daXanhLam != null && manhHon.quantity >= 99) {

                        player.combineNew.goldCombine = GOLD_MOCS_BONG_TAI;
                        player.combineNew.gemCombine = Gem_MOCS_BONG_TAI;
                        player.combineNew.ratioCombine = RATIO_NANG_CAP;

                        String npcSay = "Bông tai Porata cấp " + (bongTai.template.id == 921 ? bongTai.template.id == 1128 ? "2" : "3" : "1") + " \n|2|";
                        for (Item.ItemOption io : bongTai.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            if (player.combineNew.gemCombine <= player.inventory.gem) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.gemCombine - player.inventory.gem) + " ngọc";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 2 hoặc 3, X99 Mảnh hồn bông tai và 1 Đá xanh lam", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 2 hoặc 3, X99 Mảnh hồn bông tai và 1 Đá xanh lam", "Đóng");
                }

                break;
            case EP_SAO_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item trangBi = null;
                    Item daPhaLe = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (isEpSaoPhaLeHoa(item)) {
                            trangBi = item;
                        } else if (isDaPhaLe(item)) {
                            daPhaLe = item;
                        }
                    }
                    int star = 0; //sao pha lê đã ép
                    int starEmpty = 0; //lỗ sao pha lê
                    if (trangBi != null && daPhaLe != null) {
                        for (Item.ItemOption io : trangBi.itemOptions) {
                            if (io.optionTemplate.id == 102) {
                                star = io.param;
                            } else if (io.optionTemplate.id == 107) {
                                starEmpty = io.param;
                            }
                        }
                        if (star < starEmpty) {
                            player.combineNew.gemCombine = getGemEpSao(star);
                            String npcSay = trangBi.template.name + "\n|2|";
                            for (Item.ItemOption io : trangBi.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            if (daPhaLe.template.type == 30) {
                                for (Item.ItemOption io : daPhaLe.itemOptions) {
                                    npcSay += "|7|" + io.getOptionString() + "\n";
                                }
                            } else {
                                npcSay += "|7|" + ItemService.gI().getItemOptionTemplate(getOptionDaPhaLe(daPhaLe)).name.replaceAll("#", getParamDaPhaLe(daPhaLe) + "") + "\n";
                            }
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.gemCombine) + " ngọc";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 trang bị có lỗ sao pha lê và 1 loại đá pha lê để ép vào", "Đóng");
                }
                break;
            case PHA_LE_HOA_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isTrangBiPhaLeHoa(item)) {
                        int star = 0;
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        if (star < MAX_STAR_ITEM) {
                            player.combineNew.goldCombine = getGoldPhaLeHoa(star);
                            player.combineNew.gemCombine = getGemPhaLeHoa(star);
                            player.combineNew.ratioCombine = getRatioPhaLeHoa(star);

                            String npcSay = item.template.name + "\n|2|";
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (player.combineNew.goldCombine <= player.inventory.gold) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }

                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 vật phẩm để pha lê hóa", "Đóng");
                }
                break;
            case PHA_LE_HOA_CAI_TRANG:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isCaiTrangPhaLeHoa(item)) {
                        int star = 0;
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        if (star < MAX_STAR_CAI_TRANG_HOA) {
                            player.combineNew.goldBarCombine = getGoldBarCaiTrangPhaLeHoa(star);
                            player.combineNew.ratioCombine = getRatioCaiTrangPhaLeHoa(star);

                            StringBuilder npcSay = new StringBuilder(item.template.name + "\n|2|");
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay.append(io.getOptionString()).append("\n");
                                }
                            }
                            npcSay.append("|7|Tỉ lệ thành công: ").append(player.combineNew.ratioCombine).append("%").append("\n");
                            Item barGoldItem = InventoryServiceNew.gI().getGoldBarItem(player);
                            if (barGoldItem != null && barGoldItem.isNotNullItem() && (barGoldItem.quantity - player.combineNew.goldBarCombine) >= 0) {
                                npcSay.append("|1|Cần ").append(player.combineNew.goldBarCombine).append(" thỏi vàng");
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay.toString(),
                                        "Nâng cấp");
                            } else {
                                npcSay.append("Còn thiếu ").append(player.combineNew.goldBarCombine).append(" thỏi vàng");
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay.toString(), "Đóng");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 cải trang để pha lê hóa", "Đóng");
                }
                break;
            case PHA_LE_HOA_PET:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isPetPhaLeHoa(item)) {
                        int star = 0;
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        if (star < MAX_STAR_PET) {
                            player.combineNew.ngocrongCombine = getNgocRongQuantityPetPhaLeHoa(star);
                            player.combineNew.ratioCombine = getRatioPetPhaLeHoa(star);

                            StringBuilder npcSay = new StringBuilder(item.template.name + "\n|2|");
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay.append(io.getOptionString()).append("\n");
                                }
                            }
                            npcSay.append("|7|Tỉ lệ thành công: ").append(player.combineNew.ratioCombine).append("%").append("\n");
                            Item ngocrongItem = InventoryServiceNew.gI().getNgocRongItem(player);
                            if (ngocrongItem != null && ngocrongItem.isNotNullItem() && (ngocrongItem.quantity - player.combineNew.ngocrongCombine) >= 0) {
                                npcSay.append("|1|Cần ").append(player.combineNew.ngocrongCombine).append(" ngọc rồng 3 sao");
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay.toString(),
                                        "Nâng cấp");
                            } else {
                                npcSay.append("Còn thiếu ").append(player.combineNew.goldBarCombine).append(" ngọc rồng 3 sao");
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay.toString(), "Đóng");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 pet để pha lê hóa", "Đóng");
                }
                break;
            case PHA_LE_HOA_LINH_THU:
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (isLinhThuPhaLeHoa(item)) {
                        int star = 0;
                        for (Item.ItemOption io : item.itemOptions) {
                            if (io.optionTemplate.id == 107) {
                                star = io.param;
                                break;
                            }
                        }
                        if (star < MAX_START_LINHTHU) {
                            player.combineNew.vangKhoaCombine = getNgocKhoaQuantityLinhThuPhaLeHoa(star);
                            player.combineNew.ratioCombine = getRatioCaiTrangPhaLeHoa(star);

                            StringBuilder npcSay = new StringBuilder(item.template.name + "\n|2|");
                            for (Item.ItemOption io : item.itemOptions) {
                                if (io.optionTemplate.id != 102) {
                                    npcSay.append(io.getOptionString()).append("\n");
                                }
                            }
                            npcSay.append("|7|Tỉ lệ thành công: ").append(player.combineNew.ratioCombine).append("%").append("\n");
                            Item vangKhoaItem = InventoryServiceNew.gI().getVangKhoaItem(player);
                            if (vangKhoaItem != null && vangKhoaItem.isNotNullItem() && (vangKhoaItem.quantity - player.combineNew.vangKhoaCombine) >= 0) {
                                npcSay.append("|1|Cần ").append(player.combineNew.vangKhoaCombine).append(" thoải vàng khóa.");
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay.toString(),
                                        "Nâng cấp");
                            } else {
                                npcSay.append("Còn thiếu ").append(player.combineNew.vangKhoaCombine).append("Vàng khóa.");
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay.toString(), "Đóng");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm đã đạt tối đa sao pha lê", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Vật phẩm này không thể đục lỗ", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy hãy chọn 1 pet để pha lê hóa", "Đóng");
                }
                break;
            case NHAP_NGOC_RONG:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 1) {
                        Item item = player.combineNew.itemsCombine.get(0);
                        if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                            String npcSay = "|2|Con có muốn biến 7 " + item.template.name + " thành\n"
                                    + "1 viên " + ItemService.gI().getTemplate((short) (item.template.id - 1)).name + "\n"
                                    + "|7|Cần 7 " + item.template.name;
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Làm phép", "Từ chối");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }
                break;
            case NANG_CAP_VAT_PHAM:
                if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đá nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    Item itemDo = null;
                    Item itemDNC = null;
                    Item itemDBV = null;
                    for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                        if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                                itemDBV = player.combineNew.itemsCombine.get(j);
                                continue;
                            }
                            if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                                itemDo = player.combineNew.itemsCombine.get(j);
                            } else {
                                itemDNC = player.combineNew.itemsCombine.get(j);
                            }
                        }
                    }
                    if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                        int level = 0;
                        for (Item.ItemOption io : itemDo.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < MAX_LEVEL_ITEM) {
                            player.combineNew.goldCombine = getGoldNangCapDo(level);
                            player.combineNew.ratioCombine = (float) getTileNangCapDo(level);
                            player.combineNew.countDaNangCap = getCountDaNangCapDo(level);
                            player.combineNew.countDaBaoVe = (short) getCountDaBaoVe(level);
                            String npcSay = "|2|Hiện tại " + itemDo.template.name + " (+" + level + ")\n|0|";
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id != 72) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            String option = null;
                            int param = 0;
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id == 47
                                        || io.optionTemplate.id == 6
                                        || io.optionTemplate.id == 0
                                        || io.optionTemplate.id == 7
                                        || io.optionTemplate.id == 14
                                        || io.optionTemplate.id == 22
                                        || io.optionTemplate.id == 23) {
                                    option = io.optionTemplate.name;
                                    param = io.param + (io.param * 10 / 100);
                                    break;
                                }
                            }
                            npcSay += "|2|Sau khi nâng cấp (+" + (level + 1) + ")\n|7|"
                                    + option.replaceAll("#", String.valueOf(param))
                                    + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                    + (player.combineNew.countDaNangCap > itemDNC.quantity ? "|7|" : "|1|")
                                    + "Cần " + player.combineNew.countDaNangCap + " " + itemDNC.template.name
                                    + "\n" + (player.combineNew.goldCombine > player.inventory.gold ? "|7|" : "|1|")
                                    + "Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";

                            String daNPC = player.combineNew.itemsCombine.size() == 3 && itemDBV != null ? String.format("\nCần tốn %s đá bảo vệ", player.combineNew.countDaBaoVe) : "";
                            if ((level == 2 || level == 4 || level == 6) && !(player.combineNew.itemsCombine.size() == 3 && itemDBV != null)) {
                                npcSay += "\nNếu thất bại sẽ rớt xuống (+" + (level - 1) + ")";
                            }
                            if (player.combineNew.countDaNangCap > itemDNC.quantity) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaNangCap - itemDNC.quantity) + " " + itemDNC.template.name);
                            } else if (player.combineNew.goldCombine > player.inventory.gold) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + Util.numberToMoney((player.combineNew.goldCombine - player.inventory.gold)) + " vàng");
                            } else if (player.combineNew.itemsCombine.size() == 3 && Objects.nonNull(itemDBV) && itemDBV.quantity < player.combineNew.countDaBaoVe) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaBaoVe - itemDBV.quantity) + " đá bảo vệ");
                            } else {
                                this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                        npcSay, "Nâng cấp\n" + Util.numberToMoney(player.combineNew.goldCombine) + " vàng" + daNPC, "Từ chối");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang bị của ngươi đã đạt cấp tối đa", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                    }
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        break;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                }
                break;
            case PHAN_RA_DO_THAN_LINH:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con hãy đưa ta đồ thần linh để phân rã", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 1) {
                    List<Integer> itemdov2 = new ArrayList<>(Arrays.asList(562, 564, 566));
                    int couponAdd = 0;
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (item.isNotNullItem()) {
                        if (item.template.id >= 555 && item.template.id <= 567) {
                            couponAdd = itemdov2.stream().anyMatch(t -> t == item.template.id) ? 2 : item.template.id == 561 ? 3 : 1;
                        }
                    }
                    if (couponAdd == 0) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta chỉ có thể phân rã đồ thần linh thôi", "Đóng");
                        return;
                    }
                    String npcSay = "|2|Sau khi phân rải vật phẩm\n|7|"
                            + "Bạn sẽ nhận được : " + couponAdd + " Điểm\n"
                            + (500000000 > player.inventory.gold ? "|7|" : "|1|")
                            + "Cần " + Util.numberToMoney(500000000) + " vàng";

                    if (player.inventory.gold < 500000000) {
                        this.baHatMit.npcChat(player, "Hết tiền rồi\nẢo ít thôi con");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_PHAN_RA_DO_THAN_LINH,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(500000000) + " vàng", "Từ chối");
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta chỉ có thể phân rã 1 lần 1 món đồ thần linh", "Đóng");
                }
                break;
            case NANG_CAP_DO_TS:
                if (player.combineNew.itemsCombine.size() != 4) {
                    this.npsthiensu64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Nguyên liệu", "Đóng");
                    break;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
                    Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
                    break;
                }
                if (player.combineNew.itemsCombine.size() == 4) {
                    Item manhts = null;
                    Item danc = null;
                    Item damayman = null;
                    Item congthuc = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.isManhTS()) {
                                manhts = item;
                            } else if (item.isdanangcapDoTs()) {
                                danc = item;
                            } else if (item.isDamayman()) {
                                damayman = item;
                            } else if (item.isCongthucNomal() || item.isCongthucVip()) {
                                congthuc = item;
                            }
                        }
                    }
                    if (manhts == null || manhts.quantity < 999) {
                        Service.gI().sendThongBao(player, "Cần x999 mảnh thiên sứ!!");
                        break;
                    }
                    if (danc == null) {
                        Service.gI().sendThongBao(player, "Thiếu đá nâng cấp!!");
                        break;
                    }
                    if (damayman == null) {
                        Service.gI().sendThongBao(player, "Thiếu đá may mắn!!");
                        break;
                    }
                    if (congthuc == null) {
                        Service.gI().sendThongBao(player, "Thiếu công thức!!");
                        break;
                    }
                    short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}};
                    Item itemTS = ItemService.gI().createNewItem(itemIds[congthuc.template.gender][manhts.typeIdManh()]);
                    String npcSay = "Chế tạo " + itemTS.template.name + "\n"
                            + "Mạnh hơn trang bị Hủy Diệt từ 20% đến 35%\n"
                            + "Mảnh ghép " + manhts.quantity + "/999 (Thất bại -99 mảnh ghép)\n"
                            + danc.template.name + " (thêm " + (danc.template.id - 1073) * 10 + "% tỉ lệ thành công)\n"
                            + damayman.template.name + " (thêm " + (damayman.template.id - 1078) * 10 + "% tỉ lệ tối đa các chỉ số)\n"
                            + "Tỉ lệ thành công: " + get_Tile_nang_Do_TS(danc, congthuc) + "%\n"
                            + "Phí nâng cấp: " + Util.numberToMoney(COST_NANG_DO_TS) + " vàng";

                    if (player.inventory.gold < COST_NANG_DO_TS) {
                        this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        break;
                    }
                    this.whis.createOtherMenu(player, ConstNpc.MENU_NANG_CAP_DO_TS,
                            npcSay, "Nâng cấp", "Từ chối");
                } else {
                    this.whis.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;

            case NANG_CAP_DO_KICH_HOAT:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 1 Món đồ Thiên Sứ, x20 thỏi vàng và một món đồ kích hoạt thường bất kỳ", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item dts = null;
                    Item tv = null;
                    Item dokh = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id >= 1048 && item.template.id <= 1062) {
                                dts = item;
                            } else if (item.template.id == 457) {
                                tv = item;
                            } else if (item.isSKHThuong()) {
                                dokh = item;
                            }
                        }
                    }
                    if (dts == null) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ Thiên Sứ", "Đóng");
                        return;
                    }
                    if (dokh == null) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ Kích Hoạt Thường", "Đóng");
                        return;
                    }
                    if (tv == null || tv.quantity < 20) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu " + (tv == null ? 20 : (20 - tv.quantity)) + " Thỏi Vàng", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn Nâng cấp Đồ KH thường này thành Đồ KH vip 100% chỉ số set bất kì không?\n|7|"
                            + "|1|Cần " + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " vàng";

                    if (player.inventory.gold < COST_DAP_DO_KICH_HOAT) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_DAP_DO_KICH_HOAT,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST_DAP_DO_KICH_HOAT) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp!", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;

            case NANG_CAP_SKH_VIP:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 1 món Thần Linh và 2 món SKH ngẫu nhiên", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ Thần Linh", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKHVip()).count() < 2) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ kích hoạt ", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                            + "Và nhận được " + player.combineNew.itemsCombine.stream().filter(Item::isDTL).findFirst().get().typeName() + " kích hoạt VIP tương ứng\n"
                            + "|1|Cần " + Util.numberToMoney(COST) + " vàng";

                    if (player.inventory.gold < COST) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_NANG_DOI_SKH_VIP,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
            case AP_TRUNG_LINH_THU:
                if (player.combineNew.itemsCombine.size() != 3) {
                    this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Nguyên liệu", "Đóng");
                    return;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
                    this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn phải có ít nhất 1 ô trống hành trang", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item quatrung = null;
                    Item honlinhthu = null;
                    Item thoivang = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id == 2028) {
                                quatrung = item;
                            } else if (item.template.id == 2029) {
                                honlinhthu = item;
                            } else if (item.template.id == 457) {
                                thoivang = item;
                            }
                        }
                    }
                    if (honlinhthu == null || honlinhthu.quantity < 200) {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần x200 Hồn linh thú!!", "Đóng");
                        return;
                    }
                    if (thoivang == null || thoivang.quantity < 20) {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần x20 Thỏi Vàng!!", "Đóng");
                        return;
                    }
                    if (quatrung == null) {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu quả trứng!!", "Đóng");
                        return;
                    }
                    String npcSay = "|1|Cậu đang có\n"
                            + "|5|" + quatrung.template.name + "\n"
                            + "|5|" + honlinhthu.quantity + " Hồn linh thú\n"
                            + "|1|Tôi sẽ giúp cậu ấp quả trứng này\n"
                            + "|7|Trong vòng 14 ngày với chi phí 20 thỏi vàng\n"
                            + "|1|Cậu có đồng ý không!";

                    this.bulmatl.createOtherMenu(player, ConstNpc.MENU_AP_TRUNG,
                            npcSay, "Đồng Ý", "Từ chối");
                } else {
                    this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
            case NANG_CAP_LINH_THU:
                if (player.combineNew.itemsCombine.size() != 4) {
                    this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Nguyên liệu", "Đóng");
                    break;
                }
                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
                    this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn phải có ít nhất 1 ô trống hành trang", "Đóng");
                    break;
                }
                if (player.combineNew.itemsCombine.size() == 4) {
                    Item linhthu = null;
                    Item danacap = null;
                    Item thangtinhthach = null;
                    Item thoivang = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (islinhthu(item)) {
                                linhthu = item;
                            } else if (item.template.id == 2031) {
                                thangtinhthach = item;
                            } else if (item.template.id == 457) {
                                thoivang = item;
                            } else if (isdanguyento(item)) {
                                danacap = item;
                            }
                        }
                    }
                    if (linhthu == null) {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu linh thú hmm!!", "Đóng");
                    } else if (danacap == null) {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần đá nâng cấp!!", "Đóng");
                    } else if (thoivang == null || thoivang.quantity < 20) {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần x20 Thỏi Vàng!!", "Đóng");
                    } else if (thangtinhthach == null) {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần x1 Thăng tinh thạch!!", "Đóng");
                    } else if (isCoupleItemNangLinhThuCheck(linhthu, danacap)) {
                        int level = 0;
                        for (Item.ItemOption io : linhthu.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < MAX_LEVEL_ITEM) {
                            player.combineNew.ratioCombine = (float) getTileNangCapLinhThu(level);
                            player.combineNew.countDaNangCap = getCountDaNangCapLinhThu(level);
                            String npcSay = "|2|Hiện tại " + linhthu.template.name + " (+" + level + ")\n|0|";
                            for (Item.ItemOption io : linhthu.itemOptions) {
                                if (io.optionTemplate.id != 72) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            npcSay += "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                    + (player.combineNew.countDaNangCap > danacap.quantity ? "|7|" : "|1|")
                                    + "Cần " + player.combineNew.countDaNangCap + " " + danacap.template.name;
                            if (player.combineNew.countDaNangCap > danacap.quantity) {
                                this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaNangCap - danacap.quantity) + " " + danacap.template.name);
                            } else {
                                this.bulmatl.createOtherMenu(player, ConstNpc.MENU_NANG_CAP_LINH_THU,
                                        npcSay, "Nâng cấp\n", "Từ chối");
                            }
                        } else {
                            this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Linh Thú của ngươi đã đạt cấp tối đa", "Đóng");
                        }
                    } else {
                        this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 Linh thú và 1 loại đá Nguyên tố", "Đóng");
                    }
                } else {
                    this.bulmatl.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
        }
    }

    /**
     * Bắt đầu đập đồ - điều hướng từng loại đập đồ
     *
     * @param player
     */
    public void startCombine(Player player) {
        switch (player.combineNew.typeCombine) {
            case EP_SAO_TRANG_BI:
                epSaoTrangBi(player);
                break;
            case PHA_LE_HOA_TRANG_BI:
                phaLeHoaTrangBi(player);
                break;
            case PHA_LE_HOA_CAI_TRANG:
                phaLeHoaCaiTrang(player);
                break;
            case PHA_LE_HOA_PET:
                phaLeHoaPet(player);
                break;
            case PHA_LE_HOA_LINH_THU:
                phaLeHoaLinhThu(player);
                break;
            case CHUYEN_HOA_TRANG_BI:
                break;
            case NHAP_NGOC_RONG:
                nhapNgocRong(player);
                break;

            case PHAN_RA_DO_THAN_LINH:
                phanradothanlinh(player);
                break;
            case NANG_CAP_DO_TS:
                openDTS(player);
                break;
            case NANG_CAP_SKH_VIP:
                openSKHVIP(player);
                break;
            case NANG_CAP_VAT_PHAM:
                nangCapVatPham(player);
                break;
            case NANG_CAP_BONG_TAI:
                nangCapBongTai(player);
                break;
            case MO_CHI_SO_BONG_TAI:
                moChiSoBongTai(player);
                break;
            case NANG_CAP_KHI:
                nangCapKhi(player);
                break;
            case NANG_CAP_DO_KICH_HOAT:
                dapDoKichHoat(player);
                break;
            case AP_TRUNG_LINH_THU:
                aptrung(player);
                break;
            case NANG_CAP_LINH_THU:
                nangcaplinhthu(player);
                break;
        }

        player.iDMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }

    private void nangcaplinhthu(Player player) {
        if (player.combineNew.itemsCombine.size() == 4) {
            Item linhthu = null;
            Item danacap = null;
            Item thangtinhthach = null;
            Item thoivang = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (islinhthu(item)) {
                        linhthu = item;
                    } else if (item.template.id == 2031) {
                        thangtinhthach = item;
                    } else if (item.template.id == 457) {
                        thoivang = item;
                    } else if (isdanguyento(item)) {
                        danacap = item;
                    }
                }
            }
            if (linhthu == null) {
                Service.gI().sendThongBao(player, "Thiếu linh thú!!");
            } else if (danacap == null) {
                Service.gI().sendThongBao(player, "Cần x200 Hồn linh thú!!");
            } else if (thoivang == null || thoivang.quantity < 20) {
                Service.gI().sendThongBao(player, "Cần x20 Thỏi Vàng!!");
            } else if (thangtinhthach == null) {
                Service.gI().sendThongBao(player, "Cần x1 Thăng tinh thạch!!");
            } else if (isCoupleItemNangLinhThuCheck(linhthu, danacap)) {
                int countDaNangCap = player.combineNew.countDaNangCap;
                if (danacap.quantity < countDaNangCap) {
                    return;
                }
                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : linhthu.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level < MAX_LEVEL_ITEM) {
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        linhthu.itemOptions.clear();
                        linhthu.itemOptions.add(new Item.ItemOption(72, level + 1));
                        laychisoEggLinhThu(player, linhthu, level + 1);
                        sendEffectSuccessCombine(player);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, danacap, player.combineNew.countDaNangCap);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, thoivang, 20);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, thangtinhthach, 1);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    public boolean islinhthu(Item item) {
        switch (item.template.id) {
            case 2021:
            case 1998:
            case 2020:
            case 1995:
            case 2019:
            case 1993:
                return true;
            default:
                return false;
        }
    }

    public boolean isdanguyento(Item item) {
        switch (item.template.id) {
            case 1977://Aether Stone (Hỏa)
            case 1978://Aurora Stone (Thổ)
            case 1976://Soul Stone (Kim)
            case 1981://Space Stone (Thủy)
            case 1979://Power Stone (Mộc)
            case 1980://Mind Stone (Phong)
                return true;
            default:
                return false;
        }
    }

    public void laychisoEggLinhThu(Player player, Item linhthu, int lvnow) {
        switch (linhthu.template.id) {
            case 2021:
                linhthu.itemOptions.add(new Item.ItemOption(50, 10 + 3 * lvnow));//%sd
                linhthu.itemOptions.add(new Item.ItemOption(0, 1000 + 300 * lvnow));//sd
                break;
            case 1998:
                linhthu.itemOptions.add(new Item.ItemOption(94, 10 + 3 * lvnow));//%giap
                linhthu.itemOptions.add(new Item.ItemOption(47, 1000 + 300 * lvnow));//sd
                break;
            case 2020:
                linhthu.itemOptions.add(new Item.ItemOption(14, 5 + 1 * lvnow));//%cm
                linhthu.itemOptions.add(new Item.ItemOption(5, 10 + 3 * lvnow));//%sdcm
                break;
            case 1995:
                linhthu.itemOptions.add(new Item.ItemOption(103, 10 + 3 * lvnow));//%ki
                linhthu.itemOptions.add(new Item.ItemOption(23, 10 + 3 * lvnow));//KI
                break;
            case 2019:
                linhthu.itemOptions.add(new Item.ItemOption(77, 10 + 3 * lvnow));//%HP
                linhthu.itemOptions.add(new Item.ItemOption(22, 10 + 3 * lvnow));//HP
                break;
            case 1993:
                linhthu.itemOptions.add(new Item.ItemOption(108, 5 + 1 * lvnow));//%ne
                linhthu.itemOptions.add(new Item.ItemOption(97, 10 + 3 * lvnow));//pst
                break;
        }
        InventoryServiceNew.gI().sendItemBags(player);
    }

    private boolean isCoupleItemNangLinhThuCheck(Item linhthu, Item daNangCap) {
        if (linhthu != null && daNangCap != null) {
            if (linhthu.template.id == 2021 && daNangCap.template.id == 1977) {
                return true;
            } else if (linhthu.template.id == 1998 && daNangCap.template.id == 1978) {
                return true;
            } else if (linhthu.template.id == 2020 && daNangCap.template.id == 1976) {
                return true;
            } else if (linhthu.template.id == 1995 && daNangCap.template.id == 1981) {
                return true;
            } else if (linhthu.template.id == 2019 && daNangCap.template.id == 1979) {
                return true;
            } else if (linhthu.template.id == 1993 && daNangCap.template.id == 1980) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private double getTileNangCapLinhThu(int level) {
        switch (level) {
            case 0:
                return 50f;
            case 1:
                return 30f;
            case 2:
                return 15f;
            case 3:
                return 5f;
            case 4:
                return 2f;
            case 5:
                return 1f;
            case 6:
                return 0.5f;
            case 7: // 7 sao
                return 0.1f;
            case 8:
                return 0.05f;
        }
        return 0;
    }

    private int getCountDaNangCapLinhThu(int level) {
        return 250 + 50 * level;
    }

    private void aptrung(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item quatrung = null;
            Item honlinhthu = null;
            Item thoivang = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id == 2028) {
                        quatrung = item;
                    } else if (item.template.id == 2029) {
                        honlinhthu = item;
                    } else if (item.template.id == 457) {
                        thoivang = item;
                    }
                }
            }
            if (honlinhthu == null || honlinhthu.quantity < 200) {
                Service.gI().sendThongBao(player, "Cần x200 Hồn linh thú!!");
                return;
            }
            if (thoivang == null || thoivang.quantity < 20) {
                Service.gI().sendThongBao(player, "Cần x20 Thỏi Vàng!!");
                return;
            }
            if (quatrung == null) {
                Service.gI().sendThongBao(player, "Thiếu quả trứng!!");
                return;
            }
            InventoryServiceNew.gI().subQuantityItemsBag(player, thoivang, 20);
            InventoryServiceNew.gI().subQuantityItemsBag(player, honlinhthu, 200);
            InventoryServiceNew.gI().subQuantityItemsBag(player, quatrung, 1);
            EggLinhThu.createEggLinhThu(player);
            Service.gI().sendThongBao(player, "Ấp trứng thành công!");
            InventoryServiceNew.gI().sendItemBags(player);
            ChangeMapService.gI().changeMapInYard(player, 200, player.zone.zoneId, player.location.x);
        }
    }

    private void nangCapKhi(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int ruby = player.combineNew.rubyCombine;
            if (player.inventory.ruby < ruby) {
                Service.gI().sendThongBao(player, "Không đủ hồng ngọc để thực hiện");
                return;
            }

            Item ctkhi = null;
            Item dns = null;
            Item dgl = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (checkctkhi(item)) {
                    ctkhi = item;
                } else if (item.template.id == 674) {
                    dns = item;
                } else if (item.template.id == 579) {
                    dgl = item;
                }
            }
            if (ctkhi != null && dns != null && dgl != null) {
                int level = 0;
                for (Item.ItemOption io : ctkhi.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        break;
                    }
                }
                if (level < MAX_LEVEL_ITEM) {
                    int lvkhi = lvkhi(ctkhi);
                    int countdns = getcountdnsnangkhi(lvkhi);
                    if (countdns > dns.quantity) {
                        Service.gI().sendThongBao(player, "Không đủ đá ngũ sắc");
                        return;
                    }
                    if (20 > dgl.quantity) {
                        Service.gI().sendThongBao(player, "Không đủ duoi khi");
                        return;
                    }
                    player.inventory.gold -= gold;
                    player.inventory.ruby -= ruby;
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dns, countdns);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dgl, 20);
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        short idctkhisaunc = getidctkhisaukhilencap(lvkhi);
                        ctkhi.template = ItemService.gI().getTemplate(idctkhisaunc);
                        ctkhi.itemOptions.clear();
                        ctkhi.itemOptions.add(new Item.ItemOption(72, lvkhi + 1));
                        laychisoctkhi(player, ctkhi, lvkhi);
                        sendEffectSuccessCombine(player);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    public void laychisoctkhi(Player player, Item ctkhi, int lvkhi) {
        ctkhi.itemOptions.add(new ItemOption(50, 10 + 4 * lvkhi));//sd
        ctkhi.itemOptions.add(new ItemOption(77, 15 + 7 * lvkhi));//hp
        ctkhi.itemOptions.add(new ItemOption(103, 15 + 7 * lvkhi));//ki
        ctkhi.itemOptions.add(new ItemOption(14, 7 + 1 * lvkhi));//cm
        ctkhi.itemOptions.add(new ItemOption(5, 10 + 2 * lvkhi));//sd cm
        ctkhi.itemOptions.add(new ItemOption(106, 0));
        ctkhi.itemOptions.add(new ItemOption(34, 0));
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void GetTrangBiKichHoathuydiet(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        int[][] optionNormal = {{127, 128}, {130, 132}, {133, 135}};
        int[][] paramNormal = {{139, 140}, {142, 144}, {136, 138}};
        int[][] optionVIP = {{129}, {131}, {134}};
        int[][] paramVIP = {{141}, {143}, {137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type == 0) {
            item.itemOptions.add(new ItemOption(47, Util.nextInt(1500, 2000)));
        }
        if (item.template.type == 1) {
            item.itemOptions.add(new ItemOption(22, Util.nextInt(100, 150)));
        }
        if (item.template.type == 2) {
            item.itemOptions.add(new ItemOption(0, Util.nextInt(9000, 11000)));
        }
        if (item.template.type == 3) {
            item.itemOptions.add(new ItemOption(23, Util.nextInt(90, 150)));
        }
        if (item.template.type == 4) {
            item.itemOptions.add(new ItemOption(14, Util.nextInt(15, 20)));
        }
        if (randomSkh <= 20) {//tile ra do kich hoat
            if (randomSkh <= 5) { // tile ra option vip
                item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            } else {//
                item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            }
        }

        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void GetTrangBiKichHoatthiensu(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        int[][] optionNormal = {{127, 128}, {130, 132}, {133, 135}};
        int[][] paramNormal = {{139, 140}, {142, 144}, {136, 138}};
        int[][] optionVIP = {{129}, {131}, {134}};
        int[][] paramVIP = {{141}, {143}, {137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type == 0) {
            item.itemOptions.add(new ItemOption(47, Util.nextInt(2000, 2500)));
        }
        if (item.template.type == 1) {
            item.itemOptions.add(new ItemOption(22, Util.nextInt(150, 200)));
        }
        if (item.template.type == 2) {
            item.itemOptions.add(new ItemOption(0, Util.nextInt(18000, 20000)));
        }
        if (item.template.type == 3) {
            item.itemOptions.add(new ItemOption(23, Util.nextInt(150, 200)));
        }
        if (item.template.type == 4) {
            item.itemOptions.add(new ItemOption(14, Util.nextInt(20, 25)));
        }
        if (randomSkh <= 20) {//tile ra do kich hoat
            if (randomSkh <= 5) { // tile ra option vip
                item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            } else {//
                item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 0));
                item.itemOptions.add(new ItemOption(30, 0));
            }
        }

        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    private void doiKiemThan(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item keo = null, luoiKiem = null, chuoiKiem = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 2015) {
                    keo = it;
                } else if (it.template.id == 2016) {
                    chuoiKiem = it;
                } else if (it.template.id == 2017) {
                    luoiKiem = it;
                }
            }
            if (keo != null && keo.quantity >= 99 && luoiKiem != null && chuoiKiem != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2018);
                    item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(9, 15)));
                    item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(8, 15)));
                    item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(8, 15)));
                    if (Util.isTrue(80, 100)) {
                        item.itemOptions.add(new Item.ItemOption(93, Util.nextInt(1, 15)));
                    }
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, keo, 99);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, luoiKiem, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, chuoiKiem, 1);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiChuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhNhua = player.combineNew.itemsCombine.get(0);
            if (manhNhua.template.id == 2014 && manhNhua.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2016);
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhNhua, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiLuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhSat = player.combineNew.itemsCombine.get(0);
            if (manhSat.template.id == 2013 && manhSat.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2017);
                    InventoryServiceNew.gI().addItemBag(player, item);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhSat, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiManhKichHoat(Player player) {
        if (player.combineNew.itemsCombine.size() == 2 || player.combineNew.itemsCombine.size() == 3) {
            Item nr1s = null, doThan = null, buaBaoVe = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 14) {
                    nr1s = it;
                } else if (it.template.id == 2010) {
                    buaBaoVe = it;
                } else if (it.template.id >= 555 && it.template.id <= 567) {
                    doThan = it;
                }
            }

            if (nr1s != null && doThan != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_MANH_KICH_HOAT) {
                    player.inventory.gold -= COST_DOI_MANH_KICH_HOAT;
                    int tiLe = buaBaoVe != null ? 100 : 50;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = ItemService.gI().createNewItem((short) 2009);
                        item.itemOptions.add(new Item.ItemOption(30, 0));
                        InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, nr1s, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, doThan, 1);
                    if (buaBaoVe != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, buaBaoVe, 1);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            } else {
                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị thần linh và 1 viên ngọc rồng 1 sao", "Đóng");
            }
        }
    }

    private void dapDoKichHoat(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item dts = null;
            Item tv = null;
            Item dokh = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id >= 1048 && item.template.id <= 1062) {
                        dts = item;
                    } else if (item.template.id == 457) {
                        tv = item;
                    } else if (item.isSKHThuong()) {
                        dokh = item;
                    }
                }
            }
            if (dts != null && dokh != null && tv != null && tv.quantity >= 20) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0 //check chỗ trống hành trang
                        && player.inventory.gold >= COST_DAP_DO_KICH_HOAT) {
                    player.inventory.gold -= COST_DAP_DO_KICH_HOAT;
                    int tiLe = 100;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = ArrietyDrop.randomCS_DKH(dokh.template.id, (byte) 1, dokh.template.gender == 3 ? player.gender : dokh.template.gender);
                        InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dts, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 20);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, dokh, 1);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                } else {
                    Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
                }
            }
        }
    }

    private void phanradothanlinh(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            player.inventory.gold -= 500000000;
            List<Integer> itemdov2 = new ArrayList<>(Arrays.asList(562, 564, 566));
            Item item = player.combineNew.itemsCombine.get(0);
            int couponAdd = itemdov2.stream().anyMatch(t -> t == item.template.id) ? 2 : item.template.id == 561 ? 3 : 1;
            sendEffectSuccessCombine(player);
            player.inventory.coupon += couponAdd;
            this.baHatMit.npcChat(player, "Con đã nhận được " + couponAdd + " điểm");
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
            player.combineNew.itemsCombine.clear();
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            reOpenItemCombine(player);
        }
    }

    public void openDTS(Player player) {
        //check sl đồ tl, đồ hd
        // new update 2 mon huy diet + 1 mon than linh(skh theo style) +  5 manh bat ki
        if (player.combineNew.itemsCombine.size() != 4) {
            Service.gI().sendThongBao(player, "Thiếu đồ");
            return;
        }
        if (player.inventory.gold < COST_NANG_DO_TS) {
            Service.gI().sendThongBao(player, "Ảo ít thôi con...");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
            Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
            return;
        }
        if (player.combineNew.itemsCombine.size() == 4) {
            Item manhts = null;
            Item danc = null;
            Item damayman = null;
            Item congthuc = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.isManhTS()) {
                        manhts = item;
                    } else if (item.isdanangcapDoTs()) {
                        danc = item;
                    } else if (item.isDamayman()) {
                        damayman = item;
                    } else if (item.isCongthucNomal() || item.isCongthucVip()) {
                        congthuc = item;
                    }
                }
            }
            if (manhts == null || danc == null || damayman == null || congthuc == null) {
                Service.gI().sendThongBao(player, "Thiếu đồ!!");
                return;
            }
            if (manhts.quantity < 999) {
                Service.gI().sendThongBao(player, "Cần x999 mảnh thiên sứ!");
                return;
            }
            if (manhts != null && danc != null && damayman != null && congthuc != null && manhts.quantity >= 999) {
                int tile = get_Tile_nang_Do_TS(danc, congthuc);
                int perLucky = 20;
                perLucky += perLucky * (damayman.template.id - 1078) * 10 / 100;
                int perSuccesslucky = Util.nextInt(0, 100);
                if (Util.isTrue(tile, 100)) {
                    short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}};
                    Item itemTS = ItemService.gI().DoThienSu(itemIds[congthuc.template.gender][manhts.typeIdManh()], congthuc.template.gender, perSuccesslucky, perLucky);
                    sendEffectSuccessCombineDoTS(player, itemTS.template.iconID);
                    InventoryServiceNew.gI().addItemBag(player, itemTS);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhts, 999);
                } else {
                    sendEffectFailCombineDoTS(player);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhts, 99);
                }
                player.inventory.gold -= COST_NANG_DO_TS;
                InventoryServiceNew.gI().subQuantityItemsBag(player, danc, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(player, damayman, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(player, congthuc, 1);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                player.combineNew.itemsCombine.clear();
                reOpenItemCombine(player);
            }
        }
    }

    public int get_Tile_nang_Do_TS(Item danc, Item congthuc) {
        int tile = 0;
        if (congthuc.isCongthucVip()) {
            tile = 35;
        } else if (congthuc.isCongthucNomal()) {
            tile = 20;
        }
        if (danc != null && danc.isdanangcapDoTs()) {
            tile += (danc.template.id - 1073) * 10;
        }
        return tile;
    }

    public void openSKHVIP(Player player) {
        // 1 thiên sứ + 2 món kích hoạt -- món đầu kh làm gốc
        if (player.combineNew.itemsCombine.size() != 3) {
            Service.gI().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTL()).count() != 1) {
            Service.gI().sendThongBao(player, "Thiếu đồ Thần Linh");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKHVip()).count() != 2) {
            Service.gI().sendThongBao(player, "Thiếu đồ kích hoạt");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < 1) {
                Service.gI().sendThongBao(player, "Con cần thêm vàng để đổi...");
                return;
            }
            player.inventory.gold -= COST;
            Item itemTS = player.combineNew.itemsCombine.stream().filter(Item::isDTL).findFirst().get();
            List<Item> itemSKH = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKHVip()).collect(Collectors.toList());
            CombineServiceNew.gI().sendEffectOpenItem(player, itemTS.template.iconID, itemTS.template.iconID);
            short itemId;
            if (itemTS.template.gender == 3 || itemTS.template.type == 4) {
                itemId = Manager.radaSKHVip[Util.nextInt(0, 5)];
                if (player.getSession().getBdPlayer() > 0 && Util.isTrue(1, (int) (100 / player.getSession().getBdPlayer()))) {
                    itemId = Manager.radaSKHVip[6];
                }
            } else {
                itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][Util.nextInt(0, 5)];
                if (player.getSession().getBdPlayer() > 0 && Util.isTrue(1, (int) (100 / player.getSession().getBdPlayer()))) {
                    itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][6];
                }
            }
            int skhId = ItemService.gI().randomSKHId(itemTS.template.gender);
            Item item;
            if (new Item(itemId).isDTL()) {
                item = Util.ratiItemTL(itemId);
                item.itemOptions.add(new Item.ItemOption(skhId, 1));
                item.itemOptions.add(new Item.ItemOption(ItemService.gI().optionIdSKH(skhId), 1));
                item.itemOptions.remove(item.itemOptions.stream().filter(itemOption -> itemOption.optionTemplate.id == 21).findFirst().get());
                item.itemOptions.add(new Item.ItemOption(21, 15));
                item.itemOptions.add(new Item.ItemOption(30, 1));
            } else {
                item = ItemService.gI().itemSKH(itemId, skhId);
            }
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemTS, 1);
            itemSKH.forEach(i -> InventoryServiceNew.gI().subQuantityItemsBag(player, i, 1));
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        } else {
            Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    private void doiVeHuyDiet(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (item.isNotNullItem() && item.template.id >= 555 && item.template.id <= 567) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_VE_DOI_DO_HUY_DIET) {
                    player.inventory.gold -= COST_DOI_VE_DOI_DO_HUY_DIET;
                    Item ticket = ItemService.gI().createNewItem((short) (2001 + item.template.type));
                    ticket.itemOptions.add(new Item.ItemOption(30, 0));
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                    InventoryServiceNew.gI().addItemBag(player, ticket);
                    sendEffectOpenItem(player, item.template.iconID, ticket.template.iconID);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void nangCapBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item bongtai = null;
            Item manhvobt = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (checkbongtai(item)) {
                    bongtai = item;
                } else if (item.template.id == 933) {
                    manhvobt = item;
                }
            }
            if (bongtai != null && manhvobt != null) {
                int level = 0;
                for (Item.ItemOption io : bongtai.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        break;
                    }
                }
                if (level < 2) {
                    int lvbt = lvbt(bongtai);
                    int countmvbt = getcountmvbtnangbt(lvbt);
                    if (countmvbt > manhvobt.quantity) {
                        Service.gI().sendThongBao(player, "Không đủ Mảnh vỡ bông tai");
                        return;
                    }
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhvobt, countmvbt);
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        bongtai.template = ItemService.gI().getTemplate(getidbtsaukhilencap(lvbt));
                        bongtai.itemOptions.clear();
                        bongtai.itemOptions.add(new Item.ItemOption(72, lvbt + 1));
                        sendEffectSuccessCombine(player);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void moChiSoBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item bongTai = null;
            Item manhHon = null;
            Item daXanhLam = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == 921 || item.template.id == 1128) {
                    bongTai = item;
                } else if (item.template.id == 934) {
                    manhHon = item;
                } else if (item.template.id == 935) {
                    daXanhLam = item;
                }
            }
            if (bongTai != null && daXanhLam != null && manhHon.quantity >= 99) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, manhHon, 99);
                InventoryServiceNew.gI().subQuantityItemsBag(player, daXanhLam, 1);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    bongTai.itemOptions.clear();
                    if (bongTai.template.id == 921) {
                        bongTai.itemOptions.add(new Item.ItemOption(72, 2));
                    } else if (bongTai.template.id == 1128) {
                        bongTai.itemOptions.add(new Item.ItemOption(72, 3));
                    }
                    int rdUp = Util.nextInt(0, 7);
                    if (rdUp == 0) {
                        bongTai.itemOptions.add(new Item.ItemOption(50, Util.nextInt(5, 25)));
                    } else if (rdUp == 1) {
                        bongTai.itemOptions.add(new Item.ItemOption(77, Util.nextInt(5, 25)));
                    } else if (rdUp == 2) {
                        bongTai.itemOptions.add(new Item.ItemOption(103, Util.nextInt(5, 25)));
                    } else if (rdUp == 3) {
                        bongTai.itemOptions.add(new Item.ItemOption(108, Util.nextInt(5, 25)));
                    } else if (rdUp == 4) {
                        bongTai.itemOptions.add(new Item.ItemOption(94, Util.nextInt(5, 15)));
                    } else if (rdUp == 5) {
                        bongTai.itemOptions.add(new Item.ItemOption(14, Util.nextInt(5, 15)));
                    } else if (rdUp == 6) {
                        bongTai.itemOptions.add(new Item.ItemOption(80, Util.nextInt(5, 25)));
                    } else if (rdUp == 7) {
                        bongTai.itemOptions.add(new Item.ItemOption(81, Util.nextInt(5, 25)));
                    }
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void epSaoTrangBi(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item trangBi = null;
            Item daPhaLe = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (isEpSaoPhaLeHoa(item)) {
                    trangBi = item;
                } else if (isDaPhaLe(item)) {
                    daPhaLe = item;
                }
            }
            int star = 0; //sao pha lê đã ép
            int starEmpty = 0; //lỗ sao pha lê
            if (trangBi != null && daPhaLe != null) {
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : trangBi.itemOptions) {
                    if (io.optionTemplate.id == 102) {
                        star = io.param;
                        optionStar = io;
                    } else if (io.optionTemplate.id == 107) {
                        starEmpty = io.param;
                    }
                }
                if (star < starEmpty) {
                    player.inventory.gem -= gem;
                    int optionId = getOptionDaPhaLe(daPhaLe);
                    int param = getParamDaPhaLe(daPhaLe);
                    Item.ItemOption option = null;
                    for (Item.ItemOption io : trangBi.itemOptions) {
                        if (io.optionTemplate.id == optionId) {
                            option = io;
                            break;
                        }
                    }
                    if (option != null) {
                        option.param += param;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(optionId, param));
                    }
                    if (optionStar != null) {
                        optionStar.param++;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(102, 1));
                    }

                    InventoryServiceNew.gI().subQuantityItemsBag(player, daPhaLe, 1);
                    sendEffectSuccessCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void phaLeHoaTrangBi(Player player) {
        if (!player.combineNew.itemsCombine.isEmpty()) {
            int gold = player.combineNew.goldCombine;
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            } else if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item item = player.combineNew.itemsCombine.get(0);
            if (isTrangBiPhaLeHoa(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_STAR_ITEM) {
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    byte ratio = (optionStar != null && optionStar.param > 4) ? (byte) 2 : 1;
                    if (Util.isTrue(player.combineNew.ratioCombine, 100 * ratio)) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
//                        Util.WorhHistory("combine.txt", "-\nPlayer: " + player.name
//                                + "\nItem: " + item.template.name
//                                + "\nSố Lượng: " + item.quantity
//                                + "\nOption: " + item.itemOptions
//                                + "|Param: " + item.itemOptions.get(0).param);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void phaLeHoaCaiTrang(Player player) {
        Item goldBarItem = InventoryServiceNew.gI().getGoldBarItem(player);
        if (goldBarItem != null && goldBarItem.isNotNullItem() && goldBarItem.quantity >= player.combineNew.goldBarCombine) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (isCaiTrangPhaLeHoa(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_STAR_CAI_TRANG_HOA) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, goldBarItem, player.combineNew.goldBarCombine);
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
            }
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            reOpenItemCombine(player);
            return;
        }

        Service.gI().sendThongBao(player, "Không đủ thỏi vàng để thực hiện");

    }

    private void phaLeHoaPet(Player player) {
        Item ngocRongItem = InventoryServiceNew.gI().getNgocRongItem(player);
        if (ngocRongItem != null && ngocRongItem.isNotNullItem() && ngocRongItem.quantity >= player.combineNew.ngocrongCombine) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (isPetPhaLeHoa(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_STAR_CAI_TRANG_HOA) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, ngocRongItem, player.combineNew.ngocrongCombine);
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
            }
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            reOpenItemCombine(player);
            return;
        }

        Service.gI().sendThongBao(player, "Không đủ thỏi vàng để thực hiện");

    }

    private void phaLeHoaLinhThu(Player player) {
        Item vangKhoaItem = InventoryServiceNew.gI().getVangKhoaItem(player);
        if (vangKhoaItem != null && vangKhoaItem.isNotNullItem() && vangKhoaItem.quantity >= player.combineNew.vangKhoaCombine) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (isLinhThuPhaLeHoa(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_START_LINHTHU) {
                    InventoryServiceNew.gI().subQuantityItemsBag(player, vangKhoaItem, player.combineNew.vangKhoaCombine);
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
            }
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            reOpenItemCombine(player);
            return;
        }

        Service.gI().sendThongBao(player, "Không đủ vàng khóa để thực hiện");

    }

    private void nhapNgocRong(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                    Item nr = ItemService.gI().createNewItem((short) (item.template.id - 1));
                    InventoryServiceNew.gI().addItemBag(player, nr);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 7);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
                    sendEffectSuccessCombine(player);
                }
            }
        }
    }

    private void nangCapVatPham(Player player) {
        if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                return;//admin
            }
            Item itemDo = null;
            Item itemDNC = null;
            Item itemDBV = null;
            for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                        itemDBV = player.combineNew.itemsCombine.get(j);
                        continue;
                    }
                    if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                        itemDo = player.combineNew.itemsCombine.get(j);
                    } else {
                        itemDNC = player.combineNew.itemsCombine.get(j);
                    }
                }
            }
            if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                int countDaNangCap = player.combineNew.countDaNangCap;
                int gold = player.combineNew.goldCombine;
                short countDaBaoVe = player.combineNew.countDaBaoVe;
                if (player.inventory.gold < gold) {
                    Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }

                if (itemDNC.quantity < countDaNangCap) {
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (Objects.isNull(itemDBV)) {
                        return;
                    }
                    if (itemDBV.quantity < countDaBaoVe) {
                        return;
                    }
                }

                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : itemDo.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level < MAX_LEVEL_ITEM) {
                    player.inventory.gold -= gold;
                    Item.ItemOption option = null;
                    Item.ItemOption option2 = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 47
                                || io.optionTemplate.id == 6
                                || io.optionTemplate.id == 0
                                || io.optionTemplate.id == 7
                                || io.optionTemplate.id == 14
                                || io.optionTemplate.id == 22
                                || io.optionTemplate.id == 23) {
                            option = io;
                        } else if (io.optionTemplate.id == 27
                                || io.optionTemplate.id == 28) {
                            option2 = io;
                        }
                    }
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        option.param += (option.param * 10 / 100);
                        if (option2 != null) {
                            option2.param += (option2.param * 10 / 100);
                        }
                        if (optionLevel == null) {
                            itemDo.itemOptions.add(new Item.ItemOption(72, 1));
                        } else {
                            optionLevel.param++;
                        }
//                        if (optionLevel != null && optionLevel.param >= 5) {
//                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa nâng cấp "
//                                    + "thành công " + trangBi.template.name + " lên +" + optionLevel.param);
//                        }
                        sendEffectSuccessCombine(player);
                    } else {
                        if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                            option.param -= (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param -= (option2.param * 10 / 100);
                            }
                            optionLevel.param--;
                        }
                        sendEffectFailCombine(player);
                    }
                    if (player.combineNew.itemsCombine.size() == 3) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    /**
     * r
     * Hiệu ứng mở item
     *
     * @param player
     */
    public void sendEffectOpenItem(Player player, short icon1, short icon2) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_ITEM);
            msg.writer().writeShort(icon1);
            msg.writer().writeShort(icon2);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiệu ứng đập đồ thành công
     *
     * @param player
     */
    private void sendEffectSuccessCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_SUCCESS);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEffectSuccessCombineDoTS(Player player, short icon) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(7);
            msg.writer().writeShort(icon);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEffectFailCombineDoTS(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(8);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiệu ứng đập đồ thất bại
     *
     * @param player
     */
    private void sendEffectFailCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_FAIL);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gửi lại danh sách đồ trong tab combine
     *
     * @param player
     */
    private void reOpenItemCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(REOPEN_TAB_COMBINE);
            msg.writer().writeByte(player.combineNew.itemsCombine.size());
            for (Item it : player.combineNew.itemsCombine) {
                for (int j = 0; j < player.inventory.itemsBag.size(); j++) {
                    if (it == player.inventory.itemsBag.get(j)) {
                        msg.writer().writeByte(j);
                    }
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiệu ứng ghép ngọc rồng
     *
     * @param player
     * @param icon
     */
    private void sendEffectCombineDB(Player player, short icon) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_DRAGON_BALL);
            msg.writer().writeShort(icon);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------Ratio, cost combine
    //--------------------------------------------------------------------------Ratio, cost combine
    private int getGoldPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 50000000;
            case 1:
                return 10000000;
            case 2:
                return 20000000;
            case 3:
                return 40000000;
            case 4:
                return 60000000;
            case 5:
                return 90000000;
            case 6:
                return 120000000;
            case 7:
                return 200000000;
            case 8:
                return 220000000;
            case 9:
                return 220000000;
            case 10:
                return 220000000;
            case 11:
                return 220000000;
            case 12:
                return 220000000;
        }
        return 0;
    }

    private int getGoldBarCaiTrangPhaLeHoa(int star) {
        Map<Integer, Integer> goldBars = new HashMap<Integer, Integer>() {
            {
                put(0, 1);
                put(1, 1);
                put(2, 1);
                put(3, 2);
                put(4, 2);
                put(5, 2);
                put(6, 3);
                put(7, 3);
                put(8, 3);
                put(9, 3);
                put(10, 4);
                put(11, 4);
                put(12, 5);
            }
        };
        if (goldBars.containsKey(star)) {
            return goldBars.get(star);
        }
        return 0;
    }

    private int getNgocKhoaQuantityLinhThuPhaLeHoa(int star) {
        Map<Integer, Integer> goldBars = new HashMap<Integer, Integer>() {
            {
                put(0, 2);
                put(1, 2);
                put(2, 2);
                put(3, 3);
                put(4, 4);
                put(5, 4);
                put(6, 4);
                put(7, 5);
                put(8, 5);
                put(9, 6);
                put(10, 6);
                put(11, 7);
                put(12, 10);
            }
        };

        if (goldBars.containsKey(star)) {
            return goldBars.get(star);
        }
        return 0;
    }

    private int getNgocRongQuantityPetPhaLeHoa(int star) {
        Map<Integer, Integer> goldBars = new HashMap<Integer, Integer>() {
            {
                put(0, 1);
                put(1, 2);
                put(2, 3);
                put(3, 3);
                put(4, 4);
                put(5, 4);
                put(6, 5);
                put(7, 6);
                put(8, 6);
                put(9, 6);
                put(10, 10);
                put(11, 10);
                put(12, 10);
            }
        };
        if (goldBars.containsKey(star)) {
            return goldBars.get(star);
        }
        return 0;
    }

    private float getRatioCaiTrangPhaLeHoa(int star) {
        Map<Integer, Float> ratios = new HashMap<Integer, Float>() {
            {
                put(0, 100f);
                put(1, 100f);
                put(2, 100f);
                put(3, 100f);
                put(4, 100f);
                put(5, 100f);
                put(6, 100f);
                put(7, 100f);
                put(8, 100f);
                put(9, 100f);
                put(10, 100f);
                put(11, 100f);
                put(12, 100f);
            }
        };
        if (ratios.containsKey(star)) {
            return ratios.get(star);
        }
        return 0;
    }

    private float getRatioPetPhaLeHoa(int star) {
        Map<Integer, Float> ratios = new HashMap<Integer, Float>() {
            {
                put(0, 5f);
                put(1, 2f);
                put(2, 1f);
                put(3, 1f);
                put(4, 1f);
                put(5, 1f);
                put(6, 1f);
                put(7, 1f);
                put(8, 1f);
                put(9, 0.5f);
                put(10, 0.5f);
                put(11, 0.5f);
                put(12, 0.5f);
            }
        };
        if (ratios.containsKey(star)) {
            return ratios.get(star);
        }
        return 0;
    }

    private float getRatioPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 100f;
            case 1:
                return 100f;
            case 2:
                return 100f;
            case 3:
                return 100f;
            case 4:
                return 100f;
            case 5:
                return 100f;
            case 6:
                return 100f;
            case 7:
                return 100f;
            case 8:
                return 100f;
            case 9:
                return 100f;
            case 10:
                return 100f;
            case 11:
                return 100f;
            case 12:
                return 100f;
        }
        return 0;
    }

    private int getGemPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 10;
            case 1:
                return 20;
            case 2:
                return 30;
            case 3:
                return 40;
            case 4:
                return 50;
            case 5:
                return 60;
            case 6:
                return 70;
            case 7:
                return 80;
            case 8:
                return 50;
            case 9:
                return 50;
            case 10:
                return 50;
            case 11:
                return 50;
            case 12:
                return 50;
        }
        return 0;
    }

    private int getGemEpSao(int star) {
        switch (star) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 10;
            case 4:
                return 25;
            case 5:
                return 50;
            case 6:
                return 100;
        }
        return 0;
    }

    private double getTileNangCapDo(int level) {
        switch (level) {
            case 0:
                return 100;
            case 1:
                return 100;
            case 2:
                return 100;
            case 3:
                return 100;
            case 4:
                return 100;
            case 5:
                return 100;
            case 6:
                return 100;
            case 7: // 7 sao
                return 100;
            case 8:
                return 100;
        }
        return 0;
    }

    private int getCountDaNangCapDo(int level) {
        switch (level) {
            case 0:
                return 3;
            case 1:
                return 7;
            case 2:
                return 11;
            case 3:
                return 17;
            case 4:
                return 23;
            case 5:
                return 35;
            case 6:
                return 50;
            case 7:
                return 70;
        }
        return 0;
    }

    private int getCountDaBaoVe(int level) {
        return level + 1;
    }

    private int getGoldNangCapDo(int level) {
        switch (level) {
            case 0:
                return 10000;
            case 1:
                return 70000;
            case 2:
                return 300000;
            case 3:
                return 1500000;
            case 4:
                return 7000000;
            case 5:
                return 23000000;
            case 6:
                return 100000000;
            case 7:
                return 250000000;
        }
        return 0;
    }

    //--------------------------------------------------------------------------check
    private boolean isCoupleItemNangCap(Item item1, Item item2) {
        Item trangBi = null;
        Item daNangCap = null;
        if (item1 != null && item1.isNotNullItem()) {
            if (item1.template.type < 5) {
                trangBi = item1;
            } else if (item1.template.type == 14) {
                daNangCap = item1;
            }
        }
        if (item2 != null && item2.isNotNullItem()) {
            if (item2.template.type < 5) {
                trangBi = item2;
            } else if (item2.template.type == 14) {
                daNangCap = item2;
            }
        }
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isCoupleItemNangCapCheck(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isDaPhaLe(Item item) {
        if (item != null && item.template != null) {
            return (item.template.type == 30 || (item.template.id >= 14 && item.template.id <= 20));
        } else {
            return false;
        }
    }

    private boolean isTrangBiPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            return item.template.type < 5 || item.template.type == 32;
        } else {
            return false;
        }
    }

    private boolean isEpSaoPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            return item.template.type <= 5
                    || item.template.type == 32
                    || item.template.type == 27
                    || item.template.type == 72;
        } else {
            return false;
        }
    }

    private boolean isCaiTrangPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            return item.template.type == 5;
        }
        return false;
    }

    private boolean isPetPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            return item.template.type == 27;
        }
        return false;
    }

    private boolean isLinhThuPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            return item.template.type == 72;
        }
        return false;
    }

    private float getRationangbt(int lvbt) { //tile dap do chi hat mit
        switch (lvbt) {
            case 1:
                return 15f;
            case 2:
                return 15f;

        }

        return 0;
    }

    private int getGoldnangbt(int lvbt) {
        return GOLD_BONG_TAI2;
    }

    private int getgemdnangbt(int lvbt) {
        return GEM_BONG_TAI2;
    }

    private int getcountmvbtnangbt(int lvbt) {
        return 99;
    }

    private boolean checkbongtai(Item item) {
        if (item.template.id == 454 || item.template.id == 921) {
            return true;
        }
        return false;
    }

    private int getGoldnangkhi(int lvkhi) {
        return GOLD_NANG_KHI + 100000000 * lvkhi;
    }

    private int getRubydnangkhi(int lvkhi) {
        return RUBY_NANG_KHI + 1000 * lvkhi;
    }

    private float getRatioNangkhi(int lvkhi) { //tiledap do con khi
        switch (lvkhi) {
            case 1:
                return 100f;
            case 2:
                return 100;
            case 3:
                return 100;
            case 4:
                return 100f;
            case 5:
                return 100f;
            case 6:
                return 100f;
            case 7:
                return 100f;
        }

        return 0;
    }

    private int getcountdnsnangkhi(int lvkhi) {
        return 10 + 10 * lvkhi;
    }

    private boolean checkctkhi(Item item) {
        if ((item.template.id >= 1136 && item.template.id <= 1140) || (item.template.id >= 1208 && item.template.id <= 1210)) {
            return true;
        }
        return false;
    }

    private int lvkhi(Item ctkhi) {
        switch (ctkhi.template.id) {
            case 1137:
                return 1;
            case 1208:
                return 2;
            case 1209:
                return 3;
            case 1210:
                return 4;
            case 1138:
                return 5;
            case 1139:
                return 6;
            case 1140:
                return 7;
        }

        return 0;

    }

    private short getidctkhisaukhilencap(int lvkhicu) {
        switch (lvkhicu) {
            case 1:
                return 1208;
            case 2:
                return 1209;
            case 3:
                return 1210;
            case 4:
                return 1138;
            case 5:
                return 1139;
            case 6:
                return 1140;
            case 7:
                return 1136;
        }
        return 0;
    }

    private int lvbt(Item bongtai) {
        switch (bongtai.template.id) {
            case 454:
                return 1;
            case 921:
                return 2;

        }

        return 0;

    }

    private short getidbtsaukhilencap(int lvbtcu) {
        switch (lvbtcu) {
            case 1:
                return 921;
            case 2:
                return 1128;

        }
        return 0;
    }

    private int getParamDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).param;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 5; // +5%hp
            case 19:
                return 5; // +5%ki
            case 18:
                return 5; // +5%hp/30s
            case 17:
                return 5; // +5%ki/30s
            case 16:
                return 3; // +3%sđ
            case 15:
                return 1; // +2%giáp
            case 14:
                return 1; // +2%né đòn
            default:
                return -1;
        }
    }

    private int getOptionDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).optionTemplate.id;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 77;
            case 19:
                return 103;
            case 18:
                return 80;
            case 17:
                return 81;
            case 16:
                return 50;
            case 15:
                return 94;
            case 14:
                return 108;
            default:
                return -1;
        }
    }

    /**
     * Trả về id item c0
     *
     * @param gender
     * @param type
     * @return
     */
    private int getTempIdItemC0(int gender, int type) {
        if (type == 4) {
            return 12;
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return 0;
                    case 1:
                        return 6;
                    case 2:
                        return 21;
                    case 3:
                        return 27;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return 1;
                    case 1:
                        return 7;
                    case 2:
                        return 22;
                    case 3:
                        return 28;
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return 2;
                    case 1:
                        return 8;
                    case 2:
                        return 23;
                    case 3:
                        return 29;
                }
                break;
        }
        return -1;
    }

    //Trả về tên đồ c0
    private String getNameItemC0(int gender, int type) {
        if (type == 4) {
            return "Rada cấp 1";
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return "Áo vải 3 lỗ";
                    case 1:
                        return "Quần vải đen";
                    case 2:
                        return "Găng thun đen";
                    case 3:
                        return "Giầy nhựa";
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return "Áo sợi len";
                    case 1:
                        return "Quần sợi len";
                    case 2:
                        return "Găng sợi len";
                    case 3:
                        return "Giầy sợi len";
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return "Áo vải thô";
                    case 1:
                        return "Quần vải thô";
                    case 2:
                        return "Găng vải thô";
                    case 3:
                        return "Giầy vải thô";
                }
                break;
        }
        return "";
    }

    //--------------------------------------------------------------------------Text tab combine
    private String getTextTopTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở lên mạnh mẽ";
            case PHA_LE_HOA_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case NHAP_NGOC_RONG:
                return "Ta sẽ phù phép\ncho 7 viên Ngọc Rồng\nthành 1 viên Ngọc Rồng cấp cao";
            case NANG_CAP_VAT_PHAM:
                return "Ta sẽ phù phép cho trang bị của ngươi trở lên mạnh mẽ";
            case PHAN_RA_DO_THAN_LINH:
                return "Ta sẽ phân rã \n  trang bị của người thành điểm!";
            case NANG_CAP_DO_TS:
                return "Chế tạo trang bị thiên sứ!";
            case NANG_CAP_SKH_VIP:
                return "Thần Linh nhờ ta nâng cấp \n  trang bị của người thành\n SKH VIP!";
            case NANG_CAP_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata của ngươi\nthành cấp 2";
            case MO_CHI_SO_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata cấp 2 của ngươi\ncó 1 chỉ số ngẫu nhiên";
            case PHA_LE_HOA_CAI_TRANG:
                return "Ta sẽ phù phép\ncho cải trang của ngươi\ntrở thành cải trang pha lê";
            case PHA_LE_HOA_PET:
                return "Ta sẽ phù phép\ncho PET của ngươi";
            case PHA_LE_HOA_LINH_THU:
                return "Ta sẽ phù phép\ncho Linh Thú của ngươi";
            case NANG_CAP_DO_KICH_HOAT:
                return "Ta sẽ giúp ngươi\n làm điều đó";
            case NANG_CAP_KHI:
                return "Ta sẽ phù phép\ncho Cải trang Khỉ của ngươi\nTăng một cấp!!";
            case AP_TRUNG_LINH_THU:
                return "Tôi sẽ giúp cậu ấp quả trứng linh thú này!";
            case NANG_CAP_LINH_THU:
                return "Tôi sẽ giúp cậu Nâng cấp Linh thú!";
            default:
                return "";
        }
    }

    private String getTextInfoTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa) có ô đặt sao pha lê\nChọn loại sao pha lê\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_TRANG_BI:
                return "Chọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nSau đó chọn 'Nâng cấp'";
            case NHAP_NGOC_RONG:
                return "Vào hành trang\nChọn 7 viên ngọc cùng sao\nSau đó chọn 'Làm phép'";
            case NANG_CAP_VAT_PHAM:
                return "vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để nâng cấp\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case PHAN_RA_DO_THAN_LINH:
                return "vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để phân rã\n"
                        + "Sau đó chọn 'Phân Rã'";
            case NANG_CAP_DO_TS:
                return "Cần 1 công thức\n "
                        + "Mảnh trang bị tương ứng"
                        + "1 đá nâng cấp (tùy chọn)\n"
                        + "1 đá may mắn (tùy chọn)\n";
            case NANG_CAP_SKH_VIP:
                return "vào hành trang\nChọn 1 trang bị Thần Linh bất kì\nChọn tiếp ngẫu nhiên 2 món SKH thường \n "
                        + " đồ SKH VIP sẽ cùng loại \n với đồ Thần Linh!"
                        + "Chỉ cần chọn 'Nâng Cấp'";
            case NANG_CAP_BONG_TAI:
                return "Vào hành trang\nChọn bông tai Porata\nChọn mảnh bông tai để nâng cấp, số lượng\n99 cái\nSau đó chọn 'Nâng cấp'";
            case MO_CHI_SO_BONG_TAI:
                return "Vào hành trang\nChọn bông tai Porata\nChọn mảnh hồn bông tai số lượng 99 cái\nvà đá xanh lam để nâng cấp\nSau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_CAI_TRANG:
                return "Chọn Cải trang\nSau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_PET:
                return "Chọn PET\nSau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_LINH_THU:
                return "Chọn Linh Thú\nSau đó chọn 'Nâng cấp'";
            case NANG_CAP_DO_KICH_HOAT:
                return "vào hành trang\nChọn 1 trang bị Thiên Sứ bất kì\n "
                        + " và x20 Thỏi vàng!\n"
                        + " và 1 món SKH thường bất kỳ\n"
                        + "Chỉ cần chọn 'Nâng Cấp'";
            case AP_TRUNG_LINH_THU:
                return "Chọn 1 quả trứng linh thú\n "
                        + " và x20 Thỏi vàng!\n"
                        + " và x200 Hồn linh thú\n"
                        + "Tôi sẽ giúp cậu ấp trứng!";
            case NANG_CAP_KHI:
                return "Vào hành trang\nChọn Cải trang Khỉ \nChọn Đá Ngũ Sắc, x20 duoi khi để nâng cấp\nSau đó chọn 'Nâng cấp'";
            case NANG_CAP_LINH_THU:
                return "Chọn 1 linh thú\n "
                        + " và x20 Thỏi vàng!\n"
                        + " và x1 Thăng tinh thạch\n"
                        + " và Đá nguyên tố\n"
                        + "Tôi sẽ giúp cậu Nâng cấp!";
            default:
                return "";
        }
    }

}
