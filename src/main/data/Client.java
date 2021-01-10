package main.data;

import javafx.beans.DefaultProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NamedQueries({
        @NamedQuery(name = "Client.FindAll", query = "SELECT c from Client c")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Client.Add", query = "INSERT INTO client (id, surname, first_name, city_name, phone_number, email, orders_count, post_office_number, was_client) VALUES (?,?,?,?,?,?,?,?,?)")
})
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Basic
    private String surname;
    @Basic
    private String first_name;
    @Basic
    private String city_name;
    @Basic
    private String phone_number;
    @Basic
    private String email;
    @Basic
    private short orders_count;
    @Basic
    private boolean was_client = false;
    @Basic
    private int post_office_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getOrders_count() {
        return orders_count;
    }

    public void setOrders_count(short orders_count) {
        this.orders_count = orders_count;
    }

    public boolean isWas_client() {
        return was_client;
    }

    public void setWas_client(boolean was_client) {
        this.was_client = was_client;
    }

    public int getPost_office_number() {
        return post_office_number;
    }

    public void setPost_office_number(int post_office_number) {
        this.post_office_number = post_office_number;
    }

    @OneToMany(mappedBy = "client")
    private Collection<Order_data> orders;

    public Collection<Order_data> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order_data> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return first_name+" "+surname;
    }
}
