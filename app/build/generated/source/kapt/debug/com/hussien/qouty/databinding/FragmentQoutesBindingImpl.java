package com.hussien.qouty.databinding;
import com.hussien.qouty.R;
import com.hussien.qouty.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentQoutesBindingImpl extends FragmentQoutesBinding implements com.hussien.qouty.generated.callback.OnRefreshListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener mCallback4;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentQoutesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private FragmentQoutesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[1]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerView.setTag(null);
        this.swipeRefresh.setTag(null);
        setRootTag(root);
        // listeners
        mCallback4 = new com.hussien.qouty.generated.callback.OnRefreshListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((com.hussien.qouty.ui.quotes.QuotesViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.hussien.qouty.ui.quotes.QuotesViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelIsLoading((kotlinx.coroutines.flow.StateFlow<java.lang.Boolean>) object, fieldId);
            case 1 :
                return onChangeViewModelQuotes((androidx.lifecycle.LiveData<java.util.List<com.hussien.qouty.models.Quote>>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelIsLoading(kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> ViewModelIsLoading, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelQuotes(androidx.lifecycle.LiveData<java.util.List<com.hussien.qouty.models.Quote>> ViewModelQuotes, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoadingGetValue = false;
        kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> viewModelIsLoading = null;
        java.util.List<com.hussien.qouty.models.Quote> viewModelQuotesGetValue = null;
        com.hussien.qouty.ui.quotes.QuotesViewModel viewModel = mViewModel;
        java.lang.Boolean viewModelIsLoadingGetValue = null;
        androidx.lifecycle.LiveData<java.util.List<com.hussien.qouty.models.Quote>> viewModelQuotes = null;

        if ((dirtyFlags & 0xfL) != 0) {


            if ((dirtyFlags & 0xdL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.isLoading
                        viewModelIsLoading = viewModel.isLoading();
                    }
                    androidx.databinding.ViewDataBindingKtx.updateStateFlowRegistration(this, 0, viewModelIsLoading);


                    if (viewModelIsLoading != null) {
                        // read viewModel.isLoading.getValue()
                        viewModelIsLoadingGetValue = viewModelIsLoading.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.isLoading.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoadingGetValue = androidx.databinding.ViewDataBinding.safeUnbox(viewModelIsLoadingGetValue);
            }
            if ((dirtyFlags & 0xeL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.quotes
                        viewModelQuotes = viewModel.getQuotes();
                    }
                    updateLiveDataRegistration(1, viewModelQuotes);


                    if (viewModelQuotes != null) {
                        // read viewModel.quotes.getValue()
                        viewModelQuotesGetValue = viewModelQuotes.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0xeL) != 0) {
            // api target 1

            com.hussien.qouty.ext.BindingAdaptersKt.bindData(this.recyclerView, viewModelQuotesGetValue);
        }
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            this.swipeRefresh.setRefreshing(androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoadingGetValue);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.swipeRefresh.setOnRefreshListener(mCallback4);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnRefresh(int sourceId ) {
        // localize variables for thread safety
        // viewModel
        com.hussien.qouty.ui.quotes.QuotesViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            viewModel.refresh();
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.isLoading
        flag 1 (0x2L): viewModel.quotes
        flag 2 (0x3L): viewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}