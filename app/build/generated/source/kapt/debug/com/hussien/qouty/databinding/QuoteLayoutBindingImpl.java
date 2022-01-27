package com.hussien.qouty.databinding;
import com.hussien.qouty.R;
import com.hussien.qouty.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class QuoteLayoutBindingImpl extends QuoteLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public QuoteLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private QuoteLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.TextView) bindings[1]
            );
        this.quoteAuthor.setTag(null);
        this.quoteLayout.setTag(null);
        this.quoteText.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.quote == variableId) {
            setQuote((com.hussien.qouty.models.Quote) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setQuote(@Nullable com.hussien.qouty.models.Quote Quote) {
        this.mQuote = Quote;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.quote);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        com.hussien.qouty.models.Quote quote = mQuote;
        java.lang.String QuoteText1 = null;
        java.lang.String QuoteAuthor1 = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (quote != null) {
                    // read quote.text
                    QuoteText1 = quote.getText();
                    // read quote.author
                    QuoteAuthor1 = quote.getAuthor();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.quoteAuthor, QuoteAuthor1);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.quoteText, QuoteText1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): quote
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}