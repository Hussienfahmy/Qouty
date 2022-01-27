// Generated by Dagger (https://dagger.dev).
package com.hussien.qouty.utilities;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NotificationUtils_Factory implements Factory<NotificationUtils> {
  private final Provider<Context> contextProvider;

  private final Provider<ActionsUtils> actionsUtilsProvider;

  public NotificationUtils_Factory(Provider<Context> contextProvider,
      Provider<ActionsUtils> actionsUtilsProvider) {
    this.contextProvider = contextProvider;
    this.actionsUtilsProvider = actionsUtilsProvider;
  }

  @Override
  public NotificationUtils get() {
    return newInstance(contextProvider.get(), actionsUtilsProvider.get());
  }

  public static NotificationUtils_Factory create(Provider<Context> contextProvider,
      Provider<ActionsUtils> actionsUtilsProvider) {
    return new NotificationUtils_Factory(contextProvider, actionsUtilsProvider);
  }

  public static NotificationUtils newInstance(Context context, ActionsUtils actionsUtils) {
    return new NotificationUtils(context, actionsUtils);
  }
}