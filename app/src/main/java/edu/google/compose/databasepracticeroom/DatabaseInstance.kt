package edu.google.compose.databasepracticeroom

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.google.compose.databasepracticeroom.database.MobileCommerceDB
import java.util.UUID

class DatabaseInstance {
    companion object {
        var databaseRef: MobileCommerceDB? = null
        fun getInstance(context: Context): MobileCommerceDB  {
            if(databaseRef == null)
                databaseRef = Room.databaseBuilder(context.applicationContext, MobileCommerceDB::class.java, "mainDB")
                    .addCallback(object: RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            var statement = db.compileStatement(
                                "INSERT INTO admins(id, username, name, lastname, email, phone_number, password, image_file_path) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                            )
                            statement.bindString(1, UUID.randomUUID().toString())
                            statement.bindString(2, "admin")
                            statement.bindString(3, "admin")
                            statement.bindString(4, "admin")
                            statement.bindString(5, "admin@go.com")
                            statement.bindString(6, "53569658")
                            statement.bindString(7, "admin")
                            statement.bindString(8, "")
                            Log.d("TAG", "onCreate: ${statement.executeInsert()} result of insertion ")

                            statement = db.compileStatement(
                                "INSERT INTO categories (id, label, description)" +
                                        "VALUES (?, ?, ?)"
                            )
                            statement.bindString(1, UUID.randomUUID().toString())
                            statement.bindString(2, "electronics")
                            statement.bindString(3, "devices and gadgets like smartphones, laptops, tablets, cameras, and accessories. Often includes the latest technology for communication, entertainment, and productivity.")
                            statement.executeInsert()

                            statement.bindString(1, UUID.randomUUID().toString())
                            statement.bindString(2, "clothing and apparel")
                            statement.bindString(3, "fashion items, including men’s, women’s, and children’s wear. This category covers everything from casual and formal clothing to activewear and accessories like shoes, hats, and bags.")
                            statement.executeInsert()

                            statement.bindString(1, UUID.randomUUID().toString())
                            statement.bindString(2, "home and kitchen")
                            statement.bindString(3, "products for home improvement, furniture, kitchenware, and decor. Items in this category are designed to improve functionality, comfort, and aesthetics in your living space.")
                            statement.executeInsert()


                            statement.bindString(1, UUID.randomUUID().toString())
                            statement.bindString(2, "health and beauty")
                            statement.bindString(3, "personal care products, skincare, cosmetics, vitamins, and supplements. these products focus on enhancing well-being, beauty, and self-care routines.")
                            statement.executeInsert()

                            statement.bindString(1, UUID.randomUUID().toString())
                            statement.bindString(2, "toys and games")
                            statement.bindString(3, "items for children’s entertainment and development, including educational toys, puzzles, board games, and outdoor play equipment.")
                            statement.executeInsert()

                            statement.bindString(1, UUID.randomUUID().toString())
                            statement.bindString(2, "books and stationery")
                            statement.bindString(3, "printed books, e-books, and writing supplies. This category includes educational materials, novels, notebooks, and other items for reading and writing.")
                            statement.executeInsert()

                            statement = db.compileStatement("INSERT INTO tags ( tag_id, label ) VALUES ( ?, ?)")
                            var idInteger = 1;
                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "football")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "toy")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "table")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "sport")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "building")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "books")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "phones")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "arduino")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "esp32")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "mosfits")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "drives")
                            statement.executeInsert()

                            statement.bindString(1, (idInteger++).toString())
                            statement.bindString(2, "auto")
                            statement.executeInsert()

                            statement.bindString(1, idInteger.toString())
                            statement.bindString(2, "modems")
                            statement.executeInsert()

                        }

                    })
                    .build()
            return databaseRef!!
        }
    }


}