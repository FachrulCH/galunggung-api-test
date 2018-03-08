package com.restBooker

import com.restBooker.models.Booking

class BookingTest extends BaseTest{
    protected String idNewBooking

    def "should return default booking"(){
        expect:
            bookingSteps.userCanGetAllBookings()
    }

    def "should return detail booking of id 1"(){
        when:
            def resp = bookingSteps.userCanViewDetailBooking(1)

        then:
            resp.getStatusCode() == 200
            println "time: "+resp.getTime()
    }

    def "should create new booking"(){
        given:
            def buking = new Booking(firstname: 'Asep',
                    lastname: 'Sukoco',
                    totalprice: 16000,
                    bookingdates: [checkin: '2017-01-11', checkout:'2017-08-16'],
                    additionalnotes: 'I miss you'
            )

        when:
            def resp = bookingSteps.userCanCreateNewBooking(buking)
            idNewBooking = resp.body().path("bookingid") // assignment can not inside block of then:
            println "time: "+resp.getTime()

        and: 'View saved detail'
            def detailBooking = bookingSteps.userCanViewDetailBooking(idNewBooking)
            println "time: "+detailBooking.getTime()

        then:
            resp.getStatusCode() == 200
            // show all response:  println resp.getBody().prettyPrint()
            resp.body().path("booking.firstname") == buking.firstname
            resp.path("booking.totalprice") == buking.totalprice

            //println "Booking id sekarang => "+idNewBooking

            //println detailBooking resp.getBody().print()
            detailBooking.path("totalprice") == buking.totalprice
    }

    def "should be able to delete booking"(){
        when:
            def resp = bookingSteps.userCanDeleteBooking(id: 5)
        then:
            resp.getStatusCode() == 201
            println resp.body().prettyPrint()
    }

    def "should not be able to re-delete booking"(){
        when: 'delete using same id as previous'
            def resp = bookingSteps.userCanDeleteBooking(id: 5)
        then:
            resp.getStatusCode() == 405
            println resp.body().prettyPrint()
    }
}
