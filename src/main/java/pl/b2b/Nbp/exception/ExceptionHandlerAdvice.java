package pl.b2b.Nbp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.b2b.Nbp.dto.ErrorDto;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<ErrorDto> handle(Exception exception, HttpServletRequest httpServletRequest) {
        ErrorDto errorDto = new ErrorDto();
        if (exception instanceof BaseException) {
            errorDto.setMessage(exception.getMessage());
            errorDto.setEvent(UUID.randomUUID().toString());
        }
        logger.error(errorDto.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}