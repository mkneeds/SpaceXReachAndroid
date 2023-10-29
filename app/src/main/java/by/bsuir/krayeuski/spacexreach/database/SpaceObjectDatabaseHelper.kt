import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SpaceObjectDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "space_object_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_SPACE_OBJECTS = "space_objects"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_DATE_UTC = "date_utc"
        private const val KEY_DATE_LOCAL = "date_local"
        private const val KEY_ROCKET = "rocket"
        private const val KEY_SUCCESS = "success"
        private const val KEY_DETAILS = "details"
        private const val KEY_PATCH_SMALL = "patch_small"
        private const val KEY_PATCH_LARGE = "patch_large"
        private const val KEY_REDDIT_CAMPAIGN = "reddit_campaign"
        private const val KEY_REDDIT_LAUNCH = "reddit_launch"
        private const val KEY_REDDIT_MEDIA = "reddit_media"
        private const val KEY_REDDIT_RECOVERY = "reddit_recovery"
        private const val KEY_PRESSKIT = "presskit"
        private const val KEY_WEBCAST = "webcast"
        private const val KEY_YOUTUBE_ID = "youtube_id"
        private const val KEY_ARTICLE = "article"
        private const val KEY_WIKIPEDIA = "wikipedia"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SPACE_OBJECTS_TABLE = (
                "CREATE TABLE $TABLE_SPACE_OBJECTS ("
                        + "$KEY_ID TEXT PRIMARY KEY,"
                        + "$KEY_NAME TEXT,"
                        + "$KEY_DATE_UTC TEXT,"
                        + "$KEY_DATE_LOCAL TEXT,"
                        + "$KEY_ROCKET TEXT,"
                        + "$KEY_SUCCESS INTEGER,"
                        + "$KEY_DETAILS TEXT,"
                        + "$KEY_PATCH_SMALL TEXT,"
                        + "$KEY_PATCH_LARGE TEXT,"
                        + "$KEY_REDDIT_CAMPAIGN TEXT,"
                        + "$KEY_REDDIT_LAUNCH TEXT,"
                        + "$KEY_REDDIT_MEDIA TEXT,"
                        + "$KEY_REDDIT_RECOVERY TEXT,"
                        + "$KEY_PRESSKIT TEXT,"
                        + "$KEY_WEBCAST TEXT,"
                        + "$KEY_YOUTUBE_ID TEXT,"
                        + "$KEY_ARTICLE TEXT,"
                        + "$KEY_WIKIPEDIA TEXT"
                        + ")"
                )
        db.execSQL(CREATE_SPACE_OBJECTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_SPACE_OBJECTS")

        onCreate(db)
    }
}
