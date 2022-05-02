import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private int track;
    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorList() {
        return new Object[][] {
                {new String[] {"BLACK"}},
                {new String[] {"GREY"}},
                {new String[] {"GREY", "BLACK"}},
                {new String[0]}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderWithColors() {
        OrderModel orderModel = OrderModel.getRandom();
        orderModel.setColor(color);

        ValidatableResponse response = orderClient.createOrders(orderModel);
        int statusCode = response.extract().statusCode();
        track = response.extract().path("track");

        assertEquals("statusCode неверный", 201, statusCode);
        assertNotEquals("Track некорректный", 0, track);
    }
}
