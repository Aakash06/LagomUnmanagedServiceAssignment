import akka.actor.Actor
import play.api.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class DataActor(externalService: ExternalServices) extends Actor {
  override def receive: Receive = {
    case UnManagedServiceCall =>
      externalService.getExternalServiceCall.invoke().onComplete {

        case Success(data) => Logger.info(data.toString)

        case Failure(exception) => Logger.error(exception.getStackTrace.toString)

      }

  }

}

case object UnManagedServiceCall
