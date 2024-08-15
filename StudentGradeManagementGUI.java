import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeManagementGUI {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Student Grade Management");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
       
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font resultFont = new Font("Monospaced", Font.PLAIN, 13);

        
        JLabel label1 = new JLabel("Grade for Subject 1:");
        label1.setBounds(50, 30, 150, 20);
        label1.setFont(labelFont);
        label1.setForeground(new Color(44, 62, 80));
        frame.add(label1);

        JTextField textField1 = new JTextField();
        textField1.setBounds(200, 30, 100, 20);
        textField1.setBackground(new Color(232, 241, 242));
        frame.add(textField1);

        
        JLabel label2 = new JLabel("Grade for Subject 2:");
        label2.setBounds(50, 70, 150, 20);
        label2.setFont(labelFont);
        label2.setForeground(new Color(44, 62, 80));
        frame.add(label2);

        JTextField textField2 = new JTextField();
        textField2.setBounds(200, 70, 100, 20);
        textField2.setBackground(new Color(232, 241, 242));
        frame.add(textField2);

        
        JLabel label3 = new JLabel("Grade for Subject 3:");
        label3.setBounds(50, 110, 150, 20);
        label3.setFont(labelFont);
        label3.setForeground(new Color(44, 62, 80));
        frame.add(label3);

        JTextField textField3 = new JTextField();
        textField3.setBounds(200, 110, 100, 20);
        textField3.setBackground(new Color(232, 241, 242));
        frame.add(textField3);

        
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(150, 150, 100, 30);
        calculateButton.setBackground(new Color(52, 152, 219));
        calculateButton.setForeground(Color.WHITE);
        frame.add(calculateButton);

        
        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(50, 200, 300, 80);
        resultArea.setFont(resultFont);
        resultArea.setBackground(new Color(236, 240, 241));
        resultArea.setForeground(new Color(44, 62, 80));
        resultArea.setEditable(false);
        frame.add(resultArea);

        
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    double grade1 = Double.parseDouble(textField1.getText());
                    double grade2 = Double.parseDouble(textField2.getText());
                    double grade3 = Double.parseDouble(textField3.getText());

                    
                    double average = (grade1 + grade2 + grade3) / 3;

                    
                    char letterGrade = getLetterGrade(average);
                    double gpa = getGPA(average);

                    
                    resultArea.setText("Average Grade: " + String.format("%.2f", average) + "\n" +
                            "Letter Grade: " + letterGrade + "\n" +
                            "GPA: " + String.format("%.2f", gpa));

                } catch (NumberFormatException ex) {
                    
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for grades.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        frame.setVisible(true);
    }

    
    public static char getLetterGrade(double average) {
        if (average >= 90) {
            return 'A';
        } else if (average >= 80) {
            return 'B';
        } else if (average >= 70) {
            return 'C';
        } else if (average >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    
    public static double getGPA(double average) {
        if (average >= 90) {
            return 4.0;
        } else if (average >= 80) {
            return 3.0;
        } else if (average >= 70) {
            return 2.0;
        } else if (average >= 60) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
