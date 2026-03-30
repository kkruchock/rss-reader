package ru.itis.rssreader.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {"/login", "/register", "/css"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        String pathWithoutContext = path.substring(contextPath.length());

        boolean isPublic = false;
        for (String publicPath : PUBLIC_PATHS ) {
            if (pathWithoutContext.startsWith(publicPath)) {
                isPublic = true;
                break;
            }
        }

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(contextPath + "/login");
        }
    }
}