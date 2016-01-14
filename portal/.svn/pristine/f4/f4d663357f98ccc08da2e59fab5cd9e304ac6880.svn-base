package com.testmyinterview.portal;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testmyinterview.portal.dao.InternalDAO;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class ElearningController {
	
	@Autowired
	private InternalDAO internalDAO;
	@Autowired
	private TmiUtil tmiUtil;

	/**
	 * This method is get course data from elearning site
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/courseResponse")
	public void courseResponse(HttpServletRequest request, Model model) {	
		String status=request.getParameter("isComplete")==null?"":request.getParameter("isComplete");
		System.out.println("status="+status);
		if(!status.trim().isEmpty() && status.equals("1")){
			internalDAO.updateElearningStatus("3",tmiUtil.getUserId());
		}

	}

	/**
	 * This method is get course data from elearning site
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/assessmentResponse")
	public void assessmentResponse(HttpServletRequest request, Model model) {		
		String status=request.getParameter("isComplete")==null?"":request.getParameter("isComplete");
		System.out.println("status="+status);
		if(!status.trim().isEmpty() && status.equals("1")){
			internalDAO.updateElearningStatus("2",tmiUtil.getUserId());
		}
	}

}
