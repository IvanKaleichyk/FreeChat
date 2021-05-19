package com.kaleichyk.utils

import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.user.UserResult

fun getCheckResultError(res: Int) = CheckResult.Error(StringConverter.get().convert(res))

fun getDialogResultError(res: Int) = DialogResult.Error(StringConverter.get().convert(res))

fun getUserResultError(res: Int) = UserResult.Error(StringConverter.get().convert(res))