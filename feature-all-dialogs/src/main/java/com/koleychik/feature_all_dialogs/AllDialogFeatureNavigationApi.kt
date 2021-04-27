package com.koleychik.feature_all_dialogs

import android.os.Bundle

interface AllDialogFeatureNavigationApi {

    fun fromDialogsFeatureGoToMessagesFeature(bundle: Bundle)
    fun fromDialogsFeatureGoToSearchingFeature(bundle: Bundle? = null)
    fun fromDialogsFeatureGoToUserInfoFeature(bundle: Bundle? = null)

}