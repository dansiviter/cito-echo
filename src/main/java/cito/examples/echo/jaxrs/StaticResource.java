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
package cito.examples.echo.jaxrs;

import static javax.ws.rs.core.MediaType.APPLICATION_SVG_XML;

import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Resource to serve static page elements.
 * 
 * @author Daniel Siviter
 * @since v1.0 [16 Dec 2017]
 */
@Path("/")
public class StaticResource {
	@Inject
	private ServletContext servletCtx;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response get() {
		return get("/index.html");
	}

	@GET
	@Path("{path:.*}")
	@Produces({ "text/css", "application/javascript", "image/png", APPLICATION_SVG_XML, "image/x-icon" })
	public Response get(@PathParam("path") String path) {

		final InputStream in = this.servletCtx.getResourceAsStream(path);
		if (in == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(in, this.servletCtx.getMimeType(path)).build();
	}
}
