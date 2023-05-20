package rhettdelfierro.c195.helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class Parsers {
    /**
     * Helper method to change scenes
     *
     * @param event the action event
     * @param viewName the name of the view to change to (add-part, modify-part, add-product, modify-product)
     * @throws IOException if getResource() fails to find the .fxml file
     */
    public static void changeScene(ActionEvent event, String viewName) throws IOException {
        int height = 530;
        int width = 1000;
        if (Objects.equals(viewName, "main")) {
            height = 530;
            width = 1000;
        }
        else if (Objects.equals(viewName, "modify-part") || Objects.equals(viewName, "add-part")) {
            height = 530;
            width = 500;
        } else if (Objects.equals(viewName, "modify-product") || Objects.equals(viewName, "add-product")) {
            height = 600;
            width = 1000;
        }

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Helpers.class.getResource("/rhettdelfierro/c482/" + viewName + ".fxml"));
        stage.setScene(new Scene(scene, width, height));
        stage.show();
    }

    /**
     * Helper method to search for parts by name or id.
     *
     * @param searchText the text to search for
     * @return the list of parts
     */
    public static ObservableList<Part> searchParts(String searchText) {
        ObservableList<Part> parts = Inventory.lookupPart(searchText);
        // defining out the error here by using regex to check for int:
        if (checkValidInt(searchText)) {
            int partId = Integer.parseInt(searchText);
            Part part = Inventory.lookupPart(partId);
            // will only show if there's a matching part name or a matching part id. Not both.
            if (part != null && parts.size() == 0) {
                parts.add(part);
            }
        }
        return parts;
    }

    /**
     * Helper method to search for products by name or id.
     *
     * @param searchText the text to search for
     * @return the list of products
     * RUNTIME ERROR: parseInt() would throw an IOException here, but we guard against that by using the checkValidInt
     *                helper functions.
     */
    public static ObservableList<Product> searchProducts(String searchText) {
        ObservableList<Product> products = Inventory.lookupProduct(searchText);
        // defining out the error here by using regex to check for int:
        if (checkValidInt(searchText)) {
            int productId = Integer.parseInt(searchText);
            Product product = Inventory.lookupProduct(productId);
            // will only show if there's a matching product name or a matching product id. Not both.
            if (product != null && products.size() == 0) {
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Helper to parse strings for valid integers. Often used before Integer.parseInt() and other casting.
     * @param str the string to check.
     * @return boolean whether the string is a valid integer.
     */
    public static boolean checkValidInt(String str){
        return str.matches("^-?\\d+$");
    }

    /**
     * Helper to show error dialogs.
     * @param message the error message to display.
     */
    public static void showErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Program error.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Helper to show warning dialogs. This is really not used in the app as most warnings are errors.
     * @param message the warning message to display.
     */
    public static void showWarningDialog(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Continue?");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Helper to parse strings for valid doubles. Often used before to check if prices were correctly entered.
     * @param str string to parse
     * @return boolean whether the string is a valid double.
     */
    public static boolean checkValidDouble(String str){
        return str.matches("^[-+]?\\d*\\.?\\d+$");
    }

    /**
     * Helper to find the given part by id and return its index from the store.
     * @param id the id of the part to find.
     * @return index of the part in the store.
     */
    public static int findIndexForPart(int id) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Helper to find the given product by id and return its index from the store.
     * @param id the id of the product to find.
     * @return index of the product in the store.
     * FUTURE ENHANCEMENT: Could have overloaded with parts.
     */
    public static int findIndexForProduct(int id) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This will generate a new part id based on the next available integer for partId.
     * @return the new part id
     */
    public static int getAutoGeneratedPartId() {
        int id = 1;
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> sortedParts = allParts.sorted(Comparator.comparingInt(Part::getId));
        for (Part part : sortedParts) {
            if (part.getId() == id) {
                id++;
            } else if (part.getId() > id) {
                break;
            }
        }
        return id;
    }

    /**
     * This will generate a new product id based on the next available integer for productId.
     * @return the new product id
     */
    public static int getAutoGeneratedProductId() {
        int id = 1;
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> sortedProducts = allProducts.sorted(Comparator.comparingInt(Product::getId));
        for (Product product : sortedProducts) {
            if (product.getId() == id) {
                id++;
            } else if (product.getId() > id) {
                break;
            }
        }
        return id;
    }
}
