import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._

object Connectors {
   private val configFactory = ConfigFactory.load()
   private val hosts = configFactory.getStringList("cassandra.hosts").asScala
   private val keyspace = configFactory.getString("cassandra.keyspace")
   lazy val connector: CassandraConnection = ContactPoints(hosts).keySpace(keyspace)
}
