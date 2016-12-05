/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.apache.directory.fortress.web.bootstrap.page;

import org.apache.directory.fortress.web.bootstrap.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Sets up the standard JavaScript and CSS framework(s) for all pages in the
 * Commander.
 */
public abstract class AbstractPage extends WebPage {
	private static final long serialVersionUID = 1L;

	protected AbstractPage() {
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		// add a comment with the current page class to the head section to make
		// it easy to find the current page. Do this at the top of the page.
		if (getApplication().usesDevelopmentConfig()) {
			response.render(StringHeaderItem.forString("\n<!-- Page: " + getClass() + " -->\n"));
		}

		// Render the title of this page
		response.render(StringHeaderItem.forString(
				"<title>" + new ResourceModel(getClass().getSimpleName() + ".title").getObject() + "</title>"));

		super.renderHead(response);

		Bootstrap.renderHead(response);

		// Render the custom CSS for the application
		response.render(CssUrlReferenceHeaderItem.forUrl("css/fortress.css"));

		// If page specific CSS exists (pageClass.css) load that as the last
		// reference to allow overrides of the custom CSS of the application or
		// the general CSS of Bootstrap.

		String pageSpecificCss = getClass().getSimpleName() + ".css";
		if (getClass().getResource(pageSpecificCss) != null) {
			CssResourceReference cssReference = new CssResourceReference(getClass(), pageSpecificCss);

			// Mount the resource under /css to avoid the security check
			// installed in the web.xml. Class path resources are normally
			// served from /wicket. By mounting the resource, this security
			// filter is bypassed (and shouldn't trigger anyway).

			WebApplication.get().mountResource("/css/" + pageSpecificCss, cssReference);
			response.render(CssReferenceHeaderItem.forReference(cssReference));
		}
	}
}
