package objects.supply;

public interface SupplyInterface {
    void updateInfo(String name, double price);
    void deleteInfo();
    String getName();
    double getPrice();
}
