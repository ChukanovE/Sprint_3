package client;

import constant.DataConstant;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierData;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseHttpClient {
    public final String PATH = DataConstant.BASE_URL + DataConstant.COURIER_URL;

    @Step("Courier - создание курьера {courier}")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseReqSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Courier - Логин курьера в системе как {dataCourier}")
    public ValidatableResponse loginCourier(CourierData courierData) {
        return given()
                .spec(getBaseReqSpec())
                .body(courierData)
                .when()
                .post(PATH + "login/")
                .then();
    }

    @Step("Courier - удаление курьера {courierId}")
    public ValidatableResponse deleteCourier(int courierId) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .delete(PATH + courierId)
                .then();
    }
}
