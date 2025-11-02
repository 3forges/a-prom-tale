package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FruitService {

    private static final Logger LOGGER = Logger.getLogger(FruitService.class.getName());

    /*
    public FruitMessage buildFruitMessage(String name) {
        var message = FruitMessage.of("Say Hello to " + name + " at " + LocalDateTime.now());
        LOGGER.log(Level.INFO, "build message: {0}", message);
        return message;
    }
     */
    public List<Fruit> getFruitsByName(String name) {
        ArrayList toReturn = new ArrayList<Fruit>();
        Fruit mangue = new Fruit();
        mangue.setName(" mangue dont le nom est " + name);
        mangue.setPrice("2.50 euros");
        toReturn.add(mangue);
        return toReturn;
    }

    Fruit getFruitsById(String fruitId) {
        Fruit mangue = new Fruit();
        mangue.setName(" mangue dont le 'ID est " + fruitId);
        mangue.setPrice("2.50 euros");
        return mangue;
    }

    List<Fruit> getSomeFakeFruits(int paginationItemsPerPage) {
        ArrayList toReturn = new ArrayList<Fruit>();
        for (int i = 0; i < paginationItemsPerPage; i++) {
            Fruit mangue = new Fruit();

            mangue.setName(" mangue dont le nom est mangue" + i);
            mangue.setPrice("2.12 euros");
            toReturn.add(mangue);
        }
        return toReturn;
    }

    List<Fruit> getAllFruits(int paginationItemsPerPage) {
        /**
         * À la place je devrais récupérer la lsite de mes fruits de la base de données à travers JPA.
         */
        return this.getSomeFakeFruits(paginationItemsPerPage);
    }

    Object deleteFruitById(String fruitId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Fruit updateFruit(Fruit fruit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Fruit createFruit(Fruit fruit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
