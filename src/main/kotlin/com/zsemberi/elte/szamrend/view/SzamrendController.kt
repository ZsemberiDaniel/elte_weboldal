package com.zsemberi.elte.szamrend.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/szamrend")
class SzamrendController {

    @RequestMapping("/first")
    fun firstHandIn(): String {
        return "szamrend/first"
    }
}