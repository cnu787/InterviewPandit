package com.testmyinterview.portal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.util.Constants;

@Controller
public class LoginController {

	@Autowired
	protected AuthenticationManager authenticationManager;
	@Autowired
	private ProfileDAO profileDAO;
	/**
	 * This method is used to check login details based on input like 
	 * j_username,j_password,authType
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/individualLoginCheck.do")
	public String inidividualLoginCheck(HttpServletRequest request) {
		String returnUrl = "";
		String userName = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		String authType = request.getParameter("authType");

		String user_Role = "";

		user_Role = profileDAO.checkLoginDetails(userName, password);
		if ((user_Role.equals(Constants.ROLE_EXTERNAL) && authType
				.equals(Constants.ROLE_EXTERNAL))
				|| (user_Role.equals(Constants.ROLE_INTERNAL) && authType
						.equals(Constants.ROLE_INTERNAL))) {
			try {
				Authentication req = new UsernamePasswordAuthenticationToken(
						userName, password);
				Authentication result = authenticationManager.authenticate(req);
				SecurityContextHolder.getContext().setAuthentication(result);
				returnUrl = "redirect:/myAuthenticationSuccessHandler.do";
			} catch (Exception e) {
				if (e instanceof org.springframework.security.authentication.DisabledException) {
					if (authType.equals(Constants.ROLE_EXTERNAL)) {
						returnUrl = "redirect:/login.do";
					} else if (authType.equals(Constants.ROLE_INTERNAL)) {
						returnUrl = "redirect:/evalLogin.do";
					}
					request.getSession()
							.setAttribute("loginmessage",
									"Please click the link sent to your Email Address to Login.");
				}
			}
		} else if (user_Role.equals(Constants.ROLE_EXTERNAL)
				&& authType.equals(Constants.ROLE_INTERNAL)) {
			request.getSession().setAttribute("loginmessage",
					"You cannot login as Evaluator.");
			returnUrl = "redirect:/evalLogin.do";
		} else if (user_Role.equals(Constants.ROLE_INTERNAL)
				&& authType.equals(Constants.ROLE_EXTERNAL)) {
			request.getSession().setAttribute("loginmessage",
					"You cannot login as Applicant.");
			returnUrl = "redirect:/login.do";
		} else if (authType.equals(Constants.ROLE_EXTERNAL)) {
			request.getSession().setAttribute("loginmessage",
					"Bad credentials.");
			returnUrl = "redirect:/login.do";
		} else if (authType.equals(Constants.ROLE_INTERNAL)) {
			request.getSession().setAttribute("loginmessage",
					"Bad credentials.");
			returnUrl = "redirect:/evalLogin.do";
		}
		return returnUrl;
	}
}
