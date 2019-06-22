/*
 * auther:Xie Peiliang
 * create date:2019.4.4
 * describe:主界面
 */
package pers.xpl.ui;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.GroupLayout.Alignment;


import pers.xpl.util.Cargo;
import pers.xpl.util.FunctionsOfMysql;
import pers.xpl.util.Slotting;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.sql.RowSet;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.eltima.components.ui.DatePicker;

import org.omg.CORBA.DATA_CONVERSION;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.LayoutStyle.ComponentPlacement;

public class MyFrame extends JFrame implements ItemListener, ActionListener {
    protected Object[][] rowData1;    //货物情况表格数据
    protected Object[][] rowData2;    //货位表格数据
    protected Object[][] rowData3;    //货物货位种类信息
    protected Object[][] rowData4;    //货物名和货物数量
    protected JTable table1;  // 货物概览表
    protected JTable table2;  // 货位概览表
    protected DefaultTableModel tableModel1;
    protected DefaultTableModel tableModel2;
    protected JTextField textField;   //货物数量输入框
    protected JTextField textField_1;   //货位容积显示框
    protected JRadioButton[] radioButtons;  //入库货物选项集
    protected JRadioButton[] radioButtons1;  //出库货物选项集
    protected JButton bt_InCarousel;         //入库按钮
    protected JButton bt_addNewCargo;        //添加货位种类
    protected JButton bt_OutCarousel;        //出库按钮
    protected FunctionsOfMysql functionsOfMysql;
    protected JTextField textField_2;
    protected JTextField textField_3;
    protected JPanel panel_5, panel_7;
    protected ButtonGroup buttonGroup, buttonGroup1; //两个radiobuttton集
    protected JButton btnNewButton;
    protected JButton btnNewButton_2;
    protected JButton btnNewButton_1;
    protected JButton btnNewButton_3;
    protected JComboBox comboBox, comboBox_1, comboBox_2, comboBox_3, comboBox_4, comboBox_5;
    protected DatePicker datePicker, datePicker1;
    private JTextField textField_4;
    final String[] colunmNames1 = {"货物批号", "货物编号", "所在货斗", "所在货位", "货位内编号", "货物名", "单位体积", "数量"};    //货物概览表头
    final String[] columnNames2 = {"货位ID", "所在货斗", "货斗内编号", "货位种类", "总容量", "可用容量", "状态"};    //货位概览表头
    protected JScrollPane scrollPane, scrollPane_1;
    public String tips;  //提示字符串
    public String time;  //货物装入时间

    public MyFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();  //获取桌面大小
        int screenWidth = screenSize.width;
        int screenHight = screenSize.height;
        setSize(screenWidth, screenHight);

        functionsOfMysql = new FunctionsOfMysql();

        //四个面板
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("货物概览"));

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(BorderFactory.createTitledBorder("入库操作"));

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(BorderFactory.createTitledBorder("出库操作"));

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(BorderFactory.createTitledBorder("货位概览"));

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(panel_2, 0, 0, Short.MAX_VALUE)
                                        .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                                        .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                        .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)))
        );

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(BorderFactory.createTitledBorder("选择入库货物类型"));

        JLabel lblNewLabel = new JLabel("输入货物入库数量：");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 12));

        textField = new JTextField();
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("所选货物货位容积：");
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 12));

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setEditable(false);

        bt_InCarousel = new JButton("入库");
        bt_InCarousel.setFont(new Font("宋体", Font.PLAIN, 12));
        bt_InCarousel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        bt_InCarousel.addActionListener(this);

        bt_addNewCargo = new JButton("创建新货物");
        bt_addNewCargo.setFont(new Font("宋体", Font.PLAIN, 12));
        bt_addNewCargo.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        bt_addNewCargo.addActionListener(this);

        JLabel lblNewLabel_4 = new JLabel("所选货物单个体积：");
        lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 12));

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setEditable(false);


        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                                .addGap(10))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                                .addGap(10))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 118, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED)))
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                        .addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                        .addComponent(textField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addGap(0)
                                                .addComponent(bt_InCarousel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                                .addGap(23)))
                                .addGap(65)
                                .addComponent(bt_addNewCargo, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                .addGap(143))
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField)
                                        .addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField_4)
                                        .addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textField_1))
                                .addGap(25)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(bt_InCarousel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bt_addNewCargo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(41))
        );

        panel_5 = new JPanel();
        JScrollPane scrollPane_2 = new JScrollPane(panel_5);//用来放置radiobutton选项
        panel_5.setLayout(new GridLayout(0, 3));

        buttonGroup = null;

        GroupLayout gl_panel_4 = new GroupLayout(panel_4);
        gl_panel_4.setHorizontalGroup(
                gl_panel_4.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );
        gl_panel_4.setVerticalGroup(
                gl_panel_4.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );
        panel_4.setLayout(gl_panel_4);
        panel_1.setLayout(gl_panel_1);

        JPanel panel_6 = new JPanel();
        panel_6.setBorder(BorderFactory.createTitledBorder("选择出库货物类型"));

        JLabel lblNewLabel_2 = new JLabel("输入货物出库数量：");
        lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 12));

        textField_2 = new JTextField();
        textField_2.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("所选货物可取数量：");
        lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 12));

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setEditable(false);

        bt_OutCarousel = new JButton("出库");
        bt_OutCarousel.setFont(new Font("宋体", Font.PLAIN, 12));
        bt_OutCarousel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        bt_OutCarousel.addActionListener(this);

        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
        gl_panel_2.setHorizontalGroup(
                gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                        .addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel_2.createSequentialGroup()
                                                .addComponent(lblNewLabel_3)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                        .addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
                                                .addComponent(lblNewLabel_2)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                                .addGap(288))
                        .addGroup(gl_panel_2.createSequentialGroup()
                                .addGap(202)
                                .addComponent(bt_OutCarousel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(227))
        );
        gl_panel_2.setVerticalGroup(
                gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_2.createSequentialGroup()
                                .addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_3)
                                        .addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(65)
                                .addComponent(bt_OutCarousel)
                                .addContainerGap(80, Short.MAX_VALUE))
        );

        panel_7 = new JPanel();
        JScrollPane scrollPane_3 = new JScrollPane(panel_7);
        panel_7.setLayout(new GridLayout(0, 3));

        buttonGroup1 = new ButtonGroup();

        rowData3 = null;
        try {
            UpdateThePanelOfradiobuttons();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        GroupLayout gl_panel_6 = new GroupLayout(panel_6);
        gl_panel_6.setHorizontalGroup(
                gl_panel_6.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );
        gl_panel_6.setVerticalGroup(
                gl_panel_6.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );
        panel_6.setLayout(gl_panel_6);
        panel_2.setLayout(gl_panel_2);


        //从数据库中获取信息
        rowData1 = null;
        try {
            rowData1 = functionsOfMysql.getDataToShowInTable1();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //tableModel1 = new DefaultTableModel(rowData1, colunmNames1);
        tableModel1 = new DefaultTableModel(null, colunmNames1);
        table1 = new JTable(tableModel1) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setFont(new Font("宋体", Font.PLAIN, 12));
        table1.getColumnModel().getColumn(0).setPreferredWidth(130);

        DefaultTableCellRenderer cr = new DefaultTableCellRenderer(); //表格内数据居中
        cr.setHorizontalAlignment(JLabel.CENTER);
        table1.setDefaultRenderer(Object.class, cr);

        //表格2
        rowData2 = null;
        try {
            rowData2 = functionsOfMysql.getDataToshowInTable2();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tableModel2 = new DefaultTableModel(null, columnNames2);
        table2 = new JTable(tableModel2) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table2.setFont(new Font("宋体", Font.PLAIN, 12));
        table2.setDefaultRenderer(Object.class, cr);
        table2.getColumnModel().getColumn(0).setPreferredWidth(130);


        scrollPane_1 = new JScrollPane(table2);

        btnNewButton_1 = new JButton("显示所有货位");
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 12));
        btnNewButton_1.addActionListener(this);

        JPanel panel_9 = new JPanel();
        panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "条件筛选", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        gl_panel_3.setHorizontalGroup(
                gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
                                .addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
                                                .addGap(57)
                                                .addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(62))
                                        .addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE))
        );
        gl_panel_3.setVerticalGroup(
                gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                        .addGroup(gl_panel_3.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNewButton_1)
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))
        );
        panel_9.setLayout(null);

        JLabel lblNewLabel_10 = new JLabel("按状态：");
        lblNewLabel_10.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel_10.setBounds(10, 28, 54, 15);
        panel_9.add(lblNewLabel_10);

        comboBox_3 = new JComboBox();
        comboBox_3.setFont(new Font("宋体", Font.PLAIN, 12));
        comboBox_3.setModel(new DefaultComboBoxModel(new String[]{" ", "\u6B63\u5E38", "\u6545\u969C"}));
        comboBox_3.setBounds(10, 53, 62, 21);
        panel_9.add(comboBox_3);

        JLabel lblNewLabel_11 = new JLabel("按时间：");
        lblNewLabel_11.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel_11.setBounds(10, 94, 54, 15);
        panel_9.add(lblNewLabel_11);

        comboBox_4 = new JComboBox();
        comboBox_4.setFont(new Font("宋体", Font.PLAIN, 12));
        comboBox_4.setModel(new DefaultComboBoxModel(new String[]{"大于", "等于", "小于"}));
        comboBox_4.setBounds(10, 119, 54, 21);
        panel_9.add(comboBox_4);
        panel_3.setLayout(gl_panel_3);

        scrollPane = new JScrollPane(table1);

        btnNewButton = new JButton("显示所有货物");
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 12));
        btnNewButton.addActionListener(this);

        JPanel panel_8 = new JPanel();
        panel_8.setBorder(new TitledBorder(null, "条件筛选", TitledBorder.CENTER, TitledBorder.TOP, null, null));

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGap(50)
                                                .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                        .addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNewButton)
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE))
        );
        panel_8.setLayout(null);
        panel.setLayout(gl_panel);

        getContentPane().setLayout(groupLayout);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu jMenu = new JMenu("File");         //菜单
        JMenuItem jItem = new JMenuItem("1");
        jItem.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub

            }
        });
        JMenuItem jItem1 = new JMenuItem("2");
        jMenu.add(jItem);
        jMenu.add(jItem1);
        menuBar.add(jMenu);

        JMenu jMenu2 = new JMenu("Set");
        menuBar.add(jMenu2);

        datePicker = new DatePicker();
        datePicker.getInnerTextField().setFont(new Font("宋体", Font.PLAIN, 12));
        datePicker.setSize(135, 21);
        datePicker.setLocation(79, 48);
        panel_8.add(datePicker);

        datePicker1 = new DatePicker();
        datePicker1.getInnerTextField().setFont(new Font("宋体", Font.PLAIN, 12));
        datePicker1.setLocation(77, 119);
        datePicker1.setSize(135, 21);
        panel_9.add(datePicker1);

        JLabel lblNewLabel_12 = new JLabel("按剩余容量：");
        lblNewLabel_12.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel_12.setBounds(10, 169, 87, 15);
        panel_9.add(lblNewLabel_12);

        comboBox_5 = new JComboBox();
        comboBox_5.setFont(new Font("宋体", Font.PLAIN, 12));
        comboBox_5.setModel(new DefaultComboBoxModel(new String[]{" ", "未满", "已满"}));
        comboBox_5.setBounds(10, 199, 62, 21);
        panel_9.add(comboBox_5);

        btnNewButton_3 = new JButton("按条件筛选");
        btnNewButton_3.setFont(new Font("宋体", Font.PLAIN, 12));
        btnNewButton_3.setBounds(56, 253, 112, 23);
        btnNewButton_3.addActionListener(this);
        panel_9.add(btnNewButton_3);

        JLabel lblNewLabel_5 = new JLabel("按时间：");
        lblNewLabel_5.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel_5.setBounds(10, 23, 54, 15);
        panel_8.add(lblNewLabel_5);

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"大于", "小于", "等于"}));
        comboBox.setToolTipText("");
        comboBox.setFont(new Font("宋体", Font.PLAIN, 12));
        comboBox.setBounds(10, 48, 59, 21);

        panel_8.add(comboBox);

        JLabel lblNewLabel_6 = new JLabel("按货斗：");
        lblNewLabel_6.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel_6.setBounds(10, 93, 54, 15);
        panel_8.add(lblNewLabel_6);

        comboBox_1 = new JComboBox();
        comboBox_1.setFont(new Font("宋体", Font.PLAIN, 12));
        comboBox_1.setModel(new DefaultComboBoxModel(new String[]{" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "11", "12", "13", "14", "15", "16"}));
        comboBox_1.setBounds(39, 118, 41, 21);
        panel_8.add(comboBox_1);

        JLabel lblNewLabel_7 = new JLabel("处于");
        lblNewLabel_7.setFont(new Font("宋体", Font.PLAIN, 12));
        lblNewLabel_7.setBounds(10, 121, 24, 15);
        panel_8.add(lblNewLabel_7);

        JLabel lblNewLabel_8 = new JLabel("号货斗");
        lblNewLabel_8.setFont(new Font("宋体", Font.PLAIN, 12));
        lblNewLabel_8.setBounds(90, 121, 54, 15);
        panel_8.add(lblNewLabel_8);

        JLabel lblNewLabel_9 = new JLabel("按数量：");
        lblNewLabel_9.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel_9.setBounds(10, 156, 54, 15);
        panel_8.add(lblNewLabel_9);

        comboBox_2 = new JComboBox();
        comboBox_2.setFont(new Font("宋体", Font.PLAIN, 12));
        comboBox_2.setModel(new DefaultComboBoxModel(new String[]{"大于", "等于", "小于"}));
        comboBox_2.setBounds(10, 181, 59, 21);
        panel_8.add(comboBox_2);

        JLabel label = new JLabel("数量：");
        label.setFont(new Font("宋体", Font.PLAIN, 12));
        label.setBounds(79, 184, 41, 15);
        panel_8.add(label);

        textField_5 = new JTextField();
        textField_5.setFont(new Font("宋体", Font.PLAIN, 12));
        textField_5.setBounds(123, 181, 66, 21);
        panel_8.add(textField_5);
        textField_5.setColumns(10);

        btnNewButton_2 = new JButton("按条件筛选");
        btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 12));
        btnNewButton_2.setBounds(54, 231, 112, 23);
        btnNewButton_2.addActionListener(this);
        panel_8.add(btnNewButton_2);
    }

    /*
     * 更新radiobutton集
     */
    public void UpdateThePanelOfradiobuttons() throws SQLException {
        rowData3 = functionsOfMysql.getSlottingMessage();
        rowData4 = functionsOfMysql.getTheCountOfCargos();
        radioButtons = new JRadioButton[rowData3.length];
        radioButtons1 = new JRadioButton[rowData3.length];

        for (int i = 0; i < rowData3.length; i++) {               //初始化radiobutton名字，添加到容器
            radioButtons[i] = new JRadioButton(rowData3[i][0].toString());
            radioButtons[i].setFont(new Font("宋体", Font.PLAIN, 12));
            panel_5.add(radioButtons[i]);

            radioButtons1[i] = new JRadioButton(rowData3[i][0].toString());
            radioButtons1[i].setFont(new Font("宋体", Font.PLAIN, 12));
            panel_7.add(radioButtons1[i]);
        }

        buttonGroup = new ButtonGroup();  //构建成单选
        for (int i = 0; i < radioButtons.length; i++) {
            buttonGroup.add(radioButtons[i]);

            buttonGroup1.add(radioButtons1[i]);
        }

        for (int i = 0; i < radioButtons.length; i++) {   //为radiobutton添加监听
            radioButtons[i].addItemListener(this);

            radioButtons1[i].addItemListener(this);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        //获取数据来更新“货物货位容积”
        for (int i = 0; i < rowData3.length; i++) {
            if (e.getSource() == radioButtons[i]) {
                textField_1.setText(rowData3[i][2].toString()); //点击radiobutton集即更新"货物货位容积"文本框
                textField_4.setText(rowData3[i][1].toString()); //点击radiobutton集即更新“单个货物体积”文本框
                break;
            }
        }
        LABEL1:
        for (int i = 0; i < rowData3.length; i++) {                    //点击radiobutton1集即更新“所选货物现存数量”文本框
            if (e.getSource() == radioButtons1[i]) {
                String sum = "0";
                for (int j = 0; j < rowData4.length; j++) {
                    if (radioButtons1[i].getText().equals(rowData4[j][0].toString()))
                        sum = rowData4[j][1].toString();
                }
                textField_3.setText(sum);
                break LABEL1;
            }
        }
    }

    private String SelectedCargo = ""; //被选中的货物名
    private JTextField textField_5;

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        //添加新货物
        if (e.getSource() == bt_addNewCargo) {
            AddSlottingType addSlottingType = new AddSlottingType(this);  //弹出添加货位种类的窗口

        }
        //入库操作
        else if (e.getSource() == bt_InCarousel) {
            int index = -1;
            for (int i = 0; i < rowData3.length; i++) {
                if (radioButtons[i].isSelected() == true) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                JOptionPane.showMessageDialog(null, "请选择要入库的货物！");
                return;
            }
            if (textField.getText().trim().length() == 0 || textField.getText().equals("0")) {
                JOptionPane.showMessageDialog(null, "请输入正确的入库数量！");
                return;
            } else {
                try {
                    Integer.parseInt(textField.getText());
                } catch (NumberFormatException e1) {
                    // TODO: handle exception
                    JOptionPane.showMessageDialog(null, "请输入正确的入库数量！");
                    return;
                }
            }

            time = "";
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            time = dFormat.format(new Date());

            try {
                tips = "";
                InCarousel(radioButtons[index].getText(), Integer.valueOf(textField.getText().trim()), Float.valueOf(textField_4.getText()), Float.valueOf(textField_1.getText()));
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, tips);//弹窗提示
            //更新radiobutton集点击后的显示数据
            try {
                rowData4 = functionsOfMysql.getTheCountOfCargos();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        //出库操作
        else if (e.getSource() == bt_OutCarousel) {
            int index = -1;
            for (int i = 0; i < rowData3.length; i++) {
                if (radioButtons1[i].isSelected() == true) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                JOptionPane.showMessageDialog(null, "请选择要出库的货物！");
                return;
            }
            if (textField_2.getText().trim().length() == 0 || textField_2.getText().equals("0")) {
                JOptionPane.showMessageDialog(null, "请输入正确的出库数量！");
                return;
            } else {
                try {
                    Integer.parseInt(textField_2.getText());
                } catch (NumberFormatException e1) {
                    // TODO: handle exception
                    JOptionPane.showMessageDialog(null, "请输入正确的出库数量！");
                    return;
                }
            }

            //检查库中是否有该货物，以及数量是否能够满足出库要求
            int flag1 = 0;//货物存在标记
            int count_in_carousel = 0;

            for (int i = 0; i < rowData4.length; i++) {
                if (rowData4[i][0].toString().equals(radioButtons1[index].getText())) {
                    flag1 = 1;
                    count_in_carousel = Integer.valueOf(rowData4[i][1].toString());
                    break;
                }
            }
            if (flag1 == 0) {
                JOptionPane.showMessageDialog(null, "回转库中没有存放该货物！");
                return;
            }
            if (count_in_carousel < Integer.valueOf(textField_2.getText())) {
                JOptionPane.showMessageDialog(null, "回转库中该货物可出库数量不足！");
                return;
            }

            tips = "";
            try {
                OutCarousel(radioButtons1[index].getText(), Integer.valueOf(textField_2.getText()));
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                tips = "出库失败！";
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                tips = "出库失败！";
            }

            //出库后回收空间
            try {
                SpaceRecycle();
            } catch (SQLException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, tips);
            //更新radiobutton集点击后的显示数据
            try {
                rowData4 = functionsOfMysql.getTheCountOfCargos();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //如果是“显示所有货物”按钮
        else if (e.getSource() == btnNewButton) {
            rowData1 = null;
            try {
                rowData1 = functionsOfMysql.getDataToShowInTable1();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            tableModel1 = new DefaultTableModel(rowData1, colunmNames1);
            table1.setModel(tableModel1);
            table1.getColumnModel().getColumn(0).setPreferredWidth(130);
        }
        //如果是“显示所有货位”按钮
        else if (e.getSource() == btnNewButton_1) {
            rowData2 = null;
            try {
                rowData2 = functionsOfMysql.getDataToshowInTable2();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            tableModel2 = new DefaultTableModel(rowData2, columnNames2);
            table2.setModel(tableModel2);
            table2.getColumnModel().getColumn(0).setPreferredWidth(130);
        }
        //货物概览按条件筛选功能
        else if (e.getSource() == btnNewButton_2) {
            try {
                selecttable1();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //货位概览按条件筛选功能
        else if (e.getSource() == btnNewButton_3) {
            try {
                selecttable2();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }

    /**
     * 货物概览按条件筛选信息
     */
    public void selecttable1() throws SQLException {
        String[] time;
        String times = "";
        String sql = "";
        String factor1 = "", factor2 = "", factor3 = "";
        if (datePicker.getValue() != null) {
            if (comboBox.getSelectedItem().toString().equals("大于")) {
                factor1 = ">";
            } else if (comboBox.getSelectedItem().toString().equals("小于")) {
                factor1 = "<";
            } else {
                factor1 = "=";
            }
            time = datePicker.getInnerTextField().getText().split("[-, ,:]");
            for (String string : time) {
                times = times + string;
            }
            times = times + "000";
        } else {
            factor1 = "null";
        }

        if (comboBox_1.getSelectedItem().toString() != " ") {
            factor2 = comboBox_1.getSelectedItem().toString();
        } else {
            factor2 = "null";
        }

        if (textField_5.getText().length() > 0 && textField_5.getText().matches("[0-9]*")) {
            if (comboBox_2.getSelectedItem().toString().equals("大于")) {
                factor3 = ">";
            } else if (comboBox_2.getSelectedItem().toString().equals("小于")) {
                factor3 = "<";
            } else {
                factor3 = "=";
            }
        } else {
            if (textField_5.getText().length() == 0)
                factor3 = "null";
            else {
                JOptionPane.showMessageDialog(null, "请正确填写数量！");
                return;
            }
        }

        if (factor1 != "null") {
            if (factor2 != "null") {
                if (factor3 != "null") {
                    sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where cid" + factor1 + times + " and label_1 =" + factor2 + " and count" + factor3 + textField_5.getText();

                } else {
                    sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where cid" + factor1 + times + " and label_1 =" + factor2;
                }
            } else {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where cid" + factor1 + times;
            }
        }

        if (factor1 != "null") {
            if (factor2 != "null" && factor3 != "null") {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where cid" + factor1 + times + " and label_1 =" + factor2 + " and count" + factor3 + textField_5.getText() + " order by cid";
            } else if (factor2 != "null" && factor3 == "null") {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where cid" + factor1 + times + " and label_1 =" + factor2 + " order by cid";
            } else if (factor2 == "null" && factor3 != "null") {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where cid" + factor1 + times + " and count" + factor3 + textField_5.getText() + " order by cid";
            } else {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where cid" + factor1 + times + " order by cid";
            }
        } else {
            if (factor2 != "null" && factor3 != "null") {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where label_1 =" + factor2 + " and count" + factor3 + textField_5.getText() + " order by cid";
            } else if (factor2 != "null" && factor3 == "null") {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where label_1 =" + factor2 + " order by cid";
            } else if (factor2 == "null" && factor3 != "null") {
                sql = "select cid,clocation,label_1,label_2,label_3,type,volume,count from cargo where count" + factor3 + textField_5.getText() + " order by cid";
            } else {
                JOptionPane.showMessageDialog(null, "请输入筛选条件！");
                return;
            }
        }

        //System.out.println(sql);
        rowData1 = functionsOfMysql.getDataToShowInTable1ByFactors(sql);
        tableModel1 = new DefaultTableModel(rowData1, colunmNames1);
        table1.setModel(tableModel1);
        table1.getColumnModel().getColumn(0).setPreferredWidth(130);
    }

    /**
     * 货位概览筛选
     */
    public void selecttable2() throws SQLException {
        String factor1 = "", factor2 = "", factor3 = "";
        String[] time;
        String times = "";
        String sql = "";
        if (datePicker1.getValue() != null) {
            if (comboBox_4.getSelectedItem().toString().equals("大于")) {
                factor1 = ">";
            } else if (comboBox_4.getSelectedItem().toString().equals("小于")) {
                factor1 = "<";
            } else {
                factor1 = "=";
            }
            time = datePicker1.getInnerTextField().getText().split("[-, ,:]");
            for (String string : time) {
                times = times + string;
            }
            times = times + "000";
        } else {
            factor1 = "null";
        }
        if (comboBox_3.getSelectedItem().toString() != " ") {
            if (comboBox_3.getSelectedItem().toString().equals("正常")) {
                factor2 = "正常";
            } else if (comboBox_3.getSelectedItem().toString().equals("故障")) {
                factor2 = "故障";
            }
        } else {
            factor2 = "null";
        }
        if (comboBox_5.getSelectedItem().toString() != " ") {
            if (comboBox_5.getSelectedItem().toString().equals("未满"))
                factor3 = "capacity!=capacity_available";
            else if (comboBox_5.getSelectedItem().toString().equals("已满"))
                factor3 = "capacity=capacity_available";
        } else {
            factor3 = "null";
        }
        if (factor1 != "null") {
            if (factor2 != "null" && factor3 != "null") {
                sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where sid" + factor1 + times + " and status='" + factor2 + "' and " + factor3 + " order by label_1,label_2";
            } else if (factor2 != "null" && factor3 == "null") {
                sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where sid" + factor1 + times + " and status='" + factor2 + "' order by label_1,label_2";
            } else if (factor2 == "null" && factor3 != "null") {
                sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where sid" + factor1 + times + " and " + factor3 + " order by label_1,label_2";
            } else {
                sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where sid" + factor1 + times + " order by label_1,label_2";
            }
        } else {
            if (factor2 != "null" && factor3 != "null") {
                sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where status='" + factor2 + "' and " + factor3 + " order by label_1,label_2";
            } else if (factor2 != "null" && factor3 == "null") {
                sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where status='" + factor2 + "' order by label_1,label_2";
            } else if (factor2 == "null" && factor3 != "null") {
                sql = "select sid,label_1,label_2,type,capacity,capacity_available, status from slotting where " + factor3 + " order by label_1,label_2";
            } else {
                JOptionPane.showMessageDialog(null, "请输入筛选条件！");
                return;
            }
        }
        //System.out.println(sql);
        rowData2 = functionsOfMysql.getDataToshowInTable2ByFactorys(sql);
        tableModel2 = new DefaultTableModel(rowData2, columnNames2);
        table2.setModel(tableModel2);
        table2.getColumnModel().getColumn(0).setPreferredWidth(130);
    }


    /*
     * 入库操作方法：最佳适应算法，需要考虑同一批货物装入到不同货位上的情况
     * 情况1：在已有的货位上找到第一个存放该类货物的可用容量大于0的 货位，并且该货位的可用容量大于一个货物的大小，将该货位的最大可放入货物数量放入该货位，
     * 剩余的货物则继续情况1。
     * 情况2：已有的货位都不能满足要求，则在最近的一个容量足够的空闲空间分配一个该货物种类的货位
     *
     * 参数：货物名、入库数量、单个货物占用空间、同名货位的容量
     *
     */
    public void InCarousel(String name, int count, float CargoVolume, float SlottingVolume) throws SQLException {
        if (count * CargoVolume > functionsOfMysql.getBiggistVolumeOfCargo(name)) {
            tips += "回转库空间不足！";
            return;
        }

        LinkedList<Slotting> list = putSlottingsToList();

        /*
         * 入库
         */
        for (Slotting slotting : list) {
            //若能找到最小的一个同名货位
            if (slotting.getName().equals(name) && slotting.getCapacity_available() >= CargoVolume) {

                int in_carousel_count = -1;
                int surplus_count = -1;
                if (slotting.getCapacity_available() < count * CargoVolume) {
                    //能转入该货位的数量
                    in_carousel_count = (int) (slotting.getCapacity_available() / CargoVolume);
                    //无法装入该货位的数量
                    surplus_count = count - in_carousel_count;
                } else {
                    in_carousel_count = count;
                }
                //得到货物的位置（三级定义）
                String location = "";
                int label_3 = functionsOfMysql.getTheBiggestIndexOfASlotting(slotting.getId()) + 1;
                location = slotting.getLabel_1() + "-" + slotting.getLabel_2() + "-" + String.valueOf(label_3);

                Cargo cargo = new Cargo(name, CargoVolume, SlottingVolume, in_carousel_count);
                //设置货物的其他属性
                cargo.setLocation(location);
                cargo.setLabel_1(slotting.getLabel_1());
                cargo.setLabel_2(slotting.getLabel_2());
                cargo.setLabel_3(label_3);
                cargo.setSid(slotting.getId());
                cargo.setID(time);

                //更新cargo表
                functionsOfMysql.UpdateCargo(cargo);
                //更新slotting表
                functionsOfMysql.UpdateSlotting(slotting.getId(), slotting.getCapacity_available() - cargo.getTheSumOfVolume());
                RefreashTables();
                tips += String.format("已经将%d件[%s]放入了%s号货位，编号为%s\n", in_carousel_count, cargo.getName(), cargo.getSid(), cargo.getLocation());

                //如果还有剩余货物未入库
                if (surplus_count != -1) {
                    InCarousel(name, surplus_count, CargoVolume, SlottingVolume);
                }
                return;
            }
            //如果没有同名的货位，则创建一个新的货位
            else if (slotting.getName().equals("empty") && slotting.getCapacity_available() >= SlottingVolume) {

                int in_carousel_count = -1;
                int surplus_count = -1;
                if (SlottingVolume < count * CargoVolume) {
                    //能转入该货位的数量
                    in_carousel_count = (int) (SlottingVolume / CargoVolume);
                    //无法装入该货位的数量
                    surplus_count = count - in_carousel_count;
                } else {
                    in_carousel_count = count;
                }

                String time1 = "";
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                time1 = dateFormat1.format(new Date());

                Cargo cargo = new Cargo(name, CargoVolume, SlottingVolume, in_carousel_count);
                String location = "";
                location = slotting.getLabel_1() + "-" + slotting.getLabel_2() + "-1";
                cargo.setLocation(location);
                cargo.setID(time);
                cargo.setLabel_1(slotting.getLabel_1());
                cargo.setLabel_2(slotting.getLabel_2());
                cargo.setLabel_3(1);

                /*
                 * 如果空闲区域分配后还有空闲空间，将新的货位插入到slotting表，并且将原来的空闲区域的label_2和大小改变
                 * 如果空闲分区刚好被一个货位分配完，则将原来的的空闲货位名、可用容量更新
                 */
                if (slotting.getCapacity() > SlottingVolume) {
                    //新建货位
                    Slotting slotting1 = new Slotting(time1, cargo.getName(), slotting.getLabel_1(), slotting.getLabel_2(), cargo.getSlottingCapacity(), cargo.getSlottingCapacity());
                    cargo.setSid(time1);

                    functionsOfMysql.AddNewSlotting(slotting1, slotting1.getCapacity() - cargo.getTheSumOfVolume());
                    functionsOfMysql.UpdateCargo(cargo);
                    functionsOfMysql.UpdateTheFreeSlotting(slotting.getId(), slotting.getLabel_2() + 1, slotting.getCapacity_available() - slotting1.getCapacity());
                } else if (slotting.getCapacity() == SlottingVolume) {
                    cargo.setSid(slotting.getId());
                    functionsOfMysql.UpdateCargo(cargo);
                    functionsOfMysql.UpdateTheFreeSlotting(slotting.getId(), cargo.getName(), slotting.getCapacity() - cargo.getTheSumOfVolume());
                }
                RefreashTables();
                tips += String.format("已经将%d件[%s]放入了%s号货位，编号为%s\n", in_carousel_count, cargo.getName(), cargo.getSid(), cargo.getLocation());

                //如果还有货物未入库
                if (surplus_count != -1) {
                    InCarousel(name, surplus_count, CargoVolume, SlottingVolume);
                }
                return;

            }
        }

    }


    /*
     * 将所有的货位放置在链表中(按照可用容量的升序排列)
     */
    public LinkedList<Slotting> putSlottingsToList() throws SQLException {
        Object[][] table = functionsOfMysql.getDataToshowInTable2OrderByAvailable();
        LinkedList<Slotting> linkedList = new LinkedList<Slotting>();

        //将slotting插入链表
        for (int i = 0; i < table.length; i++) {
            String id = table[i][0].toString();
            String name = table[i][3].toString();
            int label_1 = Integer.valueOf(table[i][1].toString());
            int label_2 = Integer.valueOf(table[i][2].toString());
            float capacity = Float.valueOf(table[i][4].toString());
            float capacity_available = Float.valueOf(table[i][5].toString());
            linkedList.add(new Slotting(id, name, label_1, label_2, capacity, capacity_available));
        }

        return linkedList;
    }

    /*
     *  更新界面的两个表格
     */
    public void RefreashTables() throws SQLException {
        rowData1 = null;
        rowData1 = functionsOfMysql.getDataToShowInTable1();
        rowData2 = null;
        rowData2 = functionsOfMysql.getDataToshowInTable2();
        tableModel1 = new DefaultTableModel(rowData1, colunmNames1);
        tableModel2 = new DefaultTableModel(rowData2, columnNames2);
        table1.setModel(tableModel1);
        table1.getColumnModel().getColumn(0).setPreferredWidth(130);
        table2.setModel(tableModel2);
        table2.getColumnModel().getColumn(0).setPreferredWidth(130);
    }


    /*
     * 出库，按照先进先出的准则对货物进行出库操作。
     * 出库应该从cargo表中按照cargo 的id（时间）顺序依次取出来，取完要改变cargo表、slotting表，最后再调用“空间回收”方法将已经没有货物的货位回收作为空的待分配
     * 的货位。
     *
     * 参数：货物名、出库数量
     */
    public void OutCarousel(String name, int count) throws SQLException {

        //获取货物表信息
        try {
            rowData1 = functionsOfMysql.getDataToShowInTable1();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int len = rowData1.length;

        //未出库的货物数量
        int surplus_count = count;

        for (int i = 0; i < len; i++) {
            if (rowData1[i][5].toString().equals(name)) {
                int cnt = Integer.valueOf(rowData1[i][7].toString());//该批货物的数量
                //如果该货位上的货物的数量大于未出库的货物数量
                if (cnt > surplus_count) {
                    //变该cargo表和slotting表对应的信息
                    functionsOfMysql.ChangeTheCountOfCargo(rowData1[i][1].toString(), cnt - surplus_count);
                    functionsOfMysql.ChangeTheAvilCapacity(rowData1[i][1].toString(), surplus_count * Float.valueOf(rowData1[i][6].toString()));
                    tips += String.format("%d件%s已经从%s位置取出并出库\n", surplus_count, name, rowData1[i][1].toString());
                    surplus_count = 0;
                    break;
                }
                //如果该货位上的货物的数量小于等于未出库的货物数量
                else if (cnt > 0 && cnt <= surplus_count) {
                    functionsOfMysql.ChangeTheCountOfCargo(rowData1[i][1].toString(), 0);
                    functionsOfMysql.ChangeTheAvilCapacity(rowData1[i][1].toString(), cnt * Float.valueOf(rowData1[i][6].toString()));
                    tips += String.format("%d件%s已经从%s位置取出并出库\n", cnt, name, rowData1[i][1].toString());
                    surplus_count = surplus_count - Integer.valueOf(rowData1[i][7].toString());
                    if (surplus_count == 0)
                        break;
                }
            }
        }
        RefreashTables();

        //若当前还有没有出库的货物，则继续出库
        if (surplus_count > 0) {
            OutCarousel(name, surplus_count);
        }
    }

    /*
     * cargo 表的维护：将count为0的货物记录从cargo表中删除，并重新整理与该货物处于同一货位的货物的clocation中的三级标签label_3
     * 该方法在SpaceRecycle()中被调用
     */
    public void Update_Cargo_Label3() throws SQLException {
        //删除cargo表中的count为0 的记录
        functionsOfMysql.DelUseless_Cargo();
        //重新获取cargo表信息
        rowData1 = functionsOfMysql.getDataOfCargoOrderByLabel();
        //获取cargo表中所有货物的clocation中二级标签的数量和二级标签中三级标签的数量
        Map<String, Integer> map = new TreeMap<String, Integer>();
        if (rowData1.length > 0) {
            for (int i = 0; i < rowData1.length; i++) {
                String key = rowData1[i][1].toString().substring(0, 3);
                if (map.containsKey(key) == true) {
                    map.put(key, map.get(key) + 1);
                } else {
                    map.put(key, 1);
                }
            }
        }
        //重新整理货位中货物的三级标签
        Set<String> set = map.keySet();
        int index = 0;
        for (String str : set) {
            for (int i = 0; i < map.get(str); i++, index++) {
                if (Integer.valueOf(rowData1[index][4].toString()) != (i + 1)) {
                    String newlocation = rowData1[index][1].toString().substring(0, 3) + "-" + String.valueOf(i + 1);//新的货物位置
                    functionsOfMysql.UpdateCargoLabel_3(i + 1, newlocation, rowData1[index][1].toString());
                }
            }
        }
        RefreashTables();
    }

    /*
     * 空间回收
     * 在用户进行出库后调用
     * 首先调用Update_Cargo_Label1()方法整理cargo的三级标签
     * 功能：维护slotting表：将slotting表中已经分配货位并且没有放置货物的货位回收。具体做法是将一个货斗上的所有货位检索一遍，将那些没有装货物的货位
     * 重新命名为“empty”，并且将这些empty空间合并到一起（可用空间加起来，作为一个空货位，且二级标签为该货位上的最大的二级标签），重新整理该货斗上货位的
     * 二级标签，连带该货位上的货物的二级标签一起更新
     */
    public void SpaceRecycle() throws SQLException {
        //更新货物的三级标签
        Update_Cargo_Label3();

        rowData2 = functionsOfMysql.getDataOfSlottingOrderByLabel();
        for (int i = 0; i < rowData2.length; i++) {
            //如果货位不为empty货位，并且可用容量=总容量，则把该货位改为empty货位
            if (rowData2[i][3].toString().equals("empty") == false && rowData2[i][4].toString().equals(rowData2[i][5].toString())) {
                functionsOfMysql.Set_Empty_Slotting(rowData2[i][0].toString());
            }
        }

        //len用于记录有货物的货斗数量，若货斗中都是多个empty货位，则下面重新更新rowdate2后，rowdate2的行数会减少，会导致回收空间后货斗缺失
        //可以使用len来判断是否要追加缺失的货斗
        int len = 1;
        for (int i = 1; i < rowData2.length; i++) {
            if (Integer.valueOf(rowData2[i][1].toString()) != Integer.valueOf(rowData2[i - 1][1].toString())) {
                len++;
            }
        }

        //将slotting中的非empty且可用容量等于总容量的货位从表中删除，将empty且容量小于2000的未分配空间删除
        functionsOfMysql.Del_Slotting();

        //重新获取货物表数据
        rowData2 = functionsOfMysql.getDataOfSlottingOrderByLabel();

        //利用map得到slotting的有货物的货斗数和每个货斗内的货位数
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for (int i = 0; i < rowData2.length; i++) {
            int key = Integer.valueOf(rowData2[i][1].toString());
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }

        Set<Integer> set = map.keySet();
        int index = 0;
        for (Integer label1 : set) {
            float sum = 0;//用于当前货斗的已分配容量
            int temp = 0;
            for (int i = 0; i < map.get(label1); i++, index++) {
                if (Integer.valueOf(rowData2[index][2].toString()) != (i + 1)) {
                    functionsOfMysql.UpdateSlottingLabel2(i + 1, rowData2[index][0].toString());
                }
                sum += Float.valueOf(rowData2[index][4].toString());
                temp = i + 1;
            }
            float surplus = 2000 - sum;
            //如果该货斗剩余空间不为0，则新增一个empty货位
            if (surplus != 0) {
                String time = "";
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                time = dateFormat1.format(new Date());
                Slotting s = new Slotting(time, "empty", label1, temp + 1, surplus, surplus);
                functionsOfMysql.AddNewSlotting(s, surplus);
            }
        }
        int addcount = len - set.size();
        int label1 = set.size() + 1;
        //如果需要追加缺失的货斗
        for (int i = 0; i < addcount; i++) {
            String time = "";
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            time = dateFormat1.format(new Date());
            Slotting s = new Slotting(time, "empty", set.size() + 1, 1, 2000, 2000);
            functionsOfMysql.AddNewSlotting(s, 2000);
            label1++;
        }

        //更新货位对应的货物上的二级标签
        Update_Cargo_Label2();

        RefreashTables();

    }

    //更新货位对应货物上的二级标签
    public void Update_Cargo_Label2() throws SQLException {
        rowData1 = functionsOfMysql.getDataForUpdateCargoLabel2();
        for (int i = 0; i < rowData1.length; i++) {
            //如果货物二级标签和所在货位的二级标签不相等，则更新货物二级标签
            if (rowData1[i][3].toString().equals(rowData1[i][8].toString()) == false) {
                String newlocation = rowData1[i][2].toString() + "-" + rowData1[i][8] + "-" + rowData1[i][4];
                functionsOfMysql.UpdateCargoLabel_2(Integer.valueOf(rowData1[i][8].toString()), newlocation, rowData1[i][1].toString());
            }
        }
    }
}
















