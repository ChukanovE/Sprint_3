import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCourierErrorTest {
    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера с пустым логином и паролем")
    public void createCourierWithoutDataError() {
        courier = new Courier("", "", "Иван");
        String message = "Недостаточно данных для создания учетной записи";

        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        String courierWithoutData = response.extract().path("message");

        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение о создании курьера некорректное", courierWithoutData.contains(message));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void createCourierWithoutPasswordError() {
        courier = new Courier(RandomStringUtils.randomAlphabetic(10), "", RandomStringUtils.randomAlphabetic(10));
        String message = "Недостаточно данных для создания учетной записи";

        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        String courierWithoutData = response.extract().path("message");

        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение о создании курьера некорректное", courierWithoutData.contains(message));
    }
}
