import java.util.ArrayList;

public class Person {

    public int id;

    public boolean isMarried;

    public double height;

    public String name;

    public int age;

    public Car currentOwnerOf;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(boolean isMarried) {
        this.isMarried = isMarried;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Car getCurrentOwnerOf() {
        return currentOwnerOf;
    }

    public void setCurrentOwnerOf(Car currentOwnerOf) {
        this.currentOwnerOf = currentOwnerOf;
    }

}
