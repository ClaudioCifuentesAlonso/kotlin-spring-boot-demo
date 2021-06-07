package com.claudio.kotlin.demo.controller

import com.claudio.kotlin.demo.TheaterService
import com.claudio.kotlin.demo.data.PerformanceRepository
import com.claudio.kotlin.demo.data.SeatRepository
import com.claudio.kotlin.demo.domain.CheckAvailabilityBackingBean
import com.claudio.kotlin.demo.domain.Seat
import com.claudio.kotlin.demo.service.BookingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {

    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @GetMapping("/helloWorld")
    fun helloWorld() : ModelAndView{
        return ModelAndView("helloWorld")
    }

    @GetMapping("")
    fun homePage() : ModelAndView{
        val model = mapOf("bean" to CheckAvailabilityBackingBean(),
            "performances" to performanceRepository.findAll(),
            "seatNumbers" to 1..36,
            "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }

    @PostMapping("/checkAvailability")
    fun checkAvailability(bean: CheckAvailabilityBackingBean) : ModelAndView{
        val selectedSeat : Seat = bookingService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()
        bean.seat = selectedSeat
        bean.performance = selectedPerformance
        val result = bookingService.isSeatFree(selectedSeat, selectedPerformance)
        bean.available = result
        if(!result){
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }
        val model = mapOf("bean" to bean,
            "performances" to performanceRepository.findAll(),
            "seatNumbers" to 1..36,
            "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }

    @PostMapping("/booking")
    fun bookASeat(bean: CheckAvailabilityBackingBean) : ModelAndView{
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }

    @GetMapping("/bootstrap")
    fun createInitialData() : ModelAndView{
        //Create the data and save it to the database
        val seats = theaterService.seats
        seatRepository.saveAll(seats)
        return homePage()
    }
}