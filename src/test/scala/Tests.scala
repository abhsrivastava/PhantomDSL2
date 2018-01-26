import com.outworkers.phantom.dsl._
import com.outworkers.util.testing._

class Tests extends CassandraSpec with SongsGenerator {
   var song : Song = null

   override def beforeAll(): Unit = {
      super.beforeAll()
      database.create()
      song = gen[Song]
   }

   override def afterAll() : Unit = {
      super.afterAll()
      database.SongsModel.deleteById(song.id)
   }

   "A Song" should "be insertable " in {
      val future = this.database.saveOrUpdate(song)
      whenReady(future) { result =>
         result isExhausted() shouldBe true
         result wasApplied() shouldBe true

      }
   }

   it should "find a song by id" in {
      val future = for {
         get <- this.database.SongsModel.getSongById(song.id)
      } yield get
      whenReady(future) { found =>
         found shouldBe defined
         found.get shouldEqual song
      }
   }

   it should "find a song by artist" in {
      val future = for {
         get <- this.database.SongsByArtistModel.getSongsByArtist(song.artist)
      } yield get
      whenReady(future) { found =>
         found shouldBe defined
         found.get shouldEqual song
      }
   }

   it should "find a song by title" in {
      val future = for {
         get <- this.database.SongsByTitleModel.getSongsByTitle(song.title)
      } yield get
      whenReady(future) { found =>
         found shouldBe defined
         found.get shouldEqual song
      }
   }

   it should "find a song by album" in {
      val future = for {
         get <- this.database.SongsByAlbumModel.getSongsByAlbum(song.album)
      } yield get
      whenReady(future) { found =>
         found shouldBe defined
         found.get shouldEqual song
      }
   }

   it should "not be able to find non-existing songs" in {
      val future = for {
         get <- this.database.SongsByTitleModel.getSongsByTitle("champoo")
      } yield get
      whenReady(future) { found =>
         found shouldBe None
      }
   }
}
