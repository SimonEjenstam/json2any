import java.util.ArrayList;

public class Car {

    public int id;

    public double weight;

    public boolean taxRegistered;

    public String color;

    public Person owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean getTaxRegistered() {
        return taxRegistered;
    }

    public void setTaxRegistered(boolean taxRegistered) {
        this.taxRegistered = taxRegistered;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

}
