/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girlkun.models.boss.list_boss;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemMapService;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;

/**
 *
 * @author Stole By Peos Di Zai
 */
public class SoiHecQuyn extends Boss {

    private boolean IsPickXuong;
    private long lastTimeSetPickXuong;
    private ItemMap cucXuong;
    private Player playerChoXuong;
    private boolean FinishPickXuong;

    public SoiHecQuyn(int id) throws Exception {
        super(id, SOI_HEC_QUYN_BEO);
    }
    private static final BossData SOI_HEC_QUYN_BEO = new BossData(
            "Sói hẹc quyn",
            ConstPlayer.TRAI_DAT,
            new short[]{394, 395, 396, -1, -1, -1},
            2000,
            new int[]{500_000},//hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30,//traidat
                9, 11, 12, 13, 10, 34, 33, 32, 31,//namec
                16, 17, 18, 19, 20, 37, 38, 36, 35,//xayda
                24, 25, 26//trạm tàu vũ trụ
        },
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.KAMEJOKO, 7, 3000}},
            new String[]{"|-1|Gâu gâu ẳng ẳng mau đưa xương cho ta", "|-1|Xin thí chủ cho ta khúc xương ẳng gâu"}, //text chat 1
            new String[]{"|-1|Grrrrr mau đưa xương cho ta",
                "|-1|Gâu gâu",
                "|-1|Ngươi dám đánh ta sao ???",
                "|-2|Cút ngay không là ăn đòn"}, //text chat 2
            new String[]{"|-1|Á hự huhuhu đã không cho xương còn đánh người ta",
                "|-2|Không làm mà đòi có ăn"}, //text chat 3
            300
    );

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        return super.injured(plAtt, 5000, piercing, isMobAttack);
    }

    public static void PlayerUseCucXuong(Player player, Item cucXuongss, int tempId) {
        SoiHecQuyn soihecquyn = null;
        for (Player pl : player.zone.getBosses()) {
            if (BossID.isBossSoiHecQuyn((int) pl.id)) {
                soihecquyn = (SoiHecQuyn) pl;
            }
        }
        if (soihecquyn == null) {
            Service.getInstance().sendThongBao(player, "Không tìm thấy sói hẹc quyn");
            return;
        }
        if (Util.getDistance(player, soihecquyn) < 100) {
            InventoryServiceNew.gI().subQuantityItemsBag(player, cucXuongss, 1);
            soihecquyn.cucXuong = new ItemMap(player.zone, tempId, 1, Util.nextInt((player.location.x - 50), (player.location.x + 50)), player.zone.map.yPhysicInTop(player.location.x, player.location.y), soihecquyn.id);
            soihecquyn.cucXuong.options = cucXuongss.itemOptions;
            Service.getInstance().dropItemMap(player.zone, soihecquyn.cucXuong);
            soihecquyn.IsPickXuong = true;
            soihecquyn.playerChoXuong = player;
            soihecquyn.lastTimeSetPickXuong = System.currentTimeMillis();
            soihecquyn.chat("Ế miếng xương ngon quáaaaaa");
            soihecquyn.moveTo(soihecquyn.cucXuong.x, soihecquyn.cucXuong.y);
        } else {
            Service.getInstance().sendThongBao(player, "Khoảng cách xa quá");
        }
    }

    @Override
    public void active() {
        if (!IsPickXuong && this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        if (!IsPickXuong) {
            this.attack();
        } else {
            if (!FinishPickXuong && cucXuong != null && this.playerChoXuong != null) {
                if (System.currentTimeMillis() - lastTimeSetPickXuong > 3000) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(5) + 1;
                    this.chat("Ế miếng xương ngon quáaaaaa");
                    this.changeToTypeNonPK();
                    this.moveTo(this.cucXuong.x, this.cucXuong.y);
                    ItemMapService.gI().removeItemMapAndSendClient(cucXuong);
//                    Item item = ItemService.gI().createNewItem(((short) Util.nextInt(441, 447)), 1);
                    Item item = ItemService.gI().createNewItem((short) 457, randomNumber);
                    if (this.playerChoXuong != null && this.playerChoXuong.inventory != null && InventoryServiceNew.gI().getCountEmptyBag(this.playerChoXuong) > 0) {
                        InventoryServiceNew.gI().addItemBag(this.playerChoXuong, item);
                        InventoryServiceNew.gI().sendItemBags(this.playerChoXuong);
                        Service.getInstance().sendThongBao(this.playerChoXuong, "bạn đã nhận được x" + randomNumber + " thỏi vàng");
                    } else if (this.playerChoXuong != null) {
                        Service.getInstance().sendThongBao(this.playerChoXuong,"Hành trang không đủ chỗ trống");
                    }
                    this.chat("Cám ơn thí chủ đã cho xương ẳng gâu...");
                    FinishPickXuong = true;
                    
                }
            } else {
                if (System.currentTimeMillis() - lastTimeSetPickXuong > 8000) {
                    reSetPickXuong();
                    this.changeStatus(BossStatus.LEAVE_MAP);
                }
            }

        }

    }

    private void reSetPickXuong() {
        IsPickXuong = false;
        lastTimeSetPickXuong = 0;
        cucXuong = null;
        playerChoXuong = null;
        FinishPickXuong = false;
    }

}
