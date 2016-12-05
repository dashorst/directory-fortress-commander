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
package org.apache.directory.fortress.web.bootstrap.page.insecure;

import java.security.Principal;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.directory.fortress.web.LaunchPage;
import org.apache.directory.fortress.web.bootstrap.app.FortressSession;
import org.apache.directory.fortress.web.bootstrap.bootstrap.BootstrapFeedbackPanel;
import org.apache.directory.fortress.web.bootstrap.page.AbstractPage;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;

/**
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory
 *         Project</a>
 */
public class LoginPage extends AbstractPage {
	private static final long serialVersionUID = 1L;

	private transient String userId;

	private transient String password;

	public LoginPage() {
		if (FortressSession.get().isSignedIn())
			throw new RestartResponseException(Application.get().getHomePage());

		TransparentWebMarkupContainer body = new TransparentWebMarkupContainer("body");
		add(body);

		// alternate the background image depending on the day of week
		String loginImage = "login-image-" + (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) % 3 + 1);
		body.add(AttributeModifier.replace("class", loginImage));

		add(new BootstrapFeedbackPanel("feedback"));

		Form<?> form = new StatelessForm<Void>("form") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				HttpServletRequest servletReq = (HttpServletRequest) getRequest().getContainerRequest();
				Principal principal = servletReq.getUserPrincipal();

				if (principal == null) {
					try {
						servletReq.login(userId, password);
						setResponsePage(LaunchPage.class);
					} catch (ServletException se) {
						getRequestCycle().replaceAllRequestHandlers(new RedirectRequestHandler("/login/error.html"));
					}
				} else {
					setResponsePage(LaunchPage.class);
				}
			}
		};
		add(form);

		TextField<String> userIdField = new TextField<>("userid", new PropertyModel<String>(this, "userId"));
		userIdField.setLabel(new ResourceModel("LoginPage.username"));
		userIdField.setRequired(true);
		form.add(userIdField);

		PasswordTextField passwordField = new PasswordTextField("password",
				new PropertyModel<String>(this, "password"));
		passwordField.setLabel(new ResourceModel("LoginPage.password"));
		passwordField.setRequired(true);
		form.add(passwordField);
	}

	@Override
	protected void onDetach() {
		password = null;
		super.onDetach();
	}
}
