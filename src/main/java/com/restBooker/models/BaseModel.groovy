package com.restBooker.models

import groovy.json.JsonBuilder

class BaseModel {

    def toPayload(){
        return new JsonBuilder(this).toPrettyString()
    }
}
