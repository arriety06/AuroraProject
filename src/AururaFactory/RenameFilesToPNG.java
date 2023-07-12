package AururaFactory;
import java.io.File;

public class RenameFilesToPNG {
    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\HT\\Desktop\\GirlkunToolCBRO\\data\\girlkun\\res\\x1"; // Đường dẫn đến thư mục của bạn

        renameFilesToPNG(directoryPath);
    }

    public static void renameFilesToPNG(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && !file.getName().contains(".")) {
                    String newFilePath = file.getAbsolutePath() + ".png";
                    File newFile = new File(newFilePath);
                    file.renameTo(newFile);
                    System.out.println("Đổi tên tệp tin: " + file.getName() + " -> " + newFile.getName());
                }
            }
        } else {
            System.out.println("Đường dẫn không tồn tại hoặc không phải là một thư mục.");
        }
    }
}
