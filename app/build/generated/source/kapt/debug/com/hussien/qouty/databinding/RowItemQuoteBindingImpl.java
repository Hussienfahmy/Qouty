package com.hussien.qouty.databinding;
import com.hussien.qouty.R;
import com.hussien.qouty.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class RowItemQuoteBindingImpl extends RowItemQuoteBinding implements com.hussien.qouty.generated.callback.OnClickListener.Listener {

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
    private final androidx.cardview.widget.CardView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public RowItemQuoteBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private RowItemQuoteBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[7]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            );
        this.imageCopy.setTag(null);
        this.imageImageShare.setTag(null);
        this.imageLove.setTag(null);
        this.imageTextShare.setTag(null);
        this.mboundView0 = (androidx.cardview.widget.CardView) bindings[0];
        this.mboundView0.setTag(null);
        this.quoteAuthor.setTag(null);
        this.quoteText.setTag(null);
        this.tagTv.setTag(null);
        setRootTag(root);
        // listeners
        mCallback2 = new com.hussien.qouty.generated.callback.OnClickListener(this, 2);
        mCallback3 = new com.hussien.qouty.generated.callback.OnClickListener(this, 3);
        mCallback1 = new com.hussien.qouty.generated.callback.OnClickListener(this, 1);
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
        if (BR.quote == variableId) {
            setQuote((com.hussien.qouty.models.Quote) variable);
        }
        else if (BR.typeFace == variableId) {
            setTypeFace((android.graphics.Typeface) variable);
        }
        else if (BR.clickListener == variableId) {
            setClickListener((com.hussien.qouty.ui.quotes.OnQuoteActionClickListener) variable);
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
    public void setTypeFace(@Nullable android.graphics.Typeface TypeFace) {
        this.mTypeFace = TypeFace;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.typeFace);
        super.requestRebind();
    }
    public void setClickListener(@Nullable com.hussien.qouty.ui.quotes.OnQuoteActionClickListener ClickListener) {
        this.mClickListener = ClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.clickListener);
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
        java.lang.String quoteTag = null;
        java.lang.String QuoteText1 = null;
        java.lang.String charU201DQuoteText = null;
        boolean quoteLoved = false;
        com.hussien.qouty.models.Quote quote = mQuote;
        int quoteLovedImageLoveAndroidColorRedImageLoveAndroidColorWhite = 0;
        android.graphics.Typeface typeFace = mTypeFace;
        com.hussien.qouty.ui.quotes.OnQuoteActionClickListener clickListener = mClickListener;
        java.lang.String QuoteAuthor1 = null;
        java.lang.String charU201DQuoteTextCharU201C = null;

        if ((dirtyFlags & 0x9L) != 0) {



                if (quote != null) {
                    // read quote.tag
                    quoteTag = quote.getTag();
                    // read quote.text
                    QuoteText1 = quote.getText();
                    // read quote.loved
                    quoteLoved = quote.isLoved();
                    // read quote.author
                    QuoteAuthor1 = quote.getAuthor();
                }
            if((dirtyFlags & 0x9L) != 0) {
                if(quoteLoved) {
                        dirtyFlags |= 0x20L;
                }
                else {
                        dirtyFlags |= 0x10L;
                }
            }


                // read ('\u201D') + (quote.text)
                charU201DQuoteText = ('\u201D') + (QuoteText1);
                // read quote.loved ? @android:color/red : @android:color/white
                quoteLovedImageLoveAndroidColorRedImageLoveAndroidColorWhite = ((quoteLoved) ? (getColorFromResource(imageLove, R.color.red)) : (getColorFromResource(imageLove, R.color.white)));


                // read (('\u201D') + (quote.text)) + ('\u201C')
                charU201DQuoteTextCharU201C = (charU201DQuoteText) + ('\u201C');
        }
        if ((dirtyFlags & 0xaL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.imageCopy.setOnClickListener(mCallback1);
            this.imageImageShare.setOnClickListener(mCallback3);
            this.imageTextShare.setOnClickListener(mCallback2);
        }
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 21
            if(getBuildSdkInt() >= 21) {

                this.imageLove.setImageTintList(androidx.databinding.adapters.Converters.convertColorToColorStateList(quoteLovedImageLoveAndroidColorRedImageLoveAndroidColorWhite));
            }
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.quoteAuthor, QuoteAuthor1);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.quoteText, charU201DQuoteTextCharU201C);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tagTv, quoteTag);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            this.quoteAuthor.setTypeface(typeFace);
            this.quoteText.setTypeface(typeFace);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // quote
                com.hussien.qouty.models.Quote quote = mQuote;
                // clickListener
                com.hussien.qouty.ui.quotes.OnQuoteActionClickListener clickListener = mClickListener;
                // quote.id
                long quoteId = 0;
                // quote != null
                boolean quoteJavaLangObjectNull = false;
                // clickListener != null
                boolean clickListenerJavaLangObjectNull = false;



                clickListenerJavaLangObjectNull = (clickListener) != (null);
                if (clickListenerJavaLangObjectNull) {





                    quoteJavaLangObjectNull = (quote) != (null);
                    if (quoteJavaLangObjectNull) {


                        quoteId = quote.getId();

                        clickListener.onClick(com.hussien.qouty.ui.quotes.OnQuoteActionClickListener.QuoteAction.SHARE_TXT, quoteId);
                    }
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // quote
                com.hussien.qouty.models.Quote quote = mQuote;
                // clickListener
                com.hussien.qouty.ui.quotes.OnQuoteActionClickListener clickListener = mClickListener;
                // quote.id
                long quoteId = 0;
                // quote != null
                boolean quoteJavaLangObjectNull = false;
                // clickListener != null
                boolean clickListenerJavaLangObjectNull = false;



                clickListenerJavaLangObjectNull = (clickListener) != (null);
                if (clickListenerJavaLangObjectNull) {





                    quoteJavaLangObjectNull = (quote) != (null);
                    if (quoteJavaLangObjectNull) {


                        quoteId = quote.getId();

                        clickListener.onClick(com.hussien.qouty.ui.quotes.OnQuoteActionClickListener.QuoteAction.SHARE_IMG, quoteId);
                    }
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // quote
                com.hussien.qouty.models.Quote quote = mQuote;
                // clickListener
                com.hussien.qouty.ui.quotes.OnQuoteActionClickListener clickListener = mClickListener;
                // quote.id
                long quoteId = 0;
                // quote != null
                boolean quoteJavaLangObjectNull = false;
                // clickListener != null
                boolean clickListenerJavaLangObjectNull = false;



                clickListenerJavaLangObjectNull = (clickListener) != (null);
                if (clickListenerJavaLangObjectNull) {





                    quoteJavaLangObjectNull = (quote) != (null);
                    if (quoteJavaLangObjectNull) {


                        quoteId = quote.getId();

                        clickListener.onClick(com.hussien.qouty.ui.quotes.OnQuoteActionClickListener.QuoteAction.COPY_TXT, quoteId);
                    }
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): quote
        flag 1 (0x2L): typeFace
        flag 2 (0x3L): clickListener
        flag 3 (0x4L): null
        flag 4 (0x5L): quote.loved ? @android:color/red : @android:color/white
        flag 5 (0x6L): quote.loved ? @android:color/red : @android:color/white
    flag mapping end*/
    //end
}