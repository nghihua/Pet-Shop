package objects.pets;

public interface PetInterface {
    String id = null;
    String name = null;
    int age = 0;
    String species = null;
    String breed = null;
    double price_in = 0;
    void setInfo();
    void updateInfo(String name, int age, String breed, double price);
    void deleteInfo();
    String getName();
    int getAge();
    String getSpecies();
    String getBreed();
    double getPrice_in();
}
