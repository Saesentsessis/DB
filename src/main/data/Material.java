package main.data;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Material.FindAll", query = "SELECT m from Material m")
})
public class Material {
    @Basic
    private float weight;
    private String type;
    @Basic
    private float price_per_kg;
    @Id
    @Basic
    private String name;
    @ManyToOne(optional = false)
    private Storage storage;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Basic
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice_per_kg() {
        return price_per_kg;
    }

    public void setPrice_per_kg(float price_per_kg) {
        this.price_per_kg = price_per_kg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
