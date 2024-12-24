package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.data.dao.BarangDao
import com.example.ucp2.data.dao.SupplierDao

@Database(entities = [Barang::class, Supplier::class], version = 1, exportSchema = false)
abstract class BakulDatabase : RoomDatabase() {

    abstract fun barangDao(): BarangDao
    abstract fun supplierDao(): SupplierDao

    companion object {
        @Volatile
        private var Instance: BakulDatabase? = null

        fun getDatabase(context: Context): BakulDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BakulDatabase::class.java,
                    "Bakul Database"
                ).build().also { Instance = it }
            }
        }
    }
}