import client.CourierClient;
import generateData.GenerateDataOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginCourierErrorTest {
    private CourierClient courierClient;
    private GenerateDataOrder generateDataOrder;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        generateDataOrder = new GenerateDataOrder();
    }

    @Test
    @DisplayName("Авторизация курьера с невалидными данными")
    public void loginCourierInvalid() {
        CourierData courierData = new CourierData("invalidLogin", "****");
        String message = "Учетная запись не найдена";

        ValidatableResponse response = courierClient.loginCourier(courierData);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение об ошибке некорректное", errorMessage.contains(message));
    }

    @Test
    @DisplayName("Авторизация курьера с пустым логином")
    public void loginCourierEmptyLogin() {
        CourierData courierData = new CourierData("", "****");
        String message = "Недостаточно данных для входа";

        ValidatableResponse response = courierClient.loginCourier(courierData);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение об ошибке некорректное", errorMessage.contains(message));
    }
}
