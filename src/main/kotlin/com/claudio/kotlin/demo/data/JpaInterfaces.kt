package com.claudio.kotlin.demo.data

import com.claudio.kotlin.demo.domain.Booking
import com.claudio.kotlin.demo.domain.Performance
import com.claudio.kotlin.demo.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository: JpaRepository<Seat, Long>

interface PerformanceRepository: JpaRepository<Performance, Long>

interface BookingRepository: JpaRepository<Booking, Long>