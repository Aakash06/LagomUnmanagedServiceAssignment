import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait ExternalServices extends Service {

    def getExternalServiceCall: ServiceCall[NotUsed, Pages]

    override final def descriptor: Descriptor = {
      import Service._
      named("external-service")
        .withCalls(
          restCall(Method.GET,"/api/users%3Fpage%3D2", getExternalServiceCall _)
          //  restCall(Method.GET,"/api/users/2", getUser _)
        ).withAutoAcl(true)
    }

}


case class Pages(page:Int,per_page:Int,total :Int,total_pages: Int,data: List[UserData])

object Pages {

  implicit val format: Format[Pages] = Json.format[Pages]

}

/*
case class Data(data:UserData)

object Data{
  implicit val format: Format[Data] = Json.format[Data]
}
*/


case class UserData(id: Int)

object UserData {

  implicit val format: Format[UserData] = Json.format[UserData]

}

/*

case class UserData(userId: Int, id: Int, title:String, body: String)
object UserData {
  implicit val format: Format[UserData] = Json.format[UserData]
}
*/
