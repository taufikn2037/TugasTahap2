package stokKursi;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfNamaKursi;
    @FXML
    private ComboBox<?> tfWarna;
    @FXML
    private DatePicker tfTanggal;
    @FXML
    private TextField tfHarga;
    @FXML
    private TableView<stokKursi> tvBooks;
    @FXML
    private TableColumn<stokKursi, Integer> colId;
    @FXML
    private TableColumn<stokKursi, String> colNamaKursi;
    @FXML
    private TableColumn<stokKursi, String> colWarna;
    @FXML
    private TableColumn<stokKursi, String> colTanggal;
    @FXML
    private TableColumn<stokKursi, String> colHarga;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
 
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteButton();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         showBooks();
        ArrayList <String> list = new ArrayList<String>();
        list.add("Merah");
        list.add("Biru");
        list.add("Coklat");
        list.add("Kuning");
        ObservableList items = FXCollections.observableArrayList(list);
        tfWarna.setItems(items);
    }    
    
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kursi", "root","");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    
    public ObservableList<stokKursi> getBooksList(){
        ObservableList<stokKursi> bookList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM kursi";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            stokKursi kursi;
            while(rs.next()){
                kursi = new stokKursi(rs.getInt("id"), rs.getString("nama_kursi"), rs.getString("warna"), rs.getString("tanggal"),rs.getString("harga"));
                bookList.add(kursi);
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return bookList;
    }
    
    public void showBooks(){
        ObservableList<stokKursi> list = getBooksList();
        
        colId.setCellValueFactory(new PropertyValueFactory<stokKursi, Integer>("id"));
        colNamaKursi.setCellValueFactory(new PropertyValueFactory<stokKursi, String>("nama_kursi"));
        colWarna.setCellValueFactory(new PropertyValueFactory<stokKursi, String>("warna"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<stokKursi, String>("tanggal"));
        colHarga.setCellValueFactory(new PropertyValueFactory<stokKursi, String>("harga"));
        
        tvBooks.setItems(list);
    }
    
    private void insertRecord(){
        String query = "INSERT INTO kursi VALUES ('" + tfId.getText() + "','" + tfNamaKursi.getText() + "','" + tfWarna.getValue().toString() + "','"
                + tfTanggal.getValue().toString() + "','" + tfHarga.getText() + "')";
        executeQuery(query);
        showBooks();
    }
    
    private void updateRecord(){
        String query = "UPDATE  kursi SET nama_kursi  = '" + tfNamaKursi.getText() + "', warna = '" + tfWarna.getValue().toString() + "', tanggal = '" +
                tfTanggal.getValue().toString() + "', harga = '" + tfHarga.getText() + "' WHERE id = '" + tfId.getText() + "' ";
        executeQuery(query);
        showBooks();
    }
    
    private void deleteButton(){
        String query = "DELETE FROM kursi WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showBooks();
    }
    
    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
