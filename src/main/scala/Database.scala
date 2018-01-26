import com.outworkers.phantom.ResultSet
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import scala.concurrent.Future
import Connectors.connector

class SongsDatabase (override val connector: CassandraConnection) extends Database[SongsDatabase](connector){
   object SongsModel extends SongsModel with connector.Connector
   object SongsByArtistModel extends SongByArtistModel with connector.Connector
   object SongsByTitleModel extends SongsByTitleModel with connector.Connector
   object SongsByAlbumModel extends SongsByAlbumModel with connector.Connector
   def saveOrUpdate(song: Song) : Future[ResultSet] = {
      Batch.logged
         .add(SongsModel.store(song))
         .add(SongsByAlbumModel.store(song))
         .add(SongsByTitleModel.store(song))
         .add(SongsByArtistModel.store(song))
         .future()
   }
   def deleteSong(song: Song) : Future[ResultSet] = {
      Batch.logged
         .add(SongsModel.delete.where(_.id eqs song.id))
         .add(SongsByAlbumModel.delete.where(_.album eqs song.album).and(_.id eqs song.id))
         .add(SongsByTitleModel.delete.where(_.title eqs song.title).and(_.id eqs song.id))
         .add(SongsByArtistModel.delete.where(_.artist eqs song.artist).and(_.id eqs song.id))
         .future()

   }
}

object SongsDatabase extends SongsDatabase(connector)
