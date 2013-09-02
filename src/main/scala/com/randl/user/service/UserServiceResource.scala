package com.randl.user.service

import com.sun.jersey.spi.resource.Singleton
import javax.ws.rs.{POST, Consumes, Produces, Path}
import javax.ws.rs.core.{Response, Context}
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Response.Status
import sun.security.provider.certpath.OCSPResponse.ResponseStatus
import com.restfb.{DefaultFacebookClient, FacebookClient}
import com.restfb.types.{User => FbUser}
import com.codahale.jerkson.Json

@Singleton
@Path("/")
@Produces(Array("application/json"))
@Consumes(Array("application/json"))
class UserServiceResource {
  private val staticSalt = "This is my static part of the salt"

  @POST
  @Path("")
  def create(user: User, @Context context: HttpServletRequest): Response = {
    Response.status(Status.OK).entity(Map("message" -> "service stub working, now implement me faster!")).build
  }

  @POST
  @Path("userdata")
  def userdata(token: String): Response = {
    println("userdata called")
    val client: FacebookClient = new DefaultFacebookClient(token)
    println("got client")
    val fbUser: FbUser = client.fetchObject("me", classOf[FbUser])
    println("user:\n " + Json.generate(fbUser))

    Response.status(Status.OK).entity(Map("name" -> fbUser.getName)).build
  }

}

case class User(userId: String, password: String, salt: String, provider: String)