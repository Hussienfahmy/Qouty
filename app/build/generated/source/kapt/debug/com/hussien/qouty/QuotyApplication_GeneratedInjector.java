package com.hussien.qouty;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = QuotyApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface QuotyApplication_GeneratedInjector {
  void injectQuotyApplication(QuotyApplication quotyApplication);
}
