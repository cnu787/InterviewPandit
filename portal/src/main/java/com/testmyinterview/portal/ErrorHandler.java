package com.testmyinterview.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.testmyinterview.portal.util.TmiUtil;

public class ErrorHandler implements HandlerExceptionResolver {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TmiUtil tmiUtil;

	/**
	 * this method is to catch the exception and propagate to the appropriate
	 * page
	 * @param request
	 * @param model
	 * @return
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		if (exception instanceof org.springframework.security.access.AccessDeniedException) {
			exception.printStackTrace();
			mv = new ModelAndView("redirect:/home.do");
		} else {
			exception.printStackTrace();
			String errorQuery = "insert into errorlog(errorlogmessage,errorlogtime)values(?,?)";
			jdbcTemplate.update(errorQuery, exception.getMessage(),
					tmiUtil.getCurrentGmtTime());
			mv = new ModelAndView("error");
		}
		return mv;
	}
}