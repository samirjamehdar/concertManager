package com.example.databas_gruppupp.FXLoginTest;

import com.example.databas_gruppupp.entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.*;

public class LoggedInController implements Initializable {

    @FXML
    private TableView tableView = new TableView<>();
    private ObservableList<Concert> concertObList;
    @FXML
    private TableColumn<Concert, String> concertCol;
    @FXML
    private TableColumn<Concert, String> arenaCol;
    @FXML
    private TableColumn<Concert, String> priceCol;
    @FXML
    private TableColumn<Concert, String> addressCol;
    @FXML
    private Button buttonLogout;
    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelAddress;
    @FXML
    private Label concertPrice;
    @FXML
    private ComboBox<String> consertChoiseBox;
    @FXML
    private Label labelArena;
    @FXML
    private Label labelBigLabel;
    private Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/fxml/login-view.fxml", "Inloggning", null);
            }
        });
        updateListOfConcerts();
    }

    public void setUserInfo(Customer customer) {
        this.customer = customer;
        refreshTable();
        labelBigLabel.setText("Välkommen " + customer.getFirst_name() + "!");
    }

    public void refreshTable() {
        concertCol.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        arenaCol.setCellValueFactory(celldata -> {
            Concert concert = celldata.getValue();
            Arena arena = concert.getArena();
            return new SimpleStringProperty(arena.getName());
        });

        addressCol.setCellValueFactory(celldata -> {
            Concert concert = celldata.getValue();
            Address address = concert.getArena().getAddress();
            return new SimpleStringProperty(address.getAreaName() + " " + address.getHouseNumber() + ", " + address.getStreet() + ", " + address.getPostalCode());
        });

        priceCol.setCellValueFactory(celldata -> {
            Concert concert = celldata.getValue();
            return new SimpleStringProperty(String.valueOf(concert.getTicketPrice()));
        });
        concertObList = FXCollections.observableArrayList(customer.getConcerts());
        tableView.setItems(concertObList);
        updatePrice();
    }

    private void updatePrice() {
        double totalPrice = 0;
        List<Concert> result;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("select concert from CustomerConcert where customer.customerId = :id");
            result = query.setParameter("id", customer.getCustomerId()).list();
            for (Concert c : result) {
                totalPrice += c.getTicketPrice();
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        labelWelcome.setText("Dina bokningar " + "\t" + "Totalpris: " + totalPrice);
    }

    public void updateListOfConcerts() {
        String sql = "SELECT artistName FROM Concert order by artistName";
        List<String> result = null;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery(sql);
            result = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String s : result) {
            consertChoiseBox.getItems().add(s);
        }
    }

    @FXML
    public void select(ActionEvent event) {
        String concertChoice = consertChoiseBox.getSelectionModel().getSelectedItem().toString();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT arena FROM Concert WHERE artistName = '" + concertChoice + "'");
        Arena arena = (Arena) query.getSingleResult();
        labelArena.setText(arena.getName());

        query = session.createQuery("SELECT address FROM Arena WHERE arenaId = '" + arena.getArenaId() + "'");
        Address address = (Address) query.getSingleResult();
        labelAddress.setText(address.getStreet() + ", " + address.getAreaName());

        query = session.createQuery("SELECT ticketPrice FROM Concert WHERE artistName = '" + concertChoice + "'");
        String price = query.getSingleResult().toString() + " kr";

        query = session.createQuery("SELECT date FROM Concert WHERE artistName = '" + concertChoice + "'");
        String date = query.getSingleResult().toString();
        concertPrice.setText(date + ": " + price);
        session.getTransaction().commit();
        session.close();
    }

    @FXML
    public void bookConcert(ActionEvent event) {
        String concertChoice = consertChoiseBox.getSelectionModel().getSelectedItem().toString();
        Concert concert;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query;
        String sql1 = "SELECT concertId FROM Concert WHERE artistName = '" + concertChoice + "'";
        query = session.createQuery(sql1);
        int test = (int) query.getSingleResult();
        concert = session.get(Concert.class, test);
        boolean duplicates = false;

        for (Concert i : customer.getConcerts()) {
            if (i.getArtistName().equals(concert.getArtistName())) {
                duplicates = true;
            }
        }
        if (duplicates == false) {
            customer.addConcert(concert);
            session.update(customer);
        }
        session.getTransaction().commit();
        session.close();
        refreshTable();
    }

    @FXML
    public void deleteConcert(ActionEvent event) {
        Concert selectedConcert = (Concert) tableView.getSelectionModel().getSelectedItem();
        if (selectedConcert != null) {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<Concert> concertList = this.customer.getConcerts();
            concertList.removeIf(i -> i == selectedConcert);
            customer.setConcerts(concertList);
            session.update(customer);
            session.getTransaction().commit();
            session.close();
            refreshTable();
        }
    }
}