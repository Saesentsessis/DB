package main.data;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Wood.FindAll", query = "SELECT w from Wood w")
})
public class Wood {
    @Basic
    private String type;
    @Basic
    private int amount;
    @Id
    @Basic
    private String name;
    @Basic
    private float price_per_one;
    @ManyToOne(optional = false)
    private Storage storage;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice_per_one() {
        return price_per_one;
    }

    public void setPrice_per_one(float price_per_one) {
        this.price_per_one = price_per_one;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
