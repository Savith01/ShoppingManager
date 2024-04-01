import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ProductPage {
    JFrame frame;
    JTable tableOfProducts;
    DefaultTableModel tableModel;
    JPanel categoryPanel;
    JPanel productDetailPanel;
    JPanel westPanel;
    JPanel eastPanel;
    JComboBox<String> filterProducts;

    JButton shoppingCartBtn;
    JPanel addToCartPanel;
    JButton addToCartBtn;


    public static void main(String[] args) {
        ArrayList<Product> productList = new ArrayList<Product>();

        ProductPage productPage = new ProductPage(true);

    }

    JScrollPane tablePane;

    public ProductPage(boolean showGUI) {
        frame = new JFrame("Westminster Shopping Manager - Products");
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        if(showGUI){
            showGUI();
        }

        DefaultTableCellRenderer cellColorChange = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int noOfItems = (int) tableModel.getValueAt(row,3);
                if (noOfItems < 3){
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(table.getBackground());
                }
                return c;
            }
        };



        String[] columnsNames = {"Product ID", "Product Name", "Product Price", "No of Available Items", "Additional Info", "Category"};

        tableModel = new DefaultTableModel(columnsNames, 0);
        tableOfProducts = new JTable(tableModel);
        tablePane = new JScrollPane(tableOfProducts);

        String options[] = {"All", "Electronics", "Clothing"};
        filterProducts = new JComboBox<>(options);
        shoppingCartBtn = new JButton("Shopping Cart");
        shoppingCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCart();
            }
        });
        addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });


        tableOfProducts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    int selectedRow = tableOfProducts.getSelectedRow();
                    if (selectedRow >= 0 ){
                        productDisplay(selectedRow);
                    }
                }
            }
        });




        tablePane.setBounds(50, 150, 700, 400);

        frame.add(tablePane, BorderLayout.CENTER);

        categoryPanel = new JPanel();
        categoryPanel.setLayout(null);
        categoryPanel.setPreferredSize(new Dimension(800,100));
        frame.add(categoryPanel, BorderLayout.NORTH);

        categoryPanel.add(filterProducts);
        filterProducts.setBounds(320,20,100,20);
        filterProducts.addActionListener(e -> ProductFilter());


        categoryPanel.add(shoppingCartBtn);
        shoppingCartBtn.setBounds(600,20,120,20);


        productDetailPanel = new JPanel(new GridLayout(0,1));
        productDetailPanel.setPreferredSize(new Dimension(800,200));
        productDetailPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));
        frame.add(productDetailPanel, BorderLayout.SOUTH);

        addToCartPanel = new JPanel();
        addToCartPanel.setLayout(null);
        addToCartPanel.setPreferredSize(new Dimension(800,50));
        addToCartBtn.setBounds(280,0,100,20);



        westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(30,600));
        frame.add(westPanel, BorderLayout.WEST);

        eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(30,600));
        frame.add(eastPanel, BorderLayout.EAST);



        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        ArrayList<Product> newList = manager.getListOfProducts();

        populateTable(newList);

        tableOfProducts.setDefaultRenderer(Object.class, cellColorChange);
    }
    public void productDisplay (int selectRow) {
        String productId = (String) tableModel.getValueAt(selectRow, 0);
        String productName = (String) tableModel.getValueAt(selectRow, 1);
        double productPrice = (double) tableModel.getValueAt(selectRow, 2);
        int noOfItems = (int) tableModel.getValueAt(selectRow,3);
        String additionalInfo = (String) tableModel.getValueAt(selectRow,4);

        productDetailPanel.removeAll();
        productDetailPanel.add(new JLabel("Product ID = " + productId));
        productDetailPanel.add(new JLabel("Product Name = " + productName));
        productDetailPanel.add(new JLabel("Product Price = " + productPrice));
        productDetailPanel.add(new JLabel("No of Items Available = " + noOfItems));
        productDetailPanel.add(new JLabel("Additional Information = " + additionalInfo));
        addToCartPanel.add(addToCartBtn);
        productDetailPanel.add(addToCartPanel);
        productDetailPanel.add(Box.createVerticalStrut(10));

        productDetailPanel.revalidate();
        productDetailPanel.repaint();
    }
    private void ProductFilter() {
        String selectedFilter = (String) filterProducts.getSelectedItem();
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        ArrayList<Product> productList = manager.getListOfProducts();

        ArrayList<Product> filteredList = new ArrayList<>();

        if ("Electronics".equals(selectedFilter)) {
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    filteredList.add(product);
                }
            }
        } else if ("Clothing".equals(selectedFilter)) {
            for (Product product : productList) {
                if (product instanceof Clothing) {
                    filteredList.add(product);
                }
            }
        } else {
            // If "All" is selected, show all products
            filteredList.addAll(productList);
        }

        populateTable(filteredList);
    }
    public void openShoppingCart(){
        ShoppingCart newCart = new ShoppingCart();

    }
    public static ArrayList<ShoppingCart> cart = new ArrayList<ShoppingCart>();
    public ArrayList<ShoppingCart> getCart(){
        return cart;
    }
    public void showGUI(){
        frame.setVisible(true);
    }

    public void addToCart(){

        int selectedRow = tableOfProducts.getSelectedRow();
        if (selectedRow > -1) {
            String productId = (String) tableModel.getValueAt(selectedRow,0);
             boolean found = false;
             for (ShoppingCart item : cart){
                 if(item.getProductID().equals(productId)){
                     item.addQuantity();
                     found = true;
                     break;
                 }
             }
            int NewAvailableItems = (int) tableModel.getValueAt(selectedRow, 3);;
            if(!found) {
                String productDetails = (String) tableModel.getValueAt(selectedRow, 1)
                        + " " + (String) tableModel.getValueAt(selectedRow, 4);
                double pricePerItem = (double) tableModel.getValueAt(selectedRow, 2);
                String category = (String) tableModel.getValueAt(selectedRow,5);


                ShoppingCart Item = new ShoppingCart(productId, productDetails, pricePerItem, 1,category);
                cart.add(Item);
            }
            if (NewAvailableItems > 0){
                tableModel.setValueAt(NewAvailableItems - 1, selectedRow,3);
            }
        }
    }


    public void populateTable(ArrayList<Product> productList) {
        // Clear existing data
        tableModel.setRowCount(0);

        // Populate the table with data from the ArrayList
        for (Product product : productList) {
            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                Object[] rowData = {electronics.getProductId(), electronics.getProductName(),
                        electronics.getPrice(), electronics.getNoOfItemsAvailable(), electronics.getBrand() +" "+electronics.getWarrantyPeriod(), "Electronics"};
                tableModel.addRow(rowData);
            } else {
                Clothing clothing = (Clothing) product;
                Object[] rowData = {clothing.getProductId(), clothing.getProductName(),
                        clothing.getPrice(), clothing.getNoOfItemsAvailable(), clothing.getSize() +" "+clothing.getColor(), "Clothing"};
                tableModel.addRow(rowData);
            }
        }
    }
}

