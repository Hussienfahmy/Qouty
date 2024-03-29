// Generated by Dagger (https://dagger.dev).
package com.hussien.qouty.di;

import com.hussien.qouty.data.AppDatabase;
import com.hussien.qouty.data.QuotesDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideDaoFactory implements Factory<QuotesDao> {
  private final AppModule module;

  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideDaoFactory(AppModule module, Provider<AppDatabase> dbProvider) {
    this.module = module;
    this.dbProvider = dbProvider;
  }

  @Override
  public QuotesDao get() {
    return provideDao(module, dbProvider.get());
  }

  public static AppModule_ProvideDaoFactory create(AppModule module,
      Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideDaoFactory(module, dbProvider);
  }

  public static QuotesDao provideDao(AppModule instance, AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(instance.provideDao(db));
  }
}
