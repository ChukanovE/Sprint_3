package client;

import constant.DataConstant;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.OrderModel;
import model.Track;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseHttpClient {

    public final String PATH = DataConstant.BASE_URL + DataConstant.ORDERS_URL;

    @Step("Orders - Создание заказа {order}")
    public ValidatableResponse createOrders(OrderModel orderModel) {
        return given()
                .spec(getBaseReqSpec())
                .body(orderModel)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Orders - Отменить заказ {orderTrack}")
    public ValidatableResponse cancelOrders(int orderTrack) {
        return given()
                .spec(getBaseReqSpec())
                .body(new Track(orderTrack))
                .when()
                .put(PATH + "/cancel")
                .then();
    }

    @Step("Orders - Завершить заказ {id}")
    public ValidatableResponse finishOrders(int id) {
        String body = "{\"id\":\"" + id + "\"}";
        return given()
                .spec(getBaseReqSpec())
                .body(body)
                .when()
                .put(PATH + "/finish/" + id)
                .then();
    }

    @Step("Orders - Получение списка заказов")
    public Response getListOfOrders() {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(PATH);
    }

    @Step("Orders - Принять заказ {orderId}")
    public ValidatableResponse acceptOrders(int orderId) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .put(PATH + "/accept/" + orderId)
                .then();
    }

    @Step("Orders - Принять заказ {orderId} курьера {courierId}")
    public ValidatableResponse acceptOrders(int orderId, int courierId) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .queryParam("courierId", courierId)
                .put(PATH + "/accept/" + orderId)
                .then();
    }

    @Step("Orders - Получить заказ по номеру {track}")
    public ValidatableResponse getOrdersByTrack(int track) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .queryParam("t", track)
                .get(PATH + "/track")
                .then();
    }

    @Step("Orders - Получить заказ без номера заказа")
    public ValidatableResponse getOrdersByTrack() {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(PATH + "/track")
                .then();
    }
}
