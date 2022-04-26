package client;

import constant.DataConstant;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseHttpClient {

    protected RequestSpecification getBaseReqSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(DataConstant.BASE_URL)
                .build();
    }
}
