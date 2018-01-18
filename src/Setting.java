import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Setting extends JFrame implements ActionListener {
    private JTextField rowT, columnT, mineT;
    private JButton submit;
    public int data[] = { 0, 0, 0 };

    public Setting() {

        this.submit = new JButton("Submit");
        this.rowT = new JTextField();
        this.columnT = new JTextField();
        this.mineT = new JTextField();
        JTextField text1 = new JTextField("Number Of Rows");
        JTextField text2 = new JTextField("Number Of Columns");
        JTextField text3 = new JTextField("Number Of Mines");
        text1.setEditable(false);
        text2.setEditable(false);
        text3.setEditable(false);
        text1.setEnabled(false);
        text2.setEnabled(false);
        text3.setEnabled(false);
        this.setLayout(new GridLayout(7, 1, 0, 5));
        this.add(text1);
        this.add(this.rowT);
        this.add(text2);
        this.add(this.columnT);
        this.add(text3);
        this.add(this.mineT);
        this.add(this.submit);
        this.submit.addActionListener(this);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private boolean canParseToPositiveIntegerWithinBillion(String s) {
        boolean can = true;
        if (s.isEmpty()) {
            can = false;
        } else {
            char[] c = s.toCharArray();
            if (c[0] == '+') {
                if (c.length > 10) {
                    can = false;
                } else {
                    for (int i = 1; i < s.length() && can; i++) {
                        if (c[i] < 48 || c[i] >= 58) {
                            can = false;
                        }
                    }
                }
            } else {
                if (c.length > 9) {
                    can = false;
                } else {
                    for (int i = 0; i < s.length() && can; i++) {
                        if (c[i] < 48 || c[i] >= 58) {
                            can = false;
                        }
                    }
                }
            }
        }
        return can;
    }

    private void getData(String r, String c, String m) {
        if (!this.canParseToPositiveIntegerWithinBillion(r)) {
            JOptionPane.showMessageDialog(this,
                    "The input of rows is unacceptable.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!this.canParseToPositiveIntegerWithinBillion(c)) {
            JOptionPane.showMessageDialog(this,
                    "The input of columns is unacceptable.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!this.canParseToPositiveIntegerWithinBillion(m)) {
            JOptionPane.showMessageDialog(this,
                    "The input of mines is unacceptable.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            int row = Integer.parseInt(r);
            int column = Integer.parseInt(c);
            int mine = Integer.parseInt(m);
            if (row * column > mine) {
                this.data[0] = row;
                this.data[1] = column;
                this.data[2] = mine;
            } else {
                JOptionPane.showMessageDialog(this,
                        "The number of mines is too big.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == this.submit) {
            this.getData(this.rowT.getText(), this.columnT.getText(),
                    this.mineT.getText());
        }
    }
}
