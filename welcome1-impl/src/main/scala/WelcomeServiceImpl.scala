
import akka.actor.{ActorRef, Cancellable, Props}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import play.api.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}


class WelcomeServiceImpl(externalService: ExternalServices)(implicit ec: ExecutionContext) extends WelcomeService {

  val system = akka.actor.ActorSystem("Lagom-ActorSystem")

  val DataActor: ActorRef = system.actorOf(Props(classOf[DataActor], externalService))

  Logger.info("-------------SCHEDULER STARTED---------------")
  val cancellable: Cancellable =
    system.scheduler.schedule(
      0 milliseconds,
      5 seconds,
      DataActor,
      UnManagedServiceCall)

  override def welcomeUser(name: String) =
    ServiceCall { _=>
      Future.successful("Hello " + name)

  }

  override def testExternal() = ServiceCall{ _ =>
      val result: Future[Pages] = externalService.getExternalServiceCall.invoke()
    result.map(response => response)

  }

}