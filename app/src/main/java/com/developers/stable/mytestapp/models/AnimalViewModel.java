package com.developers.stable.mytestapp.models;

import android.arch.lifecycle.ViewModel;
import android.os.Parcelable;

import java.util.HashMap;

public class AnimalViewModel extends ViewModel {

    // objects, downloaded from api request (we need it once, because i think, the data is not changes often)
    private ModelAnimal animalCat;
    private ModelAnimal animalDog;
    //we need that "animalType" to AnimalViewModel choose, with wich data it works for now
    private String animalType;
    private HashMap<String, Parcelable> bundleHashMap;

    public AnimalViewModel() {
        bundleHashMap = new HashMap<>();
    }

    public ModelAnimal getModelCat() {
        return animalCat;
    }

    public void setModelCat(ModelAnimal animal) {
        this.animalCat = animal;
    }

    public ModelAnimal getModelDog() {
        return animalDog;
    }

    public void setModelDog(ModelAnimal animalDog) {
        this.animalDog = animalDog;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    /**
     * @param key now gets "cat" or "dog"
     * @return returns state of recyclerView
     */
    public Parcelable getStateByKey(String key) {
        return bundleHashMap.get(key);
    }

    /**
     * @param key        now gets "cat" or "dog"
     * @param parcelable is state, that we need to save
     */
    public void setStateByKey(String key, Parcelable parcelable) {
        bundleHashMap.put(key, parcelable);
    }
}
