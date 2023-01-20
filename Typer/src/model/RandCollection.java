package model;

// Interface for random collection datatype
public interface RandCollection<E> {

    // adds an object to the collection with a certain weight
    RandomCollection<E> add(double weight, E result);

    // returns the next object in the collection based on weights
    public E next();
}
