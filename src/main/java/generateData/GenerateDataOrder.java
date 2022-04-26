package generateData;

import client.OrderClient;
import io.restassured.response.ValidatableResponse;
import model.OrderModel;

import static org.junit.Assert.*;

public class GenerateDataOrder {
    private OrderClient orderClient = new OrderClient();
    private int track;
    private int idOrder;
    private OrderModel orderModel;

    public int createRandomOrder() {
        orderModel = OrderModel.getRandom();

        ValidatableResponse response = orderClient.createOrders(orderModel);
        int statusCode = response.extract().statusCode();
        track = response.extract().path("track");

        assertEquals("statusCode неверный", 201, statusCode);
        assertNotEquals("Track некорректный", 0, track);

        ValidatableResponse responseOrder = orderClient.getOrdersByTrack(track);
        int statusCodeOrder = responseOrder.extract().statusCode();
        idOrder = responseOrder.extract().path("order.id");

        assertEquals("statusCode неверный", 200, statusCodeOrder);
        assertNotEquals("Id заказ некорректный", 0, idOrder);

        return idOrder;
    }

    public boolean finishOrder(int idOrder) {
        ValidatableResponse response = orderClient.finishOrders(idOrder);
        int statusCode = response.extract().statusCode();
        boolean errorMessage = response.extract().path("ok");

        assertEquals("statusCode неверный", 200, statusCode);

        return errorMessage;
    }

    public boolean cancelOrder(int trackOrder) {
        ValidatableResponse response = orderClient.cancelOrders(trackOrder);
        int statusCode = response.extract().statusCode();
        boolean errorMessage = response.extract().path("ok");

        assertEquals("statusCode неверный", 200, statusCode);

        return errorMessage;
    }
}
