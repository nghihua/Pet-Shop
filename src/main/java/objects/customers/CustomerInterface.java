package objects.customers;

public interface CustomerInterface {
    double getDiscount();
    void setInfo();
    void updateInfo(String name, String new_phone, double discount);
    String getName();
    void deleteInfo();
}
