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
package org.apache.directory.fortress.web.bootstrap.app;

import org.apache.directory.fortress.web.bootstrap.page.insecure.ErrorPage;
import org.apache.directory.fortress.web.bootstrap.page.insecure.LoginPage;
import org.apache.directory.fortress.web.bootstrap.page.secure.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.settings.ApplicationSettings;
import org.apache.wicket.settings.JavaScriptLibrarySettings;
import org.apache.wicket.settings.SecuritySettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class FortressCommanderApp extends WebApplication {
	@Override
	protected void init() {
		super.init();

		getComponentInstantiationListeners().add( new SpringComponentInjector( this ) );

		JavaScriptLibrarySettings javaScriptLibrarySettings = getJavaScriptLibrarySettings();
		javaScriptLibrarySettings.setJQueryReference(new UrlResourceReference(
				Url.parse("https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js")));

		ApplicationSettings applicationSettings = getApplicationSettings();
		applicationSettings.setAccessDeniedPage(LoginPage.class);
		applicationSettings.setInternalErrorPage(ErrorPage.class);
		applicationSettings.setPageExpiredErrorPage(getHomePage());

		SecuritySettings securitySettings = getSecuritySettings();
		securitySettings.setAuthorizationStrategy(new FortressCommanderAuthorizationStrategy());

		mountPage("/login/login.html", LoginPage.class);
		mountPage("/home.html", HomePage.class);
		mountPage("/index.html", HomePage.class);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new FortressSession(request);
	}
}
