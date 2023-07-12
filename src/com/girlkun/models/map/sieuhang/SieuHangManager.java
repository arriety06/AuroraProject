package com.girlkun.models.map.sieuhang;

import com.girlkun.database.GirlkunDB;
import com.girlkun.jdbc.daos.GodGK;
import com.girlkun.models.matches.TOP;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dev Péo
 */
public class SieuHangManager {

    private static SieuHangManager i;
    private long lastUpdate;
    public static final List<SieuHang> list = new ArrayList<>();
    private static final List<SieuHang> toRemove = new ArrayList<>();

    public static SieuHangManager gI() {
        if (i == null) {
            i = new SieuHangManager();
        }
        return i;
    }

    public void update() {
        long timeUntil4AM = getTimeUntil4AM();
        if (Util.canDoWithTime(lastUpdate, 1000)) {
            lastUpdate = System.currentTimeMillis();
            synchronized (list) {
                for (SieuHang mc : list) {
                    try {
                        mc.update();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                list.removeAll(toRemove);
            }
        }
        if (timeUntil4AM == 0) {
            getdatatopsieuhang();
            send_data_topsh();
            System.out.println("Đã update data top rank Siêu Hạng");
        }
    }

    public void add(SieuHang mc) {
        synchronized (list) {
            list.add(mc);
        }
    }

    public void remove(SieuHang mc) {
        synchronized (toRemove) {
            toRemove.add(mc);
        }
    }

    public void getdatatopsieuhang() {
        try (Connection con = GirlkunDB.getConnection()) {
            Manager.datatopSieuHang = Manager.realDataTopSieuHang(con);
            con.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            Logger.error("Lỗi đọc top");
        }
    }

    public int getrubyranksh(int rank) {
        int ruby = 0;
        if (rank == 1) {
            ruby = 100;
        } else if (rank >= 2 && rank <= 10) {
            ruby = 20;
        } else if (rank >= 11 && rank <= 100) {
            ruby = 5;
        } else if (rank >= 101 && rank <= 1000) {
            ruby = 0;
        }
        return ruby;
    }

    private static long getTimeUntil4AM() {
        // Lấy thời gian hiện tại
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Đặt thời điểm 4 giờ sáng
        LocalDateTime targetDateTime = LocalDateTime.of(currentDateTime.toLocalDate(), LocalTime.of(4, 0));

        // Nếu thời điểm hiện tại đã sau 4 giờ sáng, thì cập nhật vào ngày hôm sau
        if (currentDateTime.isAfter(targetDateTime)) {
            targetDateTime = targetDateTime.plusDays(1);
        }

        // Tính khoảng thời gian còn bao lâu từ thời điểm hiện tại đến 4 giờ sáng
        long secodsUntil4AM = ChronoUnit.SECONDS.between(currentDateTime, targetDateTime);

        return secodsUntil4AM;
    }

    public void send_data_topsh() {
        List<TOP> tops = Manager.datatopSieuHang;
        for (int i = 0; i < tops.size(); i++) {
            TOP top = tops.get(i);
            Player pl = GodGK.loadById(top.getId_player());
            update_data_rank_sieu_hang((int) pl.id, (int) pl.rankSieuHang, (byte) 1);
        }
    }

    public void update_data_rank_sieu_hang(int idPlayer, int rank, byte type) {
        if (type == 1) {
            try (Connection con = GirlkunDB.getConnection();) {
                PreparedStatement ps = con.prepareStatement("UPDATE data_top_sieuhang set player_id = ?, is_receive = 0 WHERE rank = ?");
                ps.setInt(1, idPlayer);
                ps.setInt(2, rank);
                ps.executeUpdate();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == 2) {
            try (Connection con = GirlkunDB.getConnection();) {
                PreparedStatement ps = con.prepareStatement("UPDATE data_top_sieuhang set is_receive = 1 WHERE player_id = ?");
                ps.setInt(1, idPlayer);
                ps.executeUpdate();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int rankistop(int idplayer) {
        try (Connection con = GirlkunDB.getConnection();) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM data_top_sieuhang WHERE player_id = ?");
            ps.setInt(1, idplayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int rank = rs.getInt("rank");
                return rank;
            }
            con.close();
        } catch (Exception erorlog) {
            erorlog.printStackTrace();
        }
        return -1;
    }

    public boolean isreceivetop(int idplayer) {
        try (Connection con = GirlkunDB.getConnection();) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM data_top_sieuhang WHERE player_id = ?");
            ps.setInt(1, idplayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_receive");
            }
            con.close();
        } catch (Exception erorlog) {
            erorlog.printStackTrace();
        }
        return false;
    }
}
