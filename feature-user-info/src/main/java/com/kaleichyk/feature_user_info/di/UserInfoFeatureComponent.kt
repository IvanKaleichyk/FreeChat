package com.kaleichyk.feature_user_info.di

import com.kaleichyk.feature_user_info.ui.UserInfoFragment
import dagger.Component

@Component(
    modules = [UserInfoFeatureModule::class],
    dependencies = [UserInfoFeatureDependencies::class]
)
interface UserInfoFeatureComponent : UserInfoFeatureApi {

    fun inject(fragment: UserInfoFragment)

}