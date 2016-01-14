package com.testmyinterview.portal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KeepSession {

	private static final Logger logger = LoggerFactory
			.getLogger(KeepSession.class);

	/**
	 * This method is to get 1X1 pixel image
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/keepSession", method = RequestMethod.GET)
	@ResponseBody
	public byte[] keepSession(HttpServletResponse response) {
		byte[] img = null;
		ClassPathResource cpr = new ClassPathResource("track.gif");
		OutputStream o;
		try {
			o = response.getOutputStream();
			InputStream is = new FileInputStream(cpr.getFile());
			img = new byte[32 * 1024];
			int nRead = 0;
			while ((nRead = is.read(img)) != -1) {
				o.write(img, 0, nRead);
			}
			o.flush();
			o.close();
			is.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return img;

	}

}
