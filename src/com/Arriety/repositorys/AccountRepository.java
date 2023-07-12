package com.Arriety.repositorys;

import com.Arriety.entity.AccountEntity;
import com.girlkun.database.GirlkunDB;
import com.girlkun.result.GirlkunResultSet;

import java.sql.Timestamp;

public class AccountRepository {
    public AccountRepository() {}

    public AccountEntity findAccount(String userName, String password) {
        GirlkunResultSet rs = null;
        AccountEntity account = null;
        try {
            rs = GirlkunDB.executeQuery("select * from account where username = ? and password = ?", userName, password);
            if (rs.first()) {
                int userId = rs.getInt("account.id");
                boolean isAdmin = rs.getBoolean("is_admin");
                boolean isBan = rs.getBoolean("ban");
                long lastTimeLogout = rs.getTimestamp("last_time_logout").getTime();
                boolean actived = rs.getBoolean("active");
                int goldBar = rs.getInt("account.thoi_vang");
                int vnd = rs.getInt("account.vnd");
                int tongnap = rs.getInt("account.tongnap");
                int pointshare = rs.getInt("account.diemgioithieu");
                double bdPlayer = rs.getDouble("account.bd_player");
                long lastTimeLogin = rs.getTimestamp("last_time_login").getTime();

                account = AccountEntity.builder()
                        .userId(userId)
                        .isAdmin(isAdmin)
                        .isBan(isBan)
                        .lastTimeLogout(lastTimeLogout)
                        .lastTimeLogin(lastTimeLogin)
                        .active(actived)
                        .thoiVang(goldBar)
                        .vnd(vnd)
                        .tongnap(tongnap)
                        .pointshare(pointshare)
                        .bdPlayer(bdPlayer)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rs != null) {
            rs.dispose();
        }
        return account;
    }

    public void updateLoginTime(int account, String ipAddress) {
        try {
            GirlkunDB.executeUpdate("update account set last_time_login = '" + new Timestamp(System.currentTimeMillis()) + "', ip_address = '" + ipAddress + "' where id = " + account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
