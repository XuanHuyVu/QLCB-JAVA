package QLCB;


public class Canbo {
    private String soTk, hoTen, gioiTinh, diaChi;
    private int Luong;

    public Canbo() {
    }

    public Canbo(String soTk, String hoTen, String gioiTinh, String diaChi, int Luong) {
        this.soTk = soTk;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.Luong = Luong;
    }

    public String getSoTk() {
        return soTk;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public long getLuong() {
        return Luong;
    }

    public void setSoTk(String soTk) {
        this.soTk = soTk;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setLuong(int Luong) {
        this.Luong = Luong;
    }
}
