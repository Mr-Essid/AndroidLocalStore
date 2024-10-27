package edu.google.compose.databasepracticeroom.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation
import java.util.UUID


@Entity(primaryKeys = ["product_id", "tag_id"], indices = [Index("tag_id")])
data class TagProductCrossRef(
    @ColumnInfo("product_id")
    val productId: UUID,
    @ColumnInfo("tag_id")
    val tagId: Int
)

data class TagProduct(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "tag_id",
        associateBy = Junction(TagProductCrossRef::class)
    )
    val tags: List<Tag>
)
