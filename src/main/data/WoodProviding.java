package main.data;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "WoodProviding.FindAll", query = "SELECT w from WoodProviding w")
})
public class WoodProviding {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Basic
    private float price;
    @Basic
    private String date;
    @Basic
    private short amount;
    @ManyToOne(optional = false)
    private Provider provider;
    @ManyToOne
    private Wood woods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public short getAmount() {
        return amount;
    }

    public void setAmount(short amount) {
        this.amount = amount;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Wood getWoods() {
        return woods;
    }

    public void setWoods(Wood woods) {
        this.woods = woods;
    }
}
