package com.girlkun.models.player;

import com.girlkun.models.item.Item;

public class SetClothes {

    private Player player;

    public SetClothes(Player player) {
        this.player = player;
    }

    public byte songoku2;
    public byte thienXinHang2;
    public byte kaioken2;

    public byte lienhoan2;
    public byte pikkoroDaimao2;
    public byte picolo2;

    public byte kakarot2;
    public byte cadic2;
    public byte nappa2;

    public byte songoku1;
    public byte thienXinHang1;
    public byte kaioken1;

    public byte lienhoan1;
    public byte pikkoroDaimao1;
    public byte picolo1;

    public byte kakarot1;
    public byte cadic1;
    public byte nappa1;

    public byte worldcup;

    public boolean thienSuClothes;

    public byte setThanLinh;
    public byte SetHuyDiet;
    public byte setThienSu;
    public int ctHaiTac = -1;

    public void setup() {
        setDefault();
        setupSKT();
        //check cải trang
        Item ct = this.player.inventory.itemsBody.get(5);
        if (ct.isNotNullItem()) {
            switch (ct.template.id) {
                case 618:
                case 619:
                case 620:
                case 621:
                case 622:
                case 623:
                case 624:
                case 626:
                case 627:
                    this.ctHaiTac = ct.template.id;
                    break;

            }
        }
    }

    private void setupSKT() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                boolean isActSet = false;
                for (Item.ItemOption io : item.itemOptions) {
                    switch (io.optionTemplate.id) {
                        case 129:
                        case 141:
                            isActSet = true;
                            songoku2++;
                            break;
                        case 127:
                        case 139:
                            isActSet = true;
                            thienXinHang2++;
                            break;
                        case 128:
                        case 140:
                            isActSet = true;
                            kaioken2++;
                            break;
                        case 131:
                        case 143:
                            isActSet = true;
                            lienhoan2++;
                            break;
                        case 132:
                        case 144:
                            isActSet = true;
                            pikkoroDaimao2++;
                            break;
                        case 130:
                        case 142:
                            isActSet = true;
                            picolo2++;
                            break;
                        case 135:
                        case 138:
                            isActSet = true;
                            nappa2++;
                            break;
                        case 133:
                        case 136:
                            isActSet = true;
                            kakarot2++;
                            break;
                        case 134:
                        case 137:
                            isActSet = true;
                            cadic2++;
                            break;
                        case 212:
                        case 224:
                            isActSet = true;
                            songoku1++;
                            break;
                        case 210:
                        case 222:
                            isActSet = true;
                            thienXinHang1++;
                            break;
                        case 211:
                        case 223:
                            isActSet = true;
                            kaioken1++;
                            break;
                        case 214:
                        case 226:
                            isActSet = true;
                            lienhoan1++;
                            break;
                        case 215:
                        case 227:
                            isActSet = true;
                            pikkoroDaimao1++;
                            break;
                        case 213:
                        case 225:
                            isActSet = true;
                            picolo1++;
                            break;
                        case 218:
                        case 221:
                            isActSet = true;
                            nappa1++;
                            break;
                        case 216:
                        case 219:
                            isActSet = true;
                            kakarot1++;
                            break;
                        case 217:
                        case 220:
                            isActSet = true;
                            cadic1++;
                            break;

                    }

                    if (isActSet) {
                        break;
                    }
                }
                if (item.isDTL()) {
                    isActSet = true;
                    setThanLinh++;
                }
                if (item.isDHD()) {
                    isActSet = true;
                    SetHuyDiet++;
                }
                if (item.isDTS()) {
                    isActSet = true;
                    setThienSu++;
                }
            } else {
                break;
            }
        }
    }

    private void setDefault() {
        this.songoku1 = 0;
        this.thienXinHang1 = 0;
        this.kaioken1 = 0;
        this.lienhoan1 = 0;
        this.pikkoroDaimao1 = 0;
        this.picolo1 = 0;
        this.kakarot1 = 0;
        this.cadic1 = 0;
        this.nappa1 = 0;
        this.worldcup = 0;
        this.SetHuyDiet = 0;
        this.setThienSu = 0;
        this.setThanLinh = 0;
        this.ctHaiTac = -1;

        this.songoku2 = 0;
        this.thienXinHang2 = 0;
        this.kaioken2 = 0;
        this.lienhoan2 = 0;
        this.pikkoroDaimao2 = 0;
        this.picolo2 = 0;
        this.kakarot2 = 0;
        this.cadic2 = 0;
        this.nappa2 = 0;
    }

    public void dispose() {
        this.player = null;
    }
}
