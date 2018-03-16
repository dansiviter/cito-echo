/*
 * Copyright 2016-2017 Daniel Siviter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cito.examples.echo.service;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;

import cito.annotation.Body;
import cito.annotation.OnSend;
import cito.event.Message;
import cito.server.MessagingSupport;

/**
 * A simple service to handle messages and echo them back.
 * 
 * @author Daniel Siviter
 * @since v1.0 [16 Dec 2017]
 */
@ApplicationScoped
public class EchoService {
	@Inject
	private MessagingSupport support;

	/**
	 * Called when a {@code SEND} message for destination {@code echo} is recieved.
	 * 
	 * @param msg the message event.
	 * @param body the payload from the message. This will be validated against the {@link Pattern}.
	 * @throws IOException
	 */
	public void onSend(
			@Observes @OnSend("echo")
			Message msg,
			@Body @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
			String body)
	throws IOException
	{
		this.support.sendTo(msg.sessionId(), "queue/echo", body, TEXT_PLAIN_TYPE);
	}
}
