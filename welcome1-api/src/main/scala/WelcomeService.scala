
import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait WelcomeService extends Service {

  override final def descriptor: Descriptor = {
    import Service._
    named("welcome1")
      .withCalls(
        restCall(Method.GET, "/welcome/api/:name", welcomeUser _),
        restCall(Method.GET, "/welcome/external", testExternal _)

      )
      .withAutoAcl(true)

  }

  def welcomeUser(name: String): ServiceCall[NotUsed, String]

  def testExternal(): ServiceCall[NotUsed, Pages]

}