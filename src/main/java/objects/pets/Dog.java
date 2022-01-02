package objects.pets;

public class Dog extends Pets{
    public Dog(String name, int age, double price_in, String breed)
    {
        super(name, age, price_in);
        this.breed = breed;
        this.species = "Dog";
    }
}
