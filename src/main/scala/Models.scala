import com.outworkers.phantom.dsl._
import scala.concurrent.Future

abstract class SongsModel extends Table[SongsModel, Song] {
   override def tableName: String = "songs"
   object id extends TimeUUIDColumn with PartitionKey {
      override lazy val name = "song_id"
   }
   object artist extends StringColumn
   object title extends StringColumn
   object album extends StringColumn
   def getSongById(id: UUID) : Future[Option[Song]] = {
      select.where(_.id eqs id).consistencyLevel_=(ConsistencyLevel.ONE).one()
   }
   def deleteById(id: UUID) : Future[ResultSet] = {
      delete.where(_.id eqs id).consistencyLevel_=(ConsistencyLevel.ONE).future()
   }
}

abstract class SongByArtistModel extends Table[SongByArtistModel, Song] {
   override def tableName: String = "songs_by_artist"
   object id extends TimeUUIDColumn with ClusteringOrder {
      override lazy val name = "song_id"
   }
   object artist extends StringColumn with PartitionKey
   object title extends StringColumn
   object album extends StringColumn
   def getSongsByArtist(artist: String) : Future[Option[Song]] = {
      select.where(_.artist eqs artist).consistencyLevel_=(ConsistencyLevel.ONE).one()
   }
   def deleteByArtistAndId(artist: String, id: UUID) : Future[ResultSet] = {
      delete.where(_.artist eqs artist).and(_.id eqs id).consistencyLevel_=(ConsistencyLevel.ONE).future()
   }
}

abstract class SongsByAlbumModel extends Table[SongsByAlbumModel, Song] {
   override def tableName: String = "songs_by_album"
   object id extends TimeUUIDColumn with ClusteringOrder {
      override lazy val name = "song_id"
   }
   object album extends StringColumn with PartitionKey
   object artist extends StringColumn
   object title extends StringColumn
   def getSongsByAlbum(album: String) : Future[Option[Song]] = {
      select.where(_.album eqs album).consistencyLevel_=(ConsistencyLevel.ONE).one()
   }
   def deleteByAlbumAndId(album: String, id: UUID) : Future[ResultSet] = {
      delete.where(_.album eqs album).and(_.id eqs id).consistencyLevel_=(ConsistencyLevel.ONE).future()
   }
}

abstract class SongsByTitleModel extends Table[SongsByTitleModel, Song] {
   override def tableName: String = "songs_by_title"
   object id extends TimeUUIDColumn with ClusteringOrder {
      override lazy val name = "song_id"
   }
   object title extends StringColumn with PartitionKey
   object album extends StringColumn
   object artist extends StringColumn
   def getSongsByTitle(title: String) : Future[Option[Song]] = {
      select.where(_.title eqs title).consistencyLevel_=(ConsistencyLevel.ONE).one()
   }
   def deleteByTitleAndId(title:String, id: UUID) : Future[ResultSet] = {
      delete.where(_.title eqs title).and(_.id eqs id).consistencyLevel_=(ConsistencyLevel.ONE).future()
   }
}
