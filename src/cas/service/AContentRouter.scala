package cas.service

import akka.actor.Actor
import akka.pattern.pipe
import cas.subject.Subject

object RoutingScheme {
  case object PullSubjects
  case class PulledSubjects(subjs: List[Subject])
  case class PushEstimations(estims: List[Estimation])
}

class AContentRouter(dealer: ContentDealer) extends Actor {
  import RoutingScheme._
  import cas.web.interface.ImplicitActorSystem._
  import system.dispatcher

  override def receive = {
    // TODO: Handle service not available
    case PullSubjects => {
      dealer.pullSubjectsChunk.map(PulledSubjects).pipeTo(sender())
    }

    case PushEstimations(estims) => {
      dealer.pushEstimationsChunk(estims)
    }
  }
}
