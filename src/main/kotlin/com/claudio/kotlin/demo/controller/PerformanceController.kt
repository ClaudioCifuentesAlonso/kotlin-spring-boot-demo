package com.claudio.kotlin.demo.controller

import com.claudio.kotlin.demo.data.PerformanceRepository
import com.claudio.kotlin.demo.domain.Performance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView



@Controller
@RequestMapping("/performances")
class PerformanceController() {

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun performancesHomePage() =
            ModelAndView("performances/home","performances", performanceRepository.findAll())

    @RequestMapping("/add")
    fun addPerformance() =
            ModelAndView("performances/add","performance", Performance(0,""))

    @PostMapping("/save")
    fun savePerformance(performance: Performance) : String {
        performanceRepository.save(performance)
        return "redirect:/performances/"
    }
}