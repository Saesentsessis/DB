package main.data;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "MaterialProviding.FindAll", query = "SELECT m from MaterialProviding m")
})
public class MaterialProviding {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Basic
    private float weight;
    @Basic
    private String date;
    @Basic
    private float price;
    @ManyToOne(optional = false)
    private Provider provider;
    @ManyToOne
    private Material Materials;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Material getMaterials() {
        return Materials;
    }

    public void setMaterials(Material materials) {
        Materials = materials;
    }
}
