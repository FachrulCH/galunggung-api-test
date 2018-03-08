package com.restBooker

import com.restBooker.steps.BookingSteps
import spock.lang.Shared
import spock.lang.Specification

abstract class BaseTest extends Specification{

    @Shared
    protected BookingSteps bookingSteps = new BookingSteps()

    def cleanup(){
        println("Cleanup after test")
    }
}
