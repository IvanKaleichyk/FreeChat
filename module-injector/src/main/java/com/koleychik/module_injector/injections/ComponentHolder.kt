package com.koleychik.module_injector.injections

interface ComponentHolder<A : BaseApi, Dependencies : BaseDependencies, Destroyer : BaseDestroyer> {

    fun init(dependencies: Dependencies, destroyer: Destroyer)
    fun get(): A
    fun reset()

}