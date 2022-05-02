import client.OrderClient;
import generateData.GenerateDataOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AcceptOrderErrorTest {
    private OrderClient orderClient;
    private GenerateDataOrder generateDataOrder;
    private int courierId;
    private int idOrder;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        generateDataOrder = new GenerateDataOrder();
        idOrder = generateDataOrder.createRandomOrder();
    }

    @Test
    @DisplayName("Принять заказ без id курьера")
    public void acceptOrderWithoutIdCourier() {
        String message = "Недостаточно данных для поиска";

        ValidatableResponse response = orderClient.acceptOrders(idOrder);
        int statusCode = response.extract().statusCode();
        String acceptOrderError = response.extract().path("message");

        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение об ошибке некорректное", acceptOrderError.contains(message));
    }

    @Test
    @DisplayName("Принять заказ с некорректным id курьера")
    public void acceptOrderInvalidIdCourier() {
        String message = "Курьера с таким id не существует";
        courierId = 0;

        ValidatableResponse response = orderClient.acceptOrders(idOrder, courierId);
        int statusCode = response.extract().statusCode();
        String acceptOrderError = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение об ошибке некорректное", acceptOrderError.contains(message));
    }
}