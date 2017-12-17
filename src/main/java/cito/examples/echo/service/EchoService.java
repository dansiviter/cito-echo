package cito.examples.echo.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import cito.annotation.Body;
import cito.annotation.OnSend;
import cito.event.Message;
import cito.server.MessagingSupport;
import cito.server.SecurityContext;

/**
 * A service to handle messages and echo them back.
 * 
 * @author Daniel Siviter
 * @since v1.0 [16 Dec 2017]
 */
@ApplicationScoped
public class EchoService {
	@Inject
	private MessagingSupport support;

	/**
	 * 
	 * @param msg
	 */
	public void onMessage(@Observes @OnSend("echo") Message msg, SecurityContext securityCtx, @Body String payload) {
		this.support.sendTo(msg.sessionId(), "topic/echo", payload);
	}
}
