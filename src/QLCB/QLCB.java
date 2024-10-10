package QLCB;
import java.sql.*;
import java.util.*;

public class QLCB {
    private List<Canbo> dscb;
    private static Connection cn;

    // Connect to the database
    public static void getCon() {
        try {
            cn = DriverManager.getConnection("jdbc:sqlserver://XUY\\SQLEXPRESS;database=QLCB;user=sa;password=12345678;trustServerCertificate=true;");
            System.out.println("Passed to load");
        } catch (Exception e) {
            System.out.println("Failed to load: " + e.getMessage());
        }
    }

    // Get data by account number
    public static ResultSet getData(String SoTk) {
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM tbCanBo WHERE SoTk = '"+SoTk+"'");
            return pst.executeQuery();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // Update data
    public static boolean updateData(String SoTk, Canbo cb) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE tbCanBo SET Hoten = '"+cb.getHoTen()+"', GioiTinh = '"+cb.getGioiTinh()+"', DiaChi = '"+cb.getDiaChi()+"', Luong = '"+cb.getLuong()+"' WHERE SoTk = '"+SoTk+"'");
            int res = pst.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Get all data
    public static ResultSet getAllData() {
        try {
            Statement st = cn.createStatement();
            return st.executeQuery("SELECT * FROM tbCanBo");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // Insert a new Canbo
    public boolean insertCB(Canbo cb) {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO tbCanBo (SoTk, Hoten, GioiTinh, DiaChi, Luong) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, cb.getSoTk());
            pst.setString(2, cb.getHoTen());
            pst.setString(3, cb.getGioiTinh());
            pst.setString(4, cb.getDiaChi());
            pst.setLong(5, cb.getLuong());
            int res = pst.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
