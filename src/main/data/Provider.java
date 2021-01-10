package main.data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Provider.FindAll", query = "SELECT p from Provider p")
})
public class Provider {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Basic
    private String name;
    @OneToMany(mappedBy = "provider")
    private Collection<MaterialProviding> materials;
    @OneToMany(mappedBy = "provider")
    private Collection<WoodProviding> woods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<MaterialProviding> getMaterials() {
        return materials;
    }

    public void setMaterials(Collection<MaterialProviding> materials) {
        this.materials = materials;
    }

    public Collection<WoodProviding> getWoods() {
        return woods;
    }

    public void setWoods(Collection<WoodProviding> woods) {
        this.woods = woods;
    }
}
