package com.girlkun.models.player;

import com.girlkun.models.item.Item;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Stole By Arriety
 */
public class NewPet extends Player {

    private long lastTimeChat;

    public Player master;
    public short body;
    public short leg;
    public static int idb = -400000;
    public List<Integer> idEffChar = new ArrayList<>();

    public NewPet(Player master, short h, short b, short l, String name) {
        this.master = master;
        this.isNewPet = true;
        this.id = idb;
        idb--;
        this.name = name;
        this.head = h;
        this.body = b;
        this.leg = l;
    }

    @Override
    public short getHead() {
        return head;
    }

    @Override
    public short getBody() {
        return body;
    }

    @Override
    public short getLeg() {
        return leg;
    }

    @Override
    public byte getAura() {
        return 35;
    }

    public void joinMapMaster() {
        if (master != null && master.zone != null && this.location != null && master.location != null) {
            this.location.x = master.location.x + Util.nextInt(-10, 10);
            this.location.y = master.location.y;
            ChangeMapService.gI().goToMap(this, master.zone);
            this.zone.load_Me_To_Another(this);
        }
    }

    private long lastTimeMoveIdle;
    private int timeMoveIdle;
    public boolean idle;

    private void moveIdle() {
        if (master != null && master.location != null && this.location != null
                && idle && Util.canDoWithTime(lastTimeMoveIdle, timeMoveIdle)) {
            int dir = this.location.x - master.location.x <= 0 ? -1 : 1;
            PlayerService.gI().playerMove(this, master.location.x
                    + Util.nextInt(dir == -1 ? 30 : -50, dir == -1 ? 50 : 30), master.location.y);
            lastTimeMoveIdle = System.currentTimeMillis();
            timeMoveIdle = Util.nextInt(5000, 8000);
        }
    }

    @Override
    public void update() {
        try {
            if (master != null && master.zone != null && master.isPl() && this.nPoint != null) {
                super.update();
//                if (Util.canDoWithTime(lastTimeChat, 1000)) {
//                    Service.getInstance().chat(this, "Hi lo Béo Đẹp Zai :3");
//                    Service.getInstance().chat(this, "Béo ơi ăn cơm đi :33");
//                    lastTimeChat = System.currentTimeMillis();
//                }
                if (this.isDie()) {
                    Service.gI().hsChar(this, nPoint.hpMax, nPoint.mpMax);
                }
                if ((this.zone == null || this.zone != master.zone)) {
                    joinMapMaster();
                }
                if (!master.isDie()) {
                    moveIdle();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi update ở newpet");
        }
    }

    public void followMaster() {
        if (!this.master.isBoss) {
            followMaster(50);
        }
    }

    private void followMaster(int dis) {
        int mX = master.location.x;
        int mY = master.location.y;
        int disX = this.location.x - mX;
        if (Math.sqrt(Math.pow(mX - this.location.x, 2) + Math.pow(mY - this.location.y, 2)) >= dis) {
            if (disX < 0) {
                this.location.x = mX - Util.nextInt(0, dis);
            } else {
                this.location.x = mX + Util.nextInt(0, dis);
            }
            this.location.y = mY;
            PlayerService.gI().playerMove(this, this.location.x, this.location.y);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.master != null) {
            this.master = null;
        }
    }
}
