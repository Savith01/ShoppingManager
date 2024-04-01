import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;



public class ShoppingCart {
    private  String productID;
    private String productDetails;
    private int quantity;
    private double price;
    private double pricePerProduct;
    private int newAvailableItem;
    private String itemCategory;
    JFrame shoppingFrame;
    JTable cartTable;
    DefaultTableModel tableModel;
    JScrollPane tableScrollPane;
    JPanel finalPricePanel;

    public ShoppingCart(String productID,String productDetails, double pricePerProduct, int quantity, String itemCategory) {
        this.productID = productID;
        this.productDetails = productDetails;
        this.pricePerProduct = pricePerProduct;
        this.quantity = quantity;
        this.itemCategory = itemCategory;
    }

    public String getProductID() {
        return productID;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(double pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public int getNewAvailableItem() {
        return newAvailableItem;
    }

    public void setNewAvailableItem(int newAvailableItem) {
        this.newAvailableItem = newAvailableItem;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void addQuantity(){
        quantity++;
    }

    public ShoppingCart(){
        shoppingFrame = new JFrame("Shopping Cart");
        shoppingFrame.setLayout(new BorderLayout());
        shoppingFrame.setSize(600,400);
        shoppingFrame.setResizable(false);
        shoppingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingFrame.setLocationRelativeTo(null);
        shoppingFrame.setVisible(true);

        finalPricePanel = new JPanel(new GridLayout(0,1));


        String[] columnName = {"Product Details", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columnName,0);
        cartTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(cartTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20,30,0,30));
        shoppingFrame.add(tableScrollPane, BorderLayout.CENTER);

        cartTable.setRowHeight(40);
        ArrayList<ShoppingCart> shoppedProducts = new ArrayList<ShoppingCart>();
        ProductPage productPage = new ProductPage(false);
        shoppedProducts = productPage.getCart();
        populateTable(shoppedProducts);

        double totalPrice = 0;
        double newUserDiscount = 0;
        double categoryDiscount = 0;
        double finalPrice = 0;
        for (ShoppingCart cart: shoppedProducts){
            totalPrice = totalPrice + ( cart.getPricePerProduct() * cart.getQuantity());
        }

        finalPricePanel.setPreferredSize(new Dimension(600,100));
        finalPricePanel.add(Box.createVerticalStrut(30));
        finalPricePanel.setBorder(BorderFactory.createEmptyBorder(0,30,10,0));
        finalPricePanel.add(new JLabel("Total Price:                            " + "\u00A3 " + totalPrice));

        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        GUIHome login = new GUIHome();
        String username = login.getNowLogged();
        for (int i = 0; i < manager.getUserInfo().size(); i++){
            int count = manager.getUserInfo().get(i).getLoginCount();
            if (count == 1){
                newUserDiscount = totalPrice * 0.1;
                finalPricePanel.add(new JLabel("First Purchase Discount:        " + "\u00A3 " + newUserDiscount));
            }
        }
        int ElecCount = 0;
        int ClothCount = 0;
        for (ShoppingCart cart : shoppedProducts){
            if (cart.getItemCategory().equals("Electronics")){
                ElecCount += cart.getQuantity();
            } else {
                ClothCount += cart.getQuantity();
            }
        }
        if (ElecCount >= 3){
            categoryDiscount = totalPrice * 0.2;
            finalPricePanel.add(new JLabel("Same Category Discount:            " + "\u00A3 " + categoryDiscount));
        } else if (ClothCount >= 3){
            categoryDiscount = totalPrice * 0.2;
            finalPricePanel.add(new JLabel("Same Category Discount:            " + "\u00A3 " + categoryDiscount));
        }
        finalPrice = totalPrice - newUserDiscount - categoryDiscount;
        finalPricePanel.add(new JLabel("Final Price:                        " + "\u00A3 " + finalPrice));
        shoppingFrame.add(finalPricePanel,BorderLayout.SOUTH);
    }

    public void populateTable(ArrayList<ShoppingCart> cartList){
        tableModel.setRowCount(0);
        for (ShoppingCart shoppingCart : cartList){
            ShoppingCart cart = (ShoppingCart) shoppingCart;
            Object [] rowData = {cart.getProductID() + " " + cart.getProductDetails()
            ,cart.getQuantity(),cart.getPricePerProduct() * cart.getQuantity()};
            tableModel.addRow(rowData);
        }
    }

}
