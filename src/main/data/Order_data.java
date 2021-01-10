package main.data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NamedQueries({
        @NamedQuery(name = "Order.FindAll", query = "SELECT o from Order_data o")
})
public class Order_data {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(optional = false)
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(optional = false)
    private Worker worker;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @OneToMany
    private Collection<MaterialProviding> materials;

    public Collection<MaterialProviding> getMaterials() {
        return materials;
    }

    public void setMaterials(Collection<MaterialProviding> materials) {
        this.materials = materials;
    }

    @OneToMany
    private Collection<WoodProviding> woods;

    public Collection<WoodProviding> getWoods() {
        return woods;
    }

    public void setWoods(Collection<WoodProviding> woods) {
        this.woods = woods;
    }

    @Override
    public String toString() {
        return name.substring(0, 16)+"...";
    }
}
