package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.BakulDatabase
import com.example.ucp2.repository.barang.LocalRepositoryBrg
import com.example.ucp2.repository.barang.RepositoryBrg
import com.example.ucp2.repository.supplier.LocalRepositorySup
import com.example.ucp2.repository.supplier.RepositorySup

interface InterfaceContainerApp {
    val repositorySpl: RepositorySup
    val repositoryBrg: RepositoryBrg
}

class ContainerApp(private val context: Context) :
    InterfaceContainerApp {
    override val repositoryBrg: RepositoryBrg by lazy {
        LocalRepositoryBrg(BakulDatabase.getDatabase(context).barangDao())
    }
    override val repositorySpl: RepositorySup by lazy {
        LocalRepositorySup(BakulDatabase.getDatabase(context).supplierDao())
    }
}