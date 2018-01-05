package cito.examples.echo.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.core.MediaType;

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
	 */
	public void onSend(
			@Observes @OnSend("echo")
			Message msg,
			@Body @Valid @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
			String body)
	{
		this.support.sendTo(msg.sessionId(), "queue/echo", body, MediaType.TEXT_PLAIN_TYPE);
	}
}
