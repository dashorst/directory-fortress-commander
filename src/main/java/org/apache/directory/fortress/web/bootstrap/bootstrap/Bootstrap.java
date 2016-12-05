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
package org.apache.directory.fortress.web.bootstrap.bootstrap;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;
import org.apache.wicket.markup.head.MetaDataHeaderItem;

public class Bootstrap {
	public static void renderHead(IHeaderResponse response) {
		response.render(
				MetaDataHeaderItem.forMetaTag("viewport", "width=device-width, initial-scale=1, shrink-to-fit=no"));

		response.render(JavaScriptUrlReferenceHeaderItem
				.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));

		response.render(CssUrlReferenceHeaderItem
				.forUrl("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"));

		response.render(JavaScriptUrlReferenceHeaderItem
				.forUrl("https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.7/js/tether.min.js"));

		response.render(JavaScriptUrlReferenceHeaderItem
				.forUrl("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"));
	}
}
