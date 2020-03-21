/*
package filter;

import com.google.common.cache.Cache;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class ValidateSalt implements Filter {

    private static Logger logger = Logger.getLogger(ValidateSalt.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("ValidateSalt started");

        HttpServletRequest httpReq = (HttpServletRequest) request;
        String salt = httpReq.getParameter("csrfPreventionSalt");

        logger.info("Received salt: " + salt);

        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)
                httpReq.getSession().getAttribute("csrfPreventionSaltCache");

        logger.info("Cache: " + csrfPreventionSaltCache);

        if (csrfPreventionSaltCache != null &&
                salt != null &&
                csrfPreventionSaltCache.getIfPresent(salt) != null) {
            chain.doFilter(request, response);
        } else {
            throw new ServletException("Potential CSRF detected!! Inform a scary sysadmin ASAP.");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
*/
