import client.OrderClient;
import constant.DataConstant;
import generateData.GenerateDataCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class AcceptOrderCourierErrorTest {

    private OrderClient orderClient;
    private GenerateDataCourier generateDataCourier;
    private int courierId;
    private int idOrder;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        generateDataCourier = new GenerateDataCourier();
        courierId = generateDataCourier.createRandomCourier();
    }

    @After
    public void tearDown() {
        boolean isCourierDeleted = generateDataCourier.deleteCourier(courierId);
        assertTrue("Курьер не удален", isCourierDeleted);
    }

    @Test
    @DisplayName("Принять заказ с неверным номером заказа")
    public void acceptOrderInvalidIdOrder() {
        idOrder = 0;
        String message = "Заказа с таким id не существует";

        ValidatableResponse response = orderClient.acceptOrders(idOrder, courierId);
        int statusCode = response.extract().statusCode();
        String acceptOrderError = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение об ошибке некорректное", acceptOrderError.contains(message));
    }

    @Test
    @DisplayName("Принять заказ без номера заказа")
    public void acceptOrderWithoutIdOrder() {
        String message = "Недостаточно данных для поиска";

        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .when()
                .queryParam("courierId", courierId)
                .put(DataConstant.BASE_URL + DataConstant.ORDERS_URL + "/accept/")
                .then();

        int statusCode = response.extract().statusCode();

        String acceptOrderError = response.extract().path("message");

        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение об ошибке некорректное", acceptOrderError.contains(message));
    }
}
