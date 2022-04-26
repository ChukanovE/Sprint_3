import client.CourierClient;
import constant.DataConstant;
import generateData.GenerateDataCourier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class DeleteCourierTest {
    private CourierClient courierClient;
    private GenerateDataCourier generateDataCourier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        generateDataCourier = new GenerateDataCourier();
    }

    @Test
    @DisplayName("Удаление курьера")
    public void deleteCourierValid() {
        courierId = generateDataCourier.createRandomCourier();
        boolean isCourierDeleted = courierClient.deleteCourier(courierId).assertThat().statusCode(200).extract().path("ok");
        assertTrue("Курьер не удален", isCourierDeleted);
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    public void deleteCourierWithInvalidId() {
        courierId = 0;
        String message  = "Курьера с таким id нет";

        ValidatableResponse response = courierClient.deleteCourier(courierId);
        int statusCode = response.extract().statusCode();
        String courierDeleted = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение о удалении курьера некорректное", courierDeleted.contains(message));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    public void deleteCourierWithoutId() {
        String message = "Недостаточно данных для удаления курьера";

        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .when()
                .delete(DataConstant.BASE_URL + DataConstant.COURIER_URL)
                .then();
        int statusCode = response.extract().statusCode();
        String courierDeleted = response.extract().path("message");

        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение о недостаточности данных некорректное", courierDeleted.contains(message));
    }
}
