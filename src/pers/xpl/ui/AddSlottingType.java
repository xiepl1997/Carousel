/*
 * auther:Xie Peiliang
 * create date:2019.4.6
 * describe:添加新的货位类别
 */
package pers.xpl.ui;

import javax.swing.JFrame;
import javax.naming.Context;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import pers.xpl.util.FunctionsOfMysql;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddSlottingType extends JFrame implements ActionListener{
    private JTextField text_type;
    private JTextField text_SlottingCapacity;
    private JTextField text_CargoVolume;
    private JButton btnNewButton;
    private FunctionsOfMysql functionsOfMysql;
    private MyFrame myFrame;

    public AddSlottingType(MyFrame myFrame) {
        setTitle("货物属性自定义");
        this.myFrame = myFrame;
        setSize(280, 200);
        JLabel lblNewLabel = new JLabel("货位种类名：");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 12));

        text_type = new JTextField();
        text_type.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("货位的容量：");
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 12));

        text_SlottingCapacity = new JTextField();
        text_SlottingCapacity.setColumns(10);

        btnNewButton = new JButton("新增");
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 12));
        btnNewButton.addActionListener(this);

        JLabel label = new JLabel("货物的体积：");
        label.setFont(new Font("宋体", Font.PLAIN, 12));

        text_CargoVolume = new JTextField();
        text_CargoVolume.setColumns(10);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(100)
                                                .addComponent(btnNewButton))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(56)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(lblNewLabel)
                                                                .addGap(3)
                                                                .addComponent(text_type, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(lblNewLabel_1)
                                                                        .addComponent(label))
                                                                .addGap(2)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(text_CargoVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(text_SlottingCapacity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
                                .addContainerGap(107, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(13)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(text_type, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(text_CargoVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(21)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(text_SlottingCapacity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_1))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(btnNewButton)
                                .addGap(19))
        );
        getContentPane().setLayout(groupLayout);
        setLocationRelativeTo(null);
        setVisible(true);



    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == btnNewButton) {
            if(text_type.getText().length() == 0 || text_CargoVolume.getText().length() == 0||text_SlottingCapacity.getText().length()==0) {
                JOptionPane.showMessageDialog(null, "请将信息填写完整！");
                return;
            }
            int flag = 0;
            functionsOfMysql = new FunctionsOfMysql();
            try {
                flag = functionsOfMysql.addInfoToSlottingType(text_type.getText(), text_CargoVolume.getText(),text_SlottingCapacity.getText());  // 在slotting_type中添加一行记录
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if(flag==0)
                JOptionPane.showMessageDialog(null, "添加失败！");
            else {
                JOptionPane.showConfirmDialog(null, "添加成功！", "提示", JOptionPane.OK_OPTION);
                try {
                    myFrame.UpdateThePanelOfradiobuttons();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //刷新主页
                myFrame.panel_5.removeAll();
                myFrame.panel_7.removeAll();
                myFrame.repaint();
                try {
                    myFrame.UpdateThePanelOfradiobuttons();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                myFrame.revalidate();

            }
        }
    }
}


