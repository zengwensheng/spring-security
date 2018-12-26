package com.zws.core.social;


import com.zws.core.support.JsonUtils;
import com.zws.core.support.SecurityConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.social.connect.Connection;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/11
 */
public class ConnectStatusView extends AbstractView {


    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) map.get("connectionMap");

        Map<String, Boolean> result = new HashMap<>();
        for (String key : connections.keySet()) {
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        response.setContentType(SecurityConstants.DEFAULT_CONTENT_TYPE);
        response.getWriter().write(JsonUtils.writeValueAsString(result));
    }
}
