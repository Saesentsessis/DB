package main.data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NamedQueries({
        @NamedQuery(name = "Worker.FindAll", query = "SELECT w from Worker w"),
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Worker.Add", query = "INSERT INTO worker (id, first_name, surname) VALUES (?,?,?)")
})
public class Worker {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String first_name;

    @Basic
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    private String surname;

    @Basic
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private Collection<Order_data> orders;

    @OneToMany(mappedBy = "worker")
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
