package com.restBooker.steps

import com.jayway.restassured.http.ContentType
import com.restBooker.models.Booking

import static com.jayway.restassured.RestAssured.given

class BookingSteps extends BaseStep{
    private final BOOKING_ENDPOINT = BASE_URL + "booking/"
    private final DEFAULT_BOOKING_COUNT = 12 as int

    def userCanGetAllBookings(){
        given().log().ifValidationFails()
                .when()
                .get(BOOKING_ENDPOINT)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
    }

    def userCanViewDetailBooking(id){
        given().log().ifValidationFails()
                .when()
                .get(BOOKING_ENDPOINT + id as String)
    }

    def userCanCreateNewBooking(Booking bookingPayload){
        given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .body(bookingPayload.toPayload())
                .when()
                .post(BOOKING_ENDPOINT)
    }

    def userCanDeleteBooking(args){
        given().log().ifValidationFails()
        .header("Cookie", "token=${getToken(args)}")
        .delete(BOOKING_ENDPOINT + args.id as String)
    }
}
