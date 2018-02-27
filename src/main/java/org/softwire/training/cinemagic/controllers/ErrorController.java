package org.softwire.training.cinemagic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    ErrorResponse error(HttpServletRequest request, HttpServletResponse response) {
        // TODO: include stack trace in development/test
        Map<String, Object> errorAttributes = getErrorAttributes(request, false);
        return new ErrorResponse(
                (String) errorAttributes.getOrDefault("message", "Unknown Error"),
                (String) errorAttributes.get("trace"));
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    private static class ErrorResponse {
        private String message;
        private String stack;

        private ErrorResponse() {}

        private ErrorResponse(String message, String stack) {
            this.message = message;
            this.stack = stack;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }
    }
}
