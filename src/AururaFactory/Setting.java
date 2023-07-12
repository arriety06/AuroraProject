package AururaFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Setting {

    private void __________________Server_Setting_____________________________() {
        //**********************************************************************
    }
    public static final String LIST_SERVER = "Arriety:" + getHostNameForIp("arriety.org") + ":14445:0,0,0";

    private static String getHostNameForIp(String ip) {
        String hostname = null;
        try {
            InetAddress address = InetAddress.getByName("");
            hostname = address.getHostName();
        } catch (UnknownHostException ex) {
            System.err.println("Cannot find host with IP address: " + ip);
        }
        return hostname;
    }

    private void __________________List_Event_____________________________() {
        //**********************************************************************
    }
    public static  boolean EVENT_HALLOWEEN = true;
    public static  boolean EVENT_TRUNG_THU = false;
    public static  boolean EVENT_NGU_HANH_SON = false;
    public static  boolean EVENT_GIANG_SINH = true;
    public static  boolean EVENT_TET_NGUYEN_DAN = false;
    public static boolean KY_GUI = true;
    public static boolean TRUNG_LINH_THU = true;
    public static boolean KARIN = false;

    private void __________________Role_____________________________() {
        //**********************************************************************
    }
    
    public static boolean LOG_CHAT_GLOBAL = false;
}
