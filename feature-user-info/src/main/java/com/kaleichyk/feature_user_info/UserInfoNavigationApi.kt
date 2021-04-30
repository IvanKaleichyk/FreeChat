package com.kaleichyk.feature_user_info

import android.os.Bundle

interface UserInfoNavigationApi {

    fun fromUserInfoFeatureToSignFeature()
    fun fromUserInfoFeatureToMessagesFeature(bundle: Bundle)

}