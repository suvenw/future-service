package com.suven.framework.cat;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 重写cat对springmvc的统计uri.
 *
 * @author <a href="http://blog.warningrc.com">王宁</a>
 * @date 2016-7-22 19:16:49
 */
//@Aspect
public class CatPageURIRewriteAspect implements InitializingBean {

    /**
     * Logger for CatRestfullHandlerInterceptor
     */
    private static final Logger logger = LoggerFactory.getLogger(CatPageURIRewriteAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RequestMappingInfoHandlerMapping requestMappingInfoHandlerMapping;

    private Map<HandlerMethod, RequestMappingInfo> requestMappingInfos = Maps.newLinkedHashMap();

    private Map<RequestMappingInfo, String> urlCache = Maps.newLinkedHashMap();

    private volatile boolean requestMappingInfosInit = false;

    @Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object catPageURIRewrite(ProceedingJoinPoint pjp) throws Throwable {
        try {
            String uri = null;
            if (!requestMappingInfosInit) {
                initHandlerMethods();
            }
            HandlerMethod handlerMethod = getHandlerMethod(pjp);
            RequestMappingInfo info = requestMappingInfos.get(handlerMethod);
            if (info != null) {
                uri = getUrl(info);
            }
            if (uri == null) {
                uri = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            }
            if (uri != null) {
                request.setAttribute("cat-page-uri", uri);
            }
        }
        catch (Exception e) {
            logger.warn("修改cat统计uri失败[{}]", e.getMessage(), e);
        }
        return pjp.proceed();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethods();
    }

    private HandlerMethod getHandlerMethod(ProceedingJoinPoint pjp) {
        return new HandlerMethod(pjp.getThis(), ((MethodSignature) pjp.getSignature()).getMethod());
    }

    private String getUrl(RequestMappingInfo info) {
        String url = urlCache.get(info);
        if (url == null) {
            StringBuilder sb = new StringBuilder(request.getContextPath());
            if (info.getPatternsCondition() != null) {
                Set<String> patterns = info.getPatternsCondition().getPatterns();
                if (CatUtils.isNotEmpty(patterns)) {
                    sb.append(patterns.iterator().next());
                }
            }
            List<String> params = Lists.newArrayList();
            if (info.getMethodsCondition() != null
                    && CatUtils.isNotEmpty(info.getMethodsCondition().getMethods())) {
                params.add("method=" + info.getMethodsCondition());
            }
            if (info.getParamsCondition() != null
                    && CatUtils.isNotEmpty(info.getParamsCondition().getExpressions())) {
                params.add("params=" + info.getParamsCondition());
            }
            if (info.getHeadersCondition() != null
                    && CatUtils.isNotEmpty(info.getHeadersCondition().getExpressions())) {
                params.add("headers=" + info.getHeadersCondition());
            }
            if (info.getConsumesCondition() != null
                    && CatUtils.isNotEmpty(info.getConsumesCondition().getExpressions())) {
                params.add("consumes=" + info.getConsumesCondition());
            }
            if (info.getProducesCondition() != null
                    && CatUtils.isNotEmpty(info.getProducesCondition().getExpressions())) {
                params.add("produces=" + info.getProducesCondition());
            }
            if (info.getCustomCondition() != null) {
                params.add("custom=" + info.getCustomCondition());
            }
            if (params.size() > 0) {
                sb.append("@{").append(Joiner.on(',').skipNulls().join(params)).append("}");
            }
            url = sb.toString();
            urlCache.put(info, url);
        }
        return url;
    }

    private void initHandlerMethods() {
        if (!requestMappingInfosInit) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingInfoHandlerMapping
                    .getHandlerMethods();
            if (CatUtils.isNotEmpty(handlerMethods)) {
                for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
                    requestMappingInfos.put(entry.getValue().createWithResolvedBean(), entry.getKey());
                }
                requestMappingInfosInit = true;
            }
        }
    }
}