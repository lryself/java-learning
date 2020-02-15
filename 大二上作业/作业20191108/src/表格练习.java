import java.sql.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class 表格练习 {
    public static void main(String arg[])
    {
        new show();
    }
}

class show
{
    JFrame jFrame=new JFrame("表格窗口");
    JScrollPane jScrollPane;
    JPanel jPanel=new JPanel(new GridLayout(1,2));
    DefaultTableModel defaultTableModel;
    JTable jTable;
    JButton jButton[]=new JButton[2];
    Vector<Vector<String>> tableValueV=new Vector<>();
    Vector<String> rowsV=new Vector<>();
    show()
    {
        SetJTable();
        SetJFrame();
    }
    void SetJFrame()
    {
        jButton[0]=new JButton("添加行");
        jButton[1]=new JButton("删除行");
        jPanel.add(jButton[0]);
        jPanel.add(jButton[1]);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(BorderLayout.CENTER,jScrollPane);
        jFrame.add(BorderLayout.SOUTH,jPanel);

        jFrame.pack();
        jFrame.setSize(640,480);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
    void SetJTable()
    {
        Vector<String> columnNameV=new Vector<>();
        columnNameV.add("A");
        columnNameV.add("B");
        rowsV.add("a");
        rowsV.add("b");
        tableValueV.add(rowsV);
        defaultTableModel=new DefaultTableModel(tableValueV,columnNameV);
        jTable=new JTable(defaultTableModel);
        jScrollPane=new JScrollPane(jTable);
    }
}