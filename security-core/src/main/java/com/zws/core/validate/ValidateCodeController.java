package com.zws.core.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@RestController
public class ValidateCodeController extends AbstractController {


    @Autowired
    private  ValidateCodeHandlerHolder validateCodeHandlerHolder;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }


    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request,HttpServletResponse response,@PathVariable  String type) {
        validateCodeHandlerHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }



}
