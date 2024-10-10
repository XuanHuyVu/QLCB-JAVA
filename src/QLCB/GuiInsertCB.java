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

/**
 *
 * @author Admin
 */
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
        } catch (Exception e) {
            System.out.println("Nimbus not found.");
        }
        BuildGUI();
        loadData(dfModel);
    }

    private void BuildGUI() {
        JPanel pnLeft = new JPanel();
        pnLeft.setLayout(new BoxLayout(pnLeft, BoxLayout.Y_AXIS));
        pnLeft.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Thông tin cán bộ
        JLabel lbTitle = new JLabel("Thông tin cán bộ", JLabel.CENTER);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lbTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnLeft.add(lbTitle);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Số tài khoản
        JLabel lbAccountNumber = new JLabel("Số tài khoản: ");
        lbAccountNumber.setAlignmentX(Component.RIGHT_ALIGNMENT);
        tfAccountNumber = new JTextField();
        tfAccountNumber.setMaximumSize(new Dimension(200, 40));
        pnLeft.add(lbAccountNumber);
        pnLeft.add(tfAccountNumber);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Họ tên
        JLabel lbName = new JLabel("Họ và tên: ");
        lbName.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnLeft.add(lbName);
        tfName = new JTextField();
        tfName.setMaximumSize(new Dimension(200, 40));
        pnLeft.add(tfName);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Giới tính
        JPanel pnGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup bgGender = new ButtonGroup();
        pnGender.add(new JLabel("Giới tính: "));
        rbMale = new JRadioButton("Nam");
        rbFemale = new JRadioButton("Nữ");
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        pnGender.add(rbMale);
        pnGender.add(rbFemale);
        rbMale.setPreferredSize(new Dimension(70, 30));
        rbFemale.setPreferredSize(new Dimension(70, 30));
        pnLeft.add(pnGender);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Địa chỉ
        JLabel lbAddress = new JLabel("Địa chỉ: ");
        lbAddress.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnLeft.add(lbAddress);
        tfAddress = new JTextField();
        tfAddress.setMaximumSize(new Dimension(200, 40));
        pnLeft.add(tfAddress);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Lương
        JLabel lbSalary = new JLabel("Lương: ");
        lbSalary.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnLeft.add(lbSalary);
        tfSalary = new JTextField();
        tfSalary.setMaximumSize(new Dimension(200, 40));
        pnLeft.add(tfSalary);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Buttons
        JPanel pnLeftBottom = new JPanel();
        btAdd = new JButton("Thêm");
        btEdit = new JButton("Sửa");
        btDelete = new JButton("Xóa");
        pnLeftBottom.add(btAdd);
        pnLeftBottom.add(btEdit);
        pnLeftBottom.add(btDelete);
        pnLeft.add(pnLeftBottom);

        // Table
        JPanel pnRight = new JPanel(new GridLayout(1, 1));
        String[] headers = {"Số tài khoản", "Họ tên", "Giới tính", "Địa chỉ", "Lương"};
        dfModel = new DefaultTableModel(headers, 0);
        tb = new JTable(dfModel);
        pnRight.add(new JScrollPane(tb));

        this.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight));
        //loadData(dfModel);
    }

    public static void main(String[] args) {
        QLCB qlcb = new QLCB();
        qlcb.getCon();
        SwingUtilities.invokeLater(() -> {
            new GuiInsertCB().setVisible(true);
            //guiInsertCB.loadData(guiInsertCB.dfModel);
        });
    }

    public void loadData(DefaultTableModel dfModel) {
        try {
            ResultSet res = QLCB.getAllData();
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
            dfModel.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAdd) {
        } else if (e.getSource() == btEdit) {
        } else if (e.getSource() == btDelete) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
