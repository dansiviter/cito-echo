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
package cito.examples.echo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.remoting.impl.invm.InVMConnectorFactory;

/**
 * Produces an 'In-VM' {@link ConnectionFactory}.
 * 
 * @author Daniel Siviter
 * @since v1.0 [14 Mar 2018]
 */
@ApplicationScoped
public class ConnectionFactoryProvider {
	@Produces @ApplicationScoped
	public ConnectionFactory connectionFactory() {
		return ActiveMQJMSClient.createConnectionFactoryWithoutHA(
				JMSFactoryType.XA_CF,
				new TransportConfiguration(InVMConnectorFactory.class.getName()));
	}
}
