import client.CourierClient;
import generateData.GenerateDataCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCourierTest {
    private CourierClient courierClient;
    private GenerateDataCourier generateDataCourier;
    private int courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        generateDataCourier = new GenerateDataCourier();
    }

    @After
    public void tearDown() {
        courierId = generateDataCourier.loginCourier(courier);
        boolean isCourierDeleted = generateDataCourier.deleteCourier(courierId);
        assertTrue("Курьер не удален", isCourierDeleted);
    }

    @Test
    @DisplayName("Создание курьера")
    public void createCourierValidData() {
        courier = Courier.getRandom();

        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertEquals("statusCode неверный", 201, statusCode);
        assertTrue("Курьер не создан", isCourierCreated);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьера")
    public void createdSecondSameCourierError() {
        courier = Courier.getRandom();
        String message = "Этот логин уже используется";
        boolean isCourierCreated = generateDataCourier.createCourier(courier);

        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        String secondCourierCreated = response.extract().path("message");

        assertEquals("statusCode неверный", 409, statusCode);
        assertTrue("Сообщение о создание такого курьера некорректное", isCourierCreated);
    }

    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    public void createSecondIsSameLoginError() {
        courier = Courier.getRandom();
        Courier secondCourier = new Courier(courier.login, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        String message = "Этот логин уже используется";
        boolean isCourierCreated = generateDataCourier.createCourier(courier);

        ValidatableResponse response = courierClient.createCourier(secondCourier);
        int statusCode = response.extract().statusCode();
        String secondCourierCreated = response.extract().path("message");

        assertTrue("Первый курьер не создан", isCourierCreated);
        assertEquals("statusCode неверный", 409, statusCode);
        assertTrue("Сообщение о создании второго курьера некорректное", secondCourierCreated.contains(message));
    }
}
