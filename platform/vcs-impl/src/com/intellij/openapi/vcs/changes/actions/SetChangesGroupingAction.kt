// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.openapi.vcs.changes.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vcs.changes.ui.ChangesGroupingSupport

abstract class SetChangesGroupingAction : ToggleAction(), DumbAware {
  init {
    isEnabledInModalContext = true
  }
  abstract val groupingKey: String

  override fun update(e: AnActionEvent) = super.update(e).also {
    e.presentation.isEnabledAndVisible = getGroupingSupport(e)?.isAvailable(groupingKey) ?: false
  }

  override fun isSelected(e: AnActionEvent) = getGroupingSupport(e)?.groupingKey == groupingKey

  override fun setSelected(e: AnActionEvent, state: Boolean) {
    if (state) {
      getGroupingSupport(e)!!.groupingKey = groupingKey
    }
  }

  protected fun getGroupingSupport(e: AnActionEvent) = e.getData(ChangesGroupingSupport.KEY)
}

class SetNoneChangesGroupingAction : SetChangesGroupingAction() {
  override val groupingKey get() = "none"
}

class SetDirectoryChangesGroupingAction : SetChangesGroupingAction() {
  override val groupingKey get() = "directory"
}

class SetModuleChangesGroupingAction : SetChangesGroupingAction() {
  override val groupingKey get() = "module"
}