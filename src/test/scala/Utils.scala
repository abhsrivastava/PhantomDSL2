import com.outworkers.phantom.database.DatabaseProvider
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import com.outworkers.util.samplers.Sample
import com.datastax.driver.core.utils.UUIDs
import com.outworkers.util.testing.{ShortString, gen}

trait SongsDbProvider extends DatabaseProvider[SongsDatabase] {
   override def database: SongsDatabase = SongsDatabase
}

trait CassandraSpec extends FlatSpec
   with Matchers
   with Inspectors
   with ScalaFutures
   with OptionValues
   with BeforeAndAfterAll
   with SongsDbProvider

trait SongsGenerator {
   implicit object SongGenerator extends Sample[Song] {
      override def sample: Song = Song(
         UUIDs.timeBased(),
         gen[ShortString].value,
         gen[ShortString].value,
         gen[ShortString].value
      )
   }
}