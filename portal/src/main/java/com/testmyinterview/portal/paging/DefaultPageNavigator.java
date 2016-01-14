package com.testmyinterview.portal.paging;

import org.springframework.stereotype.Service;


@Service
public class DefaultPageNavigator implements PageNavigator {

	/* (non-Javadoc)
	 * @see com.testmyinterview.portal.paging.PageNavigator#buildPageNav(java.lang.String, int, int, int, int)
	 * this method is used to build pagination user interface based on path,resultSize,page,pageSize,pageNavTrail
	 *    
	 */
	public String buildPageNav(String path, int resultSize, int page,
			int pageSize, int pageNavTrail) {
		int allPage = resultSize / pageSize;
		boolean isOdd = (resultSize % pageSize != 0);
		allPage = (isOdd ? allPage + 1 : allPage);
		int iStart = page - pageNavTrail;
		int iEnd = page + pageNavTrail - 1;
		if (iStart < 1) {
			iEnd = iEnd + (1 - iStart);
			iStart = 1;

			if (iEnd > allPage) {
				iEnd = allPage;
			}
		} else if (iEnd > allPage) {
			iStart = iStart - (iEnd - allPage);
			iEnd = allPage;

			if (iStart < 1) {
				iStart = 1;
			}
		}
		StringBuffer sb = new StringBuffer();
		if (page == 1) {
			sb.append("<li class='disabled'><a href='#' aria-label='Previous'> <span	aria-hidden='true'>&laquo;</span></a></li>");
		} else {
			sb.append("<li><a class='pageClick' href='")
					.append(path + "" + (page - 1))
					.append("'aria-label='Previous'> <span	aria-hidden='true'>&laquo;</span></a></li>");
		}
		for (int i = iStart; i <= iEnd; i++) {
			if (i == page) {
				sb.append("<li class='active'><a href='").append(path)
						.append("'>").append(page).append("</a></li>")
						.append(" ");
			} else {
				sb.append("<li><a class='pageClick' href='").append(path)
						.append(i).append("'>").append(i).append("</a></li>")
						.append(" ");
			}
		}
		if (page == iEnd) {
			sb.append("<li class='disabled'><a href='#' aria-label='Next'> <span	aria-hidden='true'>&raquo;</span></a></li>");
		} else {
			sb.append("<li><a class='pageClick' href='")
					.append(path + "" + (page + 1))
					.append("'aria-label='Next'> <span	aria-hidden='true'>&raquo;</span></a></li>");
		}
		return sb.toString();
	}
}