package com.claudio.kotlin.demo.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Seat(@Id @GeneratedValue(strategy = GenerationType.AUTO)
                val id: Long,
                val seatRow: Char,
                val num: Int,
                val price: BigDecimal,
                val description: String){
    override fun toString() = "Seat $seatRow-$num $$price ($description)"
}