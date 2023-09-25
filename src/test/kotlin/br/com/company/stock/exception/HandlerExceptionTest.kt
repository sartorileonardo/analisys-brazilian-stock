package br.com.company.stock.exception

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest

@ExtendWith(MockitoExtension::class)
class HandlerExceptionTest {

    private val handlerException = HandlerException()

    @Test
    fun `handleNotFound should return ErrorDTO with NOT_FOUND status`() {
        val request = MockHttpServletRequest()
        val exception = NotFoundException("Not found error message")

        val result = handlerException.handleNotFound(exception, request)

        assert(result.status == HttpStatus.NOT_FOUND.value())
        assert(result.error == HttpStatus.NOT_FOUND.name)
        assert(result.message == "Not found error message")
        assert(result.path == request.servletPath)
    }

    @Test
    fun `handleValidation should return ErrorDTO with BAD_REQUEST status`() {
        val request = MockHttpServletRequest()
        val exception = BusinessException("Validation error message")

        val result = handlerException.handleValidation(exception, request)

        assert(result.status == HttpStatus.BAD_REQUEST.value())
        assert(result.error == HttpStatus.BAD_REQUEST.name)
        assert(result.message == "Validation error message")
        assert(result.path == request.servletPath)
    }

    @Test
    fun `handleServerError should return ErrorDTO with INTERNAL_SERVER_ERROR status`() {
        val request = MockHttpServletRequest()
        val exception = Exception("Server error message")

        val result = handlerException.handleServerError(exception, request)

        assert(result.status == HttpStatus.INTERNAL_SERVER_ERROR.value())
        assert(result.error == HttpStatus.INTERNAL_SERVER_ERROR.name)
        assert(result.message == "Server error message")
        assert(result.path == request.servletPath)
    }
}
