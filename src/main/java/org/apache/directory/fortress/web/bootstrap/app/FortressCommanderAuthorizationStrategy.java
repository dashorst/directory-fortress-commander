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

import org.apache.directory.fortress.web.bootstrap.page.AbstractPage;
import org.apache.directory.fortress.web.bootstrap.page.secure.AbstractSecurePage;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;

public class FortressCommanderAuthorizationStrategy implements IAuthorizationStrategy {
	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {

		// always allowed to instantiate a component that is not a page
		if (!Page.class.isAssignableFrom(componentClass)) {
			return true;
		}

		// only allowed to instantiate a secure page when logged in
		if (AbstractSecurePage.class.isAssignableFrom(componentClass)) {
			return FortressSession.get().isSignedIn();
		}

		// subpages of AbstractPage that are not secure are allowed
		if (AbstractPage.class.isAssignableFrom(componentClass)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		return true;
	}

	@Override
	public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
		return true;
	}
}
