package main.data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NamedQueries({
        @NamedQuery(name = "Storage.FindAll", query = "SELECT s from Storage s")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Storage.Add", query = "INSERT INTO storage (id, city_name, adress) VALUES (?,?,?)")
})
public class Storage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Basic
    private String city_name;
    @Basic
    private String adress;
    @OneToMany(mappedBy = "storage")
    private Collection<Material> materials;
    @OneToMany(mappedBy = "storage")
    private Collection<Wood> woods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return city_name+": "+adress.substring(0,8)+"...";
    }

    public Collection<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(Collection<Material> materials) {
        this.materials = materials;
    }

    public Collection<Wood> getWoods() {
        return woods;
    }

    public void setWoods(Collection<Wood> woods) {
        this.woods = woods;
    }
}
