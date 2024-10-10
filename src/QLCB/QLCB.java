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

    public static ResultSet getData(String SoTk) {
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM tbCanBo WHERE SoTk = '"+SoTk+"'");
            return pst.executeQuery();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // Sửa
    public static boolean updateData(String SoTk, Canbo cb) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE tbCanBo SET Hoten = '"+cb.getHoTen()+"', GT = '"+cb.getGioiTinh()+"', Diachi = '"+cb.getDiaChi()+"', Luong = '"+cb.getLuong()+"' WHERE SoTK = '"+SoTk+"'");
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
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // Thêm
    public static boolean insertCB(Canbo cb) {
        try {
            if(!checkAccountNumberExists(cb.getSoTk())) {
                PreparedStatement pst = cn.prepareStatement("INSERT INTO tbCanBo (SoTk, Hoten, GT, DiaChi, Luong) VALUES (?, ?, ?, ?, ?)");
                pst.setString(1, cb.getSoTk());
                pst.setString(2, cb.getHoTen());
                pst.setString(3, cb.getGioiTinh());
                pst.setString(4, cb.getDiaChi());
                pst.setLong(5, cb.getLuong());
                int res = pst.executeUpdate();
                return res > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return false;
    }
    
    // Xóa
    public static boolean deleteCB(String accountNumber) {
        try {
            PreparedStatement pst = cn.prepareStatement("DELETE FROM tbCanBo WHERE SoTK = '"+accountNumber+"'");
            int res = pst.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    //Validate SoTK trùng lặp
    public static boolean checkAccountNumberExists(String accountNumber) {
    try {
        PreparedStatement pst = cn.prepareStatement("SELECT 1 FROM tbCanBo WHERE SoTk = ?");
        pst.setString(1, accountNumber);
        ResultSet result = pst.executeQuery();
        return result.next();
    } catch (SQLException e) {
        System.out.println("Error checking account number: " + e.getMessage());
        return false;
    }
}

}
