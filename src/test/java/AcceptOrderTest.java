import client.OrderClient;
import generateData.GenerateDataCourier;
import generateData.GenerateDataOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AcceptOrderTest {
    private OrderClient orderClient;
    private GenerateDataOrder generateDataOrder;
    private GenerateDataCourier generateDataCourier;
    private int courierId;
    private int idOrder;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        generateDataOrder = new GenerateDataOrder();
        generateDataCourier = new GenerateDataCourier();
        courierId = generateDataCourier.createRandomCourier();
        idOrder = generateDataOrder.createRandomOrder();
    }

    @After
    public void tearDown() {
        boolean isCourierDeleted = generateDataCourier.deleteCourier(courierId);
        assertTrue("Курьер не удален", isCourierDeleted);
        boolean isOrderFinished = generateDataOrder.finishOrder(idOrder);
        assertTrue("Заказ не закрыт", isOrderFinished);
    }

    @Test
    @DisplayName("Принять заказ")
    public void acceptOrderValid() {
        ValidatableResponse response = orderClient.acceptOrders(idOrder, courierId);
        int statusCode = response.extract().statusCode();
        boolean isAcceptOrder = response.extract().path("ok");

        assertEquals("statusCode неверный", 200, statusCode);
        assertTrue("Заказ не принят", isAcceptOrder);
    }
}
