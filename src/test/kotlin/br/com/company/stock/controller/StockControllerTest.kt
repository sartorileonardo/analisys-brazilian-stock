package br.com.company.stock.controller

import br.com.company.stock.config.StockParametersApiConfig
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
class StockControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val config: StockParametersApiConfig
    ) {
    private val urlApi: String = "/stock/data/"
    private val exampleValidTicker: String = "petro4"
    private val exampleInvalidTicker: String = "petroo"
    private val exampleNotFoundTicker: String = "petro8"

    /*
    @Test
    fun findAnalisysWhenReturnIs200Code() {
        mockMvc.perform(get("${urlApi}${exampleValidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun findAnalisysWhenReturnIs400Code(){
        mockMvc.perform(get("${urlApi}${exampleInvalidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun findAnalisysWhenReturnIs404Code(){
        mockMvc.perform(get("${urlApi}${exampleNotFoundTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }



    @Test
    fun findAnalisysWhenReturnIs500Code(){
        config.url = "<URL_API_RESET>"
        mockMvc.perform(get("${urlApi}${exampleValidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError)
    }

     */

}