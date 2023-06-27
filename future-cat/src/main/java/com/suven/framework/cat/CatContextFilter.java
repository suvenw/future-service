package com.suven.framework.cat;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Max on 2016/9/19.
 */
public class CatContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requsetURI = ((HttpServletRequest) request).getRequestURI();
        Transaction t = null;
        try {
           t = Cat.newTransaction(CatGcConstants.TYPE_URL, requsetURI);
            TransactionHolder.set(t);
            chain.doFilter(request, response);
            if(StringUtils.equalsIgnoreCase(t.getStatus(), CatGcConstants.CAT_STATUS))
                t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            t.setStatus(e);
            throw e;
        } finally {
            if(t != null){
                t.complete();
            }TransactionHolder.remove();
           
        }
    }

    @Override
    public void destroy() {

    }
}
