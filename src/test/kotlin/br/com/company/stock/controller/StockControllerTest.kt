package br.com.company.stock.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest(@Autowired val mockMvc: MockMvc) {
    private val urlValidApi: String = "/stock/analisys/"
    private val urlInvalidApi: String = "/stock/analisys/ticker"
    private val exampleValidTicker: String = "ABEV3"
    private val exampleInvalidTicker: String = "ABEVV"
    private val exampleNotFoundTicker: String = "ABEV5"


    @Test
    fun findAnalisysWhenReturnIs200Code() {
        mockMvc.perform(get("${urlValidApi}${exampleValidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun findAnalisysWhenReturnIs400Code(){
        mockMvc.perform(get("${urlValidApi}${exampleInvalidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun findAnalisysWhenReturnIs404Code(){
        mockMvc.perform(get("${urlInvalidApi}${exampleValidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

}