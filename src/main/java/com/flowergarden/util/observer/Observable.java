package com.flowergarden.util.observer;

public interface Observable {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}
