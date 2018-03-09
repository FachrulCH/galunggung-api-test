package com.restBooker

import com.restBooker.steps.BookingSteps
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import spock.lang.Shared
import spock.lang.Specification

abstract class BaseTest extends Specification{

    @Shared
    protected BookingSteps bookingSteps = new BookingSteps()
    protected Date startTime;

    def cleanup(){

        def endTime = new Date()
        TimeDuration duration = TimeCategory.minus(endTime, startTime)
        println "Done testing for => ${duration} \n"
    }

    def setup(){
        startTime = new Date()
        println "Ready for testing! ${specificationContext.getCurrentIteration().name}"
    }
}
