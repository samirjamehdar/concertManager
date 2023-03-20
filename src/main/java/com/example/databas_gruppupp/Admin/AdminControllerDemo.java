package com.example.databas_gruppupp.Admin;

import com.example.databas_gruppupp.entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AdminControllerDemo implements Initializable { // DEMO FOR CUSTOMER_CONCERT OBJECT IMPLEMENTAION

    @FXML
    private ChoiceBox choice;
    @FXML
    private ChoiceBox<LocalDate> dateChoice;
    @FXML
    private ChoiceBox<String> choiceInOut;
    @FXML
    private Button btnEditArena;
    @FXML
    private Button btnAddArena;
    @FXML
    private Button btnRemoveArena;
    @FXML
    private Button btnEditConcert;
    @FXML
    private Button btnAddConcert;
    @FXML
    private Button btnRemoveConcert;
    private Boolean arenaEdit = false;
    private Boolean concertEdit = false;
    private List<CustomerConcert> arrayList = new ArrayList<>();
    private Set<Concert> concerts = new HashSet<>();
    private List<Arena> arenas = new ArrayList<>();
    private ObservableList<CustomerConcert> oblist = FXCollections.observableArrayList(arrayList);
    private ObservableList<Concert> concertOb = FXCollections.observableArrayList(concerts);
    private ObservableList<Arena> arenaOb = FXCollections.observableArrayList(arenas);
    @FXML
    private TableView table = new TableView<>();
    @FXML
    private TableColumn<CustomerConcert, String> firstNameCol;
    @FXML
    private TableColumn<CustomerConcert, String> lastNameCol;
    @FXML
    private TableColumn<CustomerConcert, String> phoneNoCol;
    @FXML
    private TableColumn<CustomerConcert, String> streetCol;
    @FXML
    private TableColumn<CustomerConcert, String> cityCol;
    @FXML
    private TableColumn<CustomerConcert, String> postalCol;
    @FXML
    private TableColumn<CustomerConcert, String> birthdateCol;
    @FXML
    private TableColumn<CustomerConcert, String> concertDateCol;
    @FXML
    private TableColumn<CustomerConcert, String> concertArtistCol;
    @FXML
    private TableView arenaTable = new TableView<>();
    @FXML
    private TableColumn<Arena, String> arenaNameCol;
    @FXML
    private TableColumn<Arena, String> inOutCol;
    @FXML
    private TableColumn<Arena, String> arenaAddressCol;
    @FXML
    private TableColumn<Arena, String> arenaCityCol;
    @FXML
    private TableColumn<Arena, String> arenaPostalCol;
    @FXML
    private TextField arenaNameField;
    @FXML
    private TextField arenaAddressField;
    @FXML
    private TextField arenaCityField;
    @FXML
    private TextField arenaPostalCodeField;
    @FXML
    private TableView concertTable = new TableView<>();
    @FXML
    private TableColumn<Concert, String> artistNameCol;
    @FXML
    private TableColumn<Concert, Double> priceCol;
    @FXML
    private TableColumn<Concert, String> dateCol;
    @FXML
    private TableColumn<Concert, String> concertArenaNameCol;
    @FXML
    private TableColumn<Concert, String> concertArenaCityCol;
    @FXML
    private TableColumn<Concert, String> ageCol;
    @FXML
    private TextField concertNameField;
    @FXML
    private TextField concertPriceField;
    @FXML
    private DatePicker concertDatePicker;
    @FXML
    private ChoiceBox concertArenaChoice = new ChoiceBox();
    @FXML
    private TextField concertAgeRestriction;
    @FXML
    private Label concertInfoLabel;
    private Set<LocalDate> dates = new HashSet();
    private Arena selectedArena = null;
    private Concert selectedConcert = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choice.setValue("Alla Kunder");

        getCustomerData(null, null);
        getConcertData();
        getArenaData();
        concertTable.setItems(concertOb);
        arenaTable.setItems(arenaOb);

        //CUSTOMER TAB
        // CUSTOMER TABLE
        firstNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getFirst_name()));
        lastNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getLastName()));
        phoneNoCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getPhoneNumber()));
        birthdateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getBirthDate().toString().substring(0, 10)));

        streetCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getStreet().concat(" ").concat(data.getValue().getCustomer().getAddress().getHouseNumber())));
        cityCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getAreaName()));
        postalCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getPostalCode()));

        concertArtistCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getConcert().getArtistName()));
        concertDateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getConcert().getDate().toString().substring(0, 10)));

        //CUSTOMER BUTTONS
        choice.setOnAction(event -> {

            dateChoice.setValue(null);
            dateChoice.getItems().clear();
            dateChoice.getItems().add(null);
            dates.clear();
            System.out.println(choice.getValue());
            getCustomerData(choice.getValue().toString().equals("Alla Kunder") ? null : choice.getValue().toString(), null);
            concertInfoLabel.setText(null);
            for (Concert concert : concertOb) {
                if (concert.getArtistName().equals(choice.getValue())) {
                    concertInfoLabel.setText("Arena: " + concert.getArena().getName() + ", " + concert.getArena().getAddress().getStreet() + " " +
                            concert.getArena().getAddress().getHouseNumber() + ", " + concert.getArena().getAddress().getAreaName() + " " +
                            concert.getArena().getAddress().getPostalCode());
                    dates.add(concert.getDate());
                }
            }
            dateChoice.getItems().addAll(dates);
        });

        dateChoice.setOnAction(event -> {
            System.out.println(dateChoice.getValue());
            getCustomerData(choice.getValue().toString().equals("Alla Kunder") ? null : choice.getValue().toString(), dateChoice.getValue());
        });

        //ARENA TAB
        // ARENA TABLE
        arenaNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inOutCol.setCellValueFactory(new PropertyValueFactory<>("venueInOut"));
        arenaAddressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress().getStreet().concat(" ").concat(data.getValue().getAddress().getHouseNumber())));
        arenaCityCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress().getAreaName()));
        arenaPostalCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress().getPostalCode()));

        //ARENA BUTTONS

        choiceInOut.getItems().addAll("Inomhus", "Utomhus");

        btnEditArena.setOnAction(event -> {
            btnEditArena.setText(!arenaEdit ? "Spara" : "Uppdatera");
            if (arenaEdit) {
                editArenaSave();
            } else {
                editArenaLoad();
            }
            arenaEdit = !arenaEdit;
        });
        btnAddArena.setOnAction(event -> {
            addArena();
        });
        btnRemoveArena.setOnAction(event -> {
            removeArena();
        });

        // CONCERT TAB
        // CONCERT TABLE
        artistNameCol.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        concertArenaNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArena().getName()));
        concertArenaCityCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArena().getAddress().getAreaName()));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("ageRestriction"));

        //CONCERT BUTTONS
        btnEditConcert.setOnAction(event -> {
            btnEditConcert.setText(!concertEdit ? "Spara" : "Uppdatera");
            if (concertEdit) {
                editConcertSave();
            } else {
                editConcertLoad();
            }
            concertEdit = !concertEdit;
        });
        btnAddConcert.setOnAction(event -> {
            addConcert();
        });
        btnRemoveConcert.setOnAction(event -> {
            if (concertEdit)
                removeConcert();
        });
    }



    private void addArena() {
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            String addressText = arenaAddressField.getText();
            String houseNumber = addressText.substring(addressText.indexOf(" ") + 1, addressText.length());
            try {
                addressText = addressText.substring(0, addressText.indexOf(" "));
            } catch (Exception e) {
            }

            Arena arena = new Arena(arenaNameField.getText(), new Address(addressText, houseNumber, arenaPostalCodeField.getText(), arenaCityField.getText()), choiceInOut.getValue().toLowerCase()); //Arena() String name, Address address,String venueInOut

            System.out.println(arena);
            session.persist(arena);

            session.flush();
            session.close();

            arenaNameField.setText(null);
            arenaAddressField.setText(null);
            arenaCityField.setText(null);
            arenaPostalCodeField.setText(null);

            getArenaData();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Try again");
        }
    }

    private void removeArena() {
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.remove(arenaTable.getSelectionModel().getSelectedItem());

            session.flush();
            session.close();

            getArenaData();
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Try again");
        }
    }

    private void editArenaLoad() {
        selectedArena = (Arena) arenaTable.getSelectionModel().getSelectedItem();
        arenaNameField.setText(selectedArena.getName());
        arenaAddressField.setText(selectedArena.getAddress().getStreet().concat(" ").concat(selectedArena.getAddress().getHouseNumber()));
        arenaCityField.setText(selectedArena.getAddress().getAreaName());
        arenaPostalCodeField.setText(selectedArena.getAddress().getPostalCode());
    }

    private void editArenaSave() {
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            String address = arenaAddressField.getText();
            String[] split = address.split("(?<=[' '])(?=[0-9])");
            split[0] = split[0].substring(0, split[0].length() - 1);
            selectedArena.setName(arenaNameField.getText());
            selectedArena.setVenueInOut(choiceInOut.getValue());
            selectedArena.getAddress().setStreet(split[0]);
            selectedArena.getAddress().setHouseNumber(split[1]);
            selectedArena.getAddress().setAreaName(arenaCityField.getText());
            selectedArena.getAddress().setPostalCode(arenaPostalCodeField.getText());

            session.update(selectedArena);

            session.flush();
            session.close();

            arenaNameField.setText(null);
            arenaAddressField.setText(null);
            arenaCityField.setText(null);
            arenaPostalCodeField.setText(null);

            getArenaData();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Try again");
        }

    }

    private void editConcertLoad() {
        selectedConcert = (Concert) concertTable.getSelectionModel().getSelectedItem();

        concertNameField.setText(selectedConcert.getArtistName());
        concertPriceField.setText(Double.toString(selectedConcert.getTicketPrice()));
        concertDatePicker.setValue(selectedConcert.getDate());

        concertArenaChoice.setValue(selectedConcert.getArena().toString());
        concertAgeRestriction.setText(selectedConcert.getAgeRestriction().toString());
    }

    private void editConcertSave() {
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            selectedConcert.setArtistName(concertNameField.getText());
            selectedConcert.setTicketPrice(Double.parseDouble(concertPriceField.getText()));
            selectedConcert.setDate(concertDatePicker.getValue());
            selectedConcert.setArena((Arena) concertArenaChoice.getValue());
            selectedConcert.setAgeRestriction(Integer.valueOf(concertAgeRestriction.getText()));

            session.update(selectedConcert);

            session.flush();
            session.close();

            concertNameField.setText(null);
            concertPriceField.setText(null);
            concertDatePicker.setValue(null);
            concertAgeRestriction.setText(null);

            getConcertData();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Try again");
        }
    }

    private void addConcert() {
        Session session;
        Arena persist = null;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            persist = null;

            for (Arena arena : arenaOb) {
                if (arena.getArenaId() == ((Arena) concertArenaChoice.getValue()).getArenaId()) {
                    persist = arena;
                    break;
                }
            }

            if (persist != null) {
                Concert concert = new Concert(concertNameField.getText(), concertDatePicker.getValue(), Double.parseDouble(concertPriceField.getText()), persist, Integer.valueOf(concertAgeRestriction.getText())); // String artistName, LocalDate date, double ticketPrice, Arena arena, Integer ageRestriction
                session.merge(concert);
            }

            session.flush();
            session.close();

            concertNameField.setText(null);
            concertDatePicker.setValue(null);
            concertPriceField.setText(null);
            concertAgeRestriction.setText(null);

            getConcertData();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Try again");
        }
    }

    private void removeConcert() {
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.remove(concertTable.getSelectionModel().getSelectedItem());

            session.flush();
            session.close();

            getConcertData();
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Try again");
        }
    }

    private void getCustomerData(String artistName, LocalDate date) {
        Query query = null;
        List<Object> result = null;
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            if (date != null) {
                result = session.createQuery("Select cc from CustomerConcert cc where cc.concert.artistName = :input AND cc.concert.date = :date", Object.class)
                        .setParameter("input", artistName)
                        .setParameter("date", date)
                        .getResultList();

            } else if (artistName != null) {
                query = session.createQuery("Select cc from CustomerConcert cc where cc.concert.artistName = :input", CustomerConcert.class);
                query.setParameter("input", artistName);
                result = query.getResultList();

            } else {
                query = session.createQuery("From CustomerConcert");
                result = query.getResultList();
            }

            if (result == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Finns inga kunder");
                alert.show();
            } else {
                oblist.clear();
                for (Object object : result) {
                    if (object.getClass().equals(CustomerConcert.class)) {
                        oblist.add((CustomerConcert) object);
                    }
                }
            }
            session.flush();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.setItems(oblist);
    }

    private void getConcertData() {
        String hql = "FROM Concert";
        Set set = new HashSet<String>();
        Query query;
        Object[] result;
        Session session;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            query = session.createQuery(hql);
            result = query.stream().toArray();

            if (result == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Finns inga konserter");
                alert.show();
            } else {
                concerts.clear();
                choice.getItems().add("Alla Kunder");
                for (Object object : result) {
                    if (object.getClass().equals(Concert.class)) {
                        concerts.add(((Concert) object));
                        set.add(((Concert) object).getArtistName());
                    }
                }
            }
            session.flush();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        choice.getItems().addAll(set);
        concertOb.clear();
        concertOb.setAll(concerts);
        concertTable.setItems(concertOb);
    }

    public void getArenaData() {
        Session session;
        List<Object> result;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            result = session.createQuery("FROM Arena", Object.class)
                    .getResultList();

            if (result == null) {
                System.out.println(session.getTransaction().toString());

            } else {
                arenas.clear();
                for (Object object : result) {
                    if (object.getClass().equals(Arena.class)) {
                        arenas.add((Arena) object);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        arenaOb.setAll(arenas);
        arenaTable.setItems(arenaOb);
        concertArenaChoice.setItems(arenaOb);
    }
}