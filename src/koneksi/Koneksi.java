package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Muh. Nur Iksan
 */
public class Koneksi {
    
    public static void main(String[] args) {
        getKoneksi();
    }
    
    private static Connection koneksi;
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/kmmi_uas"; 
                String user = "root";
                String password = "";

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("koneksi sukses");
                
            } 
            catch (Exception ex) {
                System.out.println("koneksi eror="+ex);
            }
        }
        return koneksi;
    }
}
