import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GetOrderByTrackTest {
    private OrderClient orderClient;
    private int track;
    private int idOrder;
    private OrderModel orderModel;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение заказа по номеру")
    public void getOrderByTrackValid() {
        orderModel = OrderModel.getRandom();

        ValidatableResponse response = orderClient.createOrders(orderModel);
        int statusCode = response.extract().statusCode();
        track = response.extract().path("track");

        ValidatableResponse responseOrder = orderClient.getOrdersByTrack(track);
        int statusCodeOrder = responseOrder.extract().statusCode();
        idOrder = responseOrder.extract().path("order.id");

        assertEquals("statusCode неверный", 201, statusCode);
        assertEquals("statusCode неверный", 201, statusCodeOrder);
        assertNotEquals("Track некорректный", 0, track);
        assertNotEquals("Id заказа некорректный", 0, idOrder);
    }

    @Test
    @DisplayName("Получение заказа с несуществующим заказом")
    public void getOrderByTrackInvalid() {
        track = 0;
        String message = "Заказ не найден";

        ValidatableResponse response = orderClient.getOrdersByTrack(track);
        int statusCode = response.extract().statusCode();
        String getOrderError = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение об ошибке некорректное", getOrderError.contains(message));
    }

    @Test
    @DisplayName("Получение заказа без номера заказа")
    public void getOrderWithoutTrack() {
        String message = "Недостаточно данных для поиска";

        ValidatableResponse response = orderClient.getOrdersByTrack();
        int statusCode = response.extract().statusCode();
        String getOrderError = response.extract().path("message");

        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение об ошибке некорректное", getOrderError.contains(message));
    }
}
