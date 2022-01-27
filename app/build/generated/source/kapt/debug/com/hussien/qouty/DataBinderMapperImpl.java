package com.hussien.qouty;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.hussien.qouty.databinding.ActivityMainBindingImpl;
import com.hussien.qouty.databinding.FragmentQoutesBindingImpl;
import com.hussien.qouty.databinding.QuoteLayoutBindingImpl;
import com.hussien.qouty.databinding.RowItemQuoteBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMAIN = 1;

  private static final int LAYOUT_FRAGMENTQOUTES = 2;

  private static final int LAYOUT_QUOTELAYOUT = 3;

  private static final int LAYOUT_ROWITEMQUOTE = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.hussien.qouty.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.hussien.qouty.R.layout.fragment_qoutes, LAYOUT_FRAGMENTQOUTES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.hussien.qouty.R.layout.quote_layout, LAYOUT_QUOTELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.hussien.qouty.R.layout.row_item_quote, LAYOUT_ROWITEMQUOTE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTQOUTES: {
          if ("layout/fragment_qoutes_0".equals(tag)) {
            return new FragmentQoutesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_qoutes is invalid. Received: " + tag);
        }
        case  LAYOUT_QUOTELAYOUT: {
          if ("layout/quote_layout_0".equals(tag)) {
            return new QuoteLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for quote_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_ROWITEMQUOTE: {
          if ("layout/row_item_quote_0".equals(tag)) {
            return new RowItemQuoteBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for row_item_quote is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(5);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "clickListener");
      sKeys.put(2, "quote");
      sKeys.put(3, "typeFace");
      sKeys.put(4, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_main_0", com.hussien.qouty.R.layout.activity_main);
      sKeys.put("layout/fragment_qoutes_0", com.hussien.qouty.R.layout.fragment_qoutes);
      sKeys.put("layout/quote_layout_0", com.hussien.qouty.R.layout.quote_layout);
      sKeys.put("layout/row_item_quote_0", com.hussien.qouty.R.layout.row_item_quote);
    }
  }
}
