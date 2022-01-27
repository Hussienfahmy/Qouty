// Generated by Dagger (https://dagger.dev).
package com.hussien.qouty.ui.settings;

import androidx.work.WorkManager;
import com.hussien.qouty.data.QuotesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SettingsDataStoreManager> settingsManagerProvider;

  private final Provider<QuotesRepository> repositoryProvider;

  private final Provider<WorkManager> workManagerProvider;

  public SettingsViewModel_Factory(Provider<SettingsDataStoreManager> settingsManagerProvider,
      Provider<QuotesRepository> repositoryProvider, Provider<WorkManager> workManagerProvider) {
    this.settingsManagerProvider = settingsManagerProvider;
    this.repositoryProvider = repositoryProvider;
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(settingsManagerProvider.get(), repositoryProvider.get(), workManagerProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SettingsDataStoreManager> settingsManagerProvider,
      Provider<QuotesRepository> repositoryProvider, Provider<WorkManager> workManagerProvider) {
    return new SettingsViewModel_Factory(settingsManagerProvider, repositoryProvider, workManagerProvider);
  }

  public static SettingsViewModel newInstance(SettingsDataStoreManager settingsManager,
      QuotesRepository repository, WorkManager workManager) {
    return new SettingsViewModel(settingsManager, repository, workManager);
  }
}
