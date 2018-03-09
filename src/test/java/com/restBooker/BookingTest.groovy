package com.restBooker

import com.restBooker.models.Booking

class BookingTest extends BaseTest{
    protected String idNewBooking

    def "should return default booking"(){
        expect: 'return all bookings'
            bookingSteps.userCanGetAllBookings()
    }

    def "should return detail booking of id 1"(){
        when: 'open detail booking'
            def resp = bookingSteps.userCanViewDetailBooking(1)

        then: 'show detail booking of Jim'
            resp.getStatusCode() == 200
            resp.path("firstname") == 'Jim'
            println "\t Response time: "+resp.getTime()
    }

    def "should create new booking"(){
        given: 'Prepared data for insert'
            def buking = new Booking(firstname: 'Asep',
                    lastname: 'Sukoco',
                    totalprice: 16000,
                    bookingdates: [checkin: '2017-01-11', checkout:'2017-08-16'],
                    additionalnotes: 'I miss you'
            )

        when: 'submit the booking'
            def resp = bookingSteps.userCanCreateNewBooking(buking)
            idNewBooking = resp.body().path("bookingid") // assignment can not inside block of then:
            println "\t Response time: "+resp.getTime()

        then: 'booking created'
            resp.getStatusCode() == 200
            // show all response:  println resp.getBody().prettyPrint()
            resp.body().path("booking.firstname") == buking.firstname
            resp.path("booking.totalprice") == buking.totalprice

            //println "Booking id sekarang => "+idNewBooking

        when: 'open detail of new created booking'
            def detailBooking = bookingSteps.userCanViewDetailBooking(idNewBooking)
            println "\t Response time: "+detailBooking.getTime()

        then: 'saved data should correct'
            //println detailBooking resp.getBody().print()
            detailBooking.path("firstname") == buking.firstname
            detailBooking.path("totalprice") == buking.totalprice
            //detailBooking.path("bookingdates.checkin") == buking.bookingdates.checkin

    }

    def "should be able to delete booking"(){
        when: 'delete booking id 5'
            def resp = bookingSteps.userCanDeleteBooking(id: 5)
        then: 'success delete'
            resp.getStatusCode() == 201
            println resp.body().prettyPrint()
    }

    def "should not be able to re-delete booking"(){
        when: 'delete using same id as previous'
            def resp = bookingSteps.userCanDeleteBooking(id: 5)
        then: 'fail to delete'
            resp.getStatusCode() == 405
            println resp.body().prettyPrint()
    }
}
