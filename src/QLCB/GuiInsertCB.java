package QLCB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class GuiInsertCB extends JFrame implements ActionListener, MouseListener {

    private JTextField tfAccountNumber;
    private JTextField tfName;
    private JRadioButton rbMale;
    private JRadioButton rbFemale;
    private JTextField tfAddress;
    private JTextField tfSalary;
    private JTable tb;
    private DefaultTableModel dfModel;
    private JButton btAdd;
    private JButton btEdit;
    private JButton btDelete;
    private JButton btSearch;

    public GuiInsertCB() {
        setTitle("CHƯƠNG TRÌNH QUẢN LÝ CÁN BỘ");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Nimbus not found.");
        }
        BuildGUI();
        //loadData(dfModel);
    }

    private void BuildGUI() {
    JPanel pnLeft = new JPanel();
    pnLeft.setLayout(new BoxLayout(pnLeft, BoxLayout.Y_AXIS));
    pnLeft.setBorder(new EmptyBorder(20, 20, 20, 20)); // Thay đổi khoảng cách xung quanh để bố cục đều hơn.

    // Thông tin cán bộ
    JLabel lbTitle = new JLabel("Thông tin cán bộ", JLabel.CENTER);
    lbTitle.setFont(new Font("Arial", Font.BOLD, 20));
    lbTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    pnLeft.add(lbTitle);
    pnLeft.add(Box.createRigidArea(new Dimension(0, 20)));

    // Số tài khoản
    JLabel lbAccountNumber = new JLabel("Số tài khoản: ");
    lbAccountNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
    tfAccountNumber = new JTextField();
    tfAccountNumber.setMaximumSize(new Dimension(300, 30));
    pnLeft.add(lbAccountNumber);
    pnLeft.add(tfAccountNumber);
    pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

    // Họ tên
    JLabel lbName = new JLabel("Họ và tên: ");
    lbName.setAlignmentX(Component.LEFT_ALIGNMENT);
    pnLeft.add(lbName);
    tfName = new JTextField();
    tfName.setMaximumSize(new Dimension(300, 30));
    pnLeft.add(tfName);
    pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

    // Giới tính
    JPanel pnGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pnGender.setMaximumSize(new Dimension(300, 40));
    ButtonGroup bgGender = new ButtonGroup();
    pnGender.add(new JLabel("Giới tính: "));
    rbMale = new JRadioButton("Nam");
    rbFemale = new JRadioButton("Nữ");
    bgGender.add(rbMale);
    bgGender.add(rbFemale);
    pnGender.add(rbMale);
    pnGender.add(rbFemale);
    pnLeft.add(pnGender);
    pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

    // Địa chỉ
    JLabel lbAddress = new JLabel("Địa chỉ: ");
    lbAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
    pnLeft.add(lbAddress);
    tfAddress = new JTextField();
    tfAddress.setMaximumSize(new Dimension(300, 30));
    pnLeft.add(tfAddress);
    pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

    // Lương
    JLabel lbSalary = new JLabel("Lương: ");
    lbSalary.setAlignmentX(Component.LEFT_ALIGNMENT);
    pnLeft.add(lbSalary);
    tfSalary = new JTextField();
    tfSalary.setMaximumSize(new Dimension(300, 30));
    pnLeft.add(tfSalary);
    pnLeft.add(Box.createRigidArea(new Dimension(0, 20)));

    // Buttons
    JPanel pnLeftBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    btAdd = new JButton("Thêm");
    btEdit = new JButton("Sửa");
    btDelete = new JButton("Xóa");
    btSearch = new JButton("Tìm kiếm");
    pnLeftBottom.add(btAdd);
    pnLeftBottom.add(btEdit);
    pnLeftBottom.add(btDelete);
    pnLeftBottom.add(btSearch);
    pnLeft.add(pnLeftBottom);


        // Table
        JPanel pnRight = new JPanel(new GridLayout(1, 1));
        String[] headers = {"Số tài khoản", "Họ tên", "Giới tính", "Địa chỉ", "Lương"};
        dfModel = new DefaultTableModel(headers, 0);
        tb = new JTable(dfModel);
        pnRight.add(new JScrollPane(tb));

        this.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight));

        //Bắt sự kiện kích chọn trong bảng
        tb.getSelectionModel().addListSelectionListener(e -> {
            try {
                int selectedRow = tb.getSelectedRow();
                if (selectedRow != -1) {
                    tfAccountNumber.setText(tb.getValueAt(selectedRow, 0).toString());
                    tfName.setText(tb.getValueAt(selectedRow, 1).toString());
                    String gender = tb.getValueAt(selectedRow, 2).toString();
                    if (gender.equals("Nam")) {
                        rbMale.setSelected(true);
                    } else if (gender.equals("Nữ")) {
                        rbFemale.setSelected(true);
                    }
                    tfAddress.setText(tb.getValueAt(selectedRow, 3).toString());
                    tfSalary.setText(tb.getValueAt(selectedRow, 4).toString());
                }

            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        });

        //Bắt sự kiện thêm mới
        btAdd.addActionListener((var e) -> {
            String accountNumber = tfAccountNumber.getText().trim();
            String name = tfName.getText().trim();
            String gender = "";

            if (rbMale.isSelected()) {
                gender = rbMale.getText();
            } else if (rbFemale.isSelected()) {
                gender = rbFemale.getText();
            }

            String address = tfAddress.getText().trim();
            int salary = 0;
            boolean isValid = true;

            try {
                salary = Integer.parseInt(tfSalary.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Nhập lương không hợp lệ!" + ex.getMessage());
                isValid = false;
            }

            if (accountNumber.isEmpty() || name.isEmpty() || !isValid) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin cán bộ!");
            } else {
                if (QLCB.checkAccountNumberExists(accountNumber)) {
                    JOptionPane.showMessageDialog(null, "Số tài khoản đã tồn tại!");
                } else {
                    boolean res = QLCB.insertCB(new Canbo(accountNumber, name, gender, address, salary));
                    if (res) {
                        loadData(dfModel);
                        JOptionPane.showMessageDialog(null, "Thêm cán bộ thành công!");
                        tfAccountNumber.setText("");
                        tfName.setText("");
                        bgGender.clearSelection();
                        tfAddress.setText("");
                        tfSalary.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm cán bộ thất bại!");
                    }
                }
            }
        });

        //Bắt sự kiện btn sửa
        btEdit.addActionListener(e -> {
            String accountNumber = tfAccountNumber.getText().trim();
            String name = tfName.getText().trim();
            String gender = "";

            if (rbMale.isSelected()) {
                gender = rbMale.getText();
            } else if (rbFemale.isSelected()) {
                gender = rbFemale.getText();
            }

            String address = tfAddress.getText().trim();
            int salary = 0;
            boolean isValid = true;

            ResultSet resAccountNummber = QLCB.getData(accountNumber);

            try {
                if (resAccountNummber != null && resAccountNummber.next()) {
                    try {
                        salary = Integer.parseInt(tfSalary.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Nhập lương không hợp lệ!" + ex.getMessage());
                        isValid = false;
                    }

                    if (accountNumber.isEmpty() || name.isEmpty() || !isValid) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin cán bộ!");
                    } else {
                        boolean res = QLCB.updateData(accountNumber, new Canbo(accountNumber, name, gender, address, salary));
                        if (res) {
                            loadData(dfModel);
                            JOptionPane.showMessageDialog(null, "Sửa cán bộ thành công!");
                            tfAccountNumber.setText("");
                            tfName.setText("");
                            bgGender.clearSelection();
                            tfAddress.setText("");
                            tfSalary.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Không tồn tại mã cán bộ: " + accountNumber);
                }
            } catch (HeadlessException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        });

        //Bắt sự kiện xóa
        btDelete.addActionListener(e -> {
            String accountNumber = tfAccountNumber.getText().trim();
            if (accountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Chọn một cán bộ để xóa!");
            } else {
                int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    boolean res = QLCB.deleteCB(accountNumber);
                    if (res) {
                        loadData(dfModel);
                        JOptionPane.showMessageDialog(null, "Xóa thành công!");
                        tfAccountNumber.setText("");
                        tfName.setText("");
                        bgGender.clearSelection();
                        tfAddress.setText("");
                        tfSalary.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                    }
                }
            }
        });

        //Bắt sự kiện tìm kiếm
        btSearch.addActionListener(e -> {
            String accountNumber = tfAccountNumber.getText().trim();

            if (accountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số tài khoản để tìm kiếm!");
            } else {
                try {
                    ResultSet res = QLCB.getData(accountNumber);
                    if (res != null && res.next()) {
                        // Xóa dữ liệu cũ trong bảng
                        dfModel.setRowCount(0);

                        // Thêm kết quả tìm kiếm vào bảng
                        dfModel.addRow(new String[]{
                            res.getString("SoTk"),
                            res.getString("Hoten"),
                            res.getString("GT"),
                            res.getString("DiaChi"),
                            res.getString("Luong")
                        });

                        // Hiển thị thông tin trong các ô nhập liệu
                        tfName.setText(res.getString("Hoten"));
                        String gender = res.getString("GT");
                        if (gender.equals("Nam")) {
                            rbMale.setSelected(true);
                        } else if (gender.equals("Nữ")) {
                            rbFemale.setSelected(true);
                        }
                        tfAddress.setText(res.getString("DiaChi"));
                        tfSalary.setText(res.getString("Luong"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy cán bộ với số tài khoản: " + accountNumber);
                        // Xóa dữ liệu bảng nếu không tìm thấy
                        dfModel.setRowCount(0);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi tìm kiếm: " + ex.getMessage());
                }
            }
        });

        loadData(dfModel);
    }

    public static void main(String[] args) {
        QLCB.getCon();
        SwingUtilities.invokeLater(() -> {
            new GuiInsertCB().setVisible(true);
        });
    }

    private void loadData(DefaultTableModel dfModel) {
        try {
            ResultSet res = QLCB.getAllData();
            dfModel.setRowCount(0);
            dfModel.fireTableDataChanged();
            if (res != null) {
                while (res.next()) {
                    dfModel.addRow(new String[]{
                        res.getString("SoTk"),
                        res.getString("Hoten"),
                        res.getString("GT"),
                        res.getString("DiaChi"),
                        res.getString("Luong")
                    });
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
