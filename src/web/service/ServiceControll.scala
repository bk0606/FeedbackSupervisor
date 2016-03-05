package cas.web.service

import akka.actor.Actor
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import cas.web.pages._

trait ServiceControll extends HttpService {

  val route = respondWithMediaType(`text/html`) {
      IndexPage("") ~
      ConfigurePage("configure") ~
      TestPage("t")
  }

  implicit def commonExceptionHandler(implicit log: LoggingContext) = ExceptionHandler {
    case ex => {
      println("Unhandled error occurs. Exception: `" + ex.getMessage + "`")
      complete(StatusCodes.InternalServerError, "Something goes wrong, try to reload last page.")
    }
  }  
}

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class AServiceControll extends Actor with ServiceControll {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  def receive = runRoute(route)
}