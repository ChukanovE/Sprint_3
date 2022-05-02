import client.CourierClient;
import generateData.GenerateDataCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginCourierTest {
    private CourierClient courierClient;
    private GenerateDataCourier generateDataCourier;
    private int courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        generateDataCourier = new GenerateDataCourier();
        courier = Courier.getRandom();

        boolean isCourierCreated = generateDataCourier.createCourier(courier);
        assertTrue("Курьер не создан", isCourierCreated);
    }

    @After
    public void tearDown() {
        boolean isCourierDelete = generateDataCourier.deleteCourier(courierId);
        assertTrue("Курьер не удален", isCourierDelete);
    }

    @Test
    @DisplayName("Авторизация курьера")
    public void loginCourierValid() {
        ValidatableResponse response = courierClient.loginCourier(CourierData.getData(courier));
        int statusCode = response.extract().statusCode();
        courierId = response.extract().path("id");

        assertEquals("statusCode неверный", 200, statusCode);
        assertNotEquals("Id курьера некорректный", 0, courierId);
    }
}
