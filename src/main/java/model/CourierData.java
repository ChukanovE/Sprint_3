package model;

public class CourierData {

    private String login;
    private String password;

    public CourierData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierData getData(Courier courier) {
        return new CourierData(courier.login, courier.password);
    }

    @Override
    public String toString() {
        return "CourierData {" +
                "login:" + login + "," +
                "password:" + password + "}";
    }
}
