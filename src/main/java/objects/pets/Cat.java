package objects.pets;

public class Cat extends Pets{
    public Cat(String name, int age, double price_in, String breed)
    {
        super(name, age, price_in);
        this.breed = breed;
        this.species = "Cat";
    }
}
