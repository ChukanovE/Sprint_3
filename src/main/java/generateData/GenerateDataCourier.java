package generateData;

import client.CourierClient;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierData;

import static org.junit.Assert.*;

public class GenerateDataCourier {
    private CourierClient courierClient = new CourierClient();
    private int courierId;
    private Courier courier;

    public int createRandomCourier() {
        courier = Courier.getRandom();

        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        boolean courierCreated = response.extract().path("ok");

        assertEquals("statusCode неверный", 201, statusCode);
        assertTrue("Курьер не создан", courierCreated);

        ValidatableResponse responseCourier = courierClient.loginCourier(CourierData.getData(courier));
        int statusCodeCourier = responseCourier.extract().statusCode();
        courierId = responseCourier.extract().path("id");

        assertEquals("statusCode неверный", 200, statusCodeCourier);
        assertNotEquals("Id курьера некорректный", 0, courierId);

        return courierId;
    }

    public boolean deleteCourier(int courierId) {
        ValidatableResponse response = courierClient.deleteCourier(courierId);
        int statusCode = response.extract().statusCode();
        boolean errorMessage = response.extract().path("ok");
        assertEquals("statusCode неверный", 200, statusCode);

        return errorMessage;
    }

    public int loginCourier(Courier courier) {
        ValidatableResponse responseCourier = courierClient.loginCourier(CourierData.getData(courier));
        int statusCodeCourier = responseCourier.extract().statusCode();
        courierId = responseCourier.extract().path("id");

        assertEquals("statusCode неверный", 200, statusCodeCourier);
        assertNotEquals("Id курьера некорректный", 0, courierId);

        return courierId;
    }

    public boolean createCourier(Courier courier) {
        ValidatableResponse response = courierClient.createCourier(courier);
        int statusCode = response.extract().statusCode();
        boolean errorMessage = response.extract().path("ok");
        assertEquals("statusCode неверный", 201, statusCode);

        return errorMessage;
    }
}
