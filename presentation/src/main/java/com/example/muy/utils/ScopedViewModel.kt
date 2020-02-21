package com.example.muy.utils

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.muy.domain.common.Scope

abstract class ScopedViewModel : ViewModel(), Scope by Scope.Implementation() {

    init {
        this.initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}
