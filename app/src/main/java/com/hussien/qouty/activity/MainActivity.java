package com.hussien.qouty.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.hussien.qouty.R;
import com.hussien.qouty.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    private  NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        setSupportActionBar(binding.toolbar);

        if (navController != null) {
            AppBarConfiguration appBarConfiguration =
                    new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
        }

//        checkDatabaseRowCount();
//        export_data();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_favorites || id == R.id.menu_setting) {
            NavigationUI.onNavDestinationSelected(item, navController);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //PlayGround Area

//    private void export_data() {
//        File database = getDatabasePath("quotes_database");
//        File outputData = new File(getExternalFilesDir(null), "data.db");
//
//        try {
//            FileChannel src = new FileInputStream(database).getChannel();
//            FileChannel dst = new FileOutputStream(outputData).getChannel();
//            dst.transferFrom(src, 0, src.size());
//            src.close();
//            dst.close();
//            Toast.makeText(this, "Exported!!", Toast.LENGTH_SHORT).show();;
//        }catch (Exception e){
//            Log.e(TAG, "export_data: " + e.toString());
//        }
//    }

//    private void checkDatabaseRowCount() {
//        Log.d(TAG, "checkDatabaseRowCount: ");
//        Runnable runnable = () -> {
//            int count = QuotesRepository.getRepo_instance(getApplication()).getCount();
//            if (count == 0){
//                populateDataBaseFromJsonAssets();
//            }
//        };
//
//        AppExecutor.getInstance().getDiskIo().execute(runnable);
//    }
//
//    private void populateDataBaseFromJsonAssets() {
//        Log.d(TAG, "populateDataBaseFromJsonAssets: ");
//        String folderName = "quotes_data";
//        try {
//            Log.d(TAG, "populateDataBaseFromJsonAssets: TRY");
//            String[] languages = getAssets().list(folderName);
//            for (String lang : languages){
//                Log.d(TAG, "populateDataBaseFromJsonAssets: first for" + lang);
//                //e.x: quotes_data/en
//               String[] pathInLang = getAssets().list(folderName + "/" + lang);
//               for (String tagJsonFile : pathInLang){
//                   Log.d(TAG, "populateDataBaseFromJsonAssets: second for" + tagJsonFile);
//                   //e.x: quotes_data/en/love
//                   //Reading Json And Insert To Database
//                   String jsonContent = getJsonContent(getAssets().open(folderName + "/" + lang + "/" + tagJsonFile));
//                   // remove the .json part
//                   String tag = tagJsonFile.substring(0, tagJsonFile.length() - 5);
//                   List<Quote> quoteList = getQuotesList(jsonContent, tag, lang);
//                   List<Long> longs = QuotesRepository.getRepo_instance(getApplication()).insertQuote(quoteList);
//                   Log.d("TEST", lang + "/" + tagJsonFile + "size = " + longs.size());
//               }
//            }
//
//        } catch (IOException e) {
//            Log.d(TAG, "populateDataBaseFromJsonAssets: " + e.toString());
//        }
//    }
//
//    private List<Quote> getQuotesList(String jsonContent, String tag, String lang) throws IOException {
//        Log.d(TAG, "getQuotesList: ");
//        Moshi moshi = new Moshi.Builder().build();
//        Type type = Types.newParameterizedType(List.class, Quote.class);
//        JsonAdapter<List<Quote>> quoteJsonAdapter = moshi.adapter(type);
//        List<Quote> quoteList = quoteJsonAdapter.fromJson(jsonContent);
//        if (quoteList != null) {
//            for (Quote quote : quoteList){
//                quote.setLang(lang);
//                quote.setTags(tag);
//            }
//        }
//        return quoteList;
//    }
//
//    private String getJsonContent(InputStream inputStream) throws IOException {
//        Log.d(TAG, "getJsonContent: ");
//        //default buffer size
//        int length;
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//
//        while ((length = inputStream.read()) != -1){
//            output.write(length);
//        }
//        inputStream.close();
//        return output.toString(StandardCharsets.UTF_8.name());
//    }
}