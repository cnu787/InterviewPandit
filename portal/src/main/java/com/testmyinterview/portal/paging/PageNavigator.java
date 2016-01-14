package com.testmyinterview.portal.paging;

/**
 * this interface is used to declare build pagination method
 * 
 * 
 *
 */
public interface PageNavigator {
	String buildPageNav(String path, int resultSize, int page, int pageSize,
			int pageNavTrail);
}
