import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class InventoryManagementApp extends JFrame {

    private Map<String, Product> inventory = new HashMap<>();
    private JTextField nameField, quantityField, priceField;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public InventoryManagementApp() {
        setTitle("Inventory Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        getContentPane().setBackground(new Color(248, 249, 250));

        
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        loginPanel.setBackground(new Color(52, 152, 219));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.setBackground(new Color(39, 174, 96));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel(""));
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.NORTH);

        
        JPanel managementPanel = new JPanel();
        managementPanel.setLayout(new GridLayout(5, 2, 10, 10));
        managementPanel.setBackground(new Color(230, 230, 250));
        managementPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        managementPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        managementPanel.add(nameField);

        managementPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        managementPanel.add(quantityField);

        managementPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        managementPanel.add(priceField);

        JButton addButton = new JButton("Add Product");
        addButton.setBackground(new Color(41, 128, 185));
        addButton.setForeground(Color.WHITE);
        JButton editButton = new JButton("Edit Product");
        editButton.setBackground(new Color(241, 196, 15));
        editButton.setForeground(Color.BLACK);
        JButton deleteButton = new JButton("Delete Product");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        JButton reportButton = new JButton("Generate Report");
        reportButton.setBackground(new Color(46, 204, 113));
        reportButton.setForeground(Color.WHITE);

        managementPanel.add(addButton);
        managementPanel.add(editButton);
        managementPanel.add(deleteButton);
        managementPanel.add(reportButton);

        add(managementPanel, BorderLayout.WEST);

        
        tableModel = new DefaultTableModel(new Object[]{"Name", "Quantity", "Price"}, 0);
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        productTable.setRowHeight(25);
        productTable.setGridColor(new Color(189, 195, 199));
        productTable.getTableHeader().setBackground(new Color(52, 73, 94));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBackground(Color.WHITE);
        add(tableScrollPane, BorderLayout.CENTER);

        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    loginPanel.setVisible(false);
                    managementPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(InventoryManagementApp.this, "Invalid login credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int quantity;
                double price;
                try {
                    quantity = Integer.parseInt(quantityField.getText());
                    price = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InventoryManagementApp.this, "Invalid quantity or price.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Product product = new Product(name, quantity, price);
                inventory.put(name, product);
                updateTable();
                clearFields();
            }
        });

        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Product product = inventory.get(name);
                if (product != null) {
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        double price = Double.parseDouble(priceField.getText());
                        product.setQuantity(quantity);
                        product.setPrice(price);
                        updateTable();
                        clearFields();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(InventoryManagementApp.this, "Invalid quantity or price.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(InventoryManagementApp.this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (inventory.remove(name) != null) {
                    updateTable();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(InventoryManagementApp.this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder report = new StringBuilder("Low Stock Report:\n");
                for (Product product : inventory.values()) {
                    if (product.getQuantity() < 5) {
                        report.append(product).append("\n");
                    }
                }
                JOptionPane.showMessageDialog(InventoryManagementApp.this, report.toString());
            }
        });

        managementPanel.setVisible(false);
        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Product product : inventory.values()) {
            tableModel.addRow(new Object[]{product.getName(), product.getQuantity(), product.getPrice()});
        }
    }

    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementApp());
    }
}

class Product {
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Quantity: %d, Price: %.2f", name, quantity, price);
    }
}
