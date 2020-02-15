import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class 表格模型 extends JFrame
{
    DefaultTableModel tableModel;
    JTable table;
    JTextField textField1;
    JTextField textField2;

    public static void main(String arg[])
    {
        表格模型 dfgh=new 表格模型();
        dfgh.setVisible(true);
    }
    public 表格模型()
    {
        super();
        setTitle("表格模型");
        setBounds(100,100,510,375);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollPane=new JScrollPane();
        getContentPane().add(scrollPane,BorderLayout.CENTER);
        String[] columnNames={"A","B"};
        String[][] tableValues={{"A1","B1"},{"A2","B2"},{"A3","B3"}};
        tableModel=new DefaultTableModel(tableValues,columnNames);
        table.setRowSorter(new TableRowSorter<>(tableModel));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow=table.getSelectedRow();
                Object oa=tableModel.getValueAt(selectedRow,0);
                Object ob=tableModel.getValueAt(selectedRow,1);
                textField1.setText(oa.toString());
                textField2.setText(ob.toString());
            }
        });
        scrollPane.setViewportView(table);
        JPanel panel=new JPanel();
        getContentPane().add(panel,BorderLayout.SOUTH);
        panel.add(new JLabel("A:"));
        textField1=new JTextField("A4",10);
        panel.add(textField1);
        panel.add(new JLabel("B:"));
        textField2=new JTextField("B4,10");
        panel.add(textField2);
        JButton addButton=new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] rowValues={textField1.getText(),textField2.getText()};
                tableModel.addRow(rowValues);
                int rowCount=table.getRowCount()+1;
                textField1.setText("A"+rowCount);
                textField2.setText("B"+rowCount);
            }
        });
        panel.add(addButton);
        JButton updButton=new JButton("修改");
        updButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selecteedRow=table.getSelectedRow();
                if(selecteedRow !=-1)
                    tableModel.setValueAt(textField1.getText(),selecteedRow,0);
                tableModel.setValueAt(textField2.getText(),selecteedRow,1);
            }
        });
        panel.add(updButton);
        JButton delButton=new JButton("删除");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=table.getSelectedRow();
                if(selectedRow!=1)
                    tableModel.removeRow(selectedRow);
            }
        });
        panel.add(delButton);
    }
}