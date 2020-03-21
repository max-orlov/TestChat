/*
package filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

public class LoadSalt implements Filter {

    private static Logger logger = Logger.getLogger(LoadSalt.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.info("LoadSalt started");

        HttpServletRequest httpReq = (HttpServletRequest) request;

        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)
                httpReq.getSession().getAttribute("csrfPreventionSaltCache");

        if (csrfPreventionSaltCache == null){
            csrfPreventionSaltCache = CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(20, TimeUnit.MINUTES)
                    .build();
            httpReq.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
        }

        String salt = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());
        csrfPreventionSaltCache.put(salt, Boolean.TRUE);

        logger.info("Generated salt : " + salt);

        httpReq.setAttribute("csrfPreventionSalt", salt);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
*/
