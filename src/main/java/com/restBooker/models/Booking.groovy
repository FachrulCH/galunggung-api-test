package com.restBooker.models

class Booking extends BaseModel{
    String firstname = ''
    String lastname = ''
    Integer totalprice = 0
    Boolean depositpaid = ''
    def bookingdates = [:]
    String additionalnotes = ''
}
