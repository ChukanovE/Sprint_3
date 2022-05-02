package model;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class OrderModel {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public OrderModel(String firstName, String lastName, String address, String metroStation, String phone,
                      int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static OrderModel getRandom() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().fullAddress();
        String metroStation = faker.address().streetName();
        String phone = faker.phoneNumber().cellPhone();
        int rentTime = faker.number().numberBetween(0, 367);
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(faker.date().future(2000, TimeUnit.DAYS));
        String comment = RandomStringUtils.randomAlphabetic(100);
        String[] color = new String[0];

        return new OrderModel(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Order {" +
                "firstName:" + firstName + "," +
                "lastName:" + lastName + "," +
                "address:" + address + "," +
                "metroStation:" + metroStation + "," +
                "phone:" + phone + "," +
                "rentTime:" + rentTime + "," +
                "deliveryDate:" + deliveryDate + "," +
                "comment:" + comment + "," +
                "color:" + color + "}";
    }
}