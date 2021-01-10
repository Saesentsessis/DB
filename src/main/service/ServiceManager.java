package main.service;

import main.data.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    private EntityManager em;

    public ServiceManager(EntityManager em) {
        this.em = em;
    }

    public List<Client> getAllClients() {
        return em.createNamedQuery("Client.FindAll", Client.class).getResultList();
    }

    public List<Storage> getAllStorages() {
        return em.createNamedQuery("Storage.FindAll", Storage.class).getResultList();
    }

    public List<Material> getAllMaterials(Storage ref) {
        return (List<Material>) ref.getMaterials();
    }

    public List<Material> getAllMaterials() {
        return em.createNamedQuery("Material.FindAll", Material.class).getResultList();
    }

    public List<Wood> getAllWoods(Storage ref) {
        return (List<Wood>) ref.getWoods();
    }

    public List<Wood> getAllWoods() {
        return em.createNamedQuery("Wood.FindAll", Wood.class).getResultList();
    }

    public List<MaterialProviding> getAllMaterialProviding() {
        return em.createNamedQuery("MaterialProviding.FindAll", MaterialProviding.class).getResultList();
    }

    public List<WoodProviding> getAllWoodProviding() {
        return em.createNamedQuery("WoodProviding.FindAll", WoodProviding.class).getResultList();
    }

    public List<Provider> getAllProviders(Wood ref) {
        List<WoodProviding> provided = getAllWoodProviding();
        List<Provider> providers = new ArrayList<>();
        for (WoodProviding wp : provided)
            if (wp.getWoods().equals(ref)) providers.add(wp.getProvider());
        return providers;
    }

    public List<Provider> getAllProviders(Material ref) {
        List<MaterialProviding> provided = getAllMaterialProviding();
        List<Provider> providers = new ArrayList<>();
        for (MaterialProviding mp : provided)
            if (mp.getMaterials().equals(ref)) providers.add(mp.getProvider());
        return providers;
    }

    public List<Provider> getAllProviders(Storage ref) {
        List<MaterialProviding> providedMats = getAllMaterialProviding();
        List<WoodProviding> providedWoods = getAllWoodProviding();
        List<Provider> providers = new ArrayList<>();

        for (WoodProviding wp : providedWoods) {
            for (Wood w : ref.getWoods())
                if (wp.getWoods().getName().equals(w.getName()) && !providers.contains(wp.getProvider()))
                    providers.add(wp.getProvider());
        }

        for (MaterialProviding mp : providedMats) {
            for (Material m : ref.getMaterials())
                if (mp.getMaterials().getName().equals(m.getName()) && !providers.contains(mp.getProvider()))
                    providers.add(mp.getProvider());
        }

        return providers;
    }

    public List<Worker> getAllWorkers() {
        return em.createNamedQuery("Worker.FindAll", Worker.class).getResultList();
    }

    public List<Order_data> getAllOrders() {
        return em.createNamedQuery("Order.FindAll", Order_data.class).getResultList();
    }

    public List<Order_data> getAllOrders(Client ref) {
        List<Order_data> orders = getAllOrders();
        int max = orders.size();
        for (int i = 0; i < max; i++) {
            if (!orders.get(i).getClient().equals(ref)) {
                orders.remove(i);
                i--;
                max--;
            }
        }
        return orders;
    }
}
