import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{
    public static ArrayList<Product> listOfProducts = new ArrayList<>(50);
    public static ArrayList<User> userInfo = new ArrayList<>();
    public ArrayList<User> getUserInfo(){ return userInfo;}

    public ArrayList<Product> getListOfProducts() {
        return listOfProducts;
    }

    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.loadProducts("SavedProducts.dat");
        manager.loadUserDetails("userDetails.dat");
        boolean endOption = true;
        while(endOption){
            System.out.println("-------------------------------------------");
            System.out.println("| Welcome to Westminster Shopping Manager |");
            System.out.println("-------------------------------------------");
            System.out.println("\n Choose an option:");
            System.out.println("1) Add a new product.");
            System.out.println("2) Delete a product");
            System.out.println("3) Print the list of the products");
            System.out.println("4) Save in a file");
            System.out.println("5) Register a user");
            System.out.println("6) Exit (Open GUI)");
            System.out.print("\nSelect an option: ");
            try {
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();

                switch (option) {
                    case 1:
                        manager.addProduct();
                        break;
                    case 2:
                        manager.deleteProduct();
                        break;
                    case 3:
                        manager.printProducts();
                        break;
                    case 4:
                        manager.saveProducts("SavedProducts.dat");
                        break;
                    case 5:
                        manager.userRegistration();
                        break;
                    case 6:
                        endOption = false;
                        System.out.println("Loading GUI...");
                        GUIHome frame = new GUIHome(userInfo,true);
                        break;
                    default:
                        System.out.println("Invalid Input, Try again");
                }
            }catch (Exception e){
                System.out.println("Invalid input");
            }
        }
    }
    @Override
    public void addProduct() {
        System.out.println("|-----Add Item To The List-----|");
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter the product ID: ");
        String pId = input.next();
        System.out.print("Enter the product name: ");
        String pName = input.next();
        System.out.print("Enter the number of items available: ");
        int numberOfItems = input.nextInt();
        System.out.print("Enter the price of the product: ");
        double pPrice = input.nextDouble();
        System.out.println("is the product an electronic product or a clothing product?");
        System.out.println("Press 'e' for Electronic Product");
        System.out.println("Press 'c' for Clothing Product");
        System.out.print("\nChoose an option: ");
        String productType = input.next();
        String lowercasePT = productType.toLowerCase();
        if (lowercasePT.equals("e")){
            System.out.print("Enter the brand of the product: ");
            String pBrand = input.next();
            System.out.print("Enter the warranty period: ");
            String pWarranty = input.next();
            Product newProduct = new Electronics(pId,pName,numberOfItems,pPrice,pBrand,pWarranty);
            listOfProducts.add(newProduct);
        } else if (lowercasePT.equals("c")) {
            List<String> validSizes = Arrays.asList("small", "medium", "large", "extra large");
            System.out.print("Enter the size of the clothing (Small,Medium, Large, Extra large): ");
            Scanner input2 = new Scanner(System.in);
            String clothingSize;
            while (true) {
                clothingSize = input2.nextLine().trim();
                String loweredSize = clothingSize.toLowerCase();
                if(validSizes.contains(loweredSize)){;
                break;
                } else{
                    System.out.print("Enter a valid size for the clothing (Small,Medium, Large, Extra large): ");
                }
            }
            System.out.println("Enter the color of the clothing");
            String clothingClr = input.next();
            Product newProduct = new Clothing(pId,pName,numberOfItems,pPrice,clothingSize,clothingClr);
            listOfProducts.add(newProduct);
        } else {
            System.out.println("Enter a valid input");
        }

    }

    @Override
    public void deleteProduct() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the product ID that needs to be removed");
        String RemoveId = input.next();
        for(int i = 0; i < listOfProducts.size(); i++){
            Product removeP = listOfProducts.get(i);
            if(removeP.getProductId().equals(RemoveId)){
                listOfProducts.remove(i);
                System.out.println("Product Removed");
                return;
            }
        }
        System.out.println("Product ID = " +RemoveId+ " does not exist");

    }

    @Override
    public void printProducts() {
        for (int i = 0; i < listOfProducts.size();i++){
            Product value = listOfProducts.get(i);
            System.out.println(value);
        }

    }

    @Override
    public void saveProducts(String fileName) {
        try (ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream(fileName))) {
            savedFile.writeObject(listOfProducts);
            System.out.println("Product Saved Successfully");
        } catch (IOException e){
            System.err.println("Saving failed!" + e.getMessage());
        }
    }

    @Override
    public void loadProducts(String fileName) {
        try(ObjectInputStream loadFile = new ObjectInputStream(new FileInputStream(fileName))){
            listOfProducts = (ArrayList<Product>) loadFile.readObject();
        }catch (IOException | ClassNotFoundException e){
                System.err.println("Loading Failed!");
        }
    }

    public void userRegistration(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Your Username: ");
        String username = input.next();
        while(true) {
            Scanner input1 = new Scanner(System.in);
            System.out.print("Enter Your Password: ");
            String password = input1.next();
            System.out.print("Enter the password again to confirm: ");
            String confirmPass = input.next();
            if (password.equals(confirmPass)) {
                User newUser = new User(username, confirmPass,0);
                userInfo.add(newUser);
                saveUserDetails("userDetails.dat");
                break;
            } else {
                System.err.println("Password does not match!");
            }
        }
    }
    public void saveUserDetails(String fileName){
        try (ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream(fileName))) {
            savedFile.writeObject(userInfo);
            System.out.println("Registration Successfully");
        } catch (IOException e){
            System.err.println("Registration failed!" + e.getMessage());
        }
    }
    public void loadUserDetails(String fileName){
        try(ObjectInputStream loadFile = new ObjectInputStream(new FileInputStream(fileName))){
            userInfo = (ArrayList<User>) loadFile.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Loading Failed!" + e.getMessage());
        }
    }
}
