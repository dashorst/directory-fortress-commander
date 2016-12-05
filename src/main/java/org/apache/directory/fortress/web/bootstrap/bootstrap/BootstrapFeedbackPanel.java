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

import static org.apache.wicket.feedback.FeedbackMessage.ERROR;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class BootstrapFeedbackPanel extends FeedbackPanel {
	private static final long serialVersionUID = 1L;

	public BootstrapFeedbackPanel(String id) {
		super(id);
	}

	@Override
	protected String getCSSClass(FeedbackMessage message) {
		switch (message.getLevel()) {
		case ERROR:
			return "alert-danger";
		case FeedbackMessage.WARNING:
			return "alert-warning";
		case FeedbackMessage.INFO:
			return "alert-info";
		case FeedbackMessage.SUCCESS:
			return "alert-success";
		}
		return "alert-info";
	}
}
