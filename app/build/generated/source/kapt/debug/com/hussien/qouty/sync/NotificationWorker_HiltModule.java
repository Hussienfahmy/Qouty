package com.hussien.qouty.sync;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = NotificationWorker.class
)
public interface NotificationWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.hussien.quoty.sync.NotificationWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      NotificationWorker_AssistedFactory factory);
}