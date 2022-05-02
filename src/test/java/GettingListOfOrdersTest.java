import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderResponse;
import model.Orders;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GettingListOfOrdersTest {
    private OrderClient orderClient;
    private Response response;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказа")
    public void getListOfOrdersWithoutParams() {
        response = orderClient.getListOfOrders();
        int statusCode = response.then().extract().statusCode();
        OrderResponse[] orderResponse = response.body().as(Orders.class).getOrders();

        assertEquals("statusCode неверный", 200, statusCode);
        assertTrue("Нет заказов", orderResponse.length > 0);
    }
}
