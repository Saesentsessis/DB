package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.data.*;
import main.service.ServiceManager;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

//<Canvas fx:id="canvas" depthTest="DISABLE" height="400.0" translateZ="-1.0" width="600.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" />
public class Controller {
    Connection con;
    //@FXML private Canvas canvas;
    @FXML private AnchorPane anchorPane;
    @FXML private ListView<Client> clientListView;
    @FXML private ListView<Storage> storageListView;
    @FXML private ListView<Wood> woodListView;
    @FXML private ListView<Material> materialListView;
    @FXML private ListView<Object> lastListView;

    @FXML private Label table1, table2, table3, table4, table5;
    @FXML private Button addButton, deleteButton, updateButton, showAllOrdersBtn, showAllMaterialsBtn, showAllWoodBtn, showAllWorkersBtn;

    @FXML private Pane fieldsHolder;
    @FXML private Label label0, label1, label2, label3, label4, label5, label6, label7, label8, label9;
    @FXML private TextArea field0, field1, field2, field3, field4, field5, field6, field7, field8, field9;
    private Label[] fieldTitles = new Label[10];
    private TextArea[] textFields = new TextArea[10];

    private EntityManager em;
    private EntityTransaction et;
    private ServiceManager sm;
    private ListView<?> lastSelected;

    private int defTextWidth = 18;

    public void initialize() {
        /*try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection('',"root","onmobile");*/
        table1.setText("             Clients:");
        table2.setText("            Storage's:");
        table3.setText("              Wood:");
        table4.setText("             Material:");
        textFields[0] = field0; fieldTitles[0] = label0;
        textFields[1] = field1; fieldTitles[1] = label1;
        textFields[2] = field2; fieldTitles[2] = label2;
        textFields[3] = field3; fieldTitles[3] = label3;
        textFields[4] = field4; fieldTitles[4] = label4;
        textFields[5] = field5; fieldTitles[5] = label5;
        textFields[6] = field6; fieldTitles[6] = label6;
        textFields[7] = field7; fieldTitles[7] = label7;
        textFields[8] = field8; fieldTitles[8] = label8;
        textFields[9] = field9; fieldTitles[9] = label9;

        //for (TextField textField : textFields) textField(Color.BLACK);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MyPU");
        em = factory.createEntityManager();
        et = em.getTransaction();
        sm = new ServiceManager(em);

        addButton.setOnAction(e -> {
            try {
                Class<?> c = lastSelected.getItems().get(0).getClass();
                if (et.isActive()) et.rollback();
                et.begin();
                var q = em.createNamedQuery(c.getSimpleName()+".Add", c);
                for (int i = 0; i <= fieldTitles.length; i++) {
                    if (fieldTitles[i].getText().length() < 1) break;
                    try {
                        int in = Integer.parseInt(textFields[i].getText());
                        q.setParameter(i+1, in);
                    } catch (Exception ex) {
                        String text = textFields[i].getText();
                        var in = text.equals("false") ? 0 : text.equals("true") ? 1 : text;
                        q.setParameter(i+1, in);
                    }
                }
                q.executeUpdate();
                //em.persist(q.getSingleResult());
                et.commit();
                clientListView.setItems(FXCollections.observableArrayList(sm.getAllClients()));
                storageListView.setItems(FXCollections.observableArrayList(sm.getAllStorages()));
                woodListView.setItems(FXCollections.observableArrayList(sm.getAllWoods()));
                materialListView.setItems(FXCollections.observableArrayList(sm.getAllMaterials()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                if (et.isActive()) et.rollback();
                et.begin();
                var toDelete = lastListView.getSelectionModel().getSelectedItem();
                em.remove(toDelete);
                et.commit();
                lastListView.getItems().remove(toDelete);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        showAllOrdersBtn.setOnAction(e -> {
            lastListView.setItems(FXCollections.observableArrayList(sm.getAllOrders()));
            table5.setText("           All orders:");
            lastListView.setOnMouseClicked(e_ -> {
                Order_data selected_ = (Order_data) lastListView.getSelectionModel().getSelectedItem();
                OutOrder(selected_);
                lastSelected = lastListView;
            });
        });

        showAllWorkersBtn.setOnAction(e -> {
            lastListView.setItems(FXCollections.observableArrayList(sm.getAllWorkers()));
            table5.setText("          All workers:");
            lastListView.setOnMouseClicked(e_ -> {
                Worker selected_ = (Worker) lastListView.getSelectionModel().getSelectedItem();
                OutWorker(selected_);
                lastSelected = lastListView;
            });
        });

        clientListView.setItems(FXCollections.observableArrayList(sm.getAllClients()));
        storageListView.setItems(FXCollections.observableArrayList(sm.getAllStorages()));
        woodListView.setItems(FXCollections.observableArrayList(sm.getAllWoods()));
        materialListView.setItems(FXCollections.observableArrayList(sm.getAllMaterials()));

        clientListView.setOnMouseClicked(e -> {
            lastSelected = clientListView;
            Client selected = clientListView.getSelectionModel().getSelectedItem();
            OutClient(selected);
            table5.setText("             Orders:");
            lastListView.setItems(FXCollections.observableArrayList(sm.getAllOrders(selected)));
            lastListView.setOnMouseClicked(e_ -> {
                Order_data selected_ = (Order_data) lastListView.getSelectionModel().getSelectedItem();
                OutOrder(selected_);
                lastSelected = lastListView;
            });
            //GraphicsContext gc = canvas.getGraphicsContext2D();
            //gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
            //if (e.getPickResult().getIntersectedNode())
            //if (e.getButton() == MouseButton.PRIMARY) {
            /*double mouseY = e.getSceneY();
            double mouseX = e.getSceneX();
            if (mouseX > clientListView.getWidth()) return;
            int index = (int)(mouseY/24);
            if (index > clientListView.getItems().size()-1 || index < 0) return;
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.GREY);
            String text = ToFixedWidth(30, clientListView.getItems().get(index).toFullString());
            double fontSize = gc.getFont().getSize(); int p2d = text.split("\n").length+2;
            gc.fillRect(mouseX-2, mouseY-2-fontSize, 15*fontSize+2, fontSize*p2d+2);
            gc.strokeText(text, e.getSceneX(), e.getSceneY());
            //gc.strokeRect(300,300,200,200);
            //System.out.println(mouseY+" : "+);
            */
            //}
        });
        storageListView.setOnMouseClicked(e -> {
            lastSelected = storageListView;
            Storage selected = storageListView.getSelectionModel().getSelectedItem();
            OutStorage(selected);
            if (selected != null) {
                materialListView.setItems(FXCollections.observableArrayList(sm.getAllMaterials(selected)));
                woodListView.setItems(FXCollections.observableArrayList(sm.getAllWoods(selected)));
            }
            table5.setText("            Providers:");
            lastListView.setItems(FXCollections.observableArrayList(sm.getAllProviders(selected)));
            lastListView.setOnMouseClicked(e_ -> {
                Provider selected_ = (Provider) lastListView.getSelectionModel().getSelectedItem();
                OutProvider(selected_);
                lastSelected = lastListView;
            });
        });
        materialListView.setOnMouseClicked(e -> {
            lastSelected = materialListView;
            Material selected = materialListView.getSelectionModel().getSelectedItem();
            OutMaterial(selected);
            table5.setText("            Providers:");
            lastListView.setItems(FXCollections.observableArrayList(sm.getAllProviders(selected)));
            lastListView.setOnMouseClicked(e_ -> {
                Provider selected_ = (Provider) lastListView.getSelectionModel().getSelectedItem();
                OutProvider(selected_);
                lastSelected = lastListView;
            });
        });
        woodListView.setOnMouseClicked(e -> {
            lastSelected = woodListView;
            Wood selected = woodListView.getSelectionModel().getSelectedItem();
            OutWood(selected);
            table5.setText("            Providers:");
            lastListView.setItems(FXCollections.observableArrayList(sm.getAllProviders(selected)));
            lastListView.setOnMouseClicked(e_ -> {
                Provider selected_ = (Provider) lastListView.getSelectionModel().getSelectedItem();
                OutProvider(selected_);
                lastSelected = lastListView;
            });
        });
    }

    public void Start() {

    }

    private void OutClient(Client ref) {
        ClearFields();
        SetFieldEvents(new String[]{
                "ID:", "Surname:", "Name:", "City:", "Phone:", "E-mail:", "Orders:", "Post num:", "Was client:"
        }, ref == null ? "" : String.valueOf(ref.getId()));
        if (ref == null) return;
        textFields[1].setText(
                ToFixedWidth(defTextWidth,ref.getSurname()));
        textFields[2].setText(
                ToFixedWidth(defTextWidth,ref.getFirst_name()));
        textFields[3].setText(
                ToFixedWidth(defTextWidth,ref.getCity_name()));
        textFields[4].setText(
                ToFixedWidth(defTextWidth,ref.getPhone_number()));
        textFields[5].setText(
                ToFixedWidth(defTextWidth, ref.getEmail()));
        textFields[6].setText(String.valueOf(ref.getOrders_count()));
        textFields[7].setText(String.valueOf(ref.getPost_office_number()));
        textFields[8].setText(String.valueOf(ref.isWas_client()));
    }

    private void OutStorage(Storage ref) {
        ClearFields();
        SetFieldEvents(new String[] {"ID:", "City:", "Adress:"}, ref == null ? "" : String.valueOf(ref.getId()));
        if (ref == null) return;
        textFields[1].setText(
                ToFixedWidth(defTextWidth,ref.getCity_name()));
        textFields[2].setText(
                ToFixedWidth(defTextWidth,ref.getAdress()));
    }

    private void OutMaterial(Material ref) {
        ClearFields();
        SetFieldEvents(new String[] {"Name:", "Type:", "Weight:", "Price(kg):"}, ref == null ? "" : ref.getName());
        if (ref == null) return;
        textFields[1].setText(
                ToFixedWidth(defTextWidth, ref.getType()));
        textFields[2].setText(String.valueOf(ref.getWeight()));
        textFields[3].setText(String.valueOf(ref.getPrice_per_kg()));

    }

    private void OutWood(Wood ref) {
        ClearFields();
        SetFieldEvents(new String[] {"Name:", "Type:", "Amount:", "Price(one):"}, ref == null ? "" : ref.getName());
        if (ref == null) return;
        textFields[1].setText(
                ToFixedWidth(defTextWidth, ref.getType()));
        textFields[2].setText(String.valueOf(ref.getAmount()));
        textFields[3].setText(String.valueOf(ref.getPrice_per_one()));
    }

    private void OutProvider(Provider ref) {
        ClearFields();
        SetFieldEvents(new String[] {"ID:", "Name:"}, ref == null ? "" : String.valueOf(ref.getId()));
        if (ref == null) return;
        textFields[1].setText(
                ToFixedWidth(defTextWidth, ref.getName()));
    }

    private void OutOrder(Order_data ref) {
        ClearFields();
        SetFieldEvents(new String[] {"ID:", "Name:", "Client:", "Worker:"}, ref == null ? "" : String.valueOf(ref.getId()));
        if (ref == null) return;
        textFields[1].setText(
                ToFixedWidth(defTextWidth, ref.getName()));
        textFields[2].setText(
                ToFixedWidth(defTextWidth, ref.getClient().toString()));
        textFields[3].setText(
                ToFixedWidth(defTextWidth, ref.getWorker().toString()));
    }

    private void OutWorker(Worker ref) {
        ClearFields();
        SetFieldEvents(new String[] {"ID:", "Name:", "Surname:"}, ref == null ? "" : String.valueOf(ref.getId()));
        if (ref == null) return;
        textFields[1].setText(
                ToFixedWidth(defTextWidth, ref.getFirst_name()));
        textFields[2].setText(
                ToFixedWidth(defTextWidth, ref.getSurname()));
    }

    private void SetFieldEvents(String[] defs, String id) {
        SetFieldEvents(defs);
        textFields[0].setText(id);
        //for (int i = 0; i < defs.length; i++) {
        //    textFields[i].setOnKeyPressed(SetEvents(i));
        //}
        /*for (int i = 0; i < defs.length; i++) {
            int index = i;
            textFields[i].setOnKeyTyped(e -> {
                String text = textFields[index].getText();
                if (text.length() < defs[index].length()) {
                    textFields[index].setText(defs[index]);
                    text = textFields[index].getText();
                }

                if (!text.startsWith(defs[index])) {
                    for (int j = 0; j < text.length(); j++) {
                        if (text.substring(j, j+defs[index].length()).equals(defs[index])) { textFields[index].setText(text.substring(j)); break; }
                    }
                }
            });
        }*/
        /*textFields[index].setOnKeyReleased(e-> {
            if (defs[index] == null) return;
            if (textFields[index].getText().length() < defs[index].length()) textFields[index].setText(defs[index]);
        });
        textFields[1].setOnKeyReleased(e-> {
            if (def2 == null) return;
            if (textFields[1].getText().length() < def2.length()) textFields[1].setText(def2);
        });
        textFields[2].setOnKeyReleased(e-> {
            if (def3 == null) return;
            if (textFields[2].getText().length() < def3.length()) textFields[2].setText(def3);
        });
        textFields[3].setOnKeyReleased(e-> {
            if (def4 == null) return;
            if (textFields[3].getText().length() < def4.length()) textFields[3].setText(def4);
        });
        textFields[4].setOnKeyReleased(e-> {
            if (def5 == null) return;
            if (textFields[4].getText().length() < def5.length()) textFields[4].setText(def5);
        });
        textFields[5].setOnKeyReleased(e-> {
            if (def6 == null) return;
            if (textFields[5].getText().length() < def6.length()) textFields[5].setText(def6);
        });
        textFields[6].setOnKeyReleased(e-> {
            if (def7 == null) return;
            if (textFields[6].getText().length() < def7.length()) textFields[6].setText(def7);
        });
        textFields[7].setOnKeyReleased(e-> {
            if (def8 == null) return;
            if (textFields[7].getText().length() < def8.length()) textFields[7].setText(def8);
        });*/
    }

    private void SetFieldEvents(String[] defs) {
        for (int i = 0; i < defs.length; i++) {
            fieldTitles[i].setText(defs[i]);
            textFields[i].setOnKeyTyped(null);
        }
    }

    private void ClearFields(int startIndex) {
        for (int i = startIndex; i < textFields.length; i++) {
            int index = i;
            textFields[i].setText("");
            fieldTitles[i].setText("");
            //textFields[i].setOnKeyPressed(e -> textFields[index].setText(""));
            textFields[i].setOnKeyTyped(e -> textFields[index].setText(""));
        }
    }

    private void ClearFields() {
        ClearFields(0);
    }

    private String ToFixedWidth(int width, String text) {
        if (width > text.length()) return text;

        int start = 0, point = width;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < width; i++)
            if (text.charAt(i) == '\n') {
                start = i+1; point += i+1;
                sb.append(text, 0, start);
            }

        while (true) {
            boolean found = false;for (int i = start; i < point; i++)
                if (text.length() > i && text.charAt(i) == '\n') {
                    start = i; point += i;
                }
            for (int i = 0; i < width; i++) {
                if (text.length() <= point-i) break;

                if (text.charAt(point-i) == ' ') {
                    sb.append(text, start, point-i+1).append('\n');
                    start = point-i+1;
                    point = start+width;
                    found = true;
                    break;
                }
            }

            if (found) continue;

            if (point > text.length()) {
                sb.append(text, start, text.length());
                break;
            }

            sb.append(text, start, point).append('\n');
            start = point;
            point += width;
        }

        return sb.toString();
    }
}
